package kvinz.roflanchat.mapper;

import kvinz.roflanchat.domain.User;
import kvinz.roflanchat.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userInfoToUserInfoDTO(User user);
    User userInfoDTOToUserInfo(UserDTO userDTO);
    List<UserDTO> userInfoListToUserInfoDTOList(List<User> users);
    List<User> userInfoDTOListToUserInfoList(List<UserDTO> userInfos);
}
