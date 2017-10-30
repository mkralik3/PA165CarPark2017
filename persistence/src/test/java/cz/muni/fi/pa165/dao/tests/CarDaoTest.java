/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao.tests;

import cz.muni.fi.pa165.dao.tests.support.TestObjectFactory;
import cz.muni.fi.pa165.entity.Car;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 *
 * @author Martin Miškeje
 */
public class CarDaoTest extends TestBase {

    private final TestObjectFactory objectFactory = new TestObjectFactory();
    
    @Test
    public void findAllCarsTest() {
        List<Car> carsToCreate = new ArrayList<Car>();
        carsToCreate.add(objectFactory.createCar("findAllCarsTest1"));
        carsToCreate.add(objectFactory.createCar("findAllCarsTest2"));

        for (Car car : carsToCreate){
            carDao.createCar(car);
        }

        Collection<Car> loadedCars = carDao.findAllCars();

        for (Car createdCar : carsToCreate){
            assertThat(loadedCars).contains(createdCar);
        }
    }

    @Test
    public void findCarByIdTest() {
        Car carToCreate = objectFactory.createCar("findCarByIdTest");
        carDao.createCar(carToCreate);

        Car loadedCar = carDao.findCarById(carToCreate.getId());

        assertThat(loadedCar).isNotNull();
        assertThat(loadedCar.getName()).isEqualTo(carToCreate.getName());
        assertThat(loadedCar.getCreationDate()).isEqualTo(carToCreate.getCreationDate());
        assertThat(loadedCar.getActivationDate()).isEqualTo(carToCreate.getActivationDate());
        assertThat(loadedCar.getModificationDate()).isEqualTo(carToCreate.getModificationDate());
    }

    @Test
    public void createCarTest() {
        Car carToCreate = objectFactory.createCar("createCarTest");
        carDao.createCar(carToCreate);
        assertThat(carToCreate.getId()).isGreaterThan(0);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullCarNameTest() {
        Car carToCreate = objectFactory.createCar(null);
        carDao.createCar(carToCreate);
    }

    @Test
    public void updateCarTest() {
        Car carToCreate = objectFactory.createCar("updateCarTest1");
        Car carToUpdate = objectFactory.createCar("updateCarTest2");

        carDao.createCar(carToCreate);
        carToUpdate.setId(carToCreate.getId());
        
        carDao.updateCar(carToUpdate);

        Car loadedCar = carDao.findCarById(carToUpdate.getId());

        assertThat(loadedCar.getName()).isEqualTo(carToUpdate.getName());
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void deleteNullCarIsNotAllowedTest() {
        carDao.deleteCar(null);
    }

    @Test
    public void deleteCarTest() {
        Car car = objectFactory.createCar("deleteCarTest");
        carDao.createCar(car);
        Car loadedCar = carDao.findCarById(car.getId());
        assertThat(loadedCar).isNotNull();
        carDao.deleteCar(car);
        loadedCar = carDao.findCarById(car.getId());
        assertThat(loadedCar).isNull();
    }

}
