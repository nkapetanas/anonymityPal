package com.research.privacy.anonymity.pal.api;

import com.research.privacy.anonymity.pal.api.params.CustomQueryParams;
import com.research.privacy.anonymity.pal.api.response.QueryResultsJson;
import com.research.privacy.anonymity.pal.api.response.QueryResultsResponseJson;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import com.research.privacy.anonymity.pal.services.LooselyCoupledPrivacyPreservationService;
import com.research.privacy.anonymity.pal.services.PrestoService;
import com.research.privacy.anonymity.pal.services.customquery.CustomQueryBuilderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/privacy")
public class QueriesRestService {

    private final PrestoService prestoService;
    private final CustomQueryBuilderService customQueryBuilderService;
    private final LooselyCoupledPrivacyPreservationService looselyCoupledPrivacyPreservationService;

    public QueriesRestService(PrestoService prestoService, CustomQueryBuilderService customQueryBuilderService, LooselyCoupledPrivacyPreservationService looselyCoupledPrivacyPreservationService) {
        this.prestoService = prestoService;
        this.customQueryBuilderService = customQueryBuilderService;
        this.looselyCoupledPrivacyPreservationService = looselyCoupledPrivacyPreservationService;
    }

    @GetMapping("/getQueryResultsPrivacyChecked")
    @ResponseStatus()
    public ResponseEntity<QueryResultsResponseJson> getQueryResultsPrivacyChecked(@RequestParam String query) {
        if (Utils.isEmpty(query)) {
            ResponseEntity.ok(new ArrayList<>());
        }

        QueryResultsResponseJson queryResults;
        try {
            queryResults = prestoService.getQueryResultsPrivacyChecked(query);
        } catch (AnonymityPalException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(queryResults);
    }

    @GetMapping("/getQueryResults")
    @ResponseStatus()
    public ResponseEntity<QueryResultsResponseJson> getQueryResults(@RequestParam String query) {
        if (Utils.isEmpty(query)) {
            ResponseEntity.ok(new ArrayList<>());
        }

        QueryResultsResponseJson queryResults;
        try {
            final String decodedQuery = URLDecoder.decode(query, "UTF-8");
            queryResults = prestoService.getQueryResultsSimple(decodedQuery);
        } catch (AnonymityPalException | UnsupportedEncodingException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(queryResults);
    }


    @GetMapping("/getCustomQueryResults")
    @ResponseStatus()
    public ResponseEntity<QueryResultsResponseJson> getCustomQueryResults(@RequestBody CustomQueryParams customQueryParams) {
        if (Utils.isEmpty(customQueryParams)) {
            ResponseEntity.ok(new ArrayList<>());
        }

        QueryResultsResponseJson queryResults;
        try {
            final String customQuery = customQueryBuilderService.buildQuery(customQueryParams);
            queryResults = prestoService.getQueryResultsSimple(customQuery);
        } catch (AnonymityPalException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(queryResults);
    }

    @GetMapping("/checkPrivacyPreservation")
    @ResponseStatus()
    public ResponseEntity<QueryResultsJson> checkPrivacyPreservation(@RequestBody QueryResultsJson queryResults) {
        if (Utils.isEmpty(queryResults)) {
            ResponseEntity.ok(new ArrayList<>());
        }

        QueryResultsJson checkedQueryResults;
        try {
            checkedQueryResults = looselyCoupledPrivacyPreservationService.looselyCoupledPrivacyPreservationCheck(queryResults);
        } catch (AnonymityPalException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(checkedQueryResults);
    }
}