package com.example.canteen.canteenapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteen.canteenapp.Custom.Homelistdetails;
import com.example.canteen.canteenapp.Custom.Menulistdetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VARAD on 27/01/2017.
 */

public class HomeMenulistFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView listView;
    ArrayList<Homelistdetails> menulist;
    MenuDetailsInterface menuDetailsInterface;

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.home_menulist_fragment,container,false);
        listView= (ListView)v.findViewById(R.id.lv_menu);
        menulist=new ArrayList<>();
        menulist.add(new Homelistdetails("Hot Beverages"));
        menulist.add(new Homelistdetails("Snacks"));
        menulist.add(new Homelistdetails("Sandwiches"));
        menulist.add(new Homelistdetails("Thali"));
        menulist.add(new Homelistdetails("Chat Items"));
        menulist.add(new Homelistdetails("Burger"));
        menulist.add(new Homelistdetails("Soups"));
        menulist.add(new Homelistdetails("Chinese Dishes"));
        menulist.add(new Homelistdetails("Cold Drinks"));
        HomeListAdapter adapter=new HomeListAdapter(getContext(),menulist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return v;
    }

    public class HomeListAdapter extends ArrayAdapter<Homelistdetails>
    {

        public HomeListAdapter(Context context, List<Homelistdetails> objects) {

            super(context,-1, objects);
        }
        class Menuviewholder
        {
            TextView menuname;
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Menuviewholder holder=null;
            Homelistdetails homelistdetails= menulist.get(position);
            if(convertView==null) {
                LayoutInflater li = getActivity().getLayoutInflater();
                convertView = li.inflate(R.layout.home_list_view, null);
                TextView tv_menuname= (TextView) convertView.findViewById(R.id.tv_home_menu);
                holder=new Menuviewholder();
                holder.menuname=tv_menuname;
                convertView.setTag(holder);
            }
            else{
                holder= (Menuviewholder) convertView.getTag();
            }

            //later, when the list is scrolled, the view gets added to the recycler and from there
            // the view is then again entered in the list.

            holder.menuname.setText(homelistdetails.getMenuname());
            return convertView;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Homelistdetails homelistdetails=menulist.get(position);
        menuDetailsInterface.getMenuName(homelistdetails.getMenuname());
    }

    public interface MenuDetailsInterface
    {
        void getMenuName(String menuname);
        void getMenuDetails(String menuname,int menuicon,String menuprice,String preference);
        boolean isNetworkAvailable();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        menuDetailsInterface= (MenuDetailsInterface) context;
    }


}
