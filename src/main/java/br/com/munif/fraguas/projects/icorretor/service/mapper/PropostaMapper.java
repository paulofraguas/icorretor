package br.com.munif.fraguas.projects.icorretor.service.mapper;

import br.com.munif.fraguas.projects.icorretor.domain.*;
import br.com.munif.fraguas.projects.icorretor.service.dto.PropostaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Proposta and its DTO PropostaDTO.
 */
@Mapper(componentModel = "spring", uses = {ImovelMapper.class, })
public interface PropostaMapper extends EntityMapper <PropostaDTO, Proposta> {

    @Mapping(source = "imovel.id", target = "imovelId")
    PropostaDTO toDto(Proposta proposta); 

    @Mapping(source = "imovelId", target = "imovel")
    Proposta toEntity(PropostaDTO propostaDTO); 
    default Proposta fromId(String id) {
        if (id == null) {
            return null;
        }
        Proposta proposta = new Proposta();
        proposta.setId(id);
        return proposta;
    }
}
