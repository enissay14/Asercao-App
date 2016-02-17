package com.asercao.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asercao.domain.Typeintervention;
import com.asercao.repository.TypeinterventionRepository;
import com.asercao.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Typeintervention.
 */
@RestController
@RequestMapping("/api")
public class TypeinterventionResource {

    private final Logger log = LoggerFactory.getLogger(TypeinterventionResource.class);

    @Inject
    private TypeinterventionRepository typeinterventionRepository;

    /**
     * POST  /typeinterventions -> Create a new typeintervention.
     */
    @RequestMapping(value = "/typeinterventions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Typeintervention typeintervention) throws URISyntaxException {
        log.debug("REST request to save Typeintervention : {}", typeintervention);
        if (typeintervention.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new typeintervention cannot already have an ID").build();
        }
        typeinterventionRepository.save(typeintervention);
        return ResponseEntity.created(new URI("/api/typeinterventions/" + typeintervention.getId())).build();
    }

    /**
     * PUT  /typeinterventions -> Updates an existing typeintervention.
     */
    @RequestMapping(value = "/typeinterventions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Typeintervention typeintervention) throws URISyntaxException {
        log.debug("REST request to update Typeintervention : {}", typeintervention);
        if (typeintervention.getId() == null) {
            return create(typeintervention);
        }
        typeinterventionRepository.save(typeintervention);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /typeinterventions -> get all the typeinterventions.
     */
    @RequestMapping(value = "/typeinterventions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Typeintervention>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Typeintervention> page = typeinterventionRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/typeinterventions", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /typeinterventions/:id -> get the "id" typeintervention.
     */
    @RequestMapping(value = "/typeinterventions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Typeintervention> get(@PathVariable Long id) {
        log.debug("REST request to get Typeintervention : {}", id);
        return Optional.ofNullable(typeinterventionRepository.findOne(id))
            .map(typeintervention -> new ResponseEntity<>(
                typeintervention,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /typeinterventions/:id -> delete the "id" typeintervention.
     */
    @RequestMapping(value = "/typeinterventions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Typeintervention : {}", id);
        typeinterventionRepository.delete(id);
    }
}
