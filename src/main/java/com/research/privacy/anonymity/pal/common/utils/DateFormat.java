package com.research.privacy.anonymity.pal.common.utils;

import com.research.privacy.anonymity.pal.common.enums.Granularity;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
public class DateFormat {

    @Getter
    private final Map<Granularity, String> formatMap = new HashMap<>();

    public DateFormat() {
        formatMap.put(Granularity.SECOND_MINUTE_HOUR_DAY_MONTH_YEAR, "dd.MM.yyyy-HH:mm:ss");
        formatMap.put(Granularity.MINUTE_HOUR_DAY_MONTH_YEAR, "dd.MM.yyyy-HH:mm");
        formatMap.put(Granularity.HOUR_DAY_MONTH_YEAR, "dd.MM.yyyy-HH:00");
        formatMap.put(Granularity.DAY_MONTH_YEAR, "dd.MM.yyyy");
        formatMap.put(Granularity.WEEK_MONTH_YEAR, "W/MM.yyyy");
        formatMap.put(Granularity.WEEK_YEAR, "ww/yyyy");
        formatMap.put(Granularity.MONTH_YEAR, "MM/yyyy");
    }

    public boolean contains(Granularity granularity) {
        return formatMap.containsKey(granularity);
    }

    public boolean isValid(String input, String pattern) {

        if (Utils.isEmpty(input)) {
            return false;
        }

        List<Character> listInput = patternAsList(input);
        List<Character> listPattern = patternAsList(pattern);

        if (!listInput.equals(listPattern)) {
            return false;
        }

        try {
            new SimpleDateFormat(input);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void setForm(Granularity granularity, String format) {
        if (granularity == null || format == null) {
            throw new IllegalArgumentException("Argument must not be null");
        }
        if (!granularity.isFormatSupported()) {
            throw new IllegalArgumentException("Format not supported for this granularity");
        }
        if (!isValid(format, granularity.getFormat())) {
            throw new IllegalArgumentException("Illegal format string: '" + format + "'");
        }
        formatMap.put(granularity, format);
    }

    private List<Character> patternAsList(String input) {
        List<Character> list = new ArrayList<>();

        boolean ignore = false;
        for (char c : input.toCharArray()) {
            if (c == '\'') {
                ignore = !ignore;
            } else if (!ignore && Character.isLetter(c)) {
                list.add(c);
            }
        }

        Collections.sort(list);
        return list;
    }
}

