package kvinz.roflanchat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import kvinz.roflanchat.jwt.JwtProvider;
import kvinz.roflanchat.mapper.UserMapper;
import kvinz.roflanchat.model.UserDTO;
import kvinz.roflanchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtProvider jwtProvider;

    @GetMapping("/user/info")
    public String getInfo(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id","email","username"));
        ObjectMapper om = new ObjectMapper();
        om.setFilterProvider(filterProvider);
        UserDTO userDTO = UserMapper.INSTANCE.userInfoToUserInfoDTO(jwtProvider.getLoginFromBearer(token));
        return om.writeValueAsString(userDTO);
    }
}
