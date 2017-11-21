package cz.muni.fi.pa165.facade;



import cz.muni.fi.pa165.contracts.Car;
import cz.muni.fi.pa165.contracts.request.CreateCarRequest;
import cz.muni.fi.pa165.contracts.request.DeleteCarRequest;
import cz.muni.fi.pa165.contracts.request.UpdateCarRequest;
import cz.muni.fi.pa165.contracts.response.CreateCarResponse;
import cz.muni.fi.pa165.contracts.response.DeleteCarResponse;
import cz.muni.fi.pa165.contracts.response.UpdateCarResponse;

import java.util.List;

public interface CarFacade {
    CreateCarResponse createCar(CreateCarRequest request);
    UpdateCarResponse updateCar(UpdateCarRequest request);
    DeleteCarResponse deleteCar(DeleteCarRequest request);
    List<Car> findAll();
}
