package com.d.coronavirustracker.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.d.coronavirustracker.R;
import com.d.coronavirustracker.models.Country;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CountryDetailsBottomSheetFragment extends BottomSheetDialogFragment {

    private TextView tvCases, tvActive, tvDeaths, tvRecovered, tvUpdated, tvCountryName;
    PieChart pieChart;
    private Country country;

    public CountryDetailsBottomSheetFragment(Country country) {
        this.country = country;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.coutnry_details_sheet, container,false);

        tvCountryName = view.findViewById(R.id.tvCountryName);
        tvUpdated = view.findViewById(R.id.tvUpdated);
        tvCases = view.findViewById(R.id.tvCases);
        tvActive = view.findViewById(R.id.tvActive);
        tvRecovered = view.findViewById(R.id.tvRecovered);
        tvDeaths = view.findViewById(R.id.tvDeaths);
        pieChart = view.findViewById(R.id.pieChartGlobal);

        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.setHoleColor(Color.WHITE);

        final List<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(Float.parseFloat(country.getCases()), "CASES"));
        yValues.add(new PieEntry(Float.parseFloat(country.getDeaths()), "DEATHS"));
        yValues.add(new PieEntry(Float.parseFloat(country.getActive()), "ACTIVE"));
        yValues.add(new PieEntry(Float.parseFloat(country.getRecovered()), "RECOVERDE"));

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setColors(Color.parseColor("#C9302C"),
                Color.parseColor("#666666"),
                Color.parseColor("#FF9C00"),
                Color.parseColor("#337AB7"));
        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);
        pieChart.invalidate();

        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);

        tvCases.setText(en.format(Long.parseLong(country.getCases())));
        tvActive.setText(en.format(Long.parseLong(country.getActive())));
        tvRecovered.setText(en.format(Long.parseLong(country.getRecovered())));
        tvDeaths.setText(en.format(Long.parseLong(country.getDeaths())));
        tvUpdated.setText(timeStampToString(Long.parseLong(country.getUpdated())));
        tvCountryName.setText(country.getCountry());

        return view;
    }

    private String timeStampToString(long time) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        return DateFormat.format("dd/MM/yyyy hh:mm:ss", calendar).toString();
    }
}
