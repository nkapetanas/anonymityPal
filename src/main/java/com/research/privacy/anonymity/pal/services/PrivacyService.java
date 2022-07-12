package com.research.privacy.anonymity.pal.services;

import com.research.privacy.anonymity.pal.api.params.PrivacyCheckParams;
import com.research.privacy.anonymity.pal.api.response.DBRecordWrapper;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.dataset.DBRecord;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalErrorCode;
import com.research.privacy.anonymity.pal.exceptions.AnonymityPalException;
import com.research.privacy.anonymity.pal.privacycriteria.KAnonymity;
import com.research.privacy.anonymity.pal.privacycriteria.LDiversity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.research.privacy.anonymity.pal.services.PrestoService.transformToDBRecords;

@Slf4j
@Service
public class PrivacyService {

    private final KAnonymity kAnonymity;
    private final LDiversity lDiversity;

    public PrivacyService(KAnonymity kAnonymity, LDiversity lDiversity) {
        this.kAnonymity = kAnonymity;
        this.lDiversity = lDiversity;
    }

    public boolean isPrivacyModelFulfilled(final List<DBRecord> dbRecords, final Integer kAnonymityParam, final Integer lDiversityParam) {
        boolean isKAnonymous = kAnonymity.isKAnonymous(dbRecords, kAnonymityParam);
        boolean isLDiverse = lDiversity.isLDiverse(dbRecords, lDiversityParam);
        return isKAnonymous && isLDiverse;
    }

    public boolean getQueryResultsPrivacyChecked(final PrivacyCheckParams privacyCheckParams) throws AnonymityPalException {
        final Integer kAnonymityParam = privacyCheckParams.getKAnonymityParam();
        final Integer lDiversityParam = privacyCheckParams.getLDiversityParam();
        final List<DBRecordWrapper> dataToCheck = privacyCheckParams.getDbRecordList();

        if (Utils.isEmpty(dataToCheck)) {
            throw new AnonymityPalException(AnonymityPalErrorCode.AP_E_0002);
        }

        final List<DBRecord> dataToCheckTransformed = transformToDBRecords(dataToCheck);
        return isPrivacyModelFulfilled(dataToCheckTransformed, kAnonymityParam, lDiversityParam);
    }
}
