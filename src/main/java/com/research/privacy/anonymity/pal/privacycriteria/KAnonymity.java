package com.research.privacy.anonymity.pal.privacycriteria;

import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.dataset.DBRecord;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class KAnonymity {

    @Setter
    private Integer desiredK = 2; // default value

    public boolean isKAnonymous(final List<DBRecord> dbRecords, Integer kAnonymityParam) {

        if (Utils.isNotEmpty(kAnonymityParam)) {
            this.desiredK = kAnonymityParam;
        }

        // Returns true if k is equal to 1. All data sets have a k of 1.
        if (desiredK == 1) {
            return true;
        }

        // Create a queue of all records to iterate through.
        List<DBRecord> dbRecordsQueue = new LinkedList<>(dbRecords);

        // For each record check if there are at least k matches
        while (!dbRecordsQueue.isEmpty()) {
            // Get the number of matched records
            int matches = findMatches(dbRecordsQueue);

            // If matches is less than k return false.
            if (matches < desiredK) {
                return false;
            }
        }
        return true;
    }

    /**
     * Given an List of records, return the number of times the first element if found within that list.
     *
     * @param recordsQueue List of Records
     * @return The number of matches the first element is found within that list.
     */
    private int findMatches(List<DBRecord> recordsQueue) {
        // Get the first element of the list
        DBRecord dbRecord1 = recordsQueue.remove(0);

        // Count the number of similar records for this record
        int numberOfSimilarRecords = 1;

        // Uses an iterator to look for matching pair
        Iterator<DBRecord> itr = recordsQueue.iterator();

        while (itr.hasNext()) {
            DBRecord dbRecord2 = itr.next();

            // When found removes the record from the list to speed things up.
            if (dbRecord1.equivalentTo(dbRecord2)) {
                numberOfSimilarRecords++;
                itr.remove();
            }
        }
        return numberOfSimilarRecords;
    }
}
