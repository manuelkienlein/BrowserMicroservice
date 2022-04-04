package de.manuelk2000.browsermicroservice.service.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

public class FormatService {

    private final ObjectMapper jsonObjectMapper;
    private final ObjectMapper yamlObjectMapper;

    public FormatService() {
        jsonObjectMapper = new ObjectMapper();

        YAMLFactory yamlFactory = new YAMLFactory();
        yamlFactory.configure(YAMLGenerator.Feature.WRITE_DOC_START_MARKER, false);
        yamlObjectMapper = new ObjectMapper(yamlFactory);
    }

    public String toJson(Object object) throws JsonProcessingException {
        return jsonObjectMapper.writeValueAsString(object);
    }

    public String toPrettyJson(Object object) throws JsonProcessingException {
        return jsonObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }
}
