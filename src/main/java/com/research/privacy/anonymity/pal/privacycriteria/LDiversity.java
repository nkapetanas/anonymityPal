package com.research.privacy.anonymity.pal.privacycriteria;

import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.dataset.DBRecord;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class LDiversity {

    @Setter
    private Integer desiredL = 2; // default value

    public boolean isLDiverse(final List<DBRecord> dbRecords) {
        HashMap<String, List<String>> equivalentClasses = getEquivalentClasses(dbRecords);

        // Check each list inside the HashMap has > desiredL
        for (Map.Entry<String, List<String>> entry : equivalentClasses.entrySet()) {
            List<String> sensitiveList = entry.getValue();
            if (sensitiveList.size() < desiredL) {
                return false;
            }
        }

        return true;
    }

    private HashMap<String, List<String>> getEquivalentClasses(final List<DBRecord> dbRecords) {
        HashMap<String, List<String>> equivalentClasses = new HashMap<>();

        for (DBRecord r : dbRecords) {

            final String qidValues = r.getModifiedQIDValues();
            final String modifiedSensitiveValues = r.getModifiedSensitiveValues();

            List<String> sensitiveList = equivalentClasses.get(qidValues);

            // If the list doesn't exist, create a new list and add it to the hashMap
            if (Utils.isEmpty(sensitiveList)) {
                sensitiveList = new ArrayList<>();
                equivalentClasses.put(qidValues, sensitiveList);
            }

            // Put the sensitive value into the list inside the HashMap
            if (!sensitiveList.contains(modifiedSensitiveValues)) {
                sensitiveList.add(modifiedSensitiveValues);
            }
        }

        return equivalentClasses;
    }
}

