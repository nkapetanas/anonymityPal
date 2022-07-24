package com.research.privacy.anonymity.pal.api.params;

import com.research.privacy.anonymity.pal.api.response.DBRecordWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrivacyCheckParams {
    private List<DBRecordWrapper> dbRecordList;
}
