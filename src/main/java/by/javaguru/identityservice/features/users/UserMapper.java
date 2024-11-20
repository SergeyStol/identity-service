package by.javaguru.identityservice.features.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * @author Sergey Stol
 * 2024-11-18
 */
@Mapper(
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface UserMapper {
   @Mapping(target = "password", expression = "java(hashedPassword)")
   User toEntity(NewUserDto newUserDto, String hashedPassword);
}
