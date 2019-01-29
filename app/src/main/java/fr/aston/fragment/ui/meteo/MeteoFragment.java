package fr.aston.fragment.ui.meteo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import fr.aston.fragment.R;

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

                if(!editTextMeteoCity.getText().toString().isEmpty()){

                    if(Network.isNetworkAvailable(getContext())){

                    }else{
                        FastDialog.showDialog(getContext(), FastDialog.SIMPLE_DIALOG, getString(R.string.meteo_dialog_network));
                    }

                }else {
                    // TODO : affciher une boite de dialogue
                    FastDialog.showDialog(getContext(), FastDialog.SIMPLE_DIALOG, getString(R.string.meteo_dialog_city_empty));
                }
            }
        });

    }
}
