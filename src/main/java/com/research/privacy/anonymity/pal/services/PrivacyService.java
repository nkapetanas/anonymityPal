package com.research.privacy.anonymity.pal.services;

import com.research.privacy.anonymity.pal.dataset.DBRecord;
import com.research.privacy.anonymity.pal.privacycriteria.KAnonymity;
import com.research.privacy.anonymity.pal.privacycriteria.LDiversity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PrivacyService {

    private final KAnonymity kAnonymity;
    private final LDiversity lDiversity;

    public PrivacyService(KAnonymity kAnonymity, LDiversity lDiversity) {
        this.kAnonymity = kAnonymity;
        this.lDiversity = lDiversity;
    }

    public boolean isPrivacyModelFulfilled(final List<DBRecord> dbRecords) {
        boolean isKAnonymous = kAnonymity.isKAnonymous(dbRecords);
        boolean isLDiverse = lDiversity.isLDiverse(dbRecords);
        return isKAnonymous && isLDiverse;
    }
}
