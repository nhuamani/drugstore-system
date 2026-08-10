package com.nhuamani.drugstore_system.configs;



import com.nhuamani.drugstore_system.models.Permission;
import com.nhuamani.drugstore_system.models.Role;
import com.nhuamani.drugstore_system.models.User;
import com.nhuamani.drugstore_system.repositories.PermissionRepository;
import com.nhuamani.drugstore_system.repositories.RoleRepository;
import com.nhuamani.drugstore_system.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // ==========================================
            // PERMISSIONS
            // ==========================================

            Permission userRead = createPermission(
                    permissionRepository,
                    "USER_READ",
                    "Ver usuarios"
            );

            Permission userCreate = createPermission(
                    permissionRepository,
                    "USER_CREATE",
                    "Crear usuarios"
            );

            Permission userUpdate = createPermission(
                    permissionRepository,
                    "USER_UPDATE",
                    "Actualizar usuarios"
            );

            Permission userDelete = createPermission(
                    permissionRepository,
                    "USER_DELETE",
                    "Eliminar usuarios"
            );


            // ==========================================
            // ROLES
            // ==========================================

            Role adminRole = createRole(
                    roleRepository,
                    "ROLE_ADMIN",
                    "Administrador del sistema"
            );

            Role managerRole = createRole(
                    roleRepository,
                    "ROLE_MANAGER",
                    "Administrador de operaciones"
            );

            Role employeeRole = createRole(
                    roleRepository,
                    "ROLE_EMPLOYEE",
                    "Empleado"
            );


            // ==========================================
            // ADMIN PERMISSIONS
            // ==========================================

            adminRole.getPermissions().add(userRead);
            adminRole.getPermissions().add(userCreate);
            adminRole.getPermissions().add(userUpdate);
            adminRole.getPermissions().add(userDelete);

            roleRepository.save(adminRole);


            // ==========================================
            // MANAGER PERMISSIONS
            // ==========================================

            managerRole.getPermissions().add(userRead);
            managerRole.getPermissions().add(userCreate);
            managerRole.getPermissions().add(userUpdate);

            roleRepository.save(managerRole);


            // ==========================================
            // EMPLOYEE PERMISSIONS
            // ==========================================

            employeeRole.getPermissions().add(userRead);

            roleRepository.save(employeeRole);


            // ==========================================
            // USERS
            // ==========================================

            createUser(
                    userRepository,
                    passwordEncoder,
                    "admin",
                    "admin@drugstore.com",
                    "admin123",
                    adminRole
            );

            createUser(
                    userRepository,
                    passwordEncoder,
                    "manager",
                    "manager@drugstore.com",
                    "manager123",
                    managerRole
            );

            createUser(
                    userRepository,
                    passwordEncoder,
                    "employee",
                    "employee@drugstore.com",
                    "employee123",
                    employeeRole
            );
        };
    }


    // ==========================================
    // CREATE ROLE
    // ==========================================

    private Role createRole(
            RoleRepository roleRepository,
            String name,
            String description) {

        return roleRepository
                .findByName(name)
                .orElseGet(() ->
                        roleRepository.save(
                                Role.builder()
                                        .name(name)
                                        .description(description)
                                        .build()
                        )
                );
    }


    // ==========================================
    // CREATE PERMISSION
    // ==========================================

    private Permission createPermission(
            PermissionRepository permissionRepository,
            String name,
            String description) {

        return permissionRepository
                .findByName(name)
                .orElseGet(() ->
                        permissionRepository.save(
                                Permission.builder()
                                        .name(name)
                                        .description(description)
                                        .build()
                        )
                );
    }


    // ==========================================
    // CREATE USER
    // ==========================================

    private void createUser(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            String username,
            String email,
            String password,
            Role role) {

        if (!userRepository.existsByUsername(username)) {

            User user = User.builder()
                    .username(username)
                    .email(email)
                    .password(
                            passwordEncoder.encode(password)
                    )
                    .enabled(true)
                    .build();

            user.getRoles().add(role);

            userRepository.save(user);
        }
    }
}
