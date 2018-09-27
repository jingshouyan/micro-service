package io.jing.server.config;

import io.jing.server.gateway.constant.AppConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class FileConfig  implements WebMvcConfigurer, AppConstant {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/"+UPLOAD_PROFFIX+"/**").addResourceLocations("file:"+UPLOAD_PATH);
    }
}
