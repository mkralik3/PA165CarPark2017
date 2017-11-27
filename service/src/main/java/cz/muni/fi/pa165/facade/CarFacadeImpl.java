package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.enums.CarOperationErrorCode;
import cz.muni.fi.pa165.dto.results.*;
import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CarFacadeImpl implements CarFacade {

    private final Logger log = LoggerFactory.getLogger(CarFacadeImpl.class);

    @Inject
    private CarService carService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public CarOperationResult createCar(CarDTO car) {
        CarOperationResult result = new CarOperationResult();
        try {
            Car carToCreate = beanMappingService.mapTo(car, Car.class);
            Set<CarOperationErrorCode> errors = new HashSet<>();
            carService.createCar(carToCreate).forEach((x) -> {
                errors.add(beanMappingService.mapEnumTo(x, CarOperationErrorCode.class));
            });
            if (errors.isEmpty()) {
                result.setData(beanMappingService.mapTo(carToCreate, CarDTO.class));
                result.setIsSuccess(true);
            }
            errors.forEach((e) -> {
                result.getErrorCodes().add(beanMappingService.mapEnumTo(e, CarOperationErrorCode.class));
            });
        } catch (Exception ex) {
            result.getErrorCodes().add(CarOperationErrorCode.UNKNOWN_ERROR);
            log.error(ex.toString());
        }
        return result;
    }

    @Override
    public CarOperationResult updateCar(CarDTO car) {
        CarOperationResult result = new CarOperationResult();
        try {
            Car carToUpdate = beanMappingService.mapTo(car, Car.class);
            Set<CarOperationErrorCode> errors = new HashSet<>();
            carService.updateCar(carToUpdate).forEach((x) -> {
                errors.add(beanMappingService.mapEnumTo(x, CarOperationErrorCode.class));
            });
            if (errors.isEmpty()) {
                result.setData(beanMappingService.mapTo(carToUpdate, CarDTO.class));
                result.setIsSuccess(true);
            }
            errors.forEach((e) -> {
                result.getErrorCodes().add(beanMappingService.mapEnumTo(e, CarOperationErrorCode.class));
            });
        } catch (Exception ex) {
            result.getErrorCodes().add(CarOperationErrorCode.UNKNOWN_ERROR);
            log.error(ex.toString());
        }
        return result;
    }

    @Override
    public SimpleResult deleteCar(long id) {
        SimpleResult result = new SimpleResult();
        try {
            Car deletedCar = carService.deleteCar(id);
            result.setIsSuccess(deletedCar != null);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return result;
    }
    
    @Override
    public CarDTO findCarById(long carId) {
        CarDTO result = null;
        try {
            Car car = carService.findCar(carId);
            if (car != null) {
                result = beanMappingService.mapTo(car, CarDTO.class);
            }
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return result;
    }

    @Override
    public List<CarDTO> findAllCars() {
        List<CarDTO> result = new ArrayList<>();
        try {
            List<Car> cars = carService.findAllCars();
            if (cars != null) {
                cars.forEach((u) -> {
                    result.add(beanMappingService.mapTo(u, CarDTO.class));
                });
            }
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return result;
    }
}
