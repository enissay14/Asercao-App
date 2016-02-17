package com.asercao.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asercao.domain.Typepointage;
import com.asercao.repository.TypepointageRepository;
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
 * REST controller for managing Typepointage.
 */
@RestController
@RequestMapping("/api")
public class TypepointageResource {

    private final Logger log = LoggerFactory.getLogger(TypepointageResource.class);

    @Inject
    private TypepointageRepository typepointageRepository;

    /**
     * POST  /typepointages -> Create a new typepointage.
     */
    @RequestMapping(value = "/typepointages",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Typepointage typepointage) throws URISyntaxException {
        log.debug("REST request to save Typepointage : {}", typepointage);
        if (typepointage.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new typepointage cannot already have an ID").build();
        }
        typepointageRepository.save(typepointage);
        return ResponseEntity.created(new URI("/api/typepointages/" + typepointage.getId())).build();
    }

    /**
     * PUT  /typepointages -> Updates an existing typepointage.
     */
    @RequestMapping(value = "/typepointages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Typepointage typepointage) throws URISyntaxException {
        log.debug("REST request to update Typepointage : {}", typepointage);
        if (typepointage.getId() == null) {
            return create(typepointage);
        }
        typepointageRepository.save(typepointage);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /typepointages -> get all the typepointages.
     */
    @RequestMapping(value = "/typepointages",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Typepointage>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Typepointage> page = typepointageRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/typepointages", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /typepointages/:id -> get the "id" typepointage.
     */
    @RequestMapping(value = "/typepointages/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Typepointage> get(@PathVariable Long id) {
        log.debug("REST request to get Typepointage : {}", id);
        return Optional.ofNullable(typepointageRepository.findOne(id))
            .map(typepointage -> new ResponseEntity<>(
                typepointage,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /typepointages/:id -> delete the "id" typepointage.
     */
    @RequestMapping(value = "/typepointages/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Typepointage : {}", id);
        typepointageRepository.delete(id);
    }
}
