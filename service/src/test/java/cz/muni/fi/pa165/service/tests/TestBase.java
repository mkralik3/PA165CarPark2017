package cz.muni.fi.pa165.service.tests;

import cz.muni.fi.pa165.config.ServiceConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 *
 * @author Martin Miskeje
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class TestBase extends AbstractTestNGSpringContextTests {

}
