package br.com.munif.fraguas.projects.icorretor.web.rest;

import br.com.munif.framework.vicente.api.BaseAPI;
import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.fraguas.projects.icorretor.domain.Corretor;
import com.codahale.metrics.annotation.Timed;
import br.com.munif.fraguas.projects.icorretor.service.CorretorService;
import br.com.munif.fraguas.projects.icorretor.web.rest.util.HeaderUtil;
import br.com.munif.fraguas.projects.icorretor.service.dto.CorretorDTO;
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
 * REST controller for managing Corretor.
 */
@RestController
@RequestMapping("/api/corretors")
public class CorretorResource extends BaseAPI<Corretor>{

    private final Logger log = LoggerFactory.getLogger(CorretorResource.class);

    private static final String ENTITY_NAME = "corretor";

    public CorretorResource(CorretorService service) {
        super(service);
    }


}
