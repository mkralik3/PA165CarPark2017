package cz.muni.fi.pa165.dao.tests.support;

import cz.muni.fi.pa165.entity.Car;

/**
 *
 * @author Martin Miškeje
 */
public class TestCarsFactory {
    
    public Car createCar(String name){
        Car result = new Car();
        result.setName(name);
        return result;
    }
}
