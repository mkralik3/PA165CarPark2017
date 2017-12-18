package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.config.ApiDefinition;
import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.dto.results.CarOperationResult;
import cz.muni.fi.pa165.exceptions.ResourceNotFound;
import cz.muni.fi.pa165.exceptions.ResourceNotValid;
import cz.muni.fi.pa165.facade.CarFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(ApiDefinition.Car.BASE)
public class CarController {

    private final static Logger logger = LoggerFactory.getLogger(CarController.class);

    @Inject
    private CarFacade carFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<CarDTO> getAllCars(){
        List<CarDTO> result = carFacade.findAllCars();
        if(result == null) {
            result = Collections.emptyList();
        }
        return result;
    }

    @RequestMapping(value = ApiDefinition.Car.ID , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final CarDTO getCarById(@PathVariable(ApiDefinition.Car.PATH_ID) long id) {
        CarDTO result = carFacade.findCarById(id);
        if (result != null) {
            return result;
        } else {
            throw new ResourceNotFound();
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final CarOperationResult createCar(@Valid @RequestBody CarDTO car,
                                              BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ResourceNotValid();
        }
        logger.debug("REST create car: ", car);

        return carFacade.createCar(car);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final CarOperationResult updateCar(@Valid @RequestBody CarDTO car,
                                              BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ResourceNotValid();
        }
        logger.debug("REST update car: ", car);

        return carFacade.createCar(car);
    }

    @RequestMapping(value = ApiDefinition.Car.ID, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteCar(@PathVariable(ApiDefinition.Car.PATH_ID) long id){
        logger.debug("REST delete car with id: ", id);
        try {
            carFacade.deleteCar(id);
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new ResourceNotFound();
        }
    }
}
