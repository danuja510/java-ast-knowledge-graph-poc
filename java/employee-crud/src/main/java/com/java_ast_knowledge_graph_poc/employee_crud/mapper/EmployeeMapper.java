package com.java_ast_knowledge_graph_poc.employee_crud.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.java_ast_knowledge_graph_poc.employee_crud.dto.EmployeeDTO;
import com.java_ast_knowledge_graph_poc.employee_crud.model.Employee;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EmployeeMapper {
    
    EmployeeDTO toDto(Employee employee);
    
    Employee toEntity(EmployeeDTO dto);
    
    // For updating existing entities
    default Employee updateEntityFromDto(EmployeeDTO dto, Employee employee) {
        if (dto == null) {
            return null;
        }

        if (employee == null) {
            employee = new Employee();
        }
        
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setSalary(dto.getSalary());
        
        return employee;
    }
}
