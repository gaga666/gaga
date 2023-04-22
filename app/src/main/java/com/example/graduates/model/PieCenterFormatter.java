package com.example.graduates.model;

import android.text.TextUtils;

import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class PieCenterFormatter extends ValueFormatter {
    private final String mUnit;

    public PieCenterFormatter(String mUnit) {
        this.mUnit = mUnit;
    }

    @Override
    public String getPieLabel(float value, com.github.mikephil.charting.data.PieEntry pieEntry) {
        PieEntry bean = (PieEntry) pieEntry.getData();
        if (bean != null){
            String unit = TextUtils.isEmpty(mUnit) ? "":mUnit;
            return bean.getLabel() + ": " + bean.getValue() + unit;
        }
        return "";
    }
}
