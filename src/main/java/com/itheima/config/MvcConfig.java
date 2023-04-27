package com.itheima.config;

import com.itheima.tools.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(new LoginInterceptor(stringRedisTemplate)).excludePathPatterns(
                "/users/login",
               "/pages/**",
               "/css/**",
               "/plugins/**",
               "/js/**"
       );
    }

        @Override
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
            StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
            converters.add(converter);
        }


}
