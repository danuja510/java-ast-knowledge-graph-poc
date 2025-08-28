package com.java_ast_knowledge_graph_poc.employee_crud.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.java_ast_knowledge_graph_poc.employee_crud.dto.EmployeeDTO;
import com.java_ast_knowledge_graph_poc.employee_crud.mapper.EmployeeMapper;
import com.java_ast_knowledge_graph_poc.employee_crud.model.Employee;
import com.java_ast_knowledge_graph_poc.employee_crud.repository.EmployeeRepository;
import com.java_ast_knowledge_graph_poc.employee_crud.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        log.debug("Saving employee: {}", employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDTO savedDTO = employeeMapper.toDto(savedEmployee);
        log.debug("Saved employee with ID: {}", savedDTO.getId());
        return savedDTO;
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        log.debug("Fetching employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with ID: {}", id);
                    return new RuntimeException("Employee not found with id: " + id);
                });
        return employeeMapper.toDto(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.debug("Fetching all employees");
        List<EmployeeDTO> employees = employeeRepository.findAll()
            .stream()
            .map(employeeMapper::toDto)
            .collect(Collectors.toList());
        log.debug("Found {} employees", employees.size());
        return employees;
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        log.debug("Updating employee with ID: {}", employeeDTO.getId());
        
        // Check if employee exists
        Employee existingEmployee = employeeRepository.findById(employeeDTO.getId())
                .orElseThrow(() -> {
                    log.error("Employee not found with ID: {}", employeeDTO.getId());
                    return new RuntimeException("Employee not found with id: " + employeeDTO.getId());
                });
        
        // Update the existing employee
        Employee employeeToUpdate = employeeMapper.toEntity(employeeDTO);
        employeeToUpdate.setId(existingEmployee.getId()); // Ensure ID doesn't change
        
        Employee updatedEmployee = employeeRepository.save(employeeToUpdate);
        EmployeeDTO updatedDTO = employeeMapper.toDto(updatedEmployee);
        log.debug("Updated employee: {}", updatedDTO);
        return updatedDTO;
    }

    @Override
    public void deleteEmployee(Long id) {
        log.debug("Deleting employee with ID: {}", id);
        // Check if employee exists
        getEmployeeById(id);
        employeeRepository.deleteById(id);
        log.debug("Deleted employee with ID: {}", id);
    }
}
