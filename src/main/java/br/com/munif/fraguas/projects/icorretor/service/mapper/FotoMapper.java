package br.com.munif.fraguas.projects.icorretor.service.mapper;

import br.com.munif.fraguas.projects.icorretor.domain.*;
import br.com.munif.fraguas.projects.icorretor.service.dto.FotoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Foto and its DTO FotoDTO.
 */
@Mapper(componentModel = "spring", uses = {ImovelMapper.class, })
public interface FotoMapper extends EntityMapper <FotoDTO, Foto> {

    @Mapping(source = "imovel.id", target = "imovelId")
    FotoDTO toDto(Foto foto); 

    @Mapping(source = "imovelId", target = "imovel")
    Foto toEntity(FotoDTO fotoDTO); 
    default Foto fromId(String id) {
        if (id == null) {
            return null;
        }
        Foto foto = new Foto();
        foto.setId(id);
        return foto;
    }
}
