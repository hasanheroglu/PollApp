package com.softeng.votit.UserServiceImplTests;

import com.softeng.votit.model.dao.user.RoleEntity;
import com.softeng.votit.model.dao.user.UserEntity;
import com.softeng.votit.model.repo.user.RoleRepository;
import com.softeng.votit.model.repo.user.UserRepository;
import com.softeng.votit.service.impl.user.UserServiceImpl;
import com.softeng.votit.service.user.UserService;
import com.softeng.votit.util.Operation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplRoleManipulationTest {
    @TestConfiguration
    static class UserServiceImplRoleManipulationTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    public UserRepository userRepository;

    @MockBean
    public RoleRepository roleRepository;


    @Test
    public void whenValidUserAndRole_thenRoleShouldBeAdded(){
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("hasanheroglu@gmail.com");

        RoleEntity role_1 = new RoleEntity();
        role_1.setRole("System Admin");

        RoleEntity role_2 = new RoleEntity();
        role_2.setRole("Company Admin");

        user.getRoles().add(role_1);

        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(Optional.ofNullable(user));

        Mockito.when(roleRepository.findByRole(role_1.getRole()))
                .thenReturn(role_1);

        Mockito.when(roleRepository.findByRole(role_2.getRole()))
                .thenReturn(role_2);

        Long id = 1L;
        String role = "Company Admin";

        Operation operation = userService.addRole(id, role);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenInvalidUser_thenRoleShouldNotBeAdded(){
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("hasanheroglu@gmail.com");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole("System Admin");

        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(Optional.ofNullable(user));

        Mockito.when(roleRepository.findByRole(roleEntity.getRole()))
                .thenReturn(roleEntity);

        Long id = 2L;
        String role = "System Admin";

        Operation operation = userService.addRole(id, role);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenInvalidRole_thenRoleShouldNotBeAdded(){
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("hasanheroglu@gmail.com");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole("System Admin");

        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(Optional.ofNullable(user));

        Mockito.when(roleRepository.findByRole(roleEntity.getRole()))
                .thenReturn(roleEntity);

        Long id = 1L;
        String role = "Company Admin";

        Operation operation = userService.addRole(id, role);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenRoleExists_thenRoleShouldNotBeAdded(){
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("hasanheroglu@gmail.com");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole("System Admin");

        user.getRoles().add(roleEntity);

        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(Optional.ofNullable(user));

        Mockito.when(roleRepository.findByRole(roleEntity.getRole()))
                .thenReturn(roleEntity);

        Long id = 1L;
        String role = "System Admin";

        Operation operation = userService.addRole(id, role);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenValidUserAndRole_thenRoleShouldBeRemoved(){
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("hasanheroglu@gmail.com");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole("System Admin");

        user.getRoles().add(roleEntity);

        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(Optional.ofNullable(user));

        Mockito.when(roleRepository.findByRole(roleEntity.getRole()))
                .thenReturn(roleEntity);

        Long id = 1L;
        String role = "System Admin";

        Operation operation = userService.removeRole(id, role);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenInvalidUser_thenRoleShouldNotBeRemoved(){
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("hasanheroglu@gmail.com");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole("System Admin");

        user.getRoles().add(roleEntity);

        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(Optional.ofNullable(user));

        Mockito.when(roleRepository.findByRole(roleEntity.getRole()))
                .thenReturn(roleEntity);

        Long id = 2L;
        String role = "System Admin";

        Operation operation = userService.removeRole(id, role);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenInvalidRole_thenRoleShouldNotBeRemoved(){
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("hasanheroglu@gmail.com");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole("System Admin");

        user.getRoles().add(roleEntity);

        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(Optional.ofNullable(user));

        Mockito.when(roleRepository.findByRole(roleEntity.getRole()))
                .thenReturn(roleEntity);

        Long id = 1L;
        String role = "Company Admin";

        Operation operation = userService.removeRole(id, role);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenRoleDoesNotExist_thenRoleShouldNotBeRemoved(){
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("hasanheroglu@gmail.com");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole("System Admin");

        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(Optional.ofNullable(user));

        Mockito.when(roleRepository.findByRole(roleEntity.getRole()))
                .thenReturn(roleEntity);

        Long id = 1L;
        String role = "System Admin";

        Operation operation = userService.removeRole(id, role);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }



}
