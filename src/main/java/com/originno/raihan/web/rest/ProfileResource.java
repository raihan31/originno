package com.originno.raihan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.originno.raihan.domain.Profile;
import com.originno.raihan.repository.ProfileRepository;
import com.originno.raihan.repository.search.ProfileSearchRepository;
import com.originno.raihan.web.rest.util.HeaderUtil;
import com.originno.raihan.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Profile.
 */
@RestController
@RequestMapping("/api")
public class ProfileResource {

    private final Logger log = LoggerFactory.getLogger(ProfileResource.class);

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private ProfileSearchRepository profileSearchRepository;

    /**
     * POST  /profiles -> Create a new profile.
     */
    @RequestMapping(value = "/profiles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) throws URISyntaxException {
        log.debug("REST request to save Profile : {}", profile);
        if (profile.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new profile cannot already have an ID").body(null);
        }
        Profile result = profileRepository.save(profile);
        profileSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("profile", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profiles -> Updates an existing profile.
     */
    @RequestMapping(value = "/profiles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile) throws URISyntaxException {
        log.debug("REST request to update Profile : {}", profile);
        if (profile.getId() == null) {
            return createProfile(profile);
        }
        Profile result = profileRepository.save(profile);
        profileSearchRepository.save(profile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("profile", profile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profiles -> get all the profiles.
     */
    @RequestMapping(value = "/profiles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Profile>> getAllProfiles(Pageable pageable)
        throws URISyntaxException {
        Page<Profile> page = profileRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /profiles/:id -> get the "id" profile.
     */
    @RequestMapping(value = "/profiles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Profile> getProfile(@PathVariable Long id) {
        log.debug("REST request to get Profile : {}", id);
        return Optional.ofNullable(profileRepository.findOne(id))
            .map(profile -> new ResponseEntity<>(
                profile,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /profiles/:id -> delete the "id" profile.
     */
    @RequestMapping(value = "/profiles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        log.debug("REST request to delete Profile : {}", id);
        profileRepository.delete(id);
        profileSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("profile", id.toString())).build();
    }

    /**
     * SEARCH  /_search/profiles/:query -> search for the profile corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/profiles/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Profile> searchProfiles(@PathVariable String query) {
        return StreamSupport
            .stream(profileSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
