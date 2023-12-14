package com.example.sbi;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button myButton;
    private EditText editTextNewText;
    private TextView temperatureTextView;

    private String CityName;
    private String apiKey = "c566ee8a6a9310059a2caf2c6c81d05e",
            city = "Venezia",
            apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        temperatureTextView = getView().findViewById(R.id.temperatureTextView);
        temperatureTextView = getView().findViewById(R.id.temperatureTextView);
        myButton = getView().findViewById(R.id.myButton);

        // Set up the button click listener
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the changeText function when the button is clicked
                changeText(v);
                String apiKey = "c566ee8a6a9310059a2caf2c6c81d05e";
                String city = CityName;
                String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
                new FetchWeatherTask().execute(apiUrl);
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        myButton = rootView.findViewById(R.id.myButton);
        editTextNewText = rootView.findViewById(R.id.editTextNewText);
        return rootView;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_search, container, false);
    }
    public void changeText(View view) {
        String newText = editTextNewText.getText().toString();

        CityName = newText;
    }

    private class FetchWeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String apiUrl = urls[0];
            String result = "";
            String result1 = "";

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    result += line;
                }

                reader.close();
                inputStream.close();
                connection.disconnect();

                JSONObject jsonObject = new JSONObject(result);
                JSONObject mainObject = jsonObject.getJSONObject("coord");
                double lat = mainObject.getDouble("lat");
                double lon = mainObject.getDouble("lon");


                String apiurl1= "";
                URL url1 = new URL("https://api.openweathermap.org/data/2.5/air_pollution?lat="+lat+"&lon="+lon+"&appid=c566ee8a6a9310059a2caf2c6c81d05e");//https://api.openweathermap.org/data/2.5/air_pollution?lat=45.438759&lon=12.327145&appid=c566ee8a6a9310059a2caf2c6c81d05e
                HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
                BufferedReader reader1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
                String line1;
                while ((line1 = reader1.readLine()) != null) {
                    result1 += line1;
                }
                reader1.close();
                connection1.disconnect();



            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            return result1;
        }

        @Override
        protected void onPostExecute(String result) {
            try {

                JSONObject jsonResult = new JSONObject(result);
                JSONObject list = jsonResult.getJSONArray("list").getJSONObject(0);
                JSONObject main = list.getJSONObject("main");
                JSONObject components = list.getJSONObject("components");
                int aqi = main.getInt("aqi");
                double co= components.getDouble("co");
                double no= components.getDouble("no");
                double no2= components.getDouble("no2");
                double o3= components.getDouble("o3");
                double so2= components.getDouble("so2");
                double pm2_5= components.getDouble("pm2_5");
                double pm10= components.getDouble("pm10");
                double nh3= components.getDouble("nh3");


                String pollutionInfo = "Air Quality Index (AQI): " + aqi +"\n co "+ co+"\nno "+ no+"\nno2 "+no2+"\no3 "+ o3+"\nso2 "+ so2+"\nmp2_5 "+ pm2_5+"\nmp10 "+ pm10+"\nnh3 "+ nh3/*+"\nlat "+ lat11+"\nlon "+ lon11*/ ;
                if(pm10 < 50){
                    pollutionInfo= pollutionInfo +"\nTranquilllllllo anche oggi non serve la maschera a gas" ;
                    pollutionInfo= pollutionInfo +"\nTranquilllllllo anche oggi non serve la maschera a gas" ;
                }



                temperatureTextView.setText( pollutionInfo);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}