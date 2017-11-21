package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.CarDAO;
import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.entity.Car;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService{

    @Inject
    private CarDAO carDAO;

    @Inject
    private TimeService timeService;

    @Override
    public void createCar(Car car) {
        if(car == null) {
            throw new IllegalArgumentException();
        }
        car.setCreationDate(timeService.getCurrentTime());
        carDAO.createCar(car);
    }

    @Override
    public void updateCar(Car car) {
        if(car == null) {
            throw new IllegalArgumentException();
        }
        car.setModificationDate(timeService.getCurrentTime());
        carDAO.updateCar(car);
    }

    @Override
    public void deleteCar(long id) {
        //carDAO.deleteCar(); TODO change DAO to crud repository
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Car> findAllCar() {
        return new ArrayList<>(carDAO.findAllCars());
    }

    @Override
    public Car findCar(long id) {
        return carDAO.findCarById(id);
    }
}
