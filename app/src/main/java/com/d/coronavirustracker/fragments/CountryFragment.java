package com.d.coronavirustracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.d.coronavirustracker.R;
import com.d.coronavirustracker.adapters.CountryAdapter;
import com.d.coronavirustracker.models.Country;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CountryFragment extends Fragment {

    private EditText editText;
    private RecyclerView recyclerView;
    private Context context;
    private List<Country> countries;
    private CountryAdapter countryAdapter;

    public CountryFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_country, container, false);

        countries = new ArrayList<>();

        editText = view.findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Country> countriesSearch = new ArrayList<>();
                for (Country i : countries) {
                    if (i.getCountry().toLowerCase().contains(s.toString().toLowerCase())) {
                        countriesSearch.add(i);
                    }
                }

                countryAdapter = new CountryAdapter(getActivity(), context, countriesSearch);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(countryAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        final SimpleArcLoader loader = view.findViewById(R.id.loader);

        recyclerView.setVisibility(View.INVISIBLE);
        loader.setVisibility(View.VISIBLE);
        loader.start();

        String url = "https://disease.sh/v2/countries";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Country country;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                country = new Country();
                                country.setUpdate(object.getString("updated"));
                                country.setCountry(object.getString("country"));
                                JSONObject countryInfo = object.getJSONObject("countryInfo");
                                country.setFlag(countryInfo.getString("flag"));
                                country.setCases(object.getString("cases"));
                                country.setDeaths(object.getString("deaths"));
                                country.setRecovered(object.getString("recovered"));
                                country.setActive(object.getString("active"));

                                countries.add(country);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Collections.sort(countries, new Comparator<Country>() {
                            @Override
                            public int compare(Country o1, Country o2) {
                                return Float.compare(Float.parseFloat(o2.getCases()), Float.parseFloat(o1.getCases()));
                            }
                        });
                        countryAdapter = new CountryAdapter(getActivity(), context, countries);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(countryAdapter);

                        loader.stop();
                        recyclerView.setVisibility(View.VISIBLE);
                        loader.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);

        return view;
    }
}
