package com.asercao.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asercao.domain.Typeaffaire;
import com.asercao.repository.TypeaffaireRepository;
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
 * REST controller for managing Typeaffaire.
 */
@RestController
@RequestMapping("/api")
public class TypeaffaireResource {

    private final Logger log = LoggerFactory.getLogger(TypeaffaireResource.class);

    @Inject
    private TypeaffaireRepository typeaffaireRepository;

    /**
     * POST  /typeaffaires -> Create a new typeaffaire.
     */
    @RequestMapping(value = "/typeaffaires",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Typeaffaire typeaffaire) throws URISyntaxException {
        log.debug("REST request to save Typeaffaire : {}", typeaffaire);
        if (typeaffaire.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new typeaffaire cannot already have an ID").build();
        }
        typeaffaireRepository.save(typeaffaire);
        return ResponseEntity.created(new URI("/api/typeaffaires/" + typeaffaire.getId())).build();
    }

    /**
     * PUT  /typeaffaires -> Updates an existing typeaffaire.
     */
    @RequestMapping(value = "/typeaffaires",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Typeaffaire typeaffaire) throws URISyntaxException {
        log.debug("REST request to update Typeaffaire : {}", typeaffaire);
        if (typeaffaire.getId() == null) {
            return create(typeaffaire);
        }
        typeaffaireRepository.save(typeaffaire);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /typeaffaires -> get all the typeaffaires.
     */
    @RequestMapping(value = "/typeaffaires",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Typeaffaire>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Typeaffaire> page = typeaffaireRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/typeaffaires", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /typeaffaires/:id -> get the "id" typeaffaire.
     */
    @RequestMapping(value = "/typeaffaires/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Typeaffaire> get(@PathVariable Long id) {
        log.debug("REST request to get Typeaffaire : {}", id);
        return Optional.ofNullable(typeaffaireRepository.findOne(id))
            .map(typeaffaire -> new ResponseEntity<>(
                typeaffaire,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /typeaffaires/:id -> delete the "id" typeaffaire.
     */
    @RequestMapping(value = "/typeaffaires/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Typeaffaire : {}", id);
        typeaffaireRepository.delete(id);
    }
}
