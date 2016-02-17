package com.asercao.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asercao.domain.Achat;
import com.asercao.repository.AchatRepository;
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
 * REST controller for managing Achat.
 */
@RestController
@RequestMapping("/api")
public class AchatResource {

    private final Logger log = LoggerFactory.getLogger(AchatResource.class);

    @Inject
    private AchatRepository achatRepository;

    /**
     * POST  /achats -> Create a new achat.
     */
    @RequestMapping(value = "/achats",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Achat achat) throws URISyntaxException {
        log.debug("REST request to save Achat : {}", achat);
        if (achat.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new achat cannot already have an ID").build();
        }
        achatRepository.save(achat);
        return ResponseEntity.created(new URI("/api/achats/" + achat.getId())).build();
    }

    /**
     * PUT  /achats -> Updates an existing achat.
     */
    @RequestMapping(value = "/achats",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Achat achat) throws URISyntaxException {
        log.debug("REST request to update Achat : {}", achat);
        if (achat.getId() == null) {
            return create(achat);
        }
        achatRepository.save(achat);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /achats -> get all the achats.
     */
    @RequestMapping(value = "/achats",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Achat>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Achat> page = achatRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/achats", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /achats/:id -> get the "id" achat.
     */
    @RequestMapping(value = "/achats/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Achat> get(@PathVariable Long id) {
        log.debug("REST request to get Achat : {}", id);
        return Optional.ofNullable(achatRepository.findOne(id))
            .map(achat -> new ResponseEntity<>(
                achat,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /achats/affaire/:id -> get the achatss of the "id" affaire.
     */
    @RequestMapping(value = "/achats/affaire/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Achat>> getByAffaire(@PathVariable Long id) {
        log.debug("REST request to get Interventions of Salarie : {}", id);
        return Optional.ofNullable(achatRepository.findByAffaireId(id))
            .map(achat -> new ResponseEntity<>(
                achat,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /achats/:id -> delete the "id" achat.
     */
    @RequestMapping(value = "/achats/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Achat : {}", id);
        achatRepository.delete(id);
    }
}
