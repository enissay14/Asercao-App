package com.asercao.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asercao.domain.Typeachat;
import com.asercao.repository.TypeachatRepository;
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
 * REST controller for managing Typeachat.
 */
@RestController
@RequestMapping("/api")
public class TypeachatResource {

    private final Logger log = LoggerFactory.getLogger(TypeachatResource.class);

    @Inject
    private TypeachatRepository typeachatRepository;

    /**
     * POST  /typeachats -> Create a new typeachat.
     */
    @RequestMapping(value = "/typeachats",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Typeachat typeachat) throws URISyntaxException {
        log.debug("REST request to save Typeachat : {}", typeachat);
        if (typeachat.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new typeachat cannot already have an ID").build();
        }
        typeachatRepository.save(typeachat);
        return ResponseEntity.created(new URI("/api/typeachats/" + typeachat.getId())).build();
    }

    /**
     * PUT  /typeachats -> Updates an existing typeachat.
     */
    @RequestMapping(value = "/typeachats",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Typeachat typeachat) throws URISyntaxException {
        log.debug("REST request to update Typeachat : {}", typeachat);
        if (typeachat.getId() == null) {
            return create(typeachat);
        }
        typeachatRepository.save(typeachat);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /typeachats -> get all the typeachats.
     */
    @RequestMapping(value = "/typeachats",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Typeachat>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Typeachat> page = typeachatRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/typeachats", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /typeachats/:id -> get the "id" typeachat.
     */
    @RequestMapping(value = "/typeachats/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Typeachat> get(@PathVariable Long id) {
        log.debug("REST request to get Typeachat : {}", id);
        return Optional.ofNullable(typeachatRepository.findOne(id))
            .map(typeachat -> new ResponseEntity<>(
                typeachat,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /typeachats/:id -> delete the "id" typeachat.
     */
    @RequestMapping(value = "/typeachats/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Typeachat : {}", id);
        typeachatRepository.delete(id);
    }
}
