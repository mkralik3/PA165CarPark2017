package cz.muni.fi.pa165.facade.tests;

import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.facade.CarFacade;
import cz.muni.fi.pa165.service.CarService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CarFacadeTest extends BaseFacadeTest {
    @Mock
    private CarService carService;

    @Autowired
    @InjectMocks
    private CarFacade carFacade;

    private Car car;

    private CarDTO carDTO;

    private Long carId = 1L;

    @BeforeMethod()
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        carFacade = (CarFacade) unwrapProxy(carFacade);
        ReflectionTestUtils.setField(carFacade, "carService", carService);
        ReflectionTestUtils.setField(carFacade, "beanMappingService", beanMappingService);

        car = new Car();
        car.setName("Audi");

        carDTO = new CarDTO();
        carDTO.setName("Audi");
    }

    @Test
    public void testCreateCar(){
        when(beanMappingService.mapTo(carDTO, Car.class)).thenReturn(car);

        carFacade.createCar(carDTO);

        verify(carService).createCar(car);
        verify(beanMappingService).mapTo(carDTO, Car.class);
    }

    @Test
    public void testUpdateCar(){
        CarDTO carUpdatedDTO = carDTO;
        carUpdatedDTO.setName("Updated");
        Car carUpdated = car;
        carUpdated.setName("Updated");

        when(beanMappingService.mapTo(carDTO, Car.class)).thenReturn(carUpdated);

        carFacade.updateCar(carUpdatedDTO);

        verify(carService).updateCar(carUpdated);
        verify(beanMappingService).mapTo(carDTO, Car.class);
    }

    @Test
    public void testFindAllCars(){
        when(carService.findAllActivatedCars()).thenReturn(Collections.singletonList(car));

        List<CarDTO> cars = carFacade.findAllActivatedCars();

        assertThat(car.getName()).isEqualTo(cars.get(0).getName());
        verify(carService).findAllActivatedCars();
        verify(beanMappingService).mapTo(car, CarDTO.class);
    }
    
    @Test
    public void testFindCarById(){
        when(carService.findCar(carId)).thenReturn(car);

        CarDTO carDTO = carFacade.findCarById(carId);

        assertThat(carDTO).isNotNull();
        assertThat(carDTO.getName()).isEqualTo(car.getName());
        verify(carService).findCar(carId);
        verify(beanMappingService).mapTo(car, CarDTO.class);
    }
    
    @Test
    public void testDeleteCar(){
        carFacade.deleteCar(carId);
        verify(carService).deleteCar(carId);
    }
}
