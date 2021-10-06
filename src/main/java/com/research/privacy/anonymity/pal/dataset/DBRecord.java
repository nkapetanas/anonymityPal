package com.research.privacy.anonymity.pal.dataset;

import com.research.privacy.anonymity.pal.dataset.attributes.Attribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DBRecord {
    private List<Attribute> attributes = new ArrayList<>();
}
