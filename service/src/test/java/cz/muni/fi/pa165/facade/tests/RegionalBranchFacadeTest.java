/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.facade.tests;

import cz.muni.fi.pa165.dto.RegionalBranchDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.entity.RegionalBranch;
import cz.muni.fi.pa165.facade.RegionalBranchFacade;
import cz.muni.fi.pa165.service.RegionalBranchService;
import cz.muni.fi.pa165.tests.support.TestObjectFactory;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

/**
 *
 * @author Tomas Pavuk
 */
public class RegionalBranchFacadeTest extends BaseFacadeTest {
    @Mock
    private RegionalBranchService branchService;

    @Autowired
    @InjectMocks
    private RegionalBranchFacade branchFacade;

    private RegionalBranch branch1;
    private RegionalBranch branch2;

    private RegionalBranchDTO branchDTO;
    
    private final TestObjectFactory factory = new TestObjectFactory();

    private String name = "testName";

    @BeforeMethod()
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        branchFacade = (RegionalBranchFacade) unwrapProxy(branchFacade);
        ReflectionTestUtils.setField(branchFacade, "branchService", branchService);
        ReflectionTestUtils.setField(branchFacade, "beanMappingService", beanMappingService);

        branch1 = factory.createRegionalBranch(name);
        branch2 = factory.createRegionalBranch(name + "2");

        branchDTO = new RegionalBranchDTO();
        branchDTO.setName(name);
    }
    
    @Test
    public void testFindAllBranchesTest(){
        when(branchService.findAll()).thenReturn(Arrays.asList(branch1, branch2));

        List<RegionalBranchDTO> branches = branchFacade.findAll();

        assertThat(branch1.getName()).isEqualTo(branches.get(0).getName());
        verify(branchService).findAll();
        verify(beanMappingService).mapTo(branch1, UserDTO.class);
        verify(beanMappingService).mapTo(branch2, UserDTO.class);
    }
    
    @Test
    public void testCreateBranch(){
        when(beanMappingService.mapTo(branchDTO, RegionalBranch.class)).thenReturn(branch1);

        branchFacade.createRegionalBranch(branchDTO);

        verify(branchService).create(branch1);
        verify(beanMappingService).mapTo(branchDTO, RegionalBranch.class);
    }
    
    
    @Test
    public void testUpdateBranch(){
        RegionalBranchDTO branchUpdatedDTO = branchDTO;
        branchUpdatedDTO.setName("UpdatedTest");
        RegionalBranch branchUpdated = branch1;
        branchUpdated.setName("UpdatedTest");

        when(beanMappingService.mapTo(branchDTO, RegionalBranch.class)).thenReturn(branchUpdated);

        branchFacade.updateRegionalBranch(branchUpdatedDTO);

        verify(branchService).update(branchUpdated);
        verify(beanMappingService).mapTo(branchDTO, RegionalBranch.class);
    }
    
    @Test
    public void testDeleteBranch(){
        when(beanMappingService.mapTo(branchDTO, RegionalBranch.class)).thenReturn(branch1);

        branchFacade.deleteRegionalBranch(branchDTO.getId());

        verify(branchService).delete(branch1.getId());
        verify(beanMappingService).mapTo(branchDTO, RegionalBranch.class);
    }

    /*@Test
    public void testRegisterUser(){
        when(beanMappingService.mapTo(branchDTO, User.class)).thenReturn(branch);

        branchFacade.registerUser(branchDTO, "testPassword");

        verify(userService).registerUser(branch, "testPassword");
        verify(beanMappingService).mapTo(branchDTO, User.class);
    }

    @Test
    public void testUpdateUser(){
        UserDTO userUpdatedDTO = branchDTO;
        userUpdatedDTO.setUserName("UpdatedTest");
        User userUpdated = branch;
        userUpdated.setUserName("UpdatedTest");

        when(beanMappingService.mapTo(branchDTO, User.class)).thenReturn(userUpdated);

        branchFacade.updateUser(userUpdatedDTO);

        verify(userService).updateUser(userUpdated);
        verify(beanMappingService).mapTo(branchDTO, User.class);
    }

    @Test
    public void testFindAllUser(){
        when(userService.findAllUser()).thenReturn(Collections.singletonList(branch));

        List<UserDTO> users = branchFacade.findAllUser();

        assertThat(branch.getUserName()).isEqualTo(users.get(0).getUserName());
        verify(userService).findAllUser();
        verify(beanMappingService).mapTo(Collections.singletonList(branch), UserDTO.class);
    }

    @Test
    public void testFindUserByUserName(){
        when(userService.findUserByUserName(userName)).thenReturn(branch);

        UserDTO userDTO = branchFacade.findUserByUserName(userName);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getUserName()).isEqualTo(branch.getUserName());
        verify(userService).findUserByUserName(userName);
        verify(beanMappingService).mapTo(branch, UserDTO.class);
    }

    @Test
    public void testChangePassword(){
        UserDTO userUpdatedDTO = branchDTO;
        userUpdatedDTO.setPassword("UpdatedTest");
        User userUpdated = branch;
        userUpdated.setPassword("UpdatedTest");

        when(beanMappingService.mapTo(branchDTO, User.class)).thenReturn(userUpdated);

        branchFacade.updateUser(userUpdatedDTO);

        verify(userService).updateUser(userUpdated);
        verify(beanMappingService).mapTo(branchDTO, User.class);
    }
    
    @Test
    public void testDeleteUser(){
        when(beanMappingService.mapTo(branchDTO, User.class)).thenReturn(branch);

        branchFacade.deleteUser(branchDTO.getId());

        verify(userService).deleteUser(branch.getId());
        verify(beanMappingService).mapTo(branchDTO, User.class);
    }
    
    @Test
    public void testIsAdmin(){
        when(beanMappingService.mapTo(branchDTO, User.class)).thenReturn(branch);

        branchFacade.isAdmin(branchDTO.getId());

        verify(userService).isAdmin(branch.getId());
        verify(beanMappingService).mapTo(branchDTO, User.class);
    }
    
    @Test
    public void testIsManager(){
        RegionalBranch regionalBranch = new RegionalBranch();
        regionalBranch.setId(1L);
        when(userService.isManager(branch.getId())).thenReturn(regionalBranch);

        RegionalBranchDTO branchDTO = branchFacade.isManager(branch.getId());

        assertThat(branchDTO).isNotNull();
        assertThat(branchDTO.getName()).isEqualTo(regionalBranch.getName());
        verify(userService).isManager(branch.getId());
        verify(beanMappingService).mapTo(branchDTO, RegionalBranchDTO.class);
    }*/
}
