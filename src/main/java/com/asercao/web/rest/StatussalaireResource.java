package com.asercao.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asercao.domain.Statussalaire;
import com.asercao.repository.StatussalaireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Statussalaire.
 */
@RestController
@RequestMapping("/api")
public class StatussalaireResource {

    private final Logger log = LoggerFactory.getLogger(StatussalaireResource.class);

    @Inject
    private StatussalaireRepository statussalaireRepository;

    /**
     * POST  /statussalaires -> Create a new statussalaire.
     */
    @RequestMapping(value = "/statussalaires",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Statussalaire statussalaire) throws URISyntaxException {
        log.debug("REST request to save Statussalaire : {}", statussalaire);
        if (statussalaire.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new statussalaire cannot already have an ID").build();
        }
        statussalaireRepository.save(statussalaire);
        return ResponseEntity.created(new URI("/api/statussalaires/" + statussalaire.getId())).build();
    }

    /**
     * PUT  /statussalaires -> Updates an existing statussalaire.
     */
    @RequestMapping(value = "/statussalaires",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Statussalaire statussalaire) throws URISyntaxException {
        log.debug("REST request to update Statussalaire : {}", statussalaire);
        if (statussalaire.getId() == null) {
            return create(statussalaire);
        }
        statussalaireRepository.save(statussalaire);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /statussalaires -> get all the statussalaires.
     */
    @RequestMapping(value = "/statussalaires",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Statussalaire> getAll() {
        log.debug("REST request to get all Statussalaires");
        return statussalaireRepository.findAll();
    }

    /**
     * GET  /statussalaires/:id -> get the "id" statussalaire.
     */
    @RequestMapping(value = "/statussalaires/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Statussalaire> get(@PathVariable Long id) {
        log.debug("REST request to get Statussalaire : {}", id);
        return Optional.ofNullable(statussalaireRepository.findOne(id))
            .map(statussalaire -> new ResponseEntity<>(
                statussalaire,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /statussalaires/:id -> delete the "id" statussalaire.
     */
    @RequestMapping(value = "/statussalaires/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Statussalaire : {}", id);
        statussalaireRepository.delete(id);
    }
}
