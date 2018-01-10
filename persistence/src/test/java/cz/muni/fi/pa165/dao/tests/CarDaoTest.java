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
import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 *
 * @author Martin Mi�keje
 */
public class CarDaoTest extends TestBase {

    private final TestObjectFactory objectFactory = new TestObjectFactory();
    
    @Test(dependsOnMethods = "createCarTest")
    public void findAllCarsTest() {
        List<Car> carsToCreate = new ArrayList<Car>();
        carsToCreate.add(objectFactory.createCar("findAllCarsTest1"));
        carsToCreate.add(objectFactory.createCar("findAllCarsTest2"));

        for (Car car : carsToCreate){
            carDao.save(car);
        }

        Collection<Car> loadedCars = carDao.findByDeactivatedFalse();

        for (Car createdCar : carsToCreate){
            assertThat(loadedCars).contains(createdCar);
        }
    }

    @Test(dependsOnMethods = "createCarTest")
    public void findCarByIdTest() {
        Car carToCreate = objectFactory.createCar("findCarByIdTest");
        carDao.save(carToCreate);

        Car loadedCar = carDao.findOne(carToCreate.getId());

        assertThat(loadedCar).isNotNull();
        assertThat(loadedCar.getName()).isEqualTo(carToCreate.getName());
        assertThat(loadedCar.getCreationDate()).isEqualTo(carToCreate.getCreationDate());
        assertThat(loadedCar.getActivationDate()).isEqualTo(carToCreate.getActivationDate());
        assertThat(loadedCar.getModificationDate()).isEqualTo(carToCreate.getModificationDate());
    }

    @Test
    public void createCarTest() {
        Car carToCreate = objectFactory.createCar("createCarTest");
        carDao.save(carToCreate);
        assertThat(carToCreate.getId()).isGreaterThan(0);
    }

    @Test(expectedExceptions = ConstraintViolationException.class, dependsOnMethods = "createCarTest")
    public void nullCarNameTest() {
        Car carToCreate = objectFactory.createCar(null);
        carDao.save(carToCreate);
    }

    @Test(dependsOnMethods = {"createCarTest", "findCarByIdTest"})
    public void updateCarTest() {
        Car carToCreate = objectFactory.createCar("updateCarTest1");
        Car carToUpdate = objectFactory.createCar("updateCarTest2");

        carDao.save(carToCreate);
        carToUpdate.setId(carToCreate.getId());
        
        carDao.save(carToUpdate);

        Car loadedCar = carDao.findOne(carToUpdate.getId());

        assertThat(loadedCar.getName()).isEqualTo(carToUpdate.getName());
    }

    @Test(dependsOnMethods = {"createCarTest", "findCarByIdTest"})
    public void deleteCarTest() {
        Car car = objectFactory.createCar("deleteCarTest");
        carDao.save(car);
        Car loadedCar = carDao.findOne(car.getId());
        assertThat(loadedCar).isNotNull();
        carDao.delete(car);
        loadedCar = carDao.findOne(car.getId());
        assertThat(loadedCar).isNull();
    }
}