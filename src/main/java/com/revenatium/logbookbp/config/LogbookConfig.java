package com.revenatium.logbookbp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.*;
import org.zalando.logbook.json.JsonHttpLogFormatter;

import static com.google.common.collect.Sets.newHashSet;
import static org.zalando.logbook.Conditions.*;
import static org.zalando.logbook.HeaderFilters.authorization;
import static org.zalando.logbook.HeaderFilters.eachHeader;
import static org.zalando.logbook.QueryFilters.accessToken;
import static org.zalando.logbook.QueryFilters.replaceQuery;

@Configuration
public class LogbookConfig {

    @Bean
    public Logbook logbook() {
        //Notes:

        //1. The LogbookFilter will, by default, treat requests with a application/x-www-form-urlencoded body not different
        // from any other request, i.e you will see the request body in the logs. The downside of this approach is that
        // you won't be able to use any of the HttpServletRequest.getParameter*(..) methods.

        //2. Secure applications usually need a slightly different setup.

        //Known Issues
        //1. The Logbook Servlet Filter interferes with downstream code using getWriter and/or getParameter*(). See Servlet for more details.
        //2. The Logbook Servlet Filter does NOT support ERROR dispatch. You're strongly encouraged to not use it to produce error responses.
        //3. The Logbook HTTP Client integration is handling gzip-compressed response entities incorrectly if the interceptor runs before a
        // decompressing interceptor. Since logging compressed contents is not really helpful it's advised to register the logbook
        // interceptor as the last interceptor in the chain.

        //Alternatives
        //1. Apache HttpClient Wire Logging
        // - Client-side only
        // - Apache HttpClient exclusive
        // - Support for HTTP bodies
        //2. Spring Boot Access Logging
        // - Spring application only
        // - Server-side only
        // - Tomcat/Undertow/Jetty exclusive
        // - No support for HTTP bodies
        //3. Tomcat Request Dumper Filter
        // - Server-side only
        // - Tomcat exclusive
        // - No support for HTTP bodies
        //4. logback-access
        // - Server-side only
        // - Any servlet container
        // - Support for HTTP bodies


        Logbook logbook = Logbook.builder()
                //Json format Config
                .sink(new DefaultSink(new JsonHttpLogFormatter(),
                        new DefaultHttpLogWriter())
                )
                //The ChunkingSink will split long messages into smaller chunks and will write them individually while delegating to another sink:
                // .sink(new ChunkingSink(sink, 1000))
                //paths config
                .condition(exclude(
                        requestTo("/health"),
                        requestTo("/admin/**"),
                        contentType("application/octet-stream"),
                        header("X-Secret", newHashSet("1", "true")::contains)))
                //filtering config
                .requestFilter(RequestFilters.replaceBody(message -> contentType("audio/*").test(message) ? "mmh mmh mmh mmh" : null))
                .responseFilter(ResponseFilters.replaceBody(message -> contentType("*/*-stream").test(message) ? "It just keeps going and going..." : null))
                //Change the response for the message
//                .responseFilter(ResponseFilters.replaceBody(message -> contentType("application/json").test(message) ? "This is json..." : null))
                //Info filtering
                .queryFilter(accessToken())
                .queryFilter(replaceQuery("password", "<secret>"))
                .headerFilter(authorization())
                .headerFilter(eachHeader((name, value) -> "X-Secret".equalsIgnoreCase(name) ? "<secret>" : value))
                .build();

        return logbook;
    }
}
