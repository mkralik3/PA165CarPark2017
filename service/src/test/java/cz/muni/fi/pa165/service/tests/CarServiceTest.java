package cz.muni.fi.pa165.service.tests;
import cz.muni.fi.pa165.dao.CarDAO;
import cz.muni.fi.pa165.tests.support.TestObjectFactory;
import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.service.CarService;
import cz.muni.fi.pa165.service.CarServiceImpl;
import cz.muni.fi.pa165.service.TimeService;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CarServiceTest extends BaseServiceTest {
    @Mock
    private CarDAO carDao;
    
    @Mock
    private TimeService timeService;
    
    @InjectMocks
    private final CarService carService = new CarServiceImpl();
    
    private final TestObjectFactory objectFactory = new TestObjectFactory();
    private Car car1;
    private Car car2;
    
    @BeforeMethod
    public void setup(){
        car1 = objectFactory.createCar("sampleCar");
        car1.setId(Long.valueOf(1));
        car2 = objectFactory.createCar("sampleCar2");

        when(carDao.findOne(1L))
            .thenReturn(car1);
        
        when(carDao.findAll())
            .thenReturn(Arrays.asList(car1, car2));
        
        doAnswer((Answer<Object>) (InvocationOnMock invocation) -> {
            Object argument = invocation.getArguments()[0];
            if(argument == null) {
                throw new IllegalArgumentException();
            }
            
            Car car = (Car) argument;
            car.setId(Long.valueOf(1));
            return null;
        }).when(carDao).save(any(Car.class));
        
        doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            Object argument = invocation.getArguments()[0];
            
            long carId = (long) argument;
            if(carId < 0){
                throw new IllegalArgumentException();
            }
            
            return null;
        }).when(carDao).delete(any(long.class));
    }
    
    @BeforeClass
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
    
    
    @Test
    public void findCar(){
        Car car = carService.findCar(Long.valueOf(1));
        
        assertThat(car).isNotNull();
        assertThat(car).isEqualToComparingFieldByField(car1);
    }
    
    @Test
    public void findNonExistingCar(){
        Car car = carService.findCar(Long.valueOf(-1));
        assertThat(car).isNull();
    }
    
    @Test
    public void createCar(){
        carService.createCar(car2);
        assertThat(car2.getId()).isGreaterThan(0);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createNullCar(){
        carService.createCar(null);
    }
    
    @Test
    public void findAllCars(){
        List<Car> cars = carService.findAllCars();
        assertThat(cars).isNotNull();
        assertThat(cars.size()).isEqualTo(2);
        assertThat(cars).contains(car1, car2);
    }
    
    @Test
    public void updateCar(){
        car1.setName("UpdatedName");
        carService.updateCar(car1);
        assertThat(car1.getName()).isEqualTo("UpdatedName");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullCar(){
        carService.updateCar(null);
    }
    
    @Test
    public void deleteNonExistingCar(){
        Car deletedCar = carService.deleteCar(-1L);
        assertThat(deletedCar).isNull();
    }
    
    @Test
    public void deleteCar(){
        Car deletedCar = carService.deleteCar(car1.getId());
        Mockito.verify(carDao, Mockito.times(1)).delete(deletedCar);
        assertThat(deletedCar).isEqualToComparingFieldByField(car1);
    }
}
