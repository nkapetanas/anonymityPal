package com.research.privacy.anonymity.pal.api;

import com.research.privacy.anonymity.pal.common.enums.FilterOperators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/presto")
public class FilterOperationsRestService {

    @GetMapping("/getFilterOperations")
    @ResponseStatus()
    public ResponseEntity<List<String>> getFilterOperations() {
        return ResponseEntity.ok().body(FilterOperators.getFilterOperators());
    }
}
