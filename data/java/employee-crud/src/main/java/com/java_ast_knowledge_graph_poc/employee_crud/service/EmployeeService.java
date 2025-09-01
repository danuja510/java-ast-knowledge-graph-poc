package com.java_ast_knowledge_graph_poc.employee_crud.service;

import java.util.List;

import com.java_ast_knowledge_graph_poc.employee_crud.dto.EmployeeDTO;

public interface EmployeeService {
    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO getEmployeeById(Long id);
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO);
    void deleteEmployee(Long id);
}
