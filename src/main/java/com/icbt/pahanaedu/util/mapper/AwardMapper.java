package com.icbt.pahanaedu.util.mapper;

import com.icbt.pahanaedu.dto.AwardDto;
import com.icbt.pahanaedu.entity.Award;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AwardMapper {
    Award toEntity(AwardDto awardDto);
    AwardDto toDto(Award award);

    List<AwardDto> toDtoList(List<Award> awards);
}
