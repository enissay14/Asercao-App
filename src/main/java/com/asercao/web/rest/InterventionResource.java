package com.asercao.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asercao.domain.Intervention;
import com.asercao.repository.InterventionRepository;
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
 * REST controller for managing Intervention.
 */
@RestController
@RequestMapping("/api")
public class InterventionResource {

    private final Logger log = LoggerFactory.getLogger(InterventionResource.class);

    @Inject
    private InterventionRepository interventionRepository;

    /**
     * POST  /interventions -> Create a new intervention.
     */
    @RequestMapping(value = "/interventions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Intervention intervention) throws URISyntaxException {
        log.debug("REST request to save Intervention : {}", intervention);
        if (intervention.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new intervention cannot already have an ID").build();
        }
        interventionRepository.save(intervention);
        return ResponseEntity.created(new URI("/api/interventions/" + intervention.getId())).build();
    }

    /**
     * PUT  /interventions -> Updates an existing intervention.
     */
    @RequestMapping(value = "/interventions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Intervention intervention) throws URISyntaxException {
        log.debug("REST request to update Intervention : {}", intervention);
        if (intervention.getId() == null) {
            return create(intervention);
        }
        interventionRepository.save(intervention);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /interventions -> get all the interventions.
     */
    @RequestMapping(value = "/interventions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Intervention>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Intervention> page = interventionRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/interventions", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /interventions/:id -> get the "id" intervention.
     */
    @RequestMapping(value = "/interventions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Intervention> get(@PathVariable Long id) {
        log.debug("REST request to get Intervention : {}", id);
        return Optional.ofNullable(interventionRepository.findOne(id))
            .map(intervention -> new ResponseEntity<>(
                intervention,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /interventions/salarie/:id -> get the interventions of the "id" salarie.
    */
    @RequestMapping(value = "/interventions/salarie/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Intervention>> getBySalarie(@PathVariable Long id) {
        log.debug("REST request to get Interventions of Salarie : {}", id);
        return Optional.ofNullable(interventionRepository.findBySalarieId(id))
            .map(intervention -> new ResponseEntity<>(
                intervention,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /interventions/affaire/:id -> get the interventions of the "id" affaire.
     */
    @RequestMapping(value = "/interventions/affaire/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Intervention>> getByAffaire(@PathVariable Long id) {
        log.debug("REST request to get Interventions of Salarie : {}", id);
        return Optional.ofNullable(interventionRepository.findByAffaireId(id))
            .map(intervention -> new ResponseEntity<>(
                intervention,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /interventions/:id -> delete the "id" intervention.
     */
    @RequestMapping(value = "/interventions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Intervention : {}", id);
        interventionRepository.delete(id);
    }
}
