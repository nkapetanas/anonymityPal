package com.research.privacy.anonymity.pal.services;

import com.research.privacy.anonymity.pal.infrastructure.repository.PrestoDbRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
public class PrestoService {

    private final PrestoDbRepository prestoDbRepository;

    public PrestoService(PrestoDbRepository prestoDbRepository) {
        this.prestoDbRepository = prestoDbRepository;
    }

    public List<Objects> getQueryResults(final String query){
        return null;
    }
}
