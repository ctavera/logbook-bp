package com.revenatium.logbookbp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.RequestFilters;
import org.zalando.logbook.ResponseFilters;

import static org.zalando.logbook.Conditions.*;
import static org.zalando.logbook.HeaderFilters.authorization;
import static org.zalando.logbook.QueryFilters.accessToken;
import static org.zalando.logbook.QueryFilters.replaceQuery;

@Configuration
public class LogbookConfig {

    @Bean
    public Logbook logbook() {
        Logbook logbook = Logbook.builder()
                //paths
                .condition(exclude(
                        requestTo("/health"),
                        requestTo("/admin/**"),
                        contentType("application/octet-stream")))
//                        header("X-Secret", newHashSet("1", "true")::contains)
                //filtering
                .requestFilter(RequestFilters.replaceBody(message -> contentType("audio/*").test(message) ? "mmh mmh mmh mmh" : null))
                .responseFilter(ResponseFilters.replaceBody(message -> contentType("*/*-stream").test(message) ? "It just keeps going and going..." : null))
                //Change the response for the message
//                .responseFilter(ResponseFilters.replaceBody(message -> contentType("application/json").test(message) ? "This is json..." : null))
                .queryFilter(accessToken())
                .queryFilter(replaceQuery("password", "<secret>"))
                .headerFilter(authorization())
//                .headerFilter(eachHeader("X-Secret"::equalsIgnoreCase, "<secret>"))
                .build();

        return logbook;
    }
}
