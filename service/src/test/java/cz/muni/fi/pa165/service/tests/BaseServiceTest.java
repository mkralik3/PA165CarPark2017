package cz.muni.fi.pa165.service.tests;

import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.service.BeanMappingService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.inject.Inject;

@ContextConfiguration(classes = ServiceConfiguration.class)
public abstract class BaseServiceTest extends AbstractTestNGSpringContextTests {
    @Inject
    protected BeanMappingService beanMappingService;
}
