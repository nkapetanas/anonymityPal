package com.research.privacy.anonymity.pal.privacy;

import com.research.privacy.anonymity.pal.Application;
import com.research.privacy.anonymity.pal.common.utils.Utils;
import com.research.privacy.anonymity.pal.dataset.DBRecord;
import com.research.privacy.anonymity.pal.dataset.attributes.NumericAttribute;
import com.research.privacy.anonymity.pal.dataset.attributes.TextAttribute;
import com.research.privacy.anonymity.pal.dataset.attributes.enums.IdentifierEnumType;
import com.research.privacy.anonymity.pal.privacycriteria.LDiversity;
import com.research.privacy.anonymity.pal.services.PrivacyService;
import com.research.privacy.anonymity.pal.utils.AnonymizedHealthSampleDataGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest(classes = Application.class)
class ITLDiversityCriteria {

    @Autowired
    PrivacyService privacyService;

    private static final String COLUMN_NAME_ZIPCODE = "zipcode";
    private static final String COLUMN_NAME_AGE = "age";
    private static final String COLUMN_NAME_SEX = "sex";
    private static final String COLUMN_NAME_NATIONALITY = "nationality";
    private static final String COLUMN_NAME_HEALTH_CONDITION = "health_condition";

    private static final String ZIP_CODE_1 = "13053";
    private static final String ZIP_CODE_2 = "77055";
    private static final String ZIP_CODE_3 = "44053";

    private static final String MALE = "M";
    private static final String FEMALE = "F";

    private static final String AGE_1 = "20";
    private static final String AGE_2 = "30";
    private static final String AGE_3 = "25";

    private static final String NATIONALITY_1 = "Greek";
    private static final String NATIONALITY_2 = "Italian";
    private static final String NATIONALITY_3 = "German";

    private static final String HEALTH_CONDITION_1 = "Heart Disease";
    private static final String HEALTH_CONDITION_2 = "Broken Arm";
    private static final String HEALTH_CONDITION_3 = "Diabetes";
    private static final String HEALTH_CONDITION_4 = "Broken Neck";

    private static final int L_DIVERSITY_PARAM = 2;

    @Autowired
    LDiversity lDiversity;

    @Test
    void privacyService_isLDiverse_OK() {
        final DBRecord dbRecord1 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecord dbRecord2 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecord dbRecord3 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_2),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecord dbRecord4 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_2),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final List<DBRecord> dbRecords = Utils.asList(dbRecord1, dbRecord2, dbRecord3, dbRecord4);
        lDiversity.setDesiredL(2);
        Assertions.assertTrue(lDiversity.isLDiverse(dbRecords, L_DIVERSITY_PARAM));
    }

    @Test
    void privacyService_isLDiverse_NOK() {
        final DBRecord dbRecord1 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecord dbRecord2 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecord dbRecord3 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_2),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_3)
        ));

        final DBRecord dbRecord4 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_2),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));

        final List<DBRecord> dbRecords = Utils.asList(dbRecord1, dbRecord2, dbRecord3, dbRecord4);
        lDiversity.setDesiredL(2);
        // TODO define if correct
        Assertions.assertTrue(lDiversity.isLDiverse(dbRecords, L_DIVERSITY_PARAM));
    }

    @Test
    void privacyService_isLDiverse_NOK_2() {
        final DBRecord dbRecord1 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecord dbRecord2 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecord dbRecord3 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_2),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecord dbRecord4 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_2),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final List<DBRecord> dbRecords = Utils.asList(dbRecord1, dbRecord2, dbRecord3, dbRecord4);
        Assertions.assertFalse(lDiversity.isLDiverse(dbRecords, 3));
    }

    @Test
    void privacyService_isLDiverse_NOK_3() {
        final DBRecord dbRecord1 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecord dbRecord2 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecord dbRecord3 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecord dbRecord4 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_3),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_3),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final List<DBRecord> dbRecords = Utils.asList(dbRecord1, dbRecord2, dbRecord3, dbRecord4);
        Assertions.assertFalse(lDiversity.isLDiverse(dbRecords, L_DIVERSITY_PARAM));
    }

    @Test
    void privacyService_isLDiverse_NOK_4() {
        final DBRecord dbRecord1 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecord dbRecord2 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecord dbRecord3 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecord dbRecord4 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecord dbRecord5 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_2),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_3)
        ));

        final DBRecord dbRecord6 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_2),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));

        final DBRecord dbRecord7 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_2),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_3)
        ));

        final DBRecord dbRecord8 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_2),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));

        final List<DBRecord> dbRecords = Utils.asList(dbRecord1, dbRecord2, dbRecord3, dbRecord4, dbRecord5, dbRecord6, dbRecord7, dbRecord8);

        Assertions.assertTrue(lDiversity.isLDiverse(dbRecords, L_DIVERSITY_PARAM));
        Assertions.assertFalse(lDiversity.isLDiverse(dbRecords, 3));
    }

    @Test
    void privacyService_isLDiverse_NOK_5() {
        final DBRecord dbRecord1 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_1)
        ));

        final DBRecord dbRecord2 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecord dbRecord3 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_3)
        ));

        final DBRecord dbRecord4 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_1),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_1),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, MALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_1),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_2)
        ));

        final DBRecord dbRecord5 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_2),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));

        final DBRecord dbRecord6 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_2),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));

        final DBRecord dbRecord7 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_2),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_3)
        ));

        final DBRecord dbRecord8 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_2),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));

        final DBRecord dbRecord9 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_3),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));

        final DBRecord dbRecord10 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_3),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));

        final DBRecord dbRecord11 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_3),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_4)
        ));

        final DBRecord dbRecord12 = new DBRecord(Utils.asList(
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_ZIPCODE, ZIP_CODE_3),
                new NumericAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_AGE, AGE_2),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_SEX, FEMALE),
                new TextAttribute(IdentifierEnumType.QUASI_IDENTIFIER, COLUMN_NAME_NATIONALITY, NATIONALITY_2),
                new TextAttribute(IdentifierEnumType.SENSITIVE, COLUMN_NAME_HEALTH_CONDITION, HEALTH_CONDITION_3)
        ));

        final List<DBRecord> dbRecords = Utils.asList(dbRecord1, dbRecord2, dbRecord3, dbRecord4, dbRecord5, dbRecord6, dbRecord7, dbRecord8, dbRecord9, dbRecord10, dbRecord11, dbRecord12);
        Assertions.assertTrue(lDiversity.isLDiverse(dbRecords, L_DIVERSITY_PARAM));
        Assertions.assertFalse(lDiversity.isLDiverse(dbRecords, 3));
    }

    @Test
    void privacyService_isLDiverse_data_generator_OK() {
        final List<DBRecord> dbRecords = AnonymizedHealthSampleDataGenerator.generateData(3, 100);
        Assertions.assertTrue(lDiversity.isLDiverse(dbRecords, L_DIVERSITY_PARAM));
    }
}
