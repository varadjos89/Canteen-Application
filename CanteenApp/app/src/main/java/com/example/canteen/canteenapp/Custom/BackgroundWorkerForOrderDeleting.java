package com.example.canteen.canteenapp.Custom;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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
 * Created by VARAD on 14/03/2017.
 */

public class BackgroundWorkerForOrderDeleting extends AsyncTask<String,Void,String> {
    String result;
    Context context;
    public BackgroundWorkerForOrderDeleting(Context ct) {
        context=ct;
    }

    @Override
    protected String doInBackground(String... params) {
        String first_name = params[0];
        String last_name = params[1];
        String college_id = params[2];
        String user_name = params[3];
        String ordered_menu= params[4];
        String menu_price= params[5];
        String menu_quantity=params[6];
        String uorder = "https://varadjos88.000webhostapp.com/orderdelete.php";
        try {
            URL url = new URL(uorder);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            OutputStream outputStream=connection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter((outputStream),"UTF-8"));
            String message= URLEncoder.encode("firstname", "UTF-8") + "=" + URLEncoder.encode(first_name, "UTF-8") + "&" +
                    URLEncoder.encode("lastname", "UTF-8") + "=" + URLEncoder.encode(last_name, "UTF-8") + "&" +
                    URLEncoder.encode("collegeid", "UTF-8") + "=" + URLEncoder.encode(college_id, "UTF-8") + "&" +
                    URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                    URLEncoder.encode("orderedmenu", "UTF-8") + "=" + URLEncoder.encode(ordered_menu, "UTF-8")+ "&" +
                    URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(menu_price, "UTF-8")+ "&"+
                    URLEncoder.encode("quantity", "UTF-8") + "=" + URLEncoder.encode(menu_quantity, "UTF-8")+"&"+
                    URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
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
        return user_name;
    }
}
