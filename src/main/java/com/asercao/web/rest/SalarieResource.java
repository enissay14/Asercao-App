package com.asercao.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asercao.domain.Salarie;
import com.asercao.repository.SalarieRepository;
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
 * REST controller for managing Salarie.
 */
@RestController
@RequestMapping("/api")
public class SalarieResource {

    private final Logger log = LoggerFactory.getLogger(SalarieResource.class);

    @Inject
    private SalarieRepository salarieRepository;

    /**
     * POST  /salaries -> Create a new salarie.
     */
    @RequestMapping(value = "/salaries",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Salarie salarie) throws URISyntaxException {
        log.debug("REST request to save Salarie : {}", salarie);
        if (salarie.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new salarie cannot already have an ID").build();
        }
        salarieRepository.save(salarie);
        return ResponseEntity.created(new URI("/api/salaries/" + salarie.getId())).build();
    }

    /**
     * PUT  /salaries -> Updates an existing salarie.
     */
    @RequestMapping(value = "/salaries",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Salarie salarie) throws URISyntaxException {
        log.debug("REST request to update Salarie : {}", salarie);
        if (salarie.getId() == null) {
            return create(salarie);
        }
        salarieRepository.save(salarie);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /salaries -> get all the salaries.
     */
    @RequestMapping(value = "/salaries",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Salarie>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Salarie> page = salarieRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/salaries", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /salaries/:id -> get the "id" salarie.
     */
    @RequestMapping(value = "/salaries/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Salarie> get(@PathVariable Long id) {
        log.debug("REST request to get Salarie : {}", id);
        return Optional.ofNullable(salarieRepository.findOne(id))
            .map(salarie -> new ResponseEntity<>(
                salarie,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /salaries/:id -> delete the "id" salarie.
     */
    @RequestMapping(value = "/salaries/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Salarie : {}", id);
        salarieRepository.delete(id);
    }
}
