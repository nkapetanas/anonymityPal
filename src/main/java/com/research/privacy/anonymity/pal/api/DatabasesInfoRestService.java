package com.research.privacy.anonymity.pal.api;

import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.dataset.DBRecord;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import com.research.privacy.anonymity.pal.services.PrestoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/presto")
public class DatabasesInfoRestService {

    private final PrestoService prestoService;

    public DatabasesInfoRestService(PrestoService prestoService) {
        this.prestoService = prestoService;
    }

    @GetMapping("/")
    @ResponseStatus()
    public ResponseEntity<List<DBRecord>> getQueryResultsPrivacyChecked(@RequestParam String query) {
        if (Utils.isEmpty(query)) {
            ResponseEntity.ok(new ArrayList<>());
        }

        List<DBRecord> queryResults = null;
        try {
            queryResults = prestoService.getQueryResultsPrivacyChecked(query);
        } catch (AnonymityPalException e) {
            ResponseEntity.badRequest();
        }
        return ResponseEntity.ok(queryResults);
    }
}
