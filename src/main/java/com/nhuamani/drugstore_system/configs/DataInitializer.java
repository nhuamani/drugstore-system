package com.nhuamani.drugstore_system.config;


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

            // =========================
            // PERMISSIONS
            // =========================

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


            // =========================
            // ROLES
            // =========================

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


            // =========================
            // ADMIN PERMISSIONS
            // =========================

            adminRole.getPermissions().add(userRead);
            adminRole.getPermissions().add(userCreate);
            adminRole.getPermissions().add(userUpdate);
            adminRole.getPermissions().add(userDelete);

            roleRepository.save(adminRole);


            // =========================
            // MANAGER PERMISSIONS
            // =========================

            managerRole.getPermissions().add(userRead);
            managerRole.getPermissions().add(userCreate);
            managerRole.getPermissions().add(userUpdate);

            roleRepository.save(managerRole);


            // =========================
            // EMPLOYEE PERMISSIONS
            // =========================

            employeeRole.getPermissions().add(userRead);

            roleRepository.save(employeeRole);


            // =========================
            // ADMIN USER
            // =========================

            if (!userRepository.existsByUsername("admin")) {

                User admin = User.builder()
                        .username("admin")
                        .email("admin@drugstore.com")
                        .password(
                                passwordEncoder.encode("admin123")
                        )
                        .enabled(true)
                        .build();

                admin.getRoles().add(adminRole);

                userRepository.save(admin);
            }
        };
    }


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
}