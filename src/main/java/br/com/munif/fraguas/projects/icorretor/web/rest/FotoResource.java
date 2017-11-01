package br.com.munif.fraguas.projects.icorretor.web.rest;

import br.com.munif.framework.vicente.api.BaseAPI;
import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.fraguas.projects.icorretor.domain.Foto;
import com.codahale.metrics.annotation.Timed;
import br.com.munif.fraguas.projects.icorretor.service.FotoService;
import br.com.munif.fraguas.projects.icorretor.web.rest.util.HeaderUtil;
import br.com.munif.fraguas.projects.icorretor.service.dto.FotoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Foto.
 */
@RestController
@RequestMapping("/api/fotos")
public class FotoResource extends BaseAPI<Foto>{

    private final Logger log = LoggerFactory.getLogger(FotoResource.class);

    private static final String ENTITY_NAME = "foto";

    public FotoResource(FotoService service) {
        super(service);
    }


}
