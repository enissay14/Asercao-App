package com.asercao.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asercao.domain.Pointage;
import com.asercao.repository.PointageRepository;
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
 * REST controller for managing Pointage.
 */
@RestController
@RequestMapping("/api")
public class PointageResource {

    private final Logger log = LoggerFactory.getLogger(PointageResource.class);

    @Inject
    private PointageRepository pointageRepository;

    /**
     * POST  /pointages -> Create a new pointage.
     */
    @RequestMapping(value = "/pointages",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Pointage pointage) throws URISyntaxException {
        log.debug("REST request to save Pointage : {}", pointage);
        if (pointage.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pointage cannot already have an ID").build();
        }
        pointageRepository.save(pointage);
        return ResponseEntity.created(new URI("/api/pointages/" + pointage.getId())).build();
    }

    /**
     * PUT  /pointages -> Updates an existing pointage.
     */
    @RequestMapping(value = "/pointages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Pointage pointage) throws URISyntaxException {
        log.debug("REST request to update Pointage : {}", pointage);
        if (pointage.getId() == null) {
            return create(pointage);
        }
        pointageRepository.save(pointage);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /pointages -> get all the pointages.
     */
    @RequestMapping(value = "/pointages",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Pointage>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Pointage> page = pointageRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pointages", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pointages/:id -> get the "id" pointage.
     */
    @RequestMapping(value = "/pointages/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pointage> get(@PathVariable Long id) {
        log.debug("REST request to get Pointage : {}", id);
        return Optional.ofNullable(pointageRepository.findOne(id))
            .map(pointage -> new ResponseEntity<>(
                pointage,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /pointages/salarie/:id -> get the pointages of the "id" salarie.
     */
    @RequestMapping(value = "/pointages/salarie/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Pointage>> getBySalarie(@PathVariable Long id) {
        log.debug("REST request to get Pointages of Salarie : {}", id);
        return Optional.ofNullable(pointageRepository.findBySalarieId(id))
            .map(pointage -> new ResponseEntity<>(
                pointage,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pointages/:id -> delete the "id" pointage.
     */
    @RequestMapping(value = "/pointages/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Pointage : {}", id);
        pointageRepository.delete(id);
    }
}
