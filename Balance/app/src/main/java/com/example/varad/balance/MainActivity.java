package com.example.varad.balance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText etuser,etbalance;
    String username;
    String balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.bt_submit);
        etuser= (EditText) findViewById(R.id.et_username);
        etbalance= (EditText) findViewById(R.id.et_balance);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=etuser.getText().toString();
                balance=etbalance.getText().toString();
                if((!username.isEmpty())&&(!balance.isEmpty()))
                {
                    etuser.setText("");
                    etbalance.setText("");
                    Background background=new Background(MainActivity.this);
                    background.execute(username,balance);
                    try {
                        String r=background.get();
                        if(r.equals("1"))
                        {
                            Toast.makeText(MainActivity.this,"Successfully updated balance",Toast.LENGTH_LONG).show();
                        }
                        else if(r.equals("5"))
                        {
                            Toast.makeText(MainActivity.this,"Username doesn't exists",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
