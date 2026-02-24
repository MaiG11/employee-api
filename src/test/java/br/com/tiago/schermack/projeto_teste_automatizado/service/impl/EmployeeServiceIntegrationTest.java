    package br.com.tiago.schermack.projeto_teste_automatizado.service.impl;
    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertNull;
    import org.junit.jupiter.api.DisplayName;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;
    import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeRequestDTO;
    import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeResponseDTO;
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
    @DisplayName("Esse teste valida a exclusão do funcionário quando o id existir.")
    public void deveExcluirFuncionarioQuandoIdExistir(){

        // Arrange
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("Maira", "maira@email.com");
        EmployeeResponseDTO responseDTO = employeeService.create(requestDTO);
        // Act
        employeeService.delete(responseDTO.id());
        // Assert
        Employee employeeDeleted = employeeRepository.findById(responseDTO.id()).orElse(null);
        assertNull(employeeDeleted);
        
    }  
    @Test 
    @DisplayName("Esse teste vai ser responsável por validar a criação do funcionário, verificando se o método de salvamento do repositório foi chamado e se o funcionário foi criado corretamente.")
    public void deveCriarFuncionarioQuandoDadosForemValidos(){

        //Arrange 
        EmployeeRequestDTO employeeCreate = new EmployeeRequestDTO("Maira", "maira@email.com");
        Employee expectedEmployee = new Employee(employeeCreate.firstName(), employeeCreate.email());
        expectedEmployee.setId(1l);
        // Act
        EmployeeResponseDTO response = employeeService.create(employeeCreate);
        // Assert
        assertEquals("Maira", response.firstName());
        assertEquals("maira@email.com", response.email());
        assertEquals(1L, response.id());

    }
    @Test
    @DisplayName("Esse teste vai ser responsável por validar a atualização do funcionário, verificando se o método de atualização do repositório foi chamado e se o funcionário foi atualizado corretamente.")
    public void deveAtualizarFuncionarioQuandoDadosForemValidos(){

        // Arrange
        Employee existingEmployee = new Employee("Maira", "maira@email.com");
        Employee persistedEmployee = employeeRepository.save(existingEmployee);
        EmployeeRequestDTO updateRequest = new EmployeeRequestDTO("Maira Geraldo", "maira.geraldo@email.com");
        // Act
        employeeService.update(persistedEmployee.getId(), updateRequest);
        // Assert
        assertEquals(updateRequest.firstName(), persistedEmployee.getFirstName());
        assertEquals(updateRequest.email(), persistedEmployee.getEmail());
    }
    }