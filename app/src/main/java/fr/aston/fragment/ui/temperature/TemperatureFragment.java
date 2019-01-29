package fr.aston.fragment.ui.temperature;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.aston.fragment.R;

import static fr.aston.fragment.ui.temperature.TemperatureConverter.celsiusFromFahrenheit;
import static fr.aston.fragment.ui.temperature.TemperatureConverter.fahrenheitFromCelcius;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemperatureFragment extends Fragment {

    private EditText editTextCelsius, editTextFahrenheit;
    private Button buttonSave;
    private ListView listViewTemperature;

    private List<String> temperatureList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    public TemperatureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            editTextCelsius.setText("");
            editTextFahrenheit.setText(null);

            temperatureList.clear();
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);

        editTextCelsius = view.findViewById(R.id.editTextCelsius);
        editTextFahrenheit = view.findViewById(R.id.editTextFahrenheit);
        buttonSave = view.findViewById(R.id.buttonSave);
        listViewTemperature = view.findViewById(R.id.listViewTemperature);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        editTextCelsius.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editTextCelsius.hasFocus() && !editable.toString().isEmpty() && isNumeric(editable.toString())){
                    editTextFahrenheit.setText(fahrenheitFromCelcius(Double.valueOf(editable.toString())));
                }

            }
        });

        editTextFahrenheit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editTextFahrenheit.hasFocus() && !editable.toString().isEmpty() && isNumeric(editable.toString())){
                    editTextCelsius.setText(celsiusFromFahrenheit(Double.valueOf(editable.toString())));
                }
            }
        });

        // Sauvegarde des infos
        buttonSave.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View view){
                 String celsius = editTextCelsius.getText().toString();
                 String fahrenheint = editTextFahrenheit.getText().toString();

                 //temperatureList.add(celsius+"°C est égal à "+fahrenheint+"°F");
                 temperatureList.add(String.format(getString(R.string.temperature_convert_text), celsius, fahrenheint));
                 adapter.notifyDataSetChanged();
             }
        });

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, temperatureList);
        listViewTemperature.setAdapter(adapter);
        listViewTemperature.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long l) {
                // effacer l'élément correspondant au clic long
                temperatureList.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

    }

    public boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
}
