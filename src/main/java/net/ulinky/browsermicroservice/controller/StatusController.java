package net.ulinky.browsermicroservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static net.ulinky.browsermicroservice.BrowserMicroservice.config;
import static net.ulinky.browsermicroservice.BrowserMicroservice.formatService;

public class StatusController {

    private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

    public static void status(Context context) {
        Map<String, Object> status = new HashMap<>();
        status.put("timestamp", System.currentTimeMillis());
        status.put("config", config);

        // Return pretty printed status json if GET parameter "?pretty=true" is given
        if (context.queryParamAsClass("pretty", Boolean.class).getOrDefault(false)) {
            try {
                context.result(formatService.toPrettyJson(status));
                return;
            } catch (JsonProcessingException e) {
                logger
                    .warn("Failed pretty print status json. Returning default status json instead.",
                        e);
            }
        }

        context.json(status);
    }

}
