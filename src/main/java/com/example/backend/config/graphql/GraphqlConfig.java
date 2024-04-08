package com.example.backend.config.graphql;

import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import static com.example.backend.utils.Constants.LOCAL_TIME_DATE;
import static com.example.backend.utils.Constants.LOCAL_TIME_DATE_SCALAR;

@Configuration
public class GraphqlConfig {
    @Bean
    public GraphQLScalarType localDateTimeScalar() {
        return GraphQLScalarType.newScalar()
                .name(LOCAL_TIME_DATE)
                .description(LOCAL_TIME_DATE_SCALAR)
                .coercing(new LocalDateTimeScalarConfig())
                .build();
    }

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(localDateTimeScalar())
                .build();
    }
}