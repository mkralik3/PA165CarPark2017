package cz.muni.fi.pa165;

import cz.muni.fi.pa165.config.SampleDataConfiguration;
import cz.muni.fi.pa165.dao.CarDAO;
import cz.muni.fi.pa165.dao.CarReservationRequestDAO;
import cz.muni.fi.pa165.dao.RegionalBranchDAO;
import cz.muni.fi.pa165.dao.UserDAO;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.UserService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@ContextConfiguration(classes = {SampleDataConfiguration.class})
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class InitializerTest extends AbstractTestNGSpringContextTests {
    @Inject
    public UserDAO userDAO;

    @Inject
    public CarDAO carDAO;

    @Inject
    public UserService userService;

    @Inject
    public RegionalBranchDAO regionalBranchDAO;

    @Inject
    public CarReservationRequestDAO carReservationRequestDAO;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void createSampleDataTest() {
        Assert.assertTrue(userDAO.findAll().size() > 0, "no users");

        Assert.assertTrue(carDAO.findByDeactivatedFalse().size() > 0, "no orders");
    }

}
