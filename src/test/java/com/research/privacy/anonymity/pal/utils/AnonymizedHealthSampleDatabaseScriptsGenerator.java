package com.research.privacy.anonymity.pal.utils;

import com.research.privacy.anonymity.pal.common.utils.Utils;

import java.io.*;
import java.util.List;
import java.util.Random;

public class AnonymizedHealthSampleDatabaseScriptsGenerator {

    private static final String POSTGRESQL_INSERT_STATEMENT = "INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (%s, '%s', '%s', '%s', '%s', '%s');";
    private static final String MONGO_INSERT_STATEMENT = "db.health_data_collection_1.insertOne({ zip: '%s', age: '%s', marital_status: '%s', health_condition: '%s'});";

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


    public static void generateDataPostgresql(int kAnonymityAttribute, int numberOfRecords) throws IOException {
        File fout = new File("postgresql.txt");
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        final List<String> healthConditionList = getHealthConditionList();
        final List<String> nationalitiesList = getNationalitiesList();
        final List<String> bloodTypeList = getBloodTypeList();
        final List<String> zipCodeList = getZipCodeList();
        final List<String> genderList = getGenderList();
        int id = 1;
        for (int i = 0; i < numberOfRecords / kAnonymityAttribute; i++) {
            final String nationality = getStringFromList(i, nationalitiesList);
            final String bloodType = getStringFromList(i, bloodTypeList);
            final String zipCode = getStringFromList(i, zipCodeList);
            final String gender = getStringFromList(i, genderList);

            for (int kpairs = 0; kpairs < kAnonymityAttribute; kpairs++) {
                Random rnd = new Random();
                int delayIndex = rnd.nextInt(healthConditionList.size());

                bw.write(String.format(POSTGRESQL_INSERT_STATEMENT, id, nationality, gender, bloodType, zipCode, getStringFromList(delayIndex, healthConditionList)));
                bw.newLine();
                id++;
            }
        }
        bw.close();
    }

    public static void generateDataMongodb(int kAnonymityAttribute, int numberOfRecords) throws IOException {
        File fout = new File("mongodb.txt");
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        final List<String> healthConditionList = getHealthConditionList();
        final List<String> ageList = getAgeList();
        final List<String> maritalStatusList = getMaritalStatusList();
        final List<String> zipCodeList = getZipCodeList();

        for (int i = 0; i < numberOfRecords / kAnonymityAttribute; i++) {
            final String age = getStringFromList(i, ageList);
            final String maritalStatus = getStringFromList(i, maritalStatusList);
            final String zipCode = getStringFromList(i, zipCodeList);

            for (int kpairs = 0; kpairs < kAnonymityAttribute; kpairs++) {
                Random rnd = new Random();
                int delayIndex = rnd.nextInt(healthConditionList.size());

                bw.write(String.format(MONGO_INSERT_STATEMENT, zipCode, age, maritalStatus, getStringFromList(delayIndex, healthConditionList)));
                bw.newLine();
            }
        }
        bw.close();
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
