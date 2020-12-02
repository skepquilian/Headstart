package com.example.headstart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TrucksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trucks);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.nav_home){
                    //home activity
                    startActivity(new Intent(TrucksActivity.this, HomeActivity.class));
                    return true;
                }
                else if (item.getItemId() == R.id.nav_trucks){
                    //truck activity
                    return true;
                }
                else if (item.getItemId() == R.id.nav_map){
                    //map activity
                    startActivity(new Intent(TrucksActivity.this, MapActivity.class));
                    return true;
                }
                else if (item.getItemId() == R.id.nav_drivers){
                    //drivers activity
                    startActivity(new Intent(TrucksActivity.this, DriversActivity.class));
                    return true;
                }
                else if (item.getItemId() == R.id.nav_settings){
                    //settings activity
                    startActivity(new Intent(TrucksActivity.this, SettingsActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}