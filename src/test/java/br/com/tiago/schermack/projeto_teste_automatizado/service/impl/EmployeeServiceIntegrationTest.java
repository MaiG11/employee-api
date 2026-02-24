package br.com.tiago.schermack.projeto_teste_automatizado.service.impl;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeRequestDTO;
import br.com.tiago.schermack.projeto_teste_automatizado.entity.Employee;
import br.com.tiago.schermack.projeto_teste_automatizado.repository.EmployeeRepository;
import jakarta.transaction.Transactional;

    @SpringBootTest
    @Transactional
public class EmployeeServiceIntegrationTest {
    
        @Autowired
        private EmployeeService employeeService;

        @Autowired
        private EmployeeRepository employeeRepository;

@Test
@DisplayName("Esse teste vai ser responsável por validar a exclusão do funcionário, verificando se o método de busca do repositório foi chamado e se a exception foi lançada quando o id não existir.")
public void deveExcluirFuncionarioQuandoIdExistir(){

    //Arrange

    Employee employeeSaved = new Employee("Maira", "maira@email.com ");
    Employee employee = employeeRepository.save(employeeSaved);
    
    //Act
    employeeService.delete(employee.getId());

    //Assert
    Employee employeeDeleted = employeeRepository.findById(employee.getId()).orElse(null);

    assertNull(employeeDeleted);

}
}