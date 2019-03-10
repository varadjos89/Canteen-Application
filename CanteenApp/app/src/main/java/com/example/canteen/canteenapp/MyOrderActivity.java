package com.example.canteen.canteenapp;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.canteen.canteenapp.Custom.BackgroundWorkerForBalance;
import com.example.canteen.canteenapp.Custom.BackgroundWorkerForOrderDeleting;
import com.example.canteen.canteenapp.Custom.DatabaseConnector;
import com.example.canteen.canteenapp.Custom.Orderlistdetails;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyOrderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<Orderlistdetails> listdetail;
    ListView listView;
    Bundle bundle;
    String username,menuname;
    int menuprice,menuquantity;
    OrderedlistAdapter orderedlistAdapter;
    DatabaseConnector databaseConnector;
    BackgroundWorkerForOrderDeleting deleting;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        setTitle("Orders");
        listView= (ListView) findViewById(R.id.lv_orders);
        bundle=getIntent().getExtras();
        String username=bundle.getString("uname");
        DatabaseConnector databaseConnector=new DatabaseConnector(this);
        Cursor cursor=databaseConnector.onExtract(username);
        listdetail=new ArrayList<>();
        while(cursor.moveToNext()) {
            String menu=cursor.getString(1);
            int totalprice=cursor.getInt(2);
            int quantity=cursor.getInt(3);
            int imgResource=cursor.getInt(4);
            int menuprice=totalprice/quantity;
            listdetail.add(new Orderlistdetails(menu,menuprice ,quantity ,totalprice,imgResource));
        }

        orderedlistAdapter=new OrderedlistAdapter(this,listdetail);
        listView.setAdapter(orderedlistAdapter);
        listView.setOnItemClickListener(this);
        //listView.setOnItemLongClickListener(this);
        registerForContextMenu(listView);
        }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add("Cancel Order");
        menu.add("Delete Order");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        position=info.position;
        Orderlistdetails orderlistdetails=listdetail.get(position);
        username=bundle.getString("uname");
        menuname=orderlistdetails.getMenu();
        menuprice=orderlistdetails.getTotalprice();
        menuquantity=orderlistdetails.getQuantity();
        databaseConnector = new DatabaseConnector(this);
        deleting=new BackgroundWorkerForOrderDeleting(this);
        if(item.getTitle().equals("Cancel Order"))
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Cancel");
            builder.setMessage("Cancelling will refund your money"+"\n"+"are you sure you want to cancel order?");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(isNetworkAvailable()==true) {
                        String firstname = bundle.getString("fname");
                        String lastname = bundle.getString("lname");
                        String collegeid = bundle.getString("cid");
                        databaseConnector.onDelete(username, menuname, menuprice + "", menuquantity + "");
                        deleting.execute(firstname, lastname, collegeid, username, menuname, menuprice + "", menuquantity + "");
                        listdetail.remove(position);
                        orderedlistAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(MyOrderActivity.this);
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
        if(item.getTitle().equals("Delete Order"))
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Delete");
            builder.setMessage("Deletion will delete information about order"+"\n"+"are you sure you want to delete order?");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    databaseConnector.onDelete(username,menuname,menuprice+"",menuquantity+"");
                    listdetail.remove(position);
                    orderedlistAdapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
        return super.onContextItemSelected(item);
    }
    public class OrderedlistAdapter extends ArrayAdapter<Orderlistdetails>{

        public OrderedlistAdapter(Context context,List<Orderlistdetails> Object) {
            super(context, -1, Object);
        }

         class listViewHolder
        {
           TextView menuname,menuprice,quantityvalue,totalpricevalue;
            ImageView iv;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            listViewHolder holder = null;
            if (convertView == null) {

                LayoutInflater li = getLayoutInflater();
                convertView = li.inflate(R.layout.order_list_view, null);
                ImageView imageView= (ImageView) convertView.findViewById(R.id.iv_orders);
                TextView menuname = (TextView) convertView.findViewById(R.id.tv_orders_menuname);
                TextView tvmenuprice = (TextView) convertView.findViewById(R.id.tv_orders_menuprice);
                TextView tvquantityvalue = (TextView) convertView.findViewById(R.id.tv_orders_quantityvalue);
                TextView tvtotalpricevalue = (TextView) convertView.findViewById(R.id.tv_orders_totalpricevalue);
                holder = new listViewHolder();
                holder.iv=imageView;
                holder.menuname = menuname;
                holder.menuprice = tvmenuprice;
                holder.quantityvalue = tvquantityvalue;
                holder.totalpricevalue = tvtotalpricevalue;
                convertView.setTag(holder);

            } else {
                //get counterview from recycler
                holder = (listViewHolder) convertView.getTag();
            }
            Orderlistdetails or = listdetail.get(position);
            Glide.with(getContext()).load(or.getImgResource()).into(holder.iv);
            holder.menuname.setText(or.getMenu());
            holder.menuprice.setText(or.getMenuprice()+"/-");
            holder.quantityvalue.setText(or.getQuantity()+"");
            holder.totalpricevalue.setText(or.getTotalprice()+"/-");
            return convertView;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Orderlistdetails orderlistdetails=listdetail.get(position);
        String menuname=orderlistdetails.getMenu();
        int menuprice=orderlistdetails.getTotalprice();
        int menuquantity=orderlistdetails.getQuantity();
        bundle.putString("mname",menuname);
        bundle.putString("mprice",menuprice+"");
        bundle.putString("mquantity",menuquantity+"");
        Intent intent=new Intent(this,GenerateCodeActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}



