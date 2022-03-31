package de.manuelk2000.browsermicroservice.controller;

import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

import static de.manuelk2000.browsermicroservice.BrowserMicroservice.config;

public class StatusController {

    public static void status(Context context) {
        Map<String, Object> status = new HashMap<>();
        status.put("timestamp", System.currentTimeMillis());
        status.put("config", config);
        context.json(status);
    }

}
