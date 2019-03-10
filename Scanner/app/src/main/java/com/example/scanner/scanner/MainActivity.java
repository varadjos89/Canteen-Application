package com.example.scanner.scanner;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1= (Button) findViewById(R.id.button1);
        final Activity activity=this;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator=new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scanning");
                //integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarco;
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult Result =IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(Result!=null)
        {
            if(Result.getContents()!=null) {
                String s=Result.getContents();
                /*//s="http://192.168.0.100/xiefest/get/?id=XIEFEST0000&phone=1234567890";
                String a=s.substring(33, s.length());
                String b="http://xiefestival.com/confirm/"+a;
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse(b));
                startActivity(httpIntent);*/
                Toast.makeText(this,requestCode+"\n"+resultCode+"\n"+data,Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,"No result",Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
