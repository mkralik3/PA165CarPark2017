package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.facade.CarFacadeImpl;
import cz.muni.fi.pa165.service.CarServiceImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceSampleApplicationContext.class)
@ComponentScan(basePackageClasses = {CarServiceImpl.class, CarFacadeImpl.class})
public class ServiceConfiguration {

    @Bean
    public Mapper dozer(){
        // currently mapping is being done only by properties name, type conversion is applied if necessary
        DozerBeanMapper dozer = new DozerBeanMapper();
        return dozer;
    }
}
