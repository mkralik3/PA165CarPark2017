package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dao.CarDAO;
import cz.muni.fi.pa165.dto.CarDTO;
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
    public void createCar(CarDTO car) {
        carService.createCar(beanMappingService.mapTo(car,Car.class));
    }

    @Override
    public void updateCar(CarDTO car) {
        carService.updateCar(beanMappingService.mapTo(car,Car.class));
    }

    @Override
    public void deleteCar(long id) {
        carService.deleteCar(id);
    }

    @Override
    public CarDTO findCar(long id) {
        Car findedCar = carService.findCar(id);
        return findedCar == null ? null : beanMappingService.mapTo(findedCar, CarDTO.class);

    }

    @Override
    public List<CarDTO> findAllCar() {
        List<Car> cars = carService.findAllCar();
        return cars == null ? null : beanMappingService.mapTo(cars, CarDTO.class);
    }

}
