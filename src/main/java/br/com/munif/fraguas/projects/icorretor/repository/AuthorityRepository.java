package br.com.munif.fraguas.projects.icorretor.repository;

import br.com.munif.fraguas.projects.icorretor.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
