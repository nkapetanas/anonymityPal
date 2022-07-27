package com.research.privacy.anonymity.pal.utils;

import com.research.privacy.anonymity.pal.common.utils.Utils;

import java.io.*;
import java.util.List;
import java.util.Random;

public class AnonymizedHealthSampleDatabaseScriptsGenerator {

    private static final String POSTGRESQL_INSERT_STATEMENT = "INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (%s, '%s', '%s', '%s', '%s', '%s');";
    private static final String POSTGRESQL_2_INSERT_STATEMENT = "INSERT INTO health_data_collection_3 (id, country, wordclass, education, race, disease) " +
            "VALUES (%s, '%s', '%s', '%s', '%s', '%s');";
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
    private static final String HEALTH_CONDITION_6 = "Eye Disease";
    private static final String HEALTH_CONDITION_7 = "Foot injury";
    private static final String HEALTH_CONDITION_8 = "Arthritis";
    private static final String HEALTH_CONDITION_9 = "Depression";
    private static final String HEALTH_CONDITION_10 = "Diphtheria";
    private static final String HEALTH_CONDITION_11 = "Flu";
    private static final String HEALTH_CONDITION_12 = "Asthma";
    private static final String HEALTH_CONDITION_13 = "Cancer";

    private static final String WORDCLASS_1 = "Private";
    private static final String WORDCLASS_2 = "Self-emp-not-inc";
    private static final String WORDCLASS_3 = "Self-emp-inc";
    private static final String WORDCLASS_4 = "Federal-gov";
    private static final String WORDCLASS_5 = "Local-gov";
    private static final String WORDCLASS_6 = "State-gov";
    private static final String WORDCLASS_7 = "Without-pay";
    private static final String WORDCLASS_8 = "Never-worked";

    private static final String EDUCATION_1 = "Bachelors";
    private static final String EDUCATION_2 = "Some-college";
    private static final String EDUCATION_3 = "HS-grad";
    private static final String EDUCATION_4 = "Prof-school";
    private static final String EDUCATION_5 = "Assoc-acdm";
    private static final String EDUCATION_6 = "Assoc-voc";
    private static final String EDUCATION_7 = "Masters";
    private static final String EDUCATION_8 = "Preschool";

    private static final String RACE_1 = "White";
    private static final String RACE_2 = "Asian-Pac-Islander";
    private static final String RACE_3 = "Amer-Indian-Eskimo";
    private static final String RACE_4 = "Other";
    private static final String RACE_5 = "Black";

    private static final String COUNTRY_1 = "United-States";
    private static final String COUNTRY_2 = "Cambodia";
    private static final String COUNTRY_3 = "England";
    private static final String COUNTRY_4 = "Puerto-Rico";
    private static final String COUNTRY_5 = "Canada";
    private static final String COUNTRY_6 = "Germany";
    private static final String COUNTRY_7 = "India";
    private static final String COUNTRY_8 = "Japan";
    private static final String COUNTRY_9 = "Greece";
    private static final String COUNTRY_10 = "South";
    private static final String COUNTRY_11 = "China";
    private static final String COUNTRY_12 = "Cuba";
    private static final String COUNTRY_13 = "Iran";
    private static final String COUNTRY_14 = "Italy";
    private static final String COUNTRY_15 = "Poland";
    private static final String COUNTRY_16 = "Jamaica";
    private static final String COUNTRY_17 = "Mexico";
    private static final String COUNTRY_18 = "Portugal";
    private static final String COUNTRY_19 = "Ireland";
    private static final String COUNTRY_20 = "France";
    private static final String COUNTRY_21 = "Scotland";
    private static final String COUNTRY_22 = "Holand-Netherlands";
    private static final String COUNTRY_23 = "Dominican-Republic";
    private static final String COUNTRY_24 = "Ecuador";


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

    public static void generateSecondDataPostgresql(int kAnonymityAttribute, int numberOfRecords) throws IOException {
        File fout = new File("postgresql_v2.txt");
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        final List<String> healthConditionList = getHealthConditionList();
        final List<String> countriesList = getCountriesList();
        final List<String> wordClassList = getWordClassList();
        final List<String> educationList = getEducationalList();
        final List<String> raceList = getRaceList();
        int id = 1;
        for (int i = 0; i < numberOfRecords / kAnonymityAttribute; i++) {
            final String country = getStringFromList(i, countriesList);
            final String wordclass = getStringFromList(i, wordClassList);
            final String education = getStringFromList(i, educationList);
            final String race = getStringFromList(i, raceList);

            for (int kpairs = 0; kpairs < kAnonymityAttribute; kpairs++) {
                Random rnd = new Random();
                int delayIndex = rnd.nextInt(healthConditionList.size());

                bw.write(String.format(POSTGRESQL_2_INSERT_STATEMENT, id, country, wordclass, education, race, getStringFromList(delayIndex, healthConditionList)));
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

    private static List<String> getEducationalList() {
        return Utils.asList(EDUCATION_1, EDUCATION_2, EDUCATION_3, EDUCATION_4, EDUCATION_5, EDUCATION_6, EDUCATION_7, EDUCATION_8);
    }

    private static List<String> getRaceList() {
        return Utils.asList(RACE_1, RACE_2, RACE_3, RACE_4, RACE_5);
    }

    private static List<String> getWordClassList() {
        return Utils.asList(WORDCLASS_1, WORDCLASS_2, WORDCLASS_3, WORDCLASS_4, WORDCLASS_5, WORDCLASS_6, WORDCLASS_7, WORDCLASS_8);
    }

    private static List<String> getCountriesList() {
        return Utils.asList(COUNTRY_1, COUNTRY_2, COUNTRY_3, COUNTRY_4, COUNTRY_5, COUNTRY_6, COUNTRY_7, COUNTRY_8,
                COUNTRY_9, COUNTRY_10, COUNTRY_11, COUNTRY_12, COUNTRY_13, COUNTRY_14, COUNTRY_15, COUNTRY_16,
                COUNTRY_17, COUNTRY_18, COUNTRY_19, COUNTRY_20, COUNTRY_21, COUNTRY_22, COUNTRY_23, COUNTRY_24);
    }

    private static List<String> getHealthConditionList() {
        return Utils.asList(HEALTH_CONDITION_1, HEALTH_CONDITION_2, HEALTH_CONDITION_3, HEALTH_CONDITION_4, HEALTH_CONDITION_5
                , HEALTH_CONDITION_6
                , HEALTH_CONDITION_7
                , HEALTH_CONDITION_8
                , HEALTH_CONDITION_9
                , HEALTH_CONDITION_10
                , HEALTH_CONDITION_11
                , HEALTH_CONDITION_12
                , HEALTH_CONDITION_13);
    }

    private static String getStringFromList(int i, List<String> list) {
        if (Utils.isEmpty(list)) {
            return null;
        }
        return list.get(i % list.size());
    }
}
