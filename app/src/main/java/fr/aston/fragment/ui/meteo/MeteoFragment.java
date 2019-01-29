package fr.aston.fragment.ui.meteo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import fr.aston.fragment.R;
import fr.aston.fragment.ui.meteo.models.OpenWeatherMap;
import fr.aston.fragment.ui.meteo.utils.Constant;
import fr.aston.fragment.ui.meteo.utils.FastDialog;
import fr.aston.fragment.ui.meteo.utils.Network;
import fr.aston.fragment.ui.meteo.utils.Preference;

public class MeteoFragment extends Fragment {

    private EditText editTextMeteoCity;
    private Button buttonSubmit;
    private TextView textViewCity, textViewTemperature;
    private ImageView imageViewIcon;

    public MeteoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meteo, container, false);

        editTextMeteoCity = view.findViewById(R.id.editTextMeteoCity);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);
        textViewCity = view.findViewById(R.id.textViewCity);
        textViewTemperature = view.findViewById(R.id.textViewTemperature);
        imageViewIcon = view.findViewById(R.id.imageViewIcon);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editTextMeteoCity.getText().toString().isEmpty()) {

                    if (Network.isNetworkAvailable(getContext())) {

                        // TODO Connexion API
                        // Instantiate the RequestQueue.
                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        String url = String.format(Constant.URL, editTextMeteoCity.getText().toString());

                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        Log.e("response", response);

                                        getData(response);

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("response", "error");

                                String json = new String(error.networkResponse.data);

                                getData(json);

                            }
                        });

                        // Add the request to the RequestQueue.
                        queue.add(stringRequest);

                    } else {
                        FastDialog.showDialog(getContext(), FastDialog.SIMPLE_DIALOG, getString(R.string.meteo_dialog_network));
                    }

                } else {
                    // TODO : affciher une boite de dialogue
                    FastDialog.showDialog(getContext(), FastDialog.SIMPLE_DIALOG, getString(R.string.meteo_dialog_city_empty));
                }
            }
        });

        if(Preference.getCity(getContext()) != null){
            editTextMeteoCity.setText(Preference.getCity(getContext()));
            buttonSubmit.performClick();
        }

    }

    private void getData(String response){

        OpenWeatherMap owp = new Gson().fromJson(response, OpenWeatherMap.class);
        if(owp.cod.equals("200")){
            // TODO Afficher les informations
            textViewCity.setText(owp.name);
            textViewTemperature.setText(owp.main.temp + "°C");
            Picasso.get().load(String.format(Constant.URL_IMAGE, owp.weather.get(0).icon)).resize(250, 250).into(imageViewIcon);
            Preference.setCity(getContext(), owp.name);
        }else {
            FastDialog.showDialog(getContext(), FastDialog.SIMPLE_DIALOG, owp.message);
        }
    }
}
