package kvinz.roflanchat.service;

import kvinz.roflanchat.controller.error_handler.custom_errors.UserNotFoundException;
import kvinz.roflanchat.domain.User;
import kvinz.roflanchat.domain.VerificationToken;
import kvinz.roflanchat.jwt.JwtProvider;
import kvinz.roflanchat.controller.error_handler.custom_errors.BadRequestException;
import kvinz.roflanchat.controller.error_handler.custom_errors.InvalidTokenExeption;
import kvinz.roflanchat.domain.Role;
import kvinz.roflanchat.mapper.UserMapper;
import kvinz.roflanchat.mapper.VerificationTokenDTOMapper;
import kvinz.roflanchat.model.UserDTO;
import kvinz.roflanchat.model.VerificationTokenDTO;
import kvinz.roflanchat.repository.RoleRepository;
import kvinz.roflanchat.repository.UserRepository;
import kvinz.roflanchat.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    TokenService tokenService;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JwtProvider jwtProvider;


    @Autowired
    EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        System.out.println(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public UserDTO findUserByEmail(String email) {
        UserDTO userDTO = userMapper.INSTANCE
                .userInfoToUserInfoDTO(userRepository.findByEmail(email));
        if (userDTO == null) {
            throw new BadRequestException("No user with this email");
        }
        return userDTO;
    }

    public UserDTO findUserById(Long userId) {
        return userMapper.INSTANCE
                .userInfoToUserInfoDTO(userRepository.findById(userId).orElse(new User()));
    }

    public List<UserDTO> allUsers() {
        return userMapper.INSTANCE
                .userInfoListToUserInfoDTOList(userRepository.findAll());
    }

    public void registerUser(UserDTO user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new BadRequestException("User with this email already exist");
        }
        user.setEnabled(false);
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User userFromDB = userMapper.INSTANCE.userInfoDTOToUserInfo(user);
        userFromDB = userRepository.save(userFromDB);
        VerificationToken verificationToken = VerificationTokenDTOMapper.
                INSTANCE.toVerificationToken(new VerificationTokenDTO());
        System.out.println(verificationToken);
        userFromDB.setToken(verificationToken);
        System.out.println(userFromDB.getToken());
        verificationToken.setUsers(userFromDB);
        userFromDB = userRepository.save(userFromDB);
        emailService.sendVerificationEmail(userFromDB);
    }

    public void saveUser(UserDTO userDTO) {
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userMapper.INSTANCE.userInfoDTOToUserInfo(userDTO));
    }


    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public void activateUser(User user) {
        user.setEnabled(true);
        user.setResetToken(null);
        userRepository.save(user);
    }
}
