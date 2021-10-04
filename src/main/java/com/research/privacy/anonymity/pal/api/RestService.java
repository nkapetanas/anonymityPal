package com.research.privacy.anonymity.pal.api;

import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.services.PrestoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/presto")
public class RestService {

    private final PrestoService prestoService;

    public RestService(PrestoService prestoService) {
        this.prestoService = prestoService;
    }

    @GetMapping("/getQueryResults")
    @ResponseStatus()
    public ResponseEntity<?> getQueryResults(@RequestParam String query) {
        if (Utils.isEmpty(query)) {
            ResponseEntity.ok(new ArrayList<>());
        }
        final List<Objects> queryResults = prestoService.getQueryResults(query);
        return ResponseEntity.ok(queryResults);
    }
}
