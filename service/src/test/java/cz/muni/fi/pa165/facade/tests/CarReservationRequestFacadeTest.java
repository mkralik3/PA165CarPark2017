package cz.muni.fi.pa165.facade.tests;

import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.dto.CarReservationRequestDTO;
import cz.muni.fi.pa165.dto.RegionalBranchDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.CarReservationRequest;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.CarReservationRequestState;
import cz.muni.fi.pa165.enums.UserType;
import cz.muni.fi.pa165.facade.CarReservationRequestFacade;
import cz.muni.fi.pa165.service.CarReservationRequestService;
import cz.muni.fi.pa165.tests.support.TestObjectFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class CarReservationRequestFacadeTest extends BaseFacadeTest {
    @Mock
    private CarReservationRequestService carReservationRequestService;

    @Mock
    private CarDTO carDTO;

    @Mock
    private UserDTO userDTO;

    @Mock
    private RegionalBranchDTO regionalBranchDTO;

    @Autowired
    @InjectMocks
    private CarReservationRequestFacade carReservationRequestFacade;

    private CarReservationRequestDTO reservationRequestDTO;

    private LocalDateTime currentTime;

    private final TestObjectFactory objectFactory = new TestObjectFactory();


    private Car car1;
    private User user1;
    private CarReservationRequest reservation1;

    @BeforeMethod()
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        carReservationRequestFacade = (CarReservationRequestFacade) unwrapProxy(carReservationRequestFacade);
        ReflectionTestUtils.setField(carReservationRequestFacade, "reservationsService", carReservationRequestService);
        ReflectionTestUtils.setField(carReservationRequestFacade, "beanMappingService", beanMappingService);

        currentTime = LocalDateTime.now();

        user1 = objectFactory.createUser("firstUser", UserType.USER);
        user1.setId(Long.valueOf(1));

        userDTO = new UserDTO();
        userDTO.setUserName("firstUser");
        userDTO.setType(cz.muni.fi.pa165.dto.enums.UserType.USER);
        userDTO.setId(Long.valueOf(1));

        car1 = objectFactory.createCar("sampleCar");
        car1.setId(Long.valueOf(1));

        carDTO = new CarDTO();
        carDTO.setName("sampleCar");
        carDTO.setId(Long.valueOf(1));

        reservation1 = new CarReservationRequest();
        reservation1.setId(1L);
        reservation1.setCar(car1);
        reservation1.setUser(user1);
        reservation1.setReservationStartDate(currentTime);
        reservation1.setReservationEndDate(currentTime.plus(4, ChronoUnit.DAYS));
        reservation1.setState(CarReservationRequestState.CREATED);

        reservationRequestDTO = new CarReservationRequestDTO();
        reservationRequestDTO.setId(1L);
        reservationRequestDTO.setCar(carDTO);
        reservationRequestDTO.setUser(userDTO);
        reservationRequestDTO.setReservationStartDate(currentTime);
        reservationRequestDTO.setReservationEndDate(currentTime.plus(4, ChronoUnit.DAYS));
        reservationRequestDTO.setState(cz.muni.fi.pa165.dto.enums.CarReservationRequestState.CREATED);

    }

    @Test
    public void createCarReservationRequestTest() {
        when(beanMappingService.mapTo(reservationRequestDTO, CarReservationRequest.class)).thenReturn(reservation1);
        carReservationRequestFacade.createCarReservationRequest(reservationRequestDTO);

        verify(carReservationRequestService).create(reservation1);
        verify(beanMappingService).mapTo(reservationRequestDTO, CarReservationRequest.class);
    }

    @Test
    public void testUpdateCar() {
        CarReservationRequestDTO updatedDTO = reservationRequestDTO;
        updatedDTO.setReservationEndDate(currentTime.plus(5, ChronoUnit.DAYS));
        CarReservationRequest updatedEntity = reservation1;
        reservation1.setReservationEndDate(currentTime.plus(5, ChronoUnit.DAYS));

        when(beanMappingService.mapTo(reservationRequestDTO, CarReservationRequest.class)).thenReturn(updatedEntity);

        carReservationRequestFacade.updateCarReservationRequest(updatedDTO);

        verify(carReservationRequestService).update(updatedEntity);
        verify(beanMappingService).mapTo(reservationRequestDTO, CarReservationRequest.class);
    }


    @Test
    public void testDeleteCar() {
        carReservationRequestFacade.deleteCarReservationRequest(reservation1.getId());
        verify(carReservationRequestService).delete(reservation1.getId());
    }

    @Test
    public void getAllForRegionalBranchTest() {
        LocalDateTime today = LocalDateTime.now();

        Long requiredBranchId = 1L;

        CarReservationRequest crr = new CarReservationRequest();
        crr.setCar(car1);
        crr.setUser(user1);
        crr.setState(CarReservationRequestState.APPROVED);
        crr.setReservationStartDate(today);
        crr.setReservationStartDate(today.plus(5, ChronoUnit.DAYS));

        CarReservationRequestDTO crrDTO = new CarReservationRequestDTO();
        crrDTO.setCar(carDTO);
        crrDTO.setUser(userDTO);
        crr.setState(CarReservationRequestState.APPROVED);
        crr.setReservationStartDate(today);
        crr.setReservationStartDate(today.plus(5, ChronoUnit.DAYS));

        when(carReservationRequestService.getAllForRegionalBranch(requiredBranchId, today, today.plus(5, ChronoUnit.DAYS)))
                .thenReturn(Arrays.asList(crr));

        when(beanMappingService.mapTo(crr, CarReservationRequestDTO.class)).thenReturn(crrDTO);

        List<CarReservationRequestDTO> result = carReservationRequestFacade.getAllForRegionalBranch(requiredBranchId, today, today.plus(5, ChronoUnit.DAYS));

        verify(carReservationRequestService).getAllForRegionalBranch(requiredBranchId, today, today.plus(5, ChronoUnit.DAYS));
        verify(beanMappingService).mapTo(crr, CarReservationRequestDTO.class);

        assertThat(result).contains(crrDTO);
    }
}