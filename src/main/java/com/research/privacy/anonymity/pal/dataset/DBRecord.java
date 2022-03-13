package com.research.privacy.anonymity.pal.dataset;

import com.research.privacy.anonymity.pal.dataset.attributes.Attribute;
import com.research.privacy.anonymity.pal.dataset.attributes.enums.IdentifierEnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DBRecord {

    private static final String SUPPRESSED_VALUE = "*";

    private List<Attribute> attributes = new ArrayList<>();

    @Setter
    private boolean suppressed;

    public DBRecord(List<Attribute> attributes){
        this.attributes = attributes;
        this.suppressed = false;
    }
    public boolean equivalentTo(final DBRecord otherDbRecord) {

        final List<Attribute> otherDbRecordAttributes = otherDbRecord.getAttributes();

        // We collect only the key, value pairs that belong to a QI column
        final List<Attribute> quasiGroup = attributes.stream().filter(a -> IdentifierEnumType.QUASI_IDENTIFIER.equals(a.getIdentifierEnumType())).collect(Collectors.toList());
        final List<Attribute> otherRecordQuasiGroup = otherDbRecordAttributes.stream().filter(a -> IdentifierEnumType.QUASI_IDENTIFIER.equals(a.getIdentifierEnumType())).collect(Collectors.toList());

        // TODO think about this case
        if (quasiGroup.size() != otherRecordQuasiGroup.size()) {
            return false;
        }

        for (int indexColumn = 0; indexColumn < quasiGroup.size(); indexColumn++) {
            Attribute attribute1 = quasiGroup.get(indexColumn);
            Attribute attribute2 = otherRecordQuasiGroup.get(indexColumn);

            if (!attribute1.equivalentTo(attribute2)) {
                return false;
            }
        }
        return true;
    }

    public List<Attribute> getQIRecords() {
        return attributes.stream().filter(a -> IdentifierEnumType.QUASI_IDENTIFIER.equals(a.getIdentifierEnumType())).collect(Collectors.toList());
    }

    public List<Attribute> getSensitiveRecords() {
        return attributes.stream().filter(a -> IdentifierEnumType.SENSITIVE.equals(a.getIdentifierEnumType())).collect(Collectors.toList());
    }

    public List<String> getQIColumnsLabels(){
        return this.attributes.stream().filter(a -> IdentifierEnumType.QUASI_IDENTIFIER.equals(a.getIdentifierEnumType())).map(Attribute::getColumnName).collect(Collectors.toList());
    }

    public String getModifiedQIDValues() {
        StringBuilder quIdsAsString = new StringBuilder();
        final List<Attribute> qiRecords = getQIRecords();
        qiRecords.stream().map(qi -> isSuppressed() ? SUPPRESSED_VALUE : String.valueOf(qi.getValue())).forEach(value -> quIdsAsString.append(value).append('\t'));
        return quIdsAsString.toString();
    }

    public String getModifiedSensitiveValues() {
        StringBuilder output = new StringBuilder();
        final List<Attribute> sensitiveRecords = getSensitiveRecords();
        sensitiveRecords.forEach(a-> output.append(a.getValue()).append('\t'));
        return output.toString();
    }

    public boolean isSuppressed() {
        return suppressed;
    }
}
