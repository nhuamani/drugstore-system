package com.nhuamani.drugstore_system.services;

import com.nhuamani.drugstore_system.dto.UserCreateDTO;
import com.nhuamani.drugstore_system.dto.UserUpdateDTO;
import com.nhuamani.drugstore_system.models.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User create(UserCreateDTO dto);

    User update(Long id, UserUpdateDTO dto);

    User changeEnabledStatus(Long id, boolean enabled);
}
