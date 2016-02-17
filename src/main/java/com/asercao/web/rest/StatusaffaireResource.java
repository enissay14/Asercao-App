package com.asercao.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asercao.domain.Statusaffaire;
import com.asercao.repository.StatusaffaireRepository;
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
 * REST controller for managing Statusaffaire.
 */
@RestController
@RequestMapping("/api")
public class StatusaffaireResource {

    private final Logger log = LoggerFactory.getLogger(StatusaffaireResource.class);

    @Inject
    private StatusaffaireRepository statusaffaireRepository;

    /**
     * POST  /statusaffaires -> Create a new statusaffaire.
     */
    @RequestMapping(value = "/statusaffaires",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Statusaffaire statusaffaire) throws URISyntaxException {
        log.debug("REST request to save Statusaffaire : {}", statusaffaire);
        if (statusaffaire.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new statusaffaire cannot already have an ID").build();
        }
        statusaffaireRepository.save(statusaffaire);
        return ResponseEntity.created(new URI("/api/statusaffaires/" + statusaffaire.getId())).build();
    }

    /**
     * PUT  /statusaffaires -> Updates an existing statusaffaire.
     */
    @RequestMapping(value = "/statusaffaires",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Statusaffaire statusaffaire) throws URISyntaxException {
        log.debug("REST request to update Statusaffaire : {}", statusaffaire);
        if (statusaffaire.getId() == null) {
            return create(statusaffaire);
        }
        statusaffaireRepository.save(statusaffaire);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /statusaffaires -> get all the statusaffaires.
     */
    @RequestMapping(value = "/statusaffaires",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Statusaffaire>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Statusaffaire> page = statusaffaireRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/statusaffaires", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /statusaffaires/:id -> get the "id" statusaffaire.
     */
    @RequestMapping(value = "/statusaffaires/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Statusaffaire> get(@PathVariable Long id) {
        log.debug("REST request to get Statusaffaire : {}", id);
        return Optional.ofNullable(statusaffaireRepository.findOne(id))
            .map(statusaffaire -> new ResponseEntity<>(
                statusaffaire,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /statusaffaires/:id -> delete the "id" statusaffaire.
     */
    @RequestMapping(value = "/statusaffaires/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Statusaffaire : {}", id);
        statusaffaireRepository.delete(id);
    }
}
