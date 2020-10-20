package ym.chat.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: ym
 * @Description:
 * @Date: 2020/3/21 1:51 下午
 * @Version:
 */
@Configuration
public class MyWebHandlerConfig implements WebMvcConfigurer {
    /**
     * 添加静态资源，防止被拦截，addResourceHandler方法是设置访问路径前缀，
     * addResourceLocations方法设置资源路径，如果你想指定外部的目录也很简单，直接addResourceLocations指定即可
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");

    }

    /**
     * addPathPatterns用来设置拦截路径，excludePathPatterns 用来设置白名单，也就是不需要触发这个拦截器的路径
     *
     * @param registry
     */
    //@Override
    //public void addInterceptors(InterceptorRegistry registry) {
    //    registry.addInterceptor(new LoginHandler())
    //            .addPathPatterns("/**")
    //            .excludePathPatterns("/check/login")
    //            .excludePathPatterns("/login.html")
    //            .excludePathPatterns("/static/**");
    //
    //}
}
