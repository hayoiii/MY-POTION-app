package com.palebluedot.mypotion;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.palebluedot.mypotion.databinding.ActivityMainBinding;
import com.palebluedot.mypotion.feature.produce.ProduceActivity;
import com.palebluedot.mypotion.feature.search.SearchActivity;
import com.palebluedot.mypotion.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private HomeFragment homeFragement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.appBarMain.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setDisplayShowTitleEnabled(false);    //기본 제목을 없애기
//        actionBar.setDisplayHomeAsUpEnabled(false);  //자동 뒤로가기 버튼
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_storage, R.id.nav_calendar)
                .setOpenableLayout(drawer)
                .build();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);
        NavController navController = navHostFragment.getNavController();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //floating action button
        FloatingActionButton searchBtn = findViewById(R.id.menu_btn_search);
        FloatingActionButton addBtn = findViewById(R.id.menu_btn_customize);

        searchBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);

        homeFragement = new HomeFragment();
        navController.navigate(R.id.nav_home);
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.nav_host_fragment_content_main, homeFragement)
//                .addToBackStack("home")
//                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu_btn_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_btn_customize:
                Intent intent1 = new Intent(this, ProduceActivity.class);
                intent1.putExtra("CUSTOM_MODE", true);
                startActivity(intent1);
                break;
        }
    }
}