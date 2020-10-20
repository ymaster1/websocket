package ym.chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: ym
 * @Description:
 * 自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
 * 要注意，如果使用独立的servlet容器，而不是直接使用springboot的内置容器，就不要注入ServerEndpointExporter，因为它将由容器自己提供和管理。
 * @Date: 2019/11/10 6:19 下午
 * @Version:
 */
@Configuration
public class config {
    /**
     *    这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
     *    springboot没有自动配置他，需要手动注入
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
