/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.RegionalBranch;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.UserType;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import org.testng.annotations.Test;

/**
 *
 * @author Tomas Pavuk
 */

@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class RegionalBranchDaoTest extends AbstractTestNGSpringContextTests{
        @Autowired
	private RegionalBranchDAO branchDao;
    
        @Autowired
	private CarDAO carDao;
	
	@PersistenceContext 
	private EntityManager em;
        
        @Test
	public void findAllRegionalBranches(){
		RegionalBranch branch1 = new RegionalBranch();
		RegionalBranch branch2 = new RegionalBranch();
		branch1.setName("Branch1");
                branch1.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));

                branch2.setName("Branch2");
                branch2.setCreationDate(LocalDateTime.of(2012, Month.MARCH, 20, 10, 10));
                
		branchDao.createRegionalBranch(branch1);
		branchDao.createRegionalBranch(branch2);
		
		List<RegionalBranch> branches  = (List<RegionalBranch>) branchDao.findAllRegionalBranches();
		
		assertThat(branches.size()).isEqualTo(2);
		assertThat(branches).contains(branch1);
                assertThat(branches).contains(branch2);
	}
        
        @Test
	public void findAllChildrenBranches(){
                RegionalBranch parentBranch = new RegionalBranch();
		RegionalBranch branch1 = new RegionalBranch();
		RegionalBranch branch2 = new RegionalBranch();
                parentBranch.setName("ParentBranch");
                parentBranch.setCreationDate(LocalDateTime.of(2015, Month.MARCH, 20, 10, 10));
                branchDao.createRegionalBranch(parentBranch);
                
		branch1.setName("Branch1");
                branch1.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
                branch1.setParent(parentBranch);
                
                branch2.setName("Branch2");
                branch2.setCreationDate(LocalDateTime.of(2012, Month.MARCH, 20, 10, 10));
                branch2.setParent(parentBranch);
                
		branchDao.createRegionalBranch(branch1);
		branchDao.createRegionalBranch(branch2);
		
		List<RegionalBranch> branches  = (List<RegionalBranch>) branchDao.findAllChildrenBranches(parentBranch);
		
		assertThat(branches.size()).isEqualTo(2);
		assertThat(branches).contains(branch1);
                assertThat(branches).contains(branch2);
	}
        
        @Test
	public void findParentBranch(){
                RegionalBranch parentBranch = new RegionalBranch();
		RegionalBranch childBranch = new RegionalBranch();
                parentBranch.setName("ParentBranch");
                parentBranch.setCreationDate(LocalDateTime.of(2015, Month.MARCH, 20, 10, 10));
                branchDao.createRegionalBranch(parentBranch);
                
		childBranch.setName("Branch1");
                childBranch.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
                childBranch.setParent(parentBranch);
		branchDao.createRegionalBranch(childBranch);
		
		RegionalBranch foundBranch  = branchDao.findParentBranch(childBranch);
		assertThat(foundBranch).isEqualTo(parentBranch);
	}
        
        @Test
	public void findAllCarsForBranch(){
                Car car1 = new Car();
		Car car2 = new Car();
		car1.setName("Car1");
                car1.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
		car2.setName("Car2");
		car2.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
                
		carDao.createCar(car1);
		carDao.createCar(car2);
                
                RegionalBranch branch = new RegionalBranch();
                branch.setName("Branch");
                branch.setCreationDate(LocalDateTime.of(2015, Month.MARCH, 20, 10, 10));
                branch.setCar(car1);
                branchDao.createRegionalBranch(branch);
		
                List<Car> foundCars = (List<Car>) branchDao.findAllCarsForBranch(branch);
		assertThat(foundCars.size()).isEqualTo(1);
                assertThat(foundCars).contains(car1);
	}
        
        @Test
	public void findAllAvailableCarsForBranch(){
                Car car1 = new Car();
		Car car2 = new Car();
		car1.setName("Car1");
                car1.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
		car2.setName("Car2");
		car2.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
                car2.setActivationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 20));
                
		carDao.createCar(car1);
		carDao.createCar(car2);
                
                RegionalBranch branch = new RegionalBranch();
                branch.setName("Branch");
                branch.setCreationDate(LocalDateTime.of(2015, Month.MARCH, 20, 10, 10));
                branch.setCar(car1);
                branchDao.createRegionalBranch(branch);
		
                List<Car> foundCars = (List<Car>) branchDao.findAllAvaliableCarsForBranch(branch);
		assertThat(foundCars.size()).isEqualTo(1);
                assertThat(foundCars).contains(car2);
	}
        
        @Test
	public void findBranchById(){
		RegionalBranch branch = new RegionalBranch();
		branch.setName("Branch");
                branch.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
                branchDao.createRegionalBranch(branch);
		
                RegionalBranch foundBranch = branchDao.findRegionalBranchById(branch.getId());
		
		assertThat(foundBranch).isNotNull();
		assertThat(foundBranch.getName()).isEqualTo("Branch");
	}
        
        @Test()
	public void createBranchSavesBranchProperties(){
		RegionalBranch branch = new RegionalBranch();
		branch.setName("Branch");
                branch.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
                branch.setModificationDate(LocalDateTime.of(2017, Month.MAY, 20, 10, 10));
                branchDao.createRegionalBranch(branch);
		
                RegionalBranch foundBranch = branchDao.findRegionalBranchById(branch.getId());
		
		assertThat(foundBranch.getName()).isEqualTo("Branch");
                assertThat(foundBranch.getCreationDate()).isEqualTo(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
                assertThat(foundBranch.getModificationDate()).isEqualTo(LocalDateTime.of(2017, Month.MAY, 20, 10, 10));
	}
        
        @Test(expectedExceptions=IllegalArgumentException.class)
	public void nullNameIsNotAllowed(){
		RegionalBranch branch = new RegionalBranch();
		branch.setName(null);
                branch.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
		branchDao.createRegionalBranch(branch);		
	}
        
        @Test(expectedExceptions=IllegalArgumentException.class)
	public void nullBranchCreationDateIsNotAllowed(){
		RegionalBranch branch = new RegionalBranch();
		branch.setName("Branch");
                branch.setCreationDate(null);
		branchDao.createRegionalBranch(branch);		
	}
        
        @Test(expectedExceptions=IllegalArgumentException.class)
	public void branchCantBeItsOwnParent(){
		RegionalBranch branch = new RegionalBranch();
		branch.setName("User");
                branch.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
                branch.setParent(branch);
		branchDao.createRegionalBranch(branch);	
	}
        
        @Test()
	public void updateUser(){
		RegionalBranch branch = new RegionalBranch();
		branch.setName("Branch");
                branch.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
		branchDao.createRegionalBranch(branch);
                
                branch.setName("BranchWithChangedName");
                branch.setCreationDate(LocalDateTime.of(2016, Month.FEBRUARY, 20, 10, 10));
                
                RegionalBranch foundBranch = branchDao.findRegionalBranchById(branch.getId());
                
                assertThat(foundBranch.getName()).isEqualTo("BranchWithChangedName");
                assertThat(foundBranch.getCreationDate()).isEqualTo(LocalDateTime.of(2016, Month.FEBRUARY, 20, 10, 10));
	}
        
        @Test(expectedExceptions=NullPointerException.class)
	public void updateNullBranchIsNotAllowed(){
		branchDao.updateRegionalBranch(null);
	}
        
        @Test(expectedExceptions=NullPointerException.class)
	public void deleteNullBranchIsNotAllowed(){
		branchDao.deleteRegionalBranch(null);
	}
        
        @Test()
	public void deleteBranch(){
		RegionalBranch branch = new RegionalBranch();
		branch.setName("Branch");
                branch.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
		branchDao.createRegionalBranch(branch);
                assertThat(branchDao.findRegionalBranchById(branch.getId())).isNotNull();
		branchDao.deleteRegionalBranch(branch);
                assertThat(branchDao.findRegionalBranchById(branch.getId())).isNull();
	}
        
        
}
