package cz.muni.fi.pa165.service.tests;

import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.entity.Car;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 
 * @author mkralik
 *
 */
public class BeanMappingServiceTest extends BaseServiceTest {


    @Test
    public void mapping(){
        Car car= new Car();
        CarDTO carDTO = new CarDTO();
        car.setName("AUDI");
        carDTO.setName("AUDI");

        CarDTO mappedCarDTO = beanMappingService.mapTo(car, CarDTO.class);
        assertThat(mappedCarDTO).isEqualTo(carDTO);
        Car mappedCar = beanMappingService.mapTo(carDTO, Car.class);
        assertThat(mappedCar).isEqualTo(car);
    }
}
