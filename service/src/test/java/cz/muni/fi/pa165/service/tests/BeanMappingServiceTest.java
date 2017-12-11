package cz.muni.fi.pa165.service.tests;

import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.dto.CarReservationRequestDTO;
import cz.muni.fi.pa165.dto.RegionalBranchDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.CarReservationRequest;
import cz.muni.fi.pa165.entity.RegionalBranch;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.UserType;

import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author mkralik
 *
 */
public class BeanMappingServiceTest extends BaseServiceTest {


	@Test
    public void mappingList(){        
		Car car1= new Car();
		Car car2= new Car();
        car1.setName("AUDI1");
        car2.setName("AUDI2");

        CarDTO carDTO1 = new CarDTO();
        CarDTO carDTO2 = new CarDTO();
        carDTO1.setName("AUDI1");
        carDTO2.setName("AUDI2");
        
        List<Car> cars = new ArrayList<>();
        List<CarDTO> carDTOs = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        carDTOs.add(carDTO1);
        carDTOs.add(carDTO2);

        List<Car> mappedToEntity = beanMappingService.mapTo(carDTOs, Car.class);
        assertThat(mappedToEntity).hasSize(2);
        assertThat(mappedToEntity).contains(car1, car2);
        
        List<CarDTO> mappedToDTOs = beanMappingService.mapTo(cars, CarDTO.class);
        assertThat(mappedToDTOs).hasSize(2);
        assertThat(mappedToDTOs).contains(carDTO1, carDTO2);
    }
	
    @Test
    public void mappingCar(){
        Car car= new Car();
        CarDTO carDTO = new CarDTO();
        car.setName("AUDI");
        carDTO.setName("AUDI");
        
        CarDTO mappedCarDTO = beanMappingService.mapTo(car, CarDTO.class);
        assertThat(mappedCarDTO).isEqualTo(carDTO);
        Car mappedCar = beanMappingService.mapTo(carDTO, Car.class);
        assertThat(mappedCar).isEqualTo(car);
    }
    
    @Test
    public void mappingUser(){
        User user = new User();
        user.setUserName("user");
        user.setType(UserType.ADMIN);

        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("user");
        userDTO.setType(cz.muni.fi.pa165.dto.enums.UserType.ADMIN);
        
        UserDTO mappedToDTO = beanMappingService.mapTo(user, UserDTO.class);
        assertThat(mappedToDTO).isEqualTo(userDTO);
        
        User mappedToEntity = beanMappingService.mapTo(userDTO, User.class);
        assertThat(mappedToEntity).isEqualTo(user);
    }
    
    @Test
    public void mappingCarReservationRequest(){
    	LocalDateTime currentTime = LocalDateTime.now();
    	LocalDateTime startDay = currentTime.plus(1, ChronoUnit.DAYS);
    	LocalDateTime endDay = currentTime.plus(4, ChronoUnit.DAYS);

    	
    	CarReservationRequest reservation = new CarReservationRequest();
        Car car= new Car();
        car.setName("AUDI");
        reservation.setCar(car);
        User user = new User();
        user.setUserName("user");
        reservation.setUser(user);
        reservation.setState(cz.muni.fi.pa165.enums.CarReservationRequestState.APPROVED);
        reservation.setReservationStartDate(startDay);
        reservation.setReservationEndDate(endDay);
        
        CarReservationRequestDTO reservationDTO = new CarReservationRequestDTO();
        CarDTO carDTO = new CarDTO();
        carDTO.setName("AUDI");
        reservationDTO.setCar(carDTO);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("user");
        reservationDTO.setUser(userDTO);
        reservationDTO.setState(cz.muni.fi.pa165.dto.enums.CarReservationRequestState.APPROVED);
        reservationDTO.setReservationStartDate(startDay);
        reservationDTO.setReservationEndDate(endDay);
        
        CarReservationRequest mappedToEntity = beanMappingService.mapTo(reservationDTO, CarReservationRequest.class);
        assertThat(mappedToEntity).isEqualTo(reservation);
        assertThat(mappedToEntity.getCar()).isEqualTo(car);
        assertThat(mappedToEntity.getUser()).isEqualTo(user);
        
        CarReservationRequestDTO mappedToDTO = beanMappingService.mapTo(reservation, CarReservationRequestDTO.class);
        assertThat(mappedToDTO).isEqualTo(reservationDTO);
        assertThat(mappedToDTO.getCar()).isEqualTo(carDTO);
        assertThat(mappedToDTO.getUser()).isEqualTo(userDTO);
    }
    
    
    @Test
    public void mappingRegionalBranch(){
        RegionalBranch branch = new RegionalBranch();
        branch.setName("branch");
        
        Car car= new Car();
        car.setName("AUDI");
        branch.addCar(car);
       
        User user = new User();
        user.setUserName("user");
        branch.addEmployee(user);
        
        RegionalBranch child = new RegionalBranch();
        child.setName("child");
        branch.addChild(child);
        
        RegionalBranch parent = new RegionalBranch();
        parent.setName("parent");
        branch.setParent(parent);
        
        RegionalBranchDTO branchDTO = new RegionalBranchDTO();
        branchDTO.setName("branch");
        
        CarDTO carDTO = new CarDTO();
        carDTO.setName("AUDI");
        branchDTO.addCar(carDTO);
        
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("user");
        branchDTO.addEmployee(userDTO);
        
        RegionalBranchDTO childDTO = new RegionalBranchDTO();
        childDTO.setName("child");
        branchDTO.addChild(childDTO);
        
        RegionalBranchDTO parentDTO = new RegionalBranchDTO();
        parentDTO.setName("parent");
        branchDTO.setParent(parentDTO);
        
        RegionalBranch mappedToEntity = beanMappingService.mapTo(branchDTO, RegionalBranch.class);
        assertThat(mappedToEntity).isEqualTo(branch);
        assertThat(mappedToEntity.getCars()).hasSize(1);
        assertThat(mappedToEntity.getCars()).contains(car);
        assertThat(mappedToEntity.getEmployees()).hasSize(1);
        assertThat(mappedToEntity.getEmployees()).contains(user);
        assertThat(mappedToEntity.getChildren()).hasSize(1);
        assertThat(mappedToEntity.getChildren()).contains(child);
        assertThat(mappedToEntity.getParent()).isEqualTo(parent);
        
        RegionalBranchDTO mappedToDTO = beanMappingService.mapTo(branch, RegionalBranchDTO.class);
        assertThat(mappedToDTO).isEqualTo(branchDTO);
        assertThat(mappedToDTO.getCars()).hasSize(1);
        assertThat(mappedToDTO.getCars()).contains(carDTO);
        assertThat(mappedToDTO.getEmployees()).hasSize(1);
        assertThat(mappedToDTO.getEmployees()).contains(userDTO);
        assertThat(mappedToDTO.getChildren()).hasSize(1);
        assertThat(mappedToDTO.getChildren()).contains(childDTO);
        assertThat(mappedToDTO.getParent()).isEqualTo(parentDTO); 
    }
}
