package com.research.privacy.anonymity.pal.utils;

import com.research.privacy.anonymity.pal.api.response.DBRecordKeyValue;
import com.research.privacy.anonymity.pal.api.response.DBRecordWrapper;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.dataset.DBRecord;
import com.research.privacy.anonymity.pal.dataset.attributes.TextAttribute;
import com.research.privacy.anonymity.pal.dataset.attributes.enums.IdentifierEnumType;

import java.util.ArrayList;
import java.util.List;

public class AnonymizedHealthSampleDataGenerator {

    private static final String COLUMN_NAME_ZIPCODE = "zipcode";
    private static final String COLUMN_NAME_AGE = "age";
    private static final String COLUMN_NAME_GENDER = "gender";
    private static final String COLUMN_NAME_NATIONALITY = "nationality";
    private static final String COLUMN_NAME_MARITAL_STATUS = "marital_status";
    private static final String COLUMN_NAME_BLOOD_TYPE = "blood_type";
    private static final String COLUMN_NAME_HEALTH_CONDITION = "health_condition";

    private static final String SUPPRESSED_ZIP_CODE_1 = "130**";
    private static final String SUPPRESSED_ZIP_CODE_2 = "150**";
    private static final String SUPPRESSED_ZIP_CODE_3 = "160**";
    private static final String SUPPRESSED_ZIP_CODE_4 = "450**";
    private static final String SUPPRESSED_ZIP_CODE_5 = "771**";

    private static final String MALE = "M";
    private static final String FEMALE = "F";
    private static final String SINGLE = "Single";
    private static final String MARRIED = "Married";

    private static final String SUPPRESSED_AGE_1 = "20-30";
    private static final String SUPPRESSED_AGE_2 = "50-60";
    private static final String SUPPRESSED_AGE_3 = "60-70";
    private static final String SUPPRESSED_AGE_4 = "40-50";

    private static final String BLOOD_TYPE_A = "A";
    private static final String BLOOD_TYPE_B = "B";
    private static final String BLOOD_TYPE_O = "O";
    private static final String BLOOD_TYPE_AB = "AB";

    private static final String NATIONALITY_1 = "European";
    private static final String NATIONALITY_2 = "American";
    private static final String NATIONALITY_3 = "Asian";
    private static final String NATIONALITY_4 = "African";
    private static final String NATIONALITY_5 = "Middle East";

    private static final String HEALTH_CONDITION_1 = "Diabetes";
    private static final String HEALTH_CONDITION_2 = "Cardiovascular";
    private static final String HEALTH_CONDITION_3 = "HIV";
    private static final String HEALTH_CONDITION_4 = "Broken Arm";
    private static final String HEALTH_CONDITION_5 = "Broken Leg";


    public static List<DBRecordWrapper> generateDBRecordWrapperData(int kAnonymityAttribute, int numberOfRecords) {
        List<DBRecordWrapper> dbRecordList = new ArrayList<>();

        for (int i = 0; i < numberOfRecords / kAnonymityAttribute; i++) {
            final String nationality = getStringFromList(i, getNationalitiesList());
            final String maritalStatus = getStringFromList(i, getMaritalStatusList());
            final String age = getStringFromList(i, getAgeList());
            final String bloodType = getStringFromList(i, getBloodTypeList());
            final String zipCode = getStringFromList(i, getZipCodeList());
            final String gender = getStringFromList(i, getGenderList());

            for (int kpairs = 0; kpairs < kAnonymityAttribute; kpairs++) {
                dbRecordList.add(new DBRecordWrapper(Utils.asList(
                        new DBRecordKeyValue(COLUMN_NAME_NATIONALITY, nationality),
                        new DBRecordKeyValue(COLUMN_NAME_MARITAL_STATUS, maritalStatus),
                        new DBRecordKeyValue(COLUMN_NAME_AGE, age),
                        new DBRecordKeyValue(COLUMN_NAME_GENDER, gender),
                        new DBRecordKeyValue(COLUMN_NAME_BLOOD_TYPE, bloodType),
                        new DBRecordKeyValue(COLUMN_NAME_ZIPCODE, zipCode),
                        new DBRecordKeyValue(COLUMN_NAME_HEALTH_CONDITION, getStringFromList(kpairs, getHealthConditionList()))
                )));
            }
        }
        return dbRecordList;
    }

    public static List<DBRecord> generateData(int kAnonymityAttribute, int numberOfRecords) {
        List<DBRecord> dbRecordList = new ArrayList<>();

        for (int i = 0; i < numberOfRecords / kAnonymityAttribute; i++) {
            final String nationality = getStringFromList(i, getNationalitiesList());
            final String maritalStatus = getStringFromList(i, getMaritalStatusList());
            final String age = getStringFromList(i, getAgeList());
            final String bloodType = getStringFromList(i, getBloodTypeList());
            final String zipCode = getStringFromList(i, getZipCodeList());
            final String gender = getStringFromList(i, getGenderList());

            for (int kpairs = 0; kpairs < kAnonymityAttribute; kpairs++) {
                dbRecordList.add(new DBRecord(Utils.asList(
                        new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, nationality),
                        new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_MARITAL_STATUS, maritalStatus),
                        new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, age),
                        new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_GENDER, gender),
                        new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_BLOOD_TYPE, bloodType),
                        new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, zipCode),
                        new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, getStringFromList(kpairs, getHealthConditionList()))
                )));
            }
        }
        return dbRecordList;
    }

    private static List<String> getNationalitiesList() {
        return Utils.asList(NATIONALITY_1, NATIONALITY_2, NATIONALITY_3, NATIONALITY_4, NATIONALITY_5);
    }

    private static List<String> getMaritalStatusList() {
        return Utils.asList(SINGLE, MARRIED);
    }

    private static List<String> getAgeList() {
        return Utils.asList(SUPPRESSED_AGE_1, SUPPRESSED_AGE_2, SUPPRESSED_AGE_3, SUPPRESSED_AGE_4);
    }

    private static List<String> getBloodTypeList() {
        return Utils.asList(BLOOD_TYPE_A, BLOOD_TYPE_B, BLOOD_TYPE_O, BLOOD_TYPE_AB);
    }

    private static List<String> getZipCodeList() {
        return Utils.asList(SUPPRESSED_ZIP_CODE_1, SUPPRESSED_ZIP_CODE_2, SUPPRESSED_ZIP_CODE_3, SUPPRESSED_ZIP_CODE_4, SUPPRESSED_ZIP_CODE_5);
    }

    private static List<String> getGenderList() {
        return Utils.asList(MALE, FEMALE);
    }

    private static List<String> getHealthConditionList() {
        return Utils.asList(HEALTH_CONDITION_1, HEALTH_CONDITION_2, HEALTH_CONDITION_3, HEALTH_CONDITION_4, HEALTH_CONDITION_5);
    }

    private static String getStringFromList(int i, List<String> list) {
        if (Utils.isEmpty(list)) {
            return null;
        }
        return list.get(i % list.size());
    }
}
