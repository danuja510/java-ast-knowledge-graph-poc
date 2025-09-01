package com.java_ast_knowledge_graph_poc.employee_crud.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java_ast_knowledge_graph_poc.employee_crud.dto.EmployeeDTO;
import com.java_ast_knowledge_graph_poc.employee_crud.service.EmployeeService;

class EmployeeControllerTest {
    private static final String API_EMPLOYEES = "/api/employees";
    private static final String API_EMPLOYEES_ID = "/api/employees/1";
    private static final String PATH_ID = "$.id";
    private static final String PATH_FIRST_NAME = "$.firstName";
    private static final String PATH_LAST_NAME = "$.lastName";
    private static final String PATH_ARRAY_ID = "$[0].id";
    private static final String PATH_ARRAY_FIRST_NAME = "$[0].firstName";
    private static final String PATH_ARRAY_LAST_NAME = "$[0].lastName";
    private static final String TEST_EMAIL = "john.doe@example.com";
    private static final String TEST_FIRST_NAME = "John";
    private static final String TEST_LAST_NAME = "Doe";
    private static final String TEST_DEPARTMENT = "IT";
    private static final Double TEST_SALARY = 75000.0;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private EmployeeService employeeService;
    private EmployeeDTO testEmployee;
    private List<EmployeeDTO> testEmployeeList;

    @BeforeEach
    void setUp() {
        employeeService = mock(EmployeeService.class);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
            .standaloneSetup(new EmployeeController(employeeService))
            .build();
        testEmployee = createTestEmployee();
        testEmployeeList = Arrays.asList(testEmployee);
    }

    private EmployeeDTO createTestEmployee() {
        EmployeeDTO employee = new EmployeeDTO();
        employee.setId(1L);
        employee.setFirstName(TEST_FIRST_NAME);
        employee.setLastName(TEST_LAST_NAME);
        employee.setEmail(TEST_EMAIL);
        employee.setDepartment(TEST_DEPARTMENT);
        employee.setSalary(TEST_SALARY);
        return employee;
    }

    @Test
    void testCreateEmployee() throws Exception {
        when(employeeService.saveEmployee(any(EmployeeDTO.class))).thenReturn(testEmployee);

        mockMvc.perform(post(API_EMPLOYEES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(PATH_ID).value(testEmployee.getId()))
                .andExpect(jsonPath(PATH_FIRST_NAME).value(testEmployee.getFirstName()))
                .andExpect(jsonPath(PATH_LAST_NAME).value(testEmployee.getLastName()));
    }

    @Test
    void testGetEmployeeById() throws Exception {
        when(employeeService.getEmployeeById(1L)).thenReturn(testEmployee);

        mockMvc.perform(get(API_EMPLOYEES_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(PATH_ID).value(testEmployee.getId()))
                .andExpect(jsonPath(PATH_FIRST_NAME).value(testEmployee.getFirstName()))
                .andExpect(jsonPath(PATH_LAST_NAME).value(testEmployee.getLastName()));
    }

    @Test
    void testGetAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(testEmployeeList);

        mockMvc.perform(get(API_EMPLOYEES))
                .andExpect(status().isOk())
                .andExpect(jsonPath(PATH_ARRAY_ID).value(testEmployee.getId()))
                .andExpect(jsonPath(PATH_ARRAY_FIRST_NAME).value(testEmployee.getFirstName()))
                .andExpect(jsonPath(PATH_ARRAY_LAST_NAME).value(testEmployee.getLastName()));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        when(employeeService.updateEmployee(any(EmployeeDTO.class))).thenReturn(testEmployee);

        mockMvc.perform(put(API_EMPLOYEES_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(PATH_ID).value(testEmployee.getId()))
                .andExpect(jsonPath(PATH_FIRST_NAME).value(testEmployee.getFirstName()))
                .andExpect(jsonPath(PATH_LAST_NAME).value(testEmployee.getLastName()));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete(API_EMPLOYEES_ID))
                .andExpect(status().isNoContent());
    }
}
