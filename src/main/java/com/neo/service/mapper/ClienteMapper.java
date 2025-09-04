package com.neo.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.neo.domain.Cliente;
import com.neo.dto.AtualizarClienteDto;
import com.neo.dto.ClienteDto;

@Component
@Mapper(componentModel = "spring")
public interface ClienteMapper {

	ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy  = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idCliente", ignore = true)
    void dtoToCliente(AtualizarClienteDto atualizarClienteDto, @MappingTarget Cliente entity);
    
    @Mapping(target = "idCliente", ignore = true)
    Cliente dtoToCliente(ClienteDto clienteDto);
    
    @Mapping(target = "idade", ignore = true)
    ClienteDto clienteToDto(Cliente entity);
    
}