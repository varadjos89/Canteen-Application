package com.example.canteen.canteenapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,HomeMenulistFragment.MenuDetailsInterface{

    PlaceOrderVegFragment vegFragment;
    PlaceOrderNonVegFragment nonVegFragment;
    SharedPreferences sharedPreferences;
    String loginstatus;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences=this.getSharedPreferences("Infofile",MODE_PRIVATE);
        vegFragment=new PlaceOrderVegFragment();
        nonVegFragment=new PlaceOrderNonVegFragment();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String firstname=sharedPreferences.getString("fname","ABC");
        String lastname=sharedPreferences.getString("lname","AB");
        String username=sharedPreferences.getString("uname","A");
        String collegeid=sharedPreferences.getString("cid","ABCD");
        String email=sharedPreferences.getString("email","ABCD");
        bundle=new Bundle();
        bundle.putString("fname",firstname);
        bundle.putString("lname",lastname);
        bundle.putString("uname",username);
        bundle.putString("cid",collegeid);
        bundle.putString("email",email);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HomeMenulistFragment fragment=new HomeMenulistFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.home_fragment_container,fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout_action) {
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
            loginstatus="0";
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("Status",loginstatus);
            editor.commit();
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        if (id == R.id.nav_home) {
        } else if (id == R.id.nav_profile) {
            Intent intent=new Intent(this,MyProfileActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.nav_balance) {
            Intent intent=new Intent(this,MyBalanceActivity.class);
            int balance=sharedPreferences.getInt("bal",0);
            bundle.putInt("bal",balance);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.nav_order) {
            Intent intent=new Intent(this,MyOrderActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void getMenuName(String menuname) {

        SubMenuListFragment fragment=new SubMenuListFragment();
        bundle.putString("name",menuname);
        fragment.setArguments(bundle);
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.add(R.id.home_fragment_container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void getMenuDetails(String menuname, int menuicon, String menuprice, String preference) {
        bundle.putString("mname",menuname);
        bundle.putInt("micon",menuicon);
        bundle.putString("mprice",menuprice);
        android.support.v4.app.FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        if(preference.equals("1"))
        {
            vegFragment.setArguments(bundle);
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                    R.anim.enter_from_left, R.anim.exit_to_right);
            transaction.add(R.id.home_fragment_container,vegFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else
        {
            nonVegFragment.setArguments(bundle);
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                    R.anim.enter_from_left, R.anim.exit_to_right);
            transaction.add(R.id.home_fragment_container,nonVegFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
