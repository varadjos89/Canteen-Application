package com.example.canteen.canteenapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MyProfileActivity extends AppCompatActivity {

    TextView Firstname,Lastname,Username,Collegeid,Phone,Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setTitle("Profile");
        Firstname= (TextView) findViewById(R.id.tv_profile_fname1);
        Lastname= (TextView) findViewById(R.id.tv_profile_lname1);
        Username= (TextView) findViewById(R.id.tv_profile_uname1);
        Collegeid= (TextView) findViewById(R.id.tv_profile_collegeid1);
        Email= (TextView) findViewById(R.id.tv_profile_email1);
        Bundle bundle=getIntent().getExtras();
        Firstname.setText(bundle.getString("fname"));
        Lastname.setText(bundle.getString("lname"));
        Username.setText(bundle.getString("uname"));
        Collegeid.setText(bundle.getString("cid"));
        Email.setText(bundle.getString("email"));
    }
}
