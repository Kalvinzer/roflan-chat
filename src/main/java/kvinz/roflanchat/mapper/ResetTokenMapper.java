package kvinz.roflanchat.mapper;

import kvinz.roflanchat.domain.ResetToken;
import kvinz.roflanchat.model.ResetTokenDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResetTokenMapper {

    ResetTokenMapper INSTANCE = Mappers.getMapper(ResetTokenMapper.class);

    ResetTokenDTO toResetTokenDTO(ResetToken resetToken);
    ResetToken toResetToken(ResetTokenDTO resetToken);
}
