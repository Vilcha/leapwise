package com.vilim.leapwise.mapper;

import com.vilim.leapwise.model.entity.ExpressionEntity;
import com.vilim.leapwise.model.request.ExpressionSaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpressionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "expression", source = "value")
    ExpressionEntity toExpressionEntity(ExpressionSaveRequest request);
}
