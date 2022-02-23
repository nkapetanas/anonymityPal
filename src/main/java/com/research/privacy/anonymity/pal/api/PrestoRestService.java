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
public class PrestoRestService {

    private final PrestoService prestoService;

    public PrestoRestService(PrestoService prestoService) {
        this.prestoService = prestoService;
    }

    @GetMapping("/getQueryResultsPrivacyChecked")
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

    @GetMapping("/getQueryResults")
    @ResponseStatus()
    public ResponseEntity<ResultsJson> getQueryResults(@RequestParam String query) {
        if (Utils.isEmpty(query)) {
            ResponseEntity.ok(new ArrayList<>());
        }

        ResultsJson queryResults;
        try {
            queryResults = prestoService.getQueryResultsSimple(query);
        } catch (AnonymityPalException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(queryResults);
    }

    @GetMapping("/getAvailableDbs")
    @ResponseStatus()
    public ResponseEntity<List<String>> getAvailableDbs() {
        return ResponseEntity.ok(prestoService.getAvailableDBs());
    }

    @GetMapping("/getAvailableDbTables")
    @ResponseStatus()
    public ResponseEntity<List<String>> getAvailableDbTables(@RequestParam String selectedDB) {
        if(Utils.isEmpty(selectedDB)){
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        try {
            final List<String> availableSchemasFromDB = prestoService.getAvailableSchemasFromDB(selectedDB);
            return ResponseEntity.ok(availableSchemasFromDB);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/getColumnsFromTable")
    @ResponseStatus()
    public ResponseEntity<List<String>> getColumnsFromTable(@RequestParam String selectedTable) {
        if(Utils.isEmpty(selectedTable)){
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        try {
            final List<String> columnsFromTable = prestoService.getColumnsFromTable(selectedTable);
            return ResponseEntity.ok(columnsFromTable);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
