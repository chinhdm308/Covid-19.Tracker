package com.d.coronavirustracker.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.d.coronavirustracker.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private TextView tvCases, tvActive, tvDeaths, tvRecovered, tvUpdated;
    private SimpleArcLoader loader;
    private Context context;
    private RelativeLayout showLayout;
    PieChart pieChart;

    public HomeFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvUpdated = view.findViewById(R.id.updated);
        tvCases = view.findViewById(R.id.tvCases);
        tvActive = view.findViewById(R.id.tvActive);
        tvRecovered = view.findViewById(R.id.tvRecovered);
        tvDeaths = view.findViewById(R.id.tvDeaths);

        loader = view.findViewById(R.id.loader);
        showLayout = view.findViewById(R.id.showLayout);

        loader.setVisibility(View.VISIBLE);
        showLayout.setVisibility(View.INVISIBLE);
        loader.start();

        pieChart = view.findViewById(R.id.pieChartGlobal);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.setHoleColor(Color.WHITE);

        final List<PieEntry> yValues = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "https://disease.sh/v2/all";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            yValues.add(new PieEntry(response.getInt("cases"), "CASES"));
                            yValues.add(new PieEntry(response.getInt("deaths"), "DEATHS"));
                            yValues.add(new PieEntry(response.getInt("active"), "ACTIVE"));
                            yValues.add(new PieEntry(response.getInt("recovered"), "RECOVERDE"));

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

                            tvCases.setText(en.format(response.getLong("cases")));
                            tvActive.setText(en.format(response.getLong("active")));
                            tvRecovered.setText(en.format(response.getLong("recovered")));
                            tvDeaths.setText(en.format(response.getLong("deaths")));

                            tvUpdated.setText(timeStampToString(response.getLong("updated")));

                            loader.stop();
                            loader.setVisibility(View.INVISIBLE);
                            showLayout.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        });
        requestQueue.add(jsonObjectRequest);

        return view;
    }

    private String timeStampToString(long time) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        return DateFormat.format("dd/MM/yyyy hh:mm:ss", calendar).toString();
    }
}
