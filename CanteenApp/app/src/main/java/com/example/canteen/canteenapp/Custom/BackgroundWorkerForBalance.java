package com.example.canteen.canteenapp.Custom;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by VARAD on 11/03/2017.
 */

public class BackgroundWorkerForBalance extends AsyncTask<String,Void,String> {
    Context context;
    String result;

    @Override
    protected String doInBackground(String... params) {
        String user_name = params[0];
        String uorder = "https://varadjos88.000webhostapp.com/bal.php";
        try {
            URL url = new URL(uorder);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            OutputStream outputStream=connection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter((outputStream),"UTF-8"));
            String message= URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8");
            bufferedWriter.write(message);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result = line;
            }
            inputStream.close();
            bufferedReader.close();
            connection.disconnect();
            return result;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
