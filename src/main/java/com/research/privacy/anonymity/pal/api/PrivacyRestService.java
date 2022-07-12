package com.research.privacy.anonymity.pal.api;

import com.research.privacy.anonymity.pal.api.params.PrivacyCheckParams;
import com.research.privacy.anonymity.pal.api.response.PrivacyPreservationResultJson;
import com.research.privacy.anonymity.pal.api.response.QueryResultsJson;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import com.research.privacy.anonymity.pal.services.LooselyCoupledPrivacyPreservationService;
import com.research.privacy.anonymity.pal.services.PrivacyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/privacy")
public class PrivacyRestService {

    private final PrivacyService privacyService;
    private final LooselyCoupledPrivacyPreservationService looselyCoupledPrivacyPreservationService;

    public PrivacyRestService(PrivacyService privacyService, LooselyCoupledPrivacyPreservationService looselyCoupledPrivacyPreservationService) {
        this.privacyService = privacyService;
        this.looselyCoupledPrivacyPreservationService = looselyCoupledPrivacyPreservationService;
    }

    @PostMapping("/getQueryResultsPrivacyChecked")
    @ResponseStatus()
    public ResponseEntity<Boolean> getQueryResultsPrivacyChecked(@RequestBody PrivacyCheckParams privacyCheckParams) {
        if (Utils.isEmpty(privacyCheckParams)) {
            ResponseEntity.ok(false);
        }

        try {
            return ResponseEntity.ok(privacyService.getQueryResultsPrivacyChecked(privacyCheckParams));
        } catch (AnonymityPalException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/checkPrivacyPreservation")
    @ResponseStatus()
    public ResponseEntity<PrivacyPreservationResultJson> checkPrivacyPreservation(@RequestBody QueryResultsJson queryResults) {
        if (Utils.isEmpty(queryResults)) {
            ResponseEntity.ok(new ArrayList<>());
        }

        PrivacyPreservationResultJson privacyCheckResult;
        try {
            privacyCheckResult = looselyCoupledPrivacyPreservationService.looselyCoupledPrivacyPreservationCheck(queryResults);
        } catch (AnonymityPalException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(privacyCheckResult);
    }
}
