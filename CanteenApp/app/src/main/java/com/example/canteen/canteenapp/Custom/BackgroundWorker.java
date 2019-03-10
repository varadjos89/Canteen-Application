package com.example.canteen.canteenapp.Custom;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;


import com.example.canteen.canteenapp.LoginActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by VARAD on 23/02/2017.
 */

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog.Builder alertDialog;
    String result = null;
    String line=null;
    public BackgroundWorker(Context ct) {
        context=ct;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String ureg = "https://varadjos88.000webhostapp.com/reg.php";
        String ulog = "https://varadjos88.000webhostapp.com/log.php";
        if (type.equals("Register"))
        {
            try {
                String user_name = params[1];
                String pass_word = params[2];
                String first_name = params[3];
                String last_name = params[4];
                String college_id = params[5];
                String email=params[6];
                URL url = new URL(ureg);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("firstname", "UTF-8") + "=" + URLEncoder.encode(first_name, "UTF-8") + "&" +
                        URLEncoder.encode("lastname", "UTF-8") + "=" + URLEncoder.encode(last_name, "UTF-8") + "&" +
                        URLEncoder.encode("collegeid", "UTF-8") + "=" + URLEncoder.encode(college_id, "UTF-8") + "&" +
                        URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pass_word, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                while ((line = bufferedReader.readLine()) != null) {
                    result = line;
                }
                inputStream.close();
                bufferedReader.close();
                connection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
        else
        {
            String user_name = params[1];
            String pass_word = params[2];

            try {
                URL url = new URL(ulog);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(pass_word,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                while((line = bufferedReader.readLine())!= null) {
                    result = line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }   catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
       /* alertDialog= new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login status");*/
    }

    @Override
    protected void onPostExecute(String result) {
        /*alertDialog= new AlertDialog.Builder(context);
        alertDialog.setMessage(result);
        alertDialog.show();*/
    }
    }


