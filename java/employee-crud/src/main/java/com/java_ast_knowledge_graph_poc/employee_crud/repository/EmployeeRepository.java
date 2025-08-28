package com.java_ast_knowledge_graph_poc.employee_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java_ast_knowledge_graph_poc.employee_crud.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Basic CRUD operations are automatically provided by JpaRepository
}
