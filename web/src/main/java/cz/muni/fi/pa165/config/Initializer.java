package cz.muni.fi.pa165.config;

import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.addListener(RequestContextListener.class);
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{ApiDefinition.REST + "/*"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RestConfiguration.class}; //TODO -> SecurityConfiguration
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
}
