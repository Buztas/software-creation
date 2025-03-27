package org.example.pskurimaslab1.configuration;

import jakarta.faces.webapp.FacesServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsfConfig {

    @Bean
    public ServletRegistrationBean<FacesServlet> facesServlet() {
        ServletRegistrationBean<FacesServlet> servletBean =
                new ServletRegistrationBean<>(new FacesServlet(), "*.xhtml");
        servletBean.setLoadOnStartup(1);
        return servletBean;
    }
}
