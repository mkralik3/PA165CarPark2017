/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.tests;

import cz.muni.fi.pa165.dao.RegionalBranchDAO;
import cz.muni.fi.pa165.entity.RegionalBranch;
import cz.muni.fi.pa165.service.RegionalBranchService;
import cz.muni.fi.pa165.service.RegionalBranchServiceImpl;
import cz.muni.fi.pa165.service.TimeService;
import cz.muni.fi.pa165.tests.support.TestObjectFactory;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Tomas Pavuk
 */
public class RegionalBranchServiceTest extends BaseServiceTest {
    @Mock
    private RegionalBranchDAO regionalBranchDao;
    
    @Mock
    private TimeService timeService;
    
    @InjectMocks
    private final RegionalBranchService branchService = new RegionalBranchServiceImpl();
    
    private final TestObjectFactory objectFactory = new TestObjectFactory();
    private RegionalBranch branch1;
    private RegionalBranch branch2;
    
    private final String testName = "testBranch";
    
    @BeforeMethod
    public void setup() throws ServiceException {
        branch1 = objectFactory.createRegionalBranch(testName);
        branch1.setId(Long.valueOf(1));
        branch2 = objectFactory.createRegionalBranch("testBranch2");
        
        when(regionalBranchDao.findOne(1L))
            .thenReturn(branch1);
        
        when(regionalBranchDao.findAll())
            .thenReturn(Arrays.asList(branch1, branch2));
        
        doAnswer((Answer<Object>) (InvocationOnMock invocation) -> {
            Object argument = invocation.getArguments()[0];
            if(argument == null) {
                throw new IllegalArgumentException();
            }
            
            RegionalBranch branch = (RegionalBranch) argument;
            branch.setId(Long.valueOf(1));
            return null;
        }).when(regionalBranchDao).save(any(RegionalBranch.class));
        
        doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            Object argument = invocation.getArguments()[0];
            
            long userId = (long) argument;
            if(userId < 0){
                throw new IllegalArgumentException();
            }
            
            return null;
        }).when(regionalBranchDao).delete(any(long.class));
    }
    
    @BeforeClass
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void findRegionalBranch() throws IllegalArgumentException{
        RegionalBranch branch = branchService.findOne(1L);
        
        assertThat(branch).isNotNull();
        assertThat(branch).isEqualToComparingFieldByField(branch1);
    }
    
    @Test
    public void findNonExistingBranch() throws IllegalArgumentException{
        RegionalBranch branch = branchService.findOne(-1);
        assertThat(branch).isNull();
    }
    
    @Test
    public void findAllBranches() {
        List<RegionalBranch> branches = branchService.findAll();
        assertThat(branches).isNotNull();
        assertThat(branches.size()).isEqualTo(2);
        assertThat(branches).contains(branch1, branch2);
    }
    
    @Test
    public void createBranch() throws IllegalArgumentException{
        branchService.create(branch2);
        assertThat(branch2.getId()).isGreaterThan(0);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createNullBranch() throws IllegalArgumentException{
        branchService.create(null);
    }
    
    @Test
    public void updateBranch() throws IllegalArgumentException{
        branch1.setName("UpdatedName");
        branchService.update(branch1);
        assertThat(branch1.getName()).isEqualTo("UpdatedName");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullBranch() throws IllegalArgumentException{
        branchService.update(null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNonExistingUser(){
        branchService.delete(-1L);
    }
    
    @Test
    public void deleteUser() throws IllegalArgumentException{
        RegionalBranch deletedBranch = branchService.delete(branch1.getId());
        Mockito.verify(regionalBranchDao, Mockito.times(1)).delete(branch1);
        
        assertThat(deletedBranch).isEqualToComparingFieldByField(branch1);
    }
}
