package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceSampleApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.transaction.Transactional;


@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public abstract class AbstractDao extends AbstractTestNGSpringContextTests {

    @Autowired
    protected CarDAO carDao;

    @Autowired
    protected UserDAO userDao;

    @Autowired
    protected CarReservationRequestDAO reservationDao;

    @Autowired
    protected RegionalBranchDAO branchDao;

}
