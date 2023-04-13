package hse.leisure.voter.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    /**
     * Метод для добавления конфигурации для контроллеров.
     * @param registry Конфигурация MVC.
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/map").setViewName("route/map");
        registry.addViewController("/").setViewName("route/map");

    }

    /**
     * Метод для конфигурации ресурсов для контроллеров.
     * @param registry Конфигурация для ресурсов.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
    }
}
