package com.asercao.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asercao.domain.Bonfacture;
import com.asercao.repository.BonfactureRepository;
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
 * REST controller for managing Bonfacture.
 */
@RestController
@RequestMapping("/api")
public class BonfactureResource {

    private final Logger log = LoggerFactory.getLogger(BonfactureResource.class);

    @Inject
    private BonfactureRepository bonfactureRepository;

    /**
     * POST  /bonfactures -> Create a new bonfacture.
     */
    @RequestMapping(value = "/bonfactures",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Bonfacture bonfacture) throws URISyntaxException {
        log.debug("REST request to save Bonfacture : {}", bonfacture);
        if (bonfacture.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bonfacture cannot already have an ID").build();
        }
        bonfactureRepository.save(bonfacture);
        return ResponseEntity.created(new URI("/api/bonfactures/" + bonfacture.getId())).build();
    }

    /**
     * PUT  /bonfactures -> Updates an existing bonfacture.
     */
    @RequestMapping(value = "/bonfactures",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Bonfacture bonfacture) throws URISyntaxException {
        log.debug("REST request to update Bonfacture : {}", bonfacture);
        if (bonfacture.getId() == null) {
            return create(bonfacture);
        }
        bonfactureRepository.save(bonfacture);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /bonfactures -> get all the bonfactures.
     */
    @RequestMapping(value = "/bonfactures",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Bonfacture>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Bonfacture> page = bonfactureRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bonfactures", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bonfactures/:id -> get the "id" bonfacture.
     */
    @RequestMapping(value = "/bonfactures/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Bonfacture> get(@PathVariable Long id) {
        log.debug("REST request to get Bonfacture : {}", id);
        return Optional.ofNullable(bonfactureRepository.findOne(id))
            .map(bonfacture -> new ResponseEntity<>(
                bonfacture,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /bonfactures/affaire/:id -> get the bonfactures of the "id" affaire.
     */
    @RequestMapping(value = "/bonfactures/affaire/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Bonfacture>> getByAffaire(@PathVariable Long id) {
        log.debug("REST request to get Interventions of Salarie : {}", id);
        return Optional.ofNullable(bonfactureRepository.findByAffaireId(id))
            .map(bonfacture -> new ResponseEntity<>(
                bonfacture,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bonfactures/:id -> delete the "id" bonfacture.
     */
    @RequestMapping(value = "/bonfactures/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Bonfacture : {}", id);
        bonfactureRepository.delete(id);
    }
}
