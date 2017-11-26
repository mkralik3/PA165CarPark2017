package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.results.*;
import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.CarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class CarFacadeImpl implements CarFacade {

    @Inject
    private CarService carService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public CarOperationResult createCar(CarDTO car) {
        CarOperationResult result = new CarOperationResult();
        carService.createCar(beanMappingService.mapTo(car, Car.class));
        result.setIsSuccess(true);
        return result;
    }

    @Override
    public CarOperationResult updateCar(CarDTO car) {
        CarOperationResult result = new CarOperationResult();
        carService.updateCar(beanMappingService.mapTo(car, Car.class));
        result.setIsSuccess(true);
        return result;
    }

    @Override
    public SimpleResult deleteCar(long id) {
        SimpleResult result = new SimpleResult();
        carService.deleteCar(id);
        result.setIsSuccess(true);
        return result;
    }
    
    @Override
    public CarDTO findCarById(long carId) {
        return beanMappingService.mapTo(carService.findCar(carId), CarDTO.class);
    }

    @Override
    public List<CarDTO> findAllCars() {
        List<Car> cars = carService.findAllCars();
        return cars == null ? null : beanMappingService.mapTo(cars, CarDTO.class);
    }
}
