    package br.com.tiago.schermack.projeto_teste_automatizado.service.impl;

    import org.junit.jupiter.api.DisplayName;
    import org.junit.jupiter.api.Test;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.springframework.boot.test.context.SpringBootTest;

    import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeRequestDTO;
    import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeResponseDTO;
    import br.com.tiago.schermack.projeto_teste_automatizado.entity.Employee;
    import br.com.tiago.schermack.projeto_teste_automatizado.repository.EmployeeRepository;
    import jakarta.persistence.EntityNotFoundException;

    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.ArgumentMatchers.any;
    import static org.mockito.Mockito.times;
    import static org.mockito.Mockito.verify;
    import static org.mockito.Mockito.when;

    @SpringBootTest
    class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Esse teste vai ser responsável por validar a criação do funcionário, verificando se o método de salvar do repositório foi chamado e se os dados retornados estão corretos.")
    public void deveCriarEmprgadoERetornarResponseDTO() {

        //Arange
        EmployeeRequestDTO  requestDTO = new EmployeeRequestDTO("Maira", "maira@email.com");
        Employee employeeSaved = new Employee(requestDTO.firstName(), requestDTO.email());
        employeeSaved.setId(1l);

        when(employeeRepository.save(any(Employee.class))).thenReturn(employeeSaved);

        //Act
        EmployeeResponseDTO responseDTO = employeeService.create(requestDTO);

        //Assert
        assertEquals(1L, responseDTO.id());
        assertEquals("Maira"        ,responseDTO.firstName());
        assertEquals("maira@email.com", responseDTO.email());

        verify(employeeRepository, times(1)).save(any(Employee.class));
    }
    @Test
    @DisplayName("Esse teste vai ser responsável por validar a atualização do funcionário, verificando se o método de busca do repositório foi chamado e se os dados retornados estão corretos.")
    public void deveAtualizarFuncionarioQuandoIdExistir(){

        //Arrange 
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("Maira", "maira@email.com");
        Employee employeeSaved = new Employee(requestDTO.firstName(), requestDTO.email());
        employeeSaved.setId(1l);

        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.of(employeeSaved));

    //Act
    EmployeeResponseDTO responseDTO =  employeeService.update(1L, requestDTO);

    //Assert
        assertEquals(1L, responseDTO.id());
        assertEquals("Maira"        ,responseDTO.firstName());
        assertEquals("maira@email.com", responseDTO.email());

        verify(employeeRepository, times(1)).findById(1L);  
    }
    @Test
    @DisplayName("Esse teste vai ser responsável por validar a atualização do funcionário, verificando se o método de busca do repositório foi chamado e se a exception foi lançada quando o id não existir.")
    public void deveLancarExceptionQuandoIdNaoExistirNaAtualizacao(){
        
        //Arrange 
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("Maira", "maira@email.com");

        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        //Act + Assert
        assertThrows(EntityNotFoundException.class, () -> employeeService.update(1L, requestDTO));  

    }
      }