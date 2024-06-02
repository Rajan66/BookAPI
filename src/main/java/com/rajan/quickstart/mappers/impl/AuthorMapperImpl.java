package com.rajan.quickstart.mappers.impl;

import com.rajan.quickstart.domain.dto.AuthorDto;
import com.rajan.quickstart.domain.entities.AuthorEntity;
import com.rajan.quickstart.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);

    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
