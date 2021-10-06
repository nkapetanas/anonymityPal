package com.research.privacy.anonymity.pal.privacyalgorithms;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class LDiversity {

    private Integer desiredL;
}

//package com.research.privacy.anonymity.pal.privacyalgorithms;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@Service
//public class LDiversity extends KAnonymity {
//
//    private int desiredL;
//
//    public LDiversity(Dataset dataset, int desiredK, int desiredL) {
//        super(dataset, desiredK);
//        this.desiredL = desiredL;
//    }
//
//    @Override
//    public boolean meetsConditions() {
//        return isKAnonymous(desiredK) && isLDiverse(desiredL);
//    }
//
//    private boolean isLDiverse(int desiredL) {
//        HashMap<String, List<String>> equivalenceClasses = getEquivalenceClasses();
//
//        // Check each list inside the HashMap has > desiredL
//        for (Map.Entry<String, List<String>> entry : equivalenceClasses.entrySet()) {
//            List<String> sensitiveList = entry.getValue();
//            if (sensitiveList.size() < desiredL) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    private int getL() {
//        HashMap<String, List<String>> equivalenceClasses = getEquivalenceClasses();
//
//        // Check each list inside the HashMap has > desiredL
//        Integer maxL = null;
//        for (Map.Entry<String, List<String>> entry : equivalenceClasses.entrySet()) {
//            List<String> sensitiveList = entry.getValue();
//
//            if (maxL == null || sensitiveList.size() < maxL) {
//                maxL = sensitiveList.size();
//            }
//        }
//
//        return maxL;
//    }
//
//
//    private HashMap<String, List<String>> getEquivalenceClasses() {
//        HashMap<String, List<String>> equivalenceClasses = new HashMap<>();
//
//        // Loop through all records
//        for (Record r : dataset.getRecords()) {
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
//
//        return equivalenceClasses;
//    }
//
//    @Override
//    public void printStats() {
//        System.out.println("Dataset has k: " + getK());
//        System.out.println("Dataset has l: " + getL());
//    }
//}

