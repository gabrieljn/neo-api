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

    // Atualiza entidade a partir de DTO de atualização
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void dtoToCliente(AtualizarClienteDto atualizarClienteDto, @MappingTarget Cliente entity);

    // Converte DTO para entidade
    Cliente dtoToCliente(ClienteDto clienteDto);

    // Converte entidade para DTO
    ClienteDto clienteToDto(Cliente entity);
}