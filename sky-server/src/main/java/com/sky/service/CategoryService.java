package com.sky.service;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    /**
     * 菜品查询
     * @param
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    List<Category> list(Integer type);
}
