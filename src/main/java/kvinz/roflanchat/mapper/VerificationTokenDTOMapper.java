package kvinz.roflanchat.mapper;

import kvinz.roflanchat.domain.VerificationToken;
import kvinz.roflanchat.model.VerificationTokenDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VerificationTokenDTOMapper {

    VerificationTokenDTOMapper INSTANCE = Mappers.getMapper(VerificationTokenDTOMapper.class);

    VerificationTokenDTO toVerificationTokenDto(VerificationToken verificationToken);

    VerificationToken toVerificationToken(VerificationTokenDTO verificationToken);
}
