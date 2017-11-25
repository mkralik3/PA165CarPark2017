package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.CarDAO;
import cz.muni.fi.pa165.entity.Car;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
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
        car.setActivationDate(timeService.getCurrentTime());
        car.setCreationDate(timeService.getCurrentTime());
        car.setModificationDate(timeService.getCurrentTime());
        carDAO.save(car);
    }

    @Override
    public void updateCar(Car car) {
        if(car == null) {
            throw new IllegalArgumentException();
        }
        car.setModificationDate(timeService.getCurrentTime());
        carDAO.save(car);
    }

    @Override
    public void deleteCar(long id) {
        carDAO.delete(id);
    }

    @Override
    public List<Car> findAllCar() {
        return carDAO.findAll();
    }

    @Override
    public Car findCar(long id) {
        return carDAO.findOne(id);
    }
}
