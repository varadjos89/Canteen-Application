package com.example.canteen.canteenapp;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteen.canteenapp.Custom.BackgroundWorkerForBalance;

import java.util.concurrent.ExecutionException;

public class MyBalanceActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_balance);
        setTitle("Balance");
        textView= (TextView) findViewById(R.id.tv_balance);
        Bundle bundle=getIntent().getExtras();
        String username=bundle.getString("uname");
        Boolean status=isNetworkAvailable();
        if(status==true) {
            BackgroundWorkerForBalance backgroundWorkerForBalance = new BackgroundWorkerForBalance();
            backgroundWorkerForBalance.execute(username);
            String b = null;
            try {
                b = backgroundWorkerForBalance.get();
                textView.setText(b + "/-");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        else
        {
            textView.setText("    Connection Problem"+"\n"+"Please Connect to Internet");
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
