package cz.muni.fi.pa165.dao.tests;

import cz.muni.fi.pa165.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.dao.CarDAO;
import cz.muni.fi.pa165.dao.CarReservationRequestDAO;
import cz.muni.fi.pa165.dao.RegionalBranchDAO;
import cz.muni.fi.pa165.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;


@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public abstract class TestBase extends AbstractTestNGSpringContextTests {

    @Autowired
    protected CarDAO carDao;

    @Autowired
    protected UserDAO userDao;

    @Autowired
    protected CarReservationRequestDAO reservationDao;

    @Autowired
    protected RegionalBranchDAO branchDao;

}
