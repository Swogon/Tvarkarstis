package com.example.tvarkarastis;

import android.os.AsyncTask;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.example.tvarkarastis.data.language;

public class fetchData extends AsyncTask<Void,Void,Void> {
    String data = "";
    String dataParsed = "";
    String singleParsed = "";
    String dateTime = "";
    String dateTimeEnd = "";
    String startTime = "";
    String startTimeEnd = "";
    String temp = "";
    String vieta = "";
    Date c = Calendar.getInstance().getTime();

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String formattedDate = df.format(c);

        @Override
        protected Void doInBackground (Void...voids){
        try {
            URL url = new URL("https://m2m.2world.eu/vu/v3/lt-LT/"+language.dalykas+"+"+language.kursas+"+"+language.grupe+".json");
            // if (language.dalykas == 6 && language.kursas == 1 && language.grupe == 2){url = new URL("https://m2m.2world.eu/vu/v3/lt-LT/6+1+2.json");}
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONArray JA = new JSONArray(data);
            int sameDate = 0;
            for (int i = 0; i < JA.length(); i++) {
                JSONObject JO = (JSONObject) JA.get(i);


                singleParsed =  JO.get("subject") + "\n" +
                        "Tipas: " + JO.get("type") + "\n";
                        //"Vieta: " + JO.get("location") + "\n";

                vieta = (String) JO.get("location");
                if(vieta.equals("")){
                    vieta = "Nuotolinis";
                }
                singleParsed = singleParsed + "Vieta: " + vieta + "\n";
                dateTime = JO.get("start") + "";
                dateTimeEnd = JO.get("end") + "";

                String[] parts = dateTime.split(" ");
                dateTime = parts[0];
                startTime = parts[1];

                String[] parts1 = dateTimeEnd.split(" ");
                dateTimeEnd = parts1[0];
                startTimeEnd = parts1[1];


                if(temp.equals(dateTime)){
                    sameDate = 1;
                }
                else
                    sameDate = 0;

                if (formattedDate.compareTo(dateTime) < 1 && sameDate == 0) {
                    dataParsed = dataParsed + "\n" + dateTime + "\n" + singleParsed + startTime + " - " + startTimeEnd + "\n";
                    temp=dateTime;
                }
                else if (formattedDate.compareTo(dateTime) < 1 && sameDate == 1) {
                    dataParsed = dataParsed +  singleParsed + startTime + " - " + startTimeEnd + "\n";
                }

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);

        scheduleActivity.data.setText(this.dataParsed);
    }
}
