package com.research.privacy.anonymity.pal.api;

import com.research.privacy.anonymity.pal.api.response.SQLOperatorsResponseJson;
import com.research.privacy.anonymity.pal.common.enums.FilterOperators;
import com.research.privacy.anonymity.pal.common.enums.JoinOperators;
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
public class SqlOperationsRestService {

    @GetMapping("/getFilterOperations")
    @ResponseStatus()
    public ResponseEntity<List<SQLOperatorsResponseJson>> getFilterOperations() {

        List<SQLOperatorsResponseJson> filterOperatorsResponseJsons = new ArrayList<>();
        Arrays.stream(FilterOperators.values()).forEach(f -> {
            filterOperatorsResponseJsons.add(new SQLOperatorsResponseJson(f.getField(), f.name()));
        });
        return ResponseEntity.ok().body(filterOperatorsResponseJsons);
    }

    @GetMapping("/getJoinOperations")
    @ResponseStatus()
    public ResponseEntity<List<SQLOperatorsResponseJson>> getJoinOperations() {

        List<SQLOperatorsResponseJson> joinOperatorsResponseJsons = new ArrayList<>();
        Arrays.stream(JoinOperators.values()).forEach(f -> {
            joinOperatorsResponseJsons.add(new SQLOperatorsResponseJson(f.getField(), f.name()));
        });
        return ResponseEntity.ok().body(joinOperatorsResponseJsons);
    }
}
