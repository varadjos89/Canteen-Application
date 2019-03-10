package com.example.canteen.canteenapp;

import android.app.AlertDialog;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.example.canteen.canteenapp.Custom.BackgroundWorker;

import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity {

    EditText usernameEt,passwordEt;
    SharedPreferences sharedPreferences;
    String loginstatus;
    String result=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences=this.getSharedPreferences("Infofile",MODE_PRIVATE);
        String s= sharedPreferences.getString("Status","0");
        if(s.equals("1"))
        {
            Intent intent=new Intent(this,HomeActivity.class);
            startActivity(intent);
            this.finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEt= (EditText) findViewById(R.id.et_username);
        passwordEt= (EditText) findViewById(R.id.et_password);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }


    public void OnRegister(View view)
    {
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
    public void OnLogin(View view)
    {
        String type="Login";
        String username=usernameEt.getText().toString();
        String password=passwordEt.getText().toString();
        if(isNetworkAvailable()==true)
        {
        if((!username.isEmpty())&&(!password.isEmpty())) {
            BackgroundWorker worker = new BackgroundWorker(this);
            worker.execute(type, username, password);
            try {
                result = worker.get();
                if (!(result.equals(null)) && !(result.equals("0"))) {
                    String firstname = result.substring(0, result.indexOf(" "));
                    result = result.substring(result.indexOf(" ") + 1, result.length());
                    String lastname = result.substring(0, result.indexOf(" "));
                    result = result.substring(result.indexOf(" ") + 1, result.length());
                    String id = result.substring(0, result.indexOf(" "));
                    result = result.substring(result.indexOf(" ") + 1, result.length());
                    String user = result.substring(0, result.indexOf(" "));
                    result = result.substring(result.indexOf(" ") + 1, result.length());
                    String email = result.substring(0, result.indexOf(" "));
                    int balance = Integer.parseInt(result.substring(result.indexOf(" ") + 1, result.length()));
                    //Toast.makeText(this,balance,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    loginstatus = "1";
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Status", loginstatus);
                    editor.putString("fname", firstname);
                    editor.putString("lname", lastname);
                    editor.putString("uname", user);
                    editor.putString("cid", id);
                    editor.putString("email", email);
                    editor.commit();
                    finish();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Incorrect username and password")
                            .setTitle("Error")
                            .setPositiveButton("OK", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        }
        else {
            final android.support.v7.app.AlertDialog.Builder builder1=new android.support.v7.app.AlertDialog.Builder(this);
            builder1.setTitle("Connection Problem");
            builder1.setMessage("Please Connect to Internet");
            builder1.setPositiveButton("Ok",null);
            builder1.show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
