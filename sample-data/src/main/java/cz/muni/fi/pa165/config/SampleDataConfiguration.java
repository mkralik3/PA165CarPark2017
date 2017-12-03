package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.Initializer;
import cz.muni.fi.pa165.InitializerImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {InitializerImpl.class})
public class SampleDataConfiguration {

    @Inject
    private Initializer initializer;

    @PostConstruct
    public void dataLoading() {
        initializer.loadData();
    }
}