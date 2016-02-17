package com.asercao.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asercao.domain.Montantcorrige;
import com.asercao.repository.MontantcorrigeRepository;
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
 * REST controller for managing Montantcorrige.
 */
@RestController
@RequestMapping("/api")
public class MontantcorrigeResource {

    private final Logger log = LoggerFactory.getLogger(MontantcorrigeResource.class);

    @Inject
    private MontantcorrigeRepository montantcorrigeRepository;

    /**
     * POST  /montantcorriges -> Create a new montantcorrige.
     */
    @RequestMapping(value = "/montantcorriges",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Montantcorrige montantcorrige) throws URISyntaxException {
        log.debug("REST request to save Montantcorrige : {}", montantcorrige);
        if (montantcorrige.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new montantcorrige cannot already have an ID").build();
        }
        montantcorrigeRepository.save(montantcorrige);
        return ResponseEntity.created(new URI("/api/montantcorriges/" + montantcorrige.getId())).build();
    }

    /**
     * PUT  /montantcorriges -> Updates an existing montantcorrige.
     */
    @RequestMapping(value = "/montantcorriges",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Montantcorrige montantcorrige) throws URISyntaxException {
        log.debug("REST request to update Montantcorrige : {}", montantcorrige);
        if (montantcorrige.getId() == null) {
            return create(montantcorrige);
        }
        montantcorrigeRepository.save(montantcorrige);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /montantcorriges -> get all the montantcorriges.
     */
    @RequestMapping(value = "/montantcorriges",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Montantcorrige> getAll() {
        log.debug("REST request to get all Montantcorriges");
        return montantcorrigeRepository.findAll();
    }

    /**
     * GET  /montantcorriges/:id -> get the "id" montantcorrige.
     */
    @RequestMapping(value = "/montantcorriges/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Montantcorrige> get(@PathVariable Long id) {
        log.debug("REST request to get Montantcorrige : {}", id);
        return Optional.ofNullable(montantcorrigeRepository.findOne(id))
            .map(montantcorrige -> new ResponseEntity<>(
                montantcorrige,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /montantcorriges/affaire/:id -> get the montantconrriges of the "id" affaire.
     */
    @RequestMapping(value = "/montantcorriges/affaire/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Montantcorrige>> getByAffaire(@PathVariable Long id) {
        log.debug("REST request to get Interventions of Salarie : {}", id);
        return Optional.ofNullable(montantcorrigeRepository.findByAffaireId(id))
            .map(montantcorrige -> new ResponseEntity<>(
                montantcorrige,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /montantcorriges/:id -> delete the "id" montantcorrige.
     */
    @RequestMapping(value = "/montantcorriges/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Montantcorrige : {}", id);
        montantcorrigeRepository.delete(id);
    }
}
