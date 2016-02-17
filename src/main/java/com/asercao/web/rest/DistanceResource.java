package com.asercao.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asercao.domain.Distance;
import com.asercao.repository.DistanceRepository;
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
 * REST controller for managing Distance.
 */
@RestController
@RequestMapping("/api")
public class DistanceResource {

    private final Logger log = LoggerFactory.getLogger(DistanceResource.class);

    @Inject
    private DistanceRepository distanceRepository;

    /**
     * POST  /distances -> Create a new distance.
     */
    @RequestMapping(value = "/distances",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Distance distance) throws URISyntaxException {
        log.debug("REST request to save Distance : {}", distance);
        if (distance.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new distance cannot already have an ID").build();
        }
        distanceRepository.save(distance);
        return ResponseEntity.created(new URI("/api/distances/" + distance.getId())).build();
    }

    /**
     * PUT  /distances -> Updates an existing distance.
     */
    @RequestMapping(value = "/distances",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Distance distance) throws URISyntaxException {
        log.debug("REST request to update Distance : {}", distance);
        if (distance.getId() == null) {
            return create(distance);
        }
        distanceRepository.save(distance);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /distances -> get all the distances.
     */
    @RequestMapping(value = "/distances",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Distance>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Distance> page = distanceRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/distances", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /distances/:id -> get the "id" distance.
     */
    @RequestMapping(value = "/distances/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Distance> get(@PathVariable Long id) {
        log.debug("REST request to get Distance : {}", id);
        return Optional.ofNullable(distanceRepository.findOne(id))
            .map(distance -> new ResponseEntity<>(
                distance,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /distances/:id -> delete the "id" distance.
     */
    @RequestMapping(value = "/distances/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Distance : {}", id);
        distanceRepository.delete(id);
    }
}
