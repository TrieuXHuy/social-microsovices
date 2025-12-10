package com.ecommerce.user_service.service.mapper;

import com.ecommerce.user_service.domain.User;
import com.ecommerce.user_service.dto.user.UserCreationDTO;
import com.ecommerce.user_service.dto.user.UserResponseDTO;
import com.ecommerce.user_service.dto.user.UserUpdateDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toResponseDto(User user);

    User toEntityFromCreationDto(UserCreationDTO userCreationDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", ignore = true)
    void partialUpdateFromUpdateDto(@MappingTarget User entity, UserUpdateDTO dto);
}