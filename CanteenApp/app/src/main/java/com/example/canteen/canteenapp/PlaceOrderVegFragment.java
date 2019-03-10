package com.example.canteen.canteenapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.canteen.canteenapp.Custom.BackgroundWorkerForBalance;

import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by VARAD on 28/01/2017.
 */
public class PlaceOrderVegFragment extends Fragment {

    NumberPicker numberPicker;
    TextView tv_menu,tv_rate;
    Button bt_placeorder;
    ImageView imageView;
    int menuprice;
    HomeMenulistFragment.MenuDetailsInterface menuDetailsInterface;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        menuDetailsInterface= (HomeMenulistFragment.MenuDetailsInterface) activity;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_order_veg_fragment, container, false);
        final Bundle bundle = getArguments();
        tv_menu = (TextView) view.findViewById(R.id.tv_menuname1);
        imageView= (ImageView) view.findViewById(R.id.iv_menudisplay1);
        tv_rate= (TextView) view.findViewById(R.id.tv_ratevalue1);
        numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker1);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setWrapSelectorWheel(true);
        String menuname = bundle.getString("mname");
        String mp=bundle.getString("mprice");
        mp=mp.substring(0,mp.indexOf("!"));
        menuprice=Integer.parseInt(mp);
        int imgResource=bundle.getInt("micon");
        tv_menu.setText(menuname);
        tv_rate.setText(menuprice+"/-");
        Glide.with(getContext()).load(imgResource).into(imageView);
        bt_placeorder = (Button) view.findViewById(R.id.bt_payment1);
        bt_placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Order");
                builder.setMessage("Are you sure you want to place order?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (menuDetailsInterface.isNetworkAvailable() == true) {
                            int menuquantity = numberPicker.getValue();
                            BackgroundWorkerForBalance forBalance = new BackgroundWorkerForBalance();
                            forBalance.execute(bundle.getString("uname"));
                            int balance = 0;
                            try {
                                balance = Integer.parseInt(forBalance.get());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                            menuprice = menuprice * menuquantity;
                            balance = balance - menuprice;
                            final int finalMenuprice = menuprice;
                            final int finalBalance = balance;
                            if (finalBalance > 0) {
                                Intent intent = new Intent(getContext(), PaymentActivity.class);
                                bundle.putString("mquantity", numberPicker.getValue() + "");
                                bundle.putString("mprice", finalMenuprice + "");
                                bundle.putInt("bal", finalBalance);
                                bundle.putInt("flag", 1);
                                bundle.putString("mpreference", "1");
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                final AlertDialog.Builder builder2=new AlertDialog.Builder(getContext());
                                builder2.setTitle("Error");
                                builder2.setMessage("Balance is not enough to place this order");
                                builder2.setPositiveButton("Ok",null);
                                builder2.show();
                            }
                        }
                        else
                        {
                            android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(getContext());
                            builder1.setMessage("Please Connect to Internet")
                                    .setTitle("Connection Problem")
                                    .setPositiveButton("OK", null);
                            android.app.AlertDialog dialog1 = builder1.create();
                            dialog1.show();
                        }
                    }
                });
               builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               });
                builder.show();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

}
