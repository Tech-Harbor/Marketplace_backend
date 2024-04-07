package com.example.backend.config.graphql;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import static com.example.backend.web.utils.Constants.DATE_FORMAT;


public class LocalDateTimeScalarConfig implements Coercing<LocalDateTime, String> {

    @Override
    @SneakyThrows
    public @Nullable String serialize(@NotNull final Object dataFetcherResult,
                                      @NotNull final GraphQLContext graphQLContext,
                                      @NotNull final Locale locale) {

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);

        return format.format(
                Date.from(((LocalDateTime) dataFetcherResult)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                )
        );
    }

    @Override
    @SneakyThrows
    public @Nullable LocalDateTime parseValue(@NotNull final Object input,
                                              @NotNull final GraphQLContext graphQLContext,
                                              @NotNull final Locale locale) {
        return LocalDateTime.parse((String) input);
    }

    @Override
    @SneakyThrows
    public @Nullable LocalDateTime parseLiteral(@NotNull final Value<?> input,
                                                @NotNull final CoercedVariables variables,
                                                @NotNull final GraphQLContext graphQLContext,
                                                @NotNull final Locale locale) {
        return LocalDateTime.parse(((StringValue) input).getValue());
    }
}