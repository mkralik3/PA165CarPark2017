package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.facade.CarFacade;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

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
}
