package com.research.privacy.anonymity.pal.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public enum Granularity {

    SECOND_MINUTE_HOUR_DAY_MONTH_YEAR("dd.MM.yyyy-HH:mm:ss"),
    MINUTE_HOUR_DAY_MONTH_YEAR("dd.MM.yyyy-HH:mm"),
    HOUR_DAY_MONTH_YEAR("dd.MM.yyyy-HH:00"),
    DAY_MONTH_YEAR("dd.MM.yyyy"),
    WEEK_MONTH_YEAR("W/MM.yyyy"),
    WEEK_YEAR("ww/yyyy"),
    MONTH_YEAR("MM/yyyy"),
    WEEKDAY("u"),
    WEEK("W"),
    QUARTER("MM", 4),
    YEAR("yyyy"),
    DECADE("yyyy", 10),
    CENTURY("yyyy", 100),
    MILLENIUM("yyyy", 1000);

    @Getter
    private String format;

    @Getter
    private Integer range;


    Granularity(String format) {
        this(format, null);
    }

    public boolean isFormatSupported() {
        return range == null;
    }
}
