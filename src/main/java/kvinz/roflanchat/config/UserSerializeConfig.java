package kvinz.roflanchat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class UserSerializeConfig {

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        FilterProvider filters = filterProvider.addFilter("userFilter", SimpleBeanPropertyFilter.serializeAll());
        objectMapper.setFilterProvider(filterProvider);

        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }
}
