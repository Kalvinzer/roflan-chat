package kvinz.roflanchat.controller;

import kvinz.roflanchat.controller.error_handler.custom_errors.InvalidTokenExeption;
import kvinz.roflanchat.mapper.VerificationTokenDTOMapper;
import kvinz.roflanchat.model.Token;
import kvinz.roflanchat.domain.User;
import kvinz.roflanchat.domain.VerificationToken;
import kvinz.roflanchat.jwt.JwtProvider;
import kvinz.roflanchat.controller.error_handler.custom_errors.BadRequestException;
import kvinz.roflanchat.mapper.UserMapper;
import kvinz.roflanchat.model.UserDTO;
import kvinz.roflanchat.model.VerificationTokenDTO;
import kvinz.roflanchat.service.TokenService;
import kvinz.roflanchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Calendar;

@RestController
@CrossOrigin
public class RegistrationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    TokenService tokenService;



    @PostMapping("/registration")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String addUser(@Valid @RequestBody UserDTO userForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Validation errors");
        }
        if (!userForm.getPassword().equals(userForm.getMatchingPassword())){
            throw new BadRequestException("Passwords are not matching");
        }
        userService.registerUser(userForm);

        return "ok";
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token) {

        VerificationToken verificationToken = tokenService.findByToken(token);
        if (verificationToken == null) {
            throw new InvalidTokenExeption("No such token");
        }

        User user = verificationToken.getUsers();
        Calendar cal = Calendar.getInstance();
        if (verificationToken.getExpiryDate().getTime() - cal.getTime().getTime() <= 0) {
            throw new InvalidTokenExeption("Token expired");
        }

        userService.activateUser(user);
        return "ok";
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public Token auth(@RequestBody UserDTO userDTO){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
        }catch (Exception e){
            throw new BadRequestException("Incorrect credentials");
        }

        return new Token(jwtProvider.generateToken(userDTO.getEmail()));
    }

}
