package com.research.privacy.anonymity.pal.dataset;

import com.research.privacy.anonymity.pal.dataset.attributes.Attribute;
import com.research.privacy.anonymity.pal.dataset.attributes.IdentifierEnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DBRecord {
    private List<Attribute> attributes = new ArrayList<>();

    public boolean equivalentTo(final DBRecord otherDbRecord) {

        final List<Attribute> otherDbRecordAttributes = otherDbRecord.getAttributes();

        // We collect only the key, value pairs that belong to a QI column
        final List<Attribute> quasiGroup = attributes.stream().filter(a -> IdentifierEnumType.QUASI_IDENTIFIER.equals(a.getIdentifierEnumType())).collect(Collectors.toList());
        final List<Attribute> otherRecordQuasiGroup = otherDbRecordAttributes.stream().filter(a -> IdentifierEnumType.QUASI_IDENTIFIER.equals(a.getIdentifierEnumType())).collect(Collectors.toList());

        // TODO think about this case
        if(quasiGroup.size() != otherRecordQuasiGroup.size()){
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
}
