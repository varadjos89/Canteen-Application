package com.example.canteen.canteenapp;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.canteen.canteenapp.Custom.BackgroundWorker;
import com.example.canteen.canteenapp.Custom.BackgroundWorkerForOrder;
import com.example.canteen.canteenapp.Custom.DatabaseConnector;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class PaymentActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        imageView= (ImageView) findViewById(R.id.iv_qrcode);
        Bundle bundle=getIntent().getExtras();
        String firstname=bundle.getString("fname");
        String lastname=bundle.getString("lname");
        String collegeid=bundle.getString("cid");
        String username=bundle.getString("uname");
        String menuname=bundle.getString("mname");
        String menuprice=bundle.getString("mprice");
        String menuquantity=bundle.getString("mquantity");
        String menupreference=bundle.getString("mpreference");
        int imgResource=bundle.getInt("micon");
        if(menupreference.equals("2"))
        {
            menuname="Chicken "+menuname;
        }
        String s=firstname+"!"+lastname+"!"+collegeid+"!"+username+"!"+menuname+"!"+menuprice+"!"+menuquantity;
        String key = "Bar12345Bar12345";
        try {
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(s.getBytes());

            BackgroundWorkerForOrder workerForOrder = new BackgroundWorkerForOrder(this);
            workerForOrder.execute(firstname, lastname, collegeid, username, menuname, menuprice + "", menuquantity);
            DatabaseConnector databaseConnector = new DatabaseConnector(this);
            Boolean b = databaseConnector.onInsert(username, menuname, menuprice, menuquantity, imgResource);
            if (b == Boolean.TRUE) {
                Toast.makeText(this, "Successfully placed order", Toast.LENGTH_LONG).show();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                BitMatrix bitMatrix = multiFormatWriter.encode(String.valueOf(encrypted), BarcodeFormat.QR_CODE, 400, 400);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imageView.setImageBitmap(bitmap);

            } else {
                Toast.makeText(this, "Fail in placing order", Toast.LENGTH_LONG).show();
            }
        }
        catch (WriterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }


    }
}
