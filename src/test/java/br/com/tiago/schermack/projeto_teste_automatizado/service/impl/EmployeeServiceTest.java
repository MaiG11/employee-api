    package br.com.tiago.schermack.projeto_teste_automatizado.service.impl;

    import java.util.List;
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

import java.util.Arrays;

    @SpringBootTest
    class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Esse teste vai ser responsável por validar a criação do funcionário, verificando se o método de salvar do repositório foi chamado e se os dados retornados estão corretos.")
    public void deveCriarEmprgadoERetornarSucesso() {

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
      
      @Test
      @DisplayName("Esse teste vai ser responsável por validar a exclusão do funcionário, verificando se o método de busca do repositório foi chamado e se o método de exclusão do repositório foi chamado quando o id existir.")
      public void deveExcluirFuncionarioQuandoIdExistir(){

        //Arrange 
        Employee employeeSaved = new Employee("Maira", "maira@email.com");
        employeeSaved.setId(1L);

        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.of(employeeSaved));

        //Act
        employeeService.delete(1L);

        //Assert
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).delete(employeeSaved);
    }
    
    @Test
    @DisplayName("Esse teste vai ser responsável por validar a exclusão do funcionário, verificando se o método de busca do repositório foi chamado e se a exception foi lançada quando o id não existir.")
    public void deveLancarExceptionQuandoIdNaoExistirNaExclusao(){

        //Arrange 
        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        //Act + Assert
        assertThrows(EntityNotFoundException.class, () -> employeeService.delete(1L));  

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(0)).delete(any(Employee.class));

    }
    
      @Test
      @DisplayName("Esse teste vai ser responsável por validar a busca do funcionário por id, verificando se o método de busca do repositório foi chamado e se os dados retornados estão corretos quando o id existir.")
      public void deveBuscarFuncionarioPorIdQuandoIdExistir(){

        //Arrange 
        Employee employeeSaved = new Employee("Maira", "maira@email.com");
        employeeSaved.setId(1L);

        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.of(employeeSaved));

        //Act
        EmployeeResponseDTO responseDTO = employeeService.findById(1L);

        //Assert
        assertEquals(1L, responseDTO.id());
        assertEquals("Maira", responseDTO.firstName());
        assertEquals("maira@email.com", responseDTO.email());

        verify(employeeRepository, times(1)).findById(1L);  
      }
      @Test
      @DisplayName("Esse teste vai ser responsável por validar a busca do funcionário por id, verificando se o método de busca do repositório foi chamado e se a exception foi lançada quando o id não existir.")
      public void deveLancarExceptionQuandoIdNaoExistirNaBuscaPorId(){

        //Arrange 
        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        //Act + Assert
        assertThrows(EntityNotFoundException.class, () -> employeeService.findById(1L));    
      }
      @Test
      @DisplayName("Esse teste vai ser responsável por validar a busca de todos os funcionários, verificando se o método de busca do repositório foi chamado e se os dados retornados estão corretos.")     
        public void deveBuscarTodosFuncionariosERetornarLista(){
    
            //Arrange 
            Employee employee1 = new Employee("Maira", "maira@email.com");
            employee1.setId(1L);
            Employee employee2 = new Employee("Tiago", "tiago@email.com");
            employee2.setId(2L);

            when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

            //Act
            List<EmployeeResponseDTO> responseList = employeeService.findAll();

            //Assert
            assertEquals(2, responseList.size());
            assertEquals(1L, responseList.get(0).id());
            assertEquals("Maira", responseList.get(0).firstName());
            assertEquals("maira@email.com", responseList.get(0).email());
            assertEquals(2L, responseList.get(1).id());
            assertEquals("Tiago", responseList.get(1).firstName());
            assertEquals("tiago@email.com", responseList.get(1).email());

            verify(employeeRepository, times(1)).findAll();  
        }
        @Test
        @DisplayName("Esse teste vai ser responsável por validar a busca de todos os funcionários, verificando se o método de busca do repositório foi chamado e se a lista retornada está vazia quando não houver funcionários cadastrados.")
        public void deveBuscarTodosFuncionariosERetornarListaVaziaQuandoNaoHouverFuncionariosCadastrados(){

            //Arrange 
            when(employeeRepository.findAll()).thenReturn(Arrays.asList());

            //Act
            List<EmployeeResponseDTO> responseList = employeeService.findAll();

            //Assert
            assertTrue(responseList.isEmpty());

            verify(employeeRepository, times(1)).findAll();  
        }
    }