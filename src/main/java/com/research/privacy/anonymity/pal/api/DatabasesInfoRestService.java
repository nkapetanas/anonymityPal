package com.research.privacy.anonymity.pal.api;

import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.services.PrestoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/presto")
public class DatabasesInfoRestService {

    private final PrestoService prestoService;

    public DatabasesInfoRestService(PrestoService prestoService) {
        this.prestoService = prestoService;
    }

    @GetMapping("/getAvailableDbs")
    @ResponseStatus()
    public ResponseEntity<List<String>> getAvailableDbs() {
        return ResponseEntity.ok(prestoService.getAvailableDBs());
    }

    @GetMapping("/getAvailableDbSchemas")
    @ResponseStatus()
    public ResponseEntity<List<String>> getAvailableDbSchemas(@RequestParam String selectedDB) {
        if (Utils.isEmpty(selectedDB)) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        final List<String> availableSchemasFromDB = prestoService.getAvailableSchemasFromDB(selectedDB);
        return ResponseEntity.ok(availableSchemasFromDB);
    }

    @GetMapping("/getAvailableSchemaTables")
    @ResponseStatus()
    public ResponseEntity<List<String>> getAvailableSchemaTables(@RequestParam String schema) {
        if (Utils.isEmpty(schema)) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        final List<String> tablesFromSchema = prestoService.getTablesFromSchema(schema);
        return ResponseEntity.ok(tablesFromSchema);
    }

    @GetMapping("/getColumnsFromTable")
    @ResponseStatus()
    public ResponseEntity<List<String>> getColumnsFromTable(@RequestParam String selectedTable) {
        if (Utils.isEmpty(selectedTable)) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        final List<String> columnsFromTable = prestoService.getColumnsFromTable(selectedTable);
        return ResponseEntity.ok(columnsFromTable);
    }
}
