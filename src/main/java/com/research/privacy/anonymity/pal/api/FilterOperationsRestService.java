package com.research.privacy.anonymity.pal.api;

import com.research.privacy.anonymity.pal.api.response.FilterOperatorsResponseJson;
import com.research.privacy.anonymity.pal.common.enums.FilterOperators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/presto")
public class FilterOperationsRestService {

    @GetMapping("/getFilterOperations")
    @ResponseStatus()
    public ResponseEntity<List<FilterOperatorsResponseJson>> getFilterOperations() {

        List<FilterOperatorsResponseJson> filterOperatorsResponseJsons = new ArrayList<>();
        Arrays.stream(FilterOperators.values()).forEach(f -> {
            filterOperatorsResponseJsons.add(new FilterOperatorsResponseJson(f.getField(), f.name()));
        });
        return ResponseEntity.ok().body(filterOperatorsResponseJsons);
    }
}
