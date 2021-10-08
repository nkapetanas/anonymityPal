package com.research.privacy.anonymity.pal.privacycriteria;
import com.research.privacy.anonymity.pal.dataset.DBRecord;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class LDiversity {

    private Integer desiredL;

    public boolean isLDiverse(final List<DBRecord> dbRecords) {
        HashMap<String, List<String>> equivalenceClasses = getEquivalenceClasses();

        // Check each list inside the HashMap has > desiredL
        for (Map.Entry<String, List<String>> entry : equivalenceClasses.entrySet()) {
            List<String> sensitiveList = entry.getValue();
            if (sensitiveList.size() < desiredL) {
                return false;
            }
        }

        return true;
    }

    private HashMap<String, List<String>> getEquivalenceClasses() {
        HashMap<String, List<String>> equivalenceClasses = new HashMap<>();

//        // Loop through all records
//        for (DBRecord r : dataset.getRecords()) {
//            // Get the modified values string
//            String qids = r.getModifiedQIDValues();
//            String sensitive = r.getModifiedSensitiveValues();
//            List<String> sensitiveList = equivalenceClasses.get(qids);
//
//            // If the list doesn't exist, create a new list and add it to the hashMap
//            if (sensitiveList == null) {
//                sensitiveList = new ArrayList<>();
//                equivalenceClasses.put(qids, sensitiveList);
//            }
//
//            // Put the sensitive value into the list inside the HashMap
//            if (!sensitiveList.contains(sensitive)) {
//                sensitiveList.add(sensitive);
//            }
//        }

        return equivalenceClasses;
    }
}

