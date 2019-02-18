package com.example.pidrone;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class loginActivity extends AsyncTask <String, String, String> {

    private Context context;
    private TextView statusField, role;

    private String dataNotNull;


    public loginActivity(Context context, TextView statusField, TextView role) {
        this.context = context;
        this.statusField = statusField;
        this.role = role;
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            String email = (String)arg0[0];
            String password = (String)arg0[1];
            String link = "https://pidrone.000webhostapp.com/index.php/Login/";

            String data  = URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                dataNotNull = line;
                sb.append(line);
                //break;
            }
            reader.close();

            return sb.toString();

        } catch (Exception e) {
            return new String("Exeption: " + e.getMessage());
        }

    }

    @Override
    protected void onPostExecute(String result){
        this.statusField.setText("sukses login");
        this.role.setText(dataNotNull);
        if(dataNotNull.equals("</div>0")){
            Intent intent = new Intent(context, myMap.class);
            context.startActivity(intent);
        }
    }
}

