package br.com.munif.fraguas.projects.icorretor.repository;

import br.com.munif.fraguas.projects.icorretor.domain.Imovel;
import org.springframework.stereotype.Repository;

import br.com.munif.framework.vicente.application.VicRepository;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Imovel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImovelRepository extends VicRepository<Imovel> {

}
