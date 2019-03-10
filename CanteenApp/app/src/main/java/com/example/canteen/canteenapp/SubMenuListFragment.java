package com.example.canteen.canteenapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.example.canteen.canteenapp.Custom.Menulistdetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VARAD on 27/01/2017.
 */
public class SubMenuListFragment extends Fragment implements AdapterView.OnItemClickListener {
    ArrayList<Menulistdetails> menulist1;
    ListView listView1;
    Menulistdetails listdetails;
    HomeMenulistFragment.MenuDetailsInterface menuDetailsInterface;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        menuDetailsInterface= (HomeMenulistFragment.MenuDetailsInterface) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_menulist_fragment, container, false);
        Bundle bundle = getArguments();
        String s = bundle.getString("name");
        menulist1 = new ArrayList();
        listView1 = (ListView) view.findViewById(R.id.lv_menu1);
        if (s.equals("Hot Beverages")) {
            menulist1.add(new Menulistdetails("Tea", "7!0", R.drawable.tea, "1"));
            menulist1.add(new Menulistdetails("Sp.Tea", "10!0",R.drawable.specialtea, "1"));
            menulist1.add(new Menulistdetails("Coffee", "10!0", R.drawable.coffee, "1"));
            menulist1.add(new Menulistdetails("Bournvita", "10!0", R.drawable.bournvita, "1"));
        } else if (s.equals("Snacks")) {
            menulist1.add(new Menulistdetails("Misal Pav", "25!0", R.drawable.misalpav, "1"));
            menulist1.add(new Menulistdetails("Usal Pav", "25!0",R.drawable.usalpav, "1"));
            menulist1.add(new Menulistdetails("Cutlet", "10!15", R.drawable.cutlet, "2"));
            menulist1.add(new Menulistdetails("Pakoda", "10!20", R.drawable.pakoda, "2"));
            menulist1.add(new Menulistdetails("Poha", "20!0", R.drawable.poha, "1"));
            menulist1.add(new Menulistdetails("Vada Pav", "12!0", R.drawable.vadapav, "1"));
            menulist1.add(new Menulistdetails("Samosa", "10!0", R.drawable.samosa, "1"));
            menulist1.add(new Menulistdetails("Idli", "20!0", R.drawable.idli, "1"));
            menulist1.add(new Menulistdetails("Medhuvada", "20!0", R.drawable.medhuvada, "1"));
            menulist1.add(new Menulistdetails("Samosa Sambar", "20!0", R.drawable.samosasambar, "1"));
        } else if (s.equals("Sandwiches")) {
            menulist1.add(new Menulistdetails("Chatni Sandwich", "20!0", R.drawable.chatnisandwich, "1"));
            menulist1.add(new Menulistdetails("Cheese Sandwich", "25!35", R.drawable.cheesesandwich, "2"));
            menulist1.add(new Menulistdetails("Jam Sandwich", "20!0", R.drawable.jamsandwich, "1"));
            menulist1.add(new Menulistdetails("Omlet Sandwich", "20!0", R.drawable.omletsandwich, "1"));
            menulist1.add(new Menulistdetails("Bread Butter", "15!0", R.drawable.breadbutter, "1"));
            menulist1.add(new Menulistdetails("Toast Sandwich", "25!35",R.drawable.toastsandwich , "2"));
            menulist1.add(new Menulistdetails("Toast cheese Sandwich", "30!40", R.drawable.toastcheesesandwich, "2"));
            menulist1.add(new Menulistdetails("Toast Jam Sandwich", "25!0", R.drawable.toastjamsandwich, "1"));
            menulist1.add(new Menulistdetails("Toast Omlet Sandwich", "25!0", R.drawable.toastomletsandwich, "1"));
            menulist1.add(new Menulistdetails("Jam Bread Butter", "20!0", R.drawable.jambreadbutter, "1"));
            menulist1.add(new Menulistdetails("Toast Butter", "15!0", R.drawable.toastbutter, "1"));
            menulist1.add(new Menulistdetails("Jam Toast Butter", "20!0", R.drawable.toastbutter, "1"));
            menulist1.add(new Menulistdetails("Roll", "20!30", R.drawable.roll, "2"));
            menulist1.add(new Menulistdetails("Cheese Roll", "25!35", R.drawable.cheeseroll, "2"));
            menulist1.add(new Menulistdetails("Grilled Sandwich", "25!35", R.drawable.grilledsandwich, "2"));
            menulist1.add(new Menulistdetails("Cheese Grilled Sandwich", "30!40", R.drawable.cheesegrilledsandwich, "2"));
        } else if (s.equals("Thali")) {
            menulist1.add(new Menulistdetails("Veg Thali", "30!0",R.drawable.thali , "1"));
            menulist1.add(new Menulistdetails("Non-veg Thali", "50!0",R.drawable.thali , "1"));
        } else if (s.equals("Chat Items")) {
            menulist1.add(new Menulistdetails("Bhel", "20!0", R.drawable.bhel, "1"));
            menulist1.add(new Menulistdetails("Sev Puri", "20!0", R.drawable.sevpuri, "1"));
            menulist1.add(new Menulistdetails("Ragda Pattice", "25!0", R.drawable.ragdapattice, "1"));
            menulist1.add(new Menulistdetails("Dahi Puri", "20!0", R.drawable.dahipuri, "1"));
            menulist1.add(new Menulistdetails("Pani Puri", "20!0", R.drawable.panipuri, "1"));

        } else if (s.equals("Burger")) {
            menulist1.add(new Menulistdetails("Veg Burger", "20!30", R.drawable.burger, "2"));
            menulist1.add(new Menulistdetails("Veg Cheese Burger", "25!35",R.drawable.cheeseburger , "2"));
        } else if (s.equals("Chinese Dishes")) {
            menulist1.add(new Menulistdetails("Fried Rice", "40!50", R.drawable.friedrice, "2"));
            menulist1.add(new Menulistdetails("Garlic Rice", "40!50", R.drawable.garlicrice, "2"));
            menulist1.add(new Menulistdetails("schezwan Rice", "40!50", R.drawable.schezwanrice, "2"));
            menulist1.add(new Menulistdetails("Mushroom Rice", "50!0", R.drawable.mushroomrice, "1"));
            menulist1.add(new Menulistdetails("Special Fried Rice", "50!60", R.drawable.specialfriedrice, "2"));
            menulist1.add(new Menulistdetails("Triple Schezwan Rice", "50!60", R.drawable.tripleszewanrice, "2"));
            menulist1.add(new Menulistdetails("Manchurian Rice", "50!60", R.drawable.munchurianrice, "2"));
            menulist1.add(new Menulistdetails("Hakka Noodle", "45!55", R.drawable.hakkanoodle, "2"));
            menulist1.add(new Menulistdetails("Schezwan Noodle", "40!50", R.drawable.szechwannoodle, "2"));
            menulist1.add(new Menulistdetails("Garlic Noodle", "40!50", R.drawable.garlicnoodle, "2"));
            menulist1.add(new Menulistdetails("Mushroom Noodle", "55!0", R.drawable.mushroomnoodle, "1"));
            menulist1.add(new Menulistdetails("Special Fried Noodle", "50!60", R.drawable.specialfriednoodle, "2"));
            menulist1.add(new Menulistdetails("Triple Schezwan Noodle", "50!60", R.drawable.tripleszechwannoodle, "2"));
            menulist1.add(new Menulistdetails("Manchurian Noodle", "50!60", R.drawable.manchuriannoodle, "2"));
            menulist1.add(new Menulistdetails("Crispy Roll", "35!45", R.drawable.crispyroll, "2"));
            menulist1.add(new Menulistdetails("Spring Roll", "45!0", R.drawable.springroll, "1"));
            menulist1.add(new Menulistdetails("Chinese Bhel", "25!35", R.drawable.chinesebhel, "2"));
            menulist1.add(new Menulistdetails("Chicken Chilly", "55!0", R.drawable.chickenchilly, "1"));
            menulist1.add(new Menulistdetails("Dry Chicken Chilly", "45!0", R.drawable.drychickenchilly, "1"));
            menulist1.add(new Menulistdetails("Chicken Lollypop", "60!0",R.drawable.chickenlollipop, "1"));
        } else if (s.equals("Soups")) {
            menulist1.add(new Menulistdetails("Tomato Soup", "30!0", R.drawable.tomatosoup, "1"));
            menulist1.add(new Menulistdetails("Sweet Corn Soup", "40!0", R.drawable.sweetcornsoup, "1"));
            menulist1.add(new Menulistdetails("Manchow Soup", "35!45", R.drawable.manchowsoup, "2"));
            menulist1.add(new Menulistdetails("Noodle Soup", "35!45", R.drawable.noodlesoup, "2"));
            menulist1.add(new Menulistdetails("Mushroom Soup", "40!0", R.drawable.mushroomsoup , "1"));
        } else if (s.equals("Cold Drinks")) {
            menulist1.add(new Menulistdetails("Fanta", "13!0", R.drawable.fanta, "1"));
            menulist1.add(new Menulistdetails("Pepsi", "12!0", R.drawable.pepsi, "1"));
            menulist1.add(new Menulistdetails("Thumbs Up", "14!0", R.drawable.thumbsup, "1"));
            menulist1.add(new Menulistdetails("Coca Cola", "15!0", R.drawable.cocacola, "1"));
            menulist1.add(new Menulistdetails("Maza", "14!0", R.drawable.maza, "1"));
            menulist1.add(new Menulistdetails("Slice", "15!0", R.drawable.slice, "1"));
            menulist1.add(new Menulistdetails("Mirinda", "13!0", R.drawable.mirinda, "1"));
        }
        MenuDetails details = new MenuDetails(getContext(), menulist1);
        listView1.setAdapter(details);
        listView1.setOnItemClickListener(this);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    class Menuviewholder
    {
        TextView Name,Price;
        ImageView ImageView;
    }

    public class MenuDetails extends ArrayAdapter<Menulistdetails>
    {

        public MenuDetails(Context context, List<Menulistdetails> objects) {

            super(context,-1, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Menuviewholder holder=null;
            Menulistdetails list= menulist1.get(position);
            //initially the recycler is empty for the screen and hence it enters the if statement.
            if(convertView==null) {
                LayoutInflater li = getActivity().getLayoutInflater();
                //null is passed because the whole xml time is to be inflated in the view object
                convertView = li.inflate(R.layout.menu_list_view, null);

                TextView tv_name= (TextView) convertView.findViewById(R.id.tv_menu);
                TextView tv_price= (TextView) convertView.findViewById(R.id.tv_price);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
                holder=new Menuviewholder();
                holder.Name=tv_name;
                holder.Price=tv_price;
                holder.ImageView=imageView;
                convertView.setTag(holder);
            }
            else{
                holder= (Menuviewholder) convertView.getTag();
            }

            //later, when the list is scrolled, the view gets added to the recycler and from there
            // the view is then again entered in the list.

            holder.Name.setText(list.getName());
            String p=list.getPrice();
            p=p.substring(0,p.indexOf("!"));
            holder.Price.setText(p+"/-");
            //holder.ImageView.setImageResource(R.drawable.lighthouse);
            Glide.with(getContext()).load(list.getMenuicon()).into(holder.ImageView);
            return convertView;

        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        listdetails=menulist1.get(position);
        String menuname=listdetails.getName();
        int menuicon=listdetails.getMenuicon();
        String menupreference=listdetails.getPreference();
        String menuprice=listdetails.getPrice();
        menuDetailsInterface.getMenuDetails(menuname,menuicon,menuprice,menupreference);
    }


}