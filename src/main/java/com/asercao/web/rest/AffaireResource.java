package com.asercao.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asercao.domain.Affaire;
import com.asercao.repository.AffaireRepository;
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
 * REST controller for managing Affaire.
 */
@RestController
@RequestMapping("/api")
public class AffaireResource {

    private final Logger log = LoggerFactory.getLogger(AffaireResource.class);

    @Inject
    private AffaireRepository affaireRepository;

    /**
     * POST  /affaires -> Create a new affaire.
     */
    @RequestMapping(value = "/affaires",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Affaire affaire) throws URISyntaxException {
        log.debug("REST request to save Affaire : {}", affaire);
        if (affaire.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new affaire cannot already have an ID").build();
        }
        affaireRepository.save(affaire);
        return ResponseEntity.created(new URI("/api/affaires/" + affaire.getId())).build();
    }

    /**
     * PUT  /affaires -> Updates an existing affaire.
     */
    @RequestMapping(value = "/affaires",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Affaire affaire) throws URISyntaxException {
        log.debug("REST request to update Affaire : {}", affaire);
        if (affaire.getId() == null) {
            return create(affaire);
        }
        affaireRepository.save(affaire);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /affaires -> get all the affaires.
     */
    @RequestMapping(value = "/affaires",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Affaire>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Affaire> page = affaireRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/affaires", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /affaires/:id -> get the "id" affaire.
     */
    @RequestMapping(value = "/affaires/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Affaire> get(@PathVariable Long id) {
        log.debug("REST request to get Affaire : {}", id);
        return Optional.ofNullable(affaireRepository.findOne(id))
            .map(affaire -> new ResponseEntity<>(
                affaire,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /affaires/:id -> delete the "id" affaire.
     */
    @RequestMapping(value = "/affaires/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Affaire : {}", id);
        affaireRepository.delete(id);
    }
}
