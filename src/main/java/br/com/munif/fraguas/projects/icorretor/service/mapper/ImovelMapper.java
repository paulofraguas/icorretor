package br.com.munif.fraguas.projects.icorretor.service.mapper;

import br.com.munif.fraguas.projects.icorretor.domain.*;
import br.com.munif.fraguas.projects.icorretor.service.dto.ImovelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Imovel and its DTO ImovelDTO.
 */
@Mapper(componentModel = "spring", uses = {CorretorMapper.class, })
public interface ImovelMapper extends EntityMapper <ImovelDTO, Imovel> {

    @Mapping(source = "corretor.id", target = "corretorId")
    ImovelDTO toDto(Imovel imovel); 
    @Mapping(target = "fotos", ignore = true)
    @Mapping(target = "propostas", ignore = true)

    @Mapping(source = "corretorId", target = "corretor")
    Imovel toEntity(ImovelDTO imovelDTO); 
    default Imovel fromId(String id) {
        if (id == null) {
            return null;
        }
        Imovel imovel = new Imovel();
        imovel.setId(id);
        return imovel;
    }
}
