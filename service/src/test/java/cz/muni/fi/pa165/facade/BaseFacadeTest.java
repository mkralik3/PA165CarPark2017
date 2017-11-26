package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.service.BeanMappingService;
import org.mockito.Spy;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.inject.Inject;

@ContextConfiguration(classes = ServiceConfiguration.class)
public abstract class BaseFacadeTest extends AbstractTestNGSpringContextTests {

    @Spy
    @Inject
    protected BeanMappingService beanMappingService;

    public static final Object unwrapProxy(Object bean) throws Exception {
    /*
     * If the given object is a proxy, set the return value as the object
     * being proxied, otherwise return the given object.
     */
        if (AopUtils.isAopProxy(bean) && bean instanceof Advised) {
            Advised advised = (Advised) bean;
            bean = advised.getTargetSource().getTarget();
        }
        return bean;
    }
}
