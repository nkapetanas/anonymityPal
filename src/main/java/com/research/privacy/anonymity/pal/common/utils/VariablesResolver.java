/*
 * Copyright (C) 2015 "IMIS-Athena R.C.",
 * Institute for the Management of Information Systems, part of the "Athena"
 * Research and Innovation Centre in Information, Communication and Knowledge Technologies.
 * [http://www.imis.athena-innovation.gr/]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 */
package com.research.privacy.anonymity.pal.common.utils;

import com.research.privacy.anonymity.pal.common.enums.Granularity;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@NoArgsConstructor
@Service
public class VariablesResolver {

    DateFormat dateFormat;

    public String lastFormat;

    @Autowired
    public VariablesResolver(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public boolean isInteger(final String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException er) {
            return false;
        }
    }

    public boolean isDouble(final String s) {
        try {
            String temp = s.replace(",", ".");
            Double.parseDouble(temp);
            return true;
        } catch (NumberFormatException er) {
            return false;
        }
    }

    public boolean isDate(final String s) {
        if (Utils.isEmpty(s)) {
            return false;
        }

        final Map<Granularity, String> formatMap = dateFormat.getFormatMap();
        for (Map.Entry<Granularity, String> format : formatMap.entrySet()) {
            final String formatValue = format.getValue();
            SimpleDateFormat sdf = new SimpleDateFormat(formatValue);

            try {
                Date date = sdf.parse(s);
                if (s.equals(sdf.format(date))) {
                    this.lastFormat = formatValue;
                    return true;
                }
            } catch (ParseException e) {
                e.getMessage();
            }
        }
        return false;
    }
}
