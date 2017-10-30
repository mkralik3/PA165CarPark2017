/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Car;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 *
 * @author Tomas Pavuk
 */
public class CarDaoTest extends AbstractDao {

	@Test
	public void findAll(){
		Car car1 = new Car();
		Car car2 = new Car();
		car1.setName("Car1");
		car1.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
		car2.setName("Car2");
		car2.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
                
		carDao.createCar(car1);
		carDao.createCar(car2);
		
		List<Car> cars  = (List<Car>) carDao.findAllCars();
		
		assertThat(cars.size()).isEqualTo(2);
		assertThat(cars).contains(car1);
		assertThat(cars).contains(car2);
	}
        
	@Test
	public void findCarById(){
		Car car1 = new Car();
		car1.setName("Car1");
		car1.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
		car1.setActivationDate(LocalDateTime.of(2017, Month.APRIL, 20, 10, 10));
		car1.setModificationDate(LocalDateTime.of(2017, Month.MAY, 20, 10, 10));
		carDao.createCar(car1);

		Car foundCar = carDao.findCarById(car1.getId());
		
		assertThat(foundCar).isNotNull();
		assertThat(foundCar.getName()).isEqualTo("Car1");
        assertThat(foundCar.getCreationDate()).isEqualTo(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        assertThat(foundCar.getActivationDate()).isEqualTo(LocalDateTime.of(2017, Month.APRIL, 20, 10, 10));
        assertThat(foundCar.getModificationDate()).isEqualTo(LocalDateTime.of(2017, Month.MAY, 20, 10, 10));
	}
        
	@Test
	public void createCarSavesCarName(){
		Car car = new Car();
		car.setName("Car1");
		car.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
		carDao.createCar(car);
	 	assertThat(carDao.findCarById(car.getId()).getName()).isEqualTo(("Car1"));
	}
        
	@Test(expectedExceptions=ConstraintViolationException.class)
	public void nullCarNameIsNotAllowed(){
		Car car = new Car();
		car.setName(null);
        car.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
		carDao.createCar(car);		
	}
        
	@Test
	public void updateCar(){
		Car car = new Car();
		car.setName("CarName");
		car.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
		carDao.createCar(car);
		car.setName("NewCarName");
		car.setCreationDate(LocalDateTime.of(2016, Month.FEBRUARY, 20, 10, 10));

		Car foundCar = carDao.findCarById(car.getId());

		assertThat(foundCar.getName()).isEqualTo("NewCarName");
		assertThat(foundCar.getCreationDate()).isEqualTo(LocalDateTime.of(2016, Month.FEBRUARY, 20, 10, 10));
	}
        
	@Test(expectedExceptions=InvalidDataAccessApiUsageException.class)
	public void deleteNullCarIsNotAllowed(){
		carDao.deleteCar(null);
	}
        
	@Test
	public void deleteCar(){
		Car car = new Car();
		car.setName("CarName");
		car.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
		carDao.createCar(car);
		assertThat(carDao.findCarById(car.getId())).isNotNull();
		carDao.deleteCar(car);
		assertThat(carDao.findCarById(car.getId())).isNull();
	}
        
	@Test(expectedExceptions=InvalidDataAccessApiUsageException.class)
	public void updateNullCarIsNotAllowed(){
		carDao.updateCar(null);
	}
        
        
}
