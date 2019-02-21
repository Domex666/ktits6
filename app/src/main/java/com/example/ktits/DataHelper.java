package com.example.ktits;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class DataHelper extends AsyncTask<String, Void, String> {

    private Context context;
    private TextView text;
    private int id = 105;

    public DataHelper(Context context, TextView text, int day) {
        this.context = context;
        this.text = text;
        this.id = day;
    }

    protected String doInBackground(String... arg0) {

        try {

            String link = "https://domex666.000webhostapp.com/rasp1.php";
            String data = URLEncoder.encode("id", "UTF-8") + "=" + "id";
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            //Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }

            return sb.toString();

        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }

    }

    protected void onPostExecute(String result) {
        this.text.setText(Html.fromHtml(result));
    }

}