package cz.muni.fi.pa165.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import cz.muni.fi.pa165.controllers.CarController;
import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.dto.CarReservationRequestDTO;
import cz.muni.fi.pa165.dto.RegionalBranchDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.mixin.CarDTOMixin;
import cz.muni.fi.pa165.mixin.CarReservationRequestDTOMixin;
import cz.muni.fi.pa165.mixin.RegionalBranchDTOMixin;
import cz.muni.fi.pa165.mixin.UserDTOMixin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Validator;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Configuration
@EnableWebMvc
@Import({SampleDataConfiguration.class})
@ComponentScan(basePackages = {"cz.muni.fi.pa165.controllers"})
public class RestConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
    }

    private MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH));

        objectMapper.addMixIn(CarDTO.class, CarDTOMixin.class);
        objectMapper.addMixIn(RegionalBranchDTO.class, RegionalBranchDTOMixin.class);
        objectMapper.addMixIn(UserDTO.class, UserDTOMixin.class);
        objectMapper.addMixIn(CarReservationRequestDTO.class, CarReservationRequestDTOMixin.class);

        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

}
