package com.asercao.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asercao.domain.Commande;
import com.asercao.repository.CommandeRepository;
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
 * REST controller for managing Commande.
 */
@RestController
@RequestMapping("/api")
public class CommandeResource {

    private final Logger log = LoggerFactory.getLogger(CommandeResource.class);

    @Inject
    private CommandeRepository commandeRepository;

    /**
     * POST  /commandes -> Create a new commande.
     */
    @RequestMapping(value = "/commandes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Commande commande) throws URISyntaxException {
        log.debug("REST request to save Commande : {}", commande);
        if (commande.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new commande cannot already have an ID").build();
        }
        commandeRepository.save(commande);
        return ResponseEntity.created(new URI("/api/commandes/" + commande.getId())).build();
    }

    /**
     * PUT  /commandes -> Updates an existing commande.
     */
    @RequestMapping(value = "/commandes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Commande commande) throws URISyntaxException {
        log.debug("REST request to update Commande : {}", commande);
        if (commande.getId() == null) {
            return create(commande);
        }
        commandeRepository.save(commande);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /commandes -> get all the commandes.
     */
    @RequestMapping(value = "/commandes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Commande>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Commande> page = commandeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commandes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /commandes/:id -> get the "id" commande.
     */
    @RequestMapping(value = "/commandes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Commande> get(@PathVariable Long id) {
        log.debug("REST request to get Commande : {}", id);
        return Optional.ofNullable(commandeRepository.findOne(id))
            .map(commande -> new ResponseEntity<>(
                commande,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /commmandess/affaire/:id -> get the commandes of the "id" affaire.
     */
    @RequestMapping(value = "/commandes/affaire/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Commande>> getByAffaire(@PathVariable Long id) {
        log.debug("REST request to get Interventions of Salarie : {}", id);
        return Optional.ofNullable(commandeRepository.findByAffaireId(id))
            .map(commande -> new ResponseEntity<>(
                commande,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /commandes/:id -> delete the "id" commande.
     */
    @RequestMapping(value = "/commandes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Commande : {}", id);
        commandeRepository.delete(id);
    }
}
