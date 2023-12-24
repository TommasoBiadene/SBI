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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;




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
    private TextView AQI;
    private TextView co;
    private TextView no;
    private TextView no2;
    private TextView o3;
    private TextView so2;
    private TextView mp2_5;
    private TextView mp10;
    private TextView nh3,error,warningp;


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
        AQI = getView().findViewById(R.id.AQI);
        co = getView().findViewById(R.id.co);
        no = getView().findViewById(R.id.no);
        no2 = getView().findViewById(R.id.no2);
        o3 = getView().findViewById(R.id.o3);
        so2 = getView().findViewById(R.id.so2);
        mp2_5 = getView().findViewById(R.id.mp2_5);
        mp10 = getView().findViewById(R.id.mp10);
        nh3 = getView().findViewById(R.id.nh3);
        error = getView().findViewById(R.id.error);
        warningp = getView().findViewById(R.id.warningp);
        myButton = getView().findViewById(R.id.myButton);

        // Set up the button click listener
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apiKey = "c566ee8a6a9310059a2caf2c6c81d05e";
                String city = editTextNewText.getText().toString();

                if(city!= null){

                    String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
                    new FetchWeatherTask().execute(apiUrl);
                }

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


    private class FetchWeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String apiUrl = urls[0];
            String result = "";
            String result1 = "";
            int numberhours=0;
            int oneday= 0;
            double maxmp10inoneday=0;
            int seriedi4= 0;
            int seriedi10=0;

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


                URL url1 = new URL("https://api.openweathermap.org/data/2.5/air_pollution?lat="+lat+"&lon="+lon+"&appid=c566ee8a6a9310059a2caf2c6c81d05e");//https://api.openweathermap.org/data/2.5/air_pollution?lat=45.438759&lon=12.327145&appid=c566ee8a6a9310059a2caf2c6c81d05e
                HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
                BufferedReader reader1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
                String line1;
                while ((line1 = reader1.readLine()) != null) {
                    result1 += line1;
                }
                reader1.close();
                connection1.disconnect();


                System.out.println(result1);

//////////////////////////////////////////////////////////////////////datetime

                // Create a LocalDateTime object representing the current date and time
                LocalDateTime localDateTime = LocalDateTime.now();

                // Convert LocalDateTime to Instant
                Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

                // Get the Unix timestamp (in seconds)
                long unixTimestamp = instant.getEpochSecond();
                long unixTimestamp1 = instant.getEpochSecond()-864000;



                Instant instant_spet_nonunix = Instant.ofEpochSecond(unixTimestamp1);

                // Convert Instant to LocalDateTime in a specific time zone (e.g., UTC)
                LocalDateTime localDateTime_nonunix = LocalDateTime.ofInstant(instant_spet_nonunix, ZoneOffset.UTC);


                System.out.println("Current LocalDateTime: " + localDateTime);
                System.out.println("Unix Timestamp: " + unixTimestamp);
                System.out.println("Current LocalDateTime 2: " + localDateTime_nonunix);
                System.out.println("Unix Timestamp 2: " + unixTimestamp1);


//////////////////////////////////////////////////////////////////////datetime


                String result2 = "";
                URL url2 = new URL("https://api.openweathermap.org/data/2.5/air_pollution/history?lat="+lat+"&lon="+lon+"&start="+unixTimestamp1+"&end="+unixTimestamp+"&appid=c566ee8a6a9310059a2caf2c6c81d05e");
                HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
                String line2;
                while ((line2 = reader2.readLine()) != null) {
                    result2 += line2;
                }
                reader2.close();
                connection2.disconnect();

                System.out.println(result2);


                try {
                    JSONObject jsonObject111 = new JSONObject(result2);
                    JSONArray listArray = jsonObject111.getJSONArray("list");

                    for (int i = 0; i < listArray.length(); i++) {

                        JSONObject entryObject = listArray.getJSONObject(i);
                        JSONObject componentsObject = entryObject.getJSONObject("components");
                        double coValue = componentsObject.getDouble("pm10");
                        if(coValue>maxmp10inoneday){
                            maxmp10inoneday=coValue;
                        }
                        if(oneday==24&&maxmp10inoneday>=50){
                            seriedi4++;
                            seriedi10++;
                            oneday=0;
                            maxmp10inoneday=0;
                        }
                        if(oneday==24&&maxmp10inoneday<50){
                            seriedi4=0;
                            seriedi10=0;
                            maxmp10inoneday=0;
                            oneday=0;
                        }

                        oneday++;
                        numberhours ++;

                        System.out.println("CO Valuessssssssssssssss: " + coValue);
                    }
                    System.out.println(numberhours+" aa " +seriedi10+" aa "+seriedi4);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JSONObject jsonResult1 = new JSONObject(result2);
                JSONObject list1 = jsonResult1.getJSONArray("list").getJSONObject(0);
                JSONObject main1 = list1.getJSONObject("main");
                int aqi1 = main1.getInt("aqi");



                JSONObject jsonResult11 = new JSONObject(result1);

                jsonResult11.put("aqi", aqi1);
                jsonResult11.put("seriedi4", seriedi4);
                jsonResult11.put("seriedi10", seriedi10);

                result1 = jsonResult11.toString();

                //System.out.println(result1);
                //System.out.println(jsonResult11);
                //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+aqi1);
               // warningp.setText( "rrrrrrrrrrrrrrrrrilevata: "+aqi1);

/*

                JSONObject jsonResult = new JSONObject(result);
                JSONObject list = jsonResult.getJSONArray("list").getJSONObject(0);
                JSONObject main = list.getJSONObject("main");
                JSONObject components = list.getJSONObject("components");

                main.put("new", 111111);

                String modifiedJsonString = jsonResult.toString();
                System.out.println(result1);
                System.out.println(modifiedJsonString);

*/




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
                int Probabilita = jsonResult.getInt("aqi");
                int ssequenza4 = jsonResult.getInt("seriedi4");
                int ssequenza10 = jsonResult.getInt("seriedi10");
                JSONObject main = list.getJSONObject("main");
                JSONObject components = list.getJSONObject("components");
                int aqi = main.getInt("aqi");
                double co_= components.getDouble("co");
                double no_= components.getDouble("no");
                double no2_= components.getDouble("no2");
                double o3_= components.getDouble("o3");
                double so2_= components.getDouble("so2");
                double pm2_5_= components.getDouble("pm2_5");
                double pm10_= components.getDouble("pm10");
                double nh3_= components.getDouble("nh3");

                //int pippo=main.getInt("new");



                /*
                String jsonString = "[{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}, {\"name\": \"Alice\", \"age\": 25, \"city\": \"London\"}]";

                JSONArray jsonArray = new JSONArray(jsonString);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject person = jsonArray.getJSONObject(i);
                    // Assuming "tel_number" is a string; you can adjust the type accordingly
                    person.put("tel_number", "123-456-7890");
                }

                String modifiedJsonString = jsonArray.toString();

                System.out.println(modifiedJsonString);
                */





                //String pollutionInfo = "Air Quality Index (AQI): " + aqi +"\n co "+ co_+"\nno "+ no_+"\nno2 "+no2_+"\no3 "+ o3_+"\nso2 "+ so2_+"\nmp2_5 "+ pm2_5_+"\nmp10 "+ pm10_+"\nnh3 "+ nh3_/*+"\nlat "+ lat11+"\nlon "+ lon11*/ ;



                if(pm10_ > 50){
                   error.setText("Alti livelli di inquinamento rilevati");
                }



                //temperatureTextView.setText( pollutionInfo);
                AQI.setText( "AQI rilevata: "+aqi);
                co.setText( "Co rilevata: "+co_);
                no.setText( "No rilevata: "+no_);
                no2.setText( "No2 rilevata: "+no2_);
                o3.setText( "O3 rilevata: "+o3_);
                so2.setText( "So2 rilevata: "+so2_);
                mp2_5.setText( "Pm2_5 rilevata: "+pm2_5_);
                mp10.setText( "Pm10 rilevata: "+pm10_);
                nh3.setText( "Nh3 rilevata: "+nh3_);



                if (ssequenza4>3&&ssequenza10<10){
                    warningp.setText("Livello di allerta 2: zona ZTL rossa");
                } else if(ssequenza4>3&&ssequenza10>9){
                    warningp.setText("Livello di allerta 1: zona ZTL arancione");
                }else {
                    warningp.setText("Livello di allerta 0: zona ZTL verde");
                }








            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}