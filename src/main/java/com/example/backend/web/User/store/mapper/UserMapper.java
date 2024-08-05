package com.example.backend.web.User.store.mapper;


import com.example.backend.utils.general.BaseMapper;
import com.example.backend.web.User.store.UserEntity;
import com.example.backend.web.User.store.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BaseMapper.class})
public interface UserMapper {
    @Mapping(target = "advertisements", qualifiedByName = {"BaseMapper", "getAllAdvertisements"})
    @Mapping(target = "image", qualifiedByName = {"BaseMapper", "getImageUrl"})
    UserDTO userMapperDTO(UserEntity user);
    @Mapping(target = "image", qualifiedByName = {"BaseMapper", "getImageUrl"})
    UserImageUpdateInfoDTO userMapperImageUpdateInfoDTO(UserEntity user);
    @Mapping(target = "image", qualifiedByName = {"BaseMapper", "getImageUrl"})
    UserInfoDTO userMapperInfoDTO(UserEntity user);
    @Mapping(target = "roles", qualifiedByName = {"BaseMapper", "getEnumRole"})
    @Mapping(target = "status", qualifiedByName = {"BaseMapper", "getEnumRegisterAuthStatus"},
                source = "registerAuthStatus")
    UserSecurityDTO userMapperSecurityDTO(UserEntity user);
    UserUpdateInfoDTO userMapperUpdateInfoDTO(UserEntity user);
}