package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.results.*;

import java.util.List;

public interface CarFacade {
    
    SimpleResult createCar(CarDTO car);

    /*
    Activation/deactivation will be through this method
    */
    SimpleResult updateCar(CarDTO car);

    SimpleResult deleteCar(long carId);

    List<CarDTO> findAllCars();
}
