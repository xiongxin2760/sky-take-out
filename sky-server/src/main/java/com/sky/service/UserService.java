package com.sky.service;

import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.Employee;
import com.sky.entity.User;

public interface UserService {
    User login(UserLoginDTO userLoginDTO);
}
