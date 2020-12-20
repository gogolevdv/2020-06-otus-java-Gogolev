package ru.otus.messagingsystem.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ru.otus.messagingsystem.core.model.User;
import ru.otus.messagingsystem.flyway.MigrationsExecutor;
import ru.otus.messagingsystem.flyway.MigrationsExecutorFlyway;
import ru.otus.messagingsystem.hibernate.HibernateUtils;

import java.time.format.DateTimeFormatter;

@Configuration
@EnableWebSocketMessageBroker
public class ApplConfig implements WebSocketMessageBrokerConfigurer {

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }


    @Bean(initMethod = "init")
        public InitMigration InitBean(){return new InitMigration();}


    @Bean
    @DependsOn({"InitBean"})
    public SessionFactory sessionFactory() {

        return HibernateUtils.buildSessionFactory(HIBERNATE_CFG_FILE, User.class);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(DATE_TIME_FORMAT);
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        };
    }
}
