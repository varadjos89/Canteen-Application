package com.example.canteen.canteenapp;

import android.app.AlertDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.canteen.canteenapp.Custom.BackgroundWorker;

import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    EditText firstnameEt,lastnameEt,collegeIdEt,usernameEt,emailEt,passwordEt,retypepasswordEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");
        firstnameEt= (EditText) findViewById(R.id.et_firstname);
        lastnameEt= (EditText) findViewById(R.id.et_lastname);
        collegeIdEt= (EditText) findViewById(R.id.et_collegeid);
        usernameEt= (EditText) findViewById(R.id.et_username);
        emailEt= (EditText) findViewById(R.id.et_email);
        passwordEt= (EditText) findViewById(R.id.et_password);
        retypepasswordEt= (EditText) findViewById(R.id.et_retypepassword);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.reset_action) {
            firstnameEt.setText("");
            lastnameEt.setText("");
            collegeIdEt.setText("");
            usernameEt.setText("");
            emailEt.setText("");
            passwordEt.setText("");
            retypepasswordEt.setText("");
        }
        return super.onOptionsItemSelected(item);
    }
    public void OnSubmit(View view)
    {
        String type="Register";
        String firstname=firstnameEt.getText().toString().trim();
        String lastname=lastnameEt.getText().toString().trim();
        String collegeid=collegeIdEt.getText().toString().trim();
        String username=usernameEt.getText().toString().trim();
        String email=emailEt.getText().toString().trim();
        String password=passwordEt.getText().toString().trim();
        String repassword=retypepasswordEt.getText().toString().trim();
        if(isNetworkAvailable()==true) {
            if ((!firstname.isEmpty()) && (!lastname.isEmpty()) && (!username.isEmpty()) && (!email.isEmpty())
                    && (!password.isEmpty()) && (!repassword.isEmpty())) {
                if ((password.length() > 6) && (repassword.length() > 6)) {
                    if (password.equals(repassword)) {
                        BackgroundWorker worker = new BackgroundWorker(this);
                        worker.execute(type, username, password, firstname, lastname, collegeid, email);
                        try {
                            String result = worker.get();
                            if (result.equals("1")) {
                                super.onBackPressed();
                                firstnameEt.setText("");
                                lastnameEt.setText("");
                                collegeIdEt.setText("");
                                usernameEt.setText("");
                                passwordEt.setText("");
                                retypepasswordEt.setText("");
                            }
                            else if(result.equals("2")){
                                usernameEt.setText("");
                                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                builder.setMessage("Username already exists"+"\n"+"please use different username for registration")
                                        .setTitle("Error")
                                        .setPositiveButton("OK", null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                            else
                            {
                                Toast.makeText(this,"Connection fail",Toast.LENGTH_LONG).show();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }


                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Password doesn't match ")
                                .setTitle("Error")
                                .setPositiveButton("OK", null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Password is too short")
                            .setTitle("Error")
                            .setPositiveButton("OK", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Please fill all the fields")
                        .setTitle("Error")
                        .setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        else
        {
            final android.support.v7.app.AlertDialog.Builder builder1=new android.support.v7.app.AlertDialog.Builder(this);
            builder1.setTitle("Connection Problem");
            builder1.setMessage("Please Connect to Internet");
            builder1.setPositiveButton("OK", null);
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
