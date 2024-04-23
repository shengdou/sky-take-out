package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import io.swagger.annotations.ApiOperation;

import java.lang.reflect.Method;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * @description: 新增员工
     * @param: 员工DTO
     * @return: null
     * @author Moyu
     * @date: 2024/4/21 23:08
     */
    @ApiOperation("新增员工")
    void save(EmployeeDTO employeeDTO);

    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void startOrStop(Integer status, long id);
/**
 * @description: 根据id查询员工信息
 * @param:
 * @return:
 * @author Moyu
 * @date: 2024/4/23 14:51
 */
    Employee getById(long id);

    /**
     * @description: 更新员工资料
     *
     * @param:
     * @return:
     * @author Moyu
     * @date: 2024/4/23 15:04
     */
    void updateEmployee(EmployeeDTO employeeDTO);
}
