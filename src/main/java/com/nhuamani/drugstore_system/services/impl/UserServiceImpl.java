package com.nhuamani.drugstore_system.services.impl;

import com.nhuamani.drugstore_system.dto.UserCreateDTO;
import com.nhuamani.drugstore_system.dto.UserUpdateDTO;
import com.nhuamani.drugstore_system.models.Role;
import com.nhuamani.drugstore_system.models.User;
import com.nhuamani.drugstore_system.repositories.RoleRepository;
import com.nhuamani.drugstore_system.repositories.UserRepository;
import com.nhuamani.drugstore_system.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAllByOrderByLastNameAscFirstNameAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Usuario no encontrado: " + id
                        ));
    }

    @Override
    public User create(UserCreateDTO dto) {
        validateUniqueFields(dto.username(), dto.email(), null);

        Role role = findRole(dto.roleId());

        User user = User.builder()
                .username(dto.username().trim())
                .firstName(dto.firstName().trim())
                .lastName(dto.lastName().trim())
                .email(dto.email().trim().toLowerCase())
                .password(passwordEncoder.encode(dto.password()))
                .enabled(true)
                .roles(new java.util.HashSet<>(List.of(role)))
                .build();

        return userRepository.save(user);
    }

    @Override
    public User update(Long id, UserUpdateDTO dto) {
        User user = findById(id);

        validateUniqueFields(dto.username(), dto.email(), id);

        Role role = findRole(dto.roleId());

        user.setUsername(dto.username().trim());
        user.setFirstName(dto.firstName().trim());
        user.setLastName(dto.lastName().trim());
        user.setEmail(dto.email().trim().toLowerCase());
        user.setRoles(new java.util.HashSet<>(List.of(role)));

        return userRepository.save(user);
    }

    @Override
    public User changeEnabledStatus(Long id, boolean enabled) {
        User user = findById(id);
        user.setEnabled(enabled);
        return userRepository.save(user);
    }

    private Role findRole(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Rol no encontrado: " + roleId
                        ));
    }

    private void validateUniqueFields(
            String username,
            String email,
            Long currentUserId
    ) {
        boolean usernameExists = currentUserId == null
                ? userRepository.existsByUsername(username.trim())
                : userRepository.existsByUsernameAndIdNot(
                username.trim(), currentUserId
        );

        if (usernameExists) {
            throw new IllegalArgumentException(
                    "El nombre de usuario ya está registrado"
            );
        }

        String normalizedEmail = email.trim().toLowerCase();

        boolean emailExists = currentUserId == null
                ? userRepository.existsByEmail(normalizedEmail)
                : userRepository.existsByEmailAndIdNot(
                normalizedEmail, currentUserId
        );

        if (emailExists) {
            throw new IllegalArgumentException(
                    "El correo electrónico ya está registrado"
            );
        }
    }
}
