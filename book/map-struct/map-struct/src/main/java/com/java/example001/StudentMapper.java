package com.java.example001;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentModel entityToModel(StudentEntity studentEntity);
    StudentEntity modelToEntity(StudentModel studentModel);

}