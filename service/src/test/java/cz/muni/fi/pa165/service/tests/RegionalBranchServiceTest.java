/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.tests;

import cz.muni.fi.pa165.dao.CarDAO;
import cz.muni.fi.pa165.dao.RegionalBranchDAO;
import cz.muni.fi.pa165.dao.UserDAO;
import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.RegionalBranch;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.UserType;
import cz.muni.fi.pa165.service.RegionalBranchService;
import cz.muni.fi.pa165.service.RegionalBranchServiceImpl;
import cz.muni.fi.pa165.service.TimeService;
import cz.muni.fi.pa165.service.enums.RegionalBranchOperationErrorCode;
import cz.muni.fi.pa165.tests.support.TestObjectFactory;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
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
    private UserDAO userDao;
    
    @Mock
    private CarDAO carDao;
    
    @Mock
    private TimeService timeService;
    
    @InjectMocks
    private final RegionalBranchService branchService = new RegionalBranchServiceImpl();
    
    private final TestObjectFactory objectFactory = new TestObjectFactory();
    private RegionalBranch branch1;
    private RegionalBranch branch2;
    
    private final String testName = "testBranch";
    
    @BeforeMethod
    public void setup(){
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
    public void findRegionalBranch(){
        RegionalBranch branch = branchService.findOne(1L);
        
        assertThat(branch).isNotNull();
        assertThat(branch).isEqualToComparingFieldByField(branch1);
    }
    
    @Test
    public void findNonExistingBranch(){
        RegionalBranch branch = branchService.findOne(-1);
        assertThat(branch).isNull();
    }
    
    @Test
    public void findAllBranches(){
        List<RegionalBranch> branches = branchService.findAll();
        assertThat(branches).isNotNull();
        assertThat(branches.size()).isEqualTo(2);
        assertThat(branches).contains(branch1, branch2);
    }
    
    @Test
    public void createBranch(){
        branchService.create(branch2);
        assertThat(branch2.getId()).isGreaterThan(0);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createNullBranch(){
        branchService.create(null);
    }
    
    @Test
    public void updateBranch(){
        branch1.setName("UpdatedName");
        branchService.update(branch1);
        assertThat(branch1.getName()).isEqualTo("UpdatedName");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullBranch(){
        branchService.update(null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNonExistingUser(){
        branchService.delete(-1L);
    }
    
    @Test
    public void deleteBranch(){
        RegionalBranch deletedBranch = branchService.delete(branch1.getId());
        Mockito.verify(regionalBranchDao, Mockito.times(1)).delete(branch1);
        
        assertThat(deletedBranch).isEqualToComparingFieldByField(branch1);
    }
    
    @Test
    public void assignUser(){
        User user = objectFactory.createUser("testUser", UserType.USER);
        user.setId(Long.valueOf(1));
        when(userDao.findOne(1L))
                .thenReturn(user);
        
        Set<RegionalBranchOperationErrorCode> errors = branchService.assignUser(branch1.getId(), user);
        
        assertThat(errors).isEmpty();
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void assignNullUser(){
        branchService.assignUser(branch1.getId(), null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void assignUserToNonExistingBranch(){
        User user = objectFactory.createUser("testUser", UserType.USER);
        user.setId(Long.valueOf(1));
        when(userDao.findOne(1L))
                .thenReturn(user);
        branchService.assignUser(-1L, user);
    }
    
    @Test
    public void assignNonExistingUserToBranch(){
        User user = objectFactory.createUser("testUser", UserType.USER);
        user.setId(Long.valueOf(-1));
        
        Set<RegionalBranchOperationErrorCode> errors = branchService.assignUser(branch1.getId(), user);
        
        assertThat(errors).contains(RegionalBranchOperationErrorCode.USER_DOESNT_EXIST);
    }
    
    @Test
    public void assignCar(){
        Car car = objectFactory.createCar("testCar");
        car.setId(Long.valueOf(1));
        when(carDao.findOne(1L))
                .thenReturn(car);
        
        Set<RegionalBranchOperationErrorCode> errors = branchService.assignCar(branch1.getId(), car);
        
        assertThat(errors).isEmpty();
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void assignNullCar(){
        branchService.assignCar(branch1.getId(), null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void assignCarToNonExistingBranch(){
        Car car = objectFactory.createCar("testCar");
        car.setId(Long.valueOf(1));
        when(carDao.findOne(1L))
                .thenReturn(car);
        branchService.assignCar(-1L, car);
    }
    
    @Test
    public void assignNonExistingCarToBranch(){
        Car car = objectFactory.createCar("testCar");
        car.setId(Long.valueOf(-1));
        
        Set<RegionalBranchOperationErrorCode> errors = branchService.assignCar(branch1.getId(), car);
        
        assertThat(errors).contains(RegionalBranchOperationErrorCode.CAR_DOESNT_EXIST);
    }
    
    @Test
    public void findAllAvailableCarsForBranch(){
        LocalDateTime today = LocalDateTime.now();
        Car availableCar = objectFactory.createCar("testCar");
        Car unavailableCar = objectFactory.createCar("testCar2");
        
        when(regionalBranchDao.findAllAvailableCarsForBranch(branch1.getName(), today))
            .thenReturn(Arrays.asList(availableCar));
        
        List<Car> cars = branchService.findAllAvailableCarsForBranch(branch1, today);
        assertThat(cars).contains(availableCar);
        assertThat(cars).doesNotContain(unavailableCar);
    }
}
