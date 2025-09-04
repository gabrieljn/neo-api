package com.neo.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.neo.domain.Usuario;
import com.neo.dto.UsuarioDto;

@Component
@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

	Usuario dtoToUsuario(UsuarioDto usuarioDto);

}