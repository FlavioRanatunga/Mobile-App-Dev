package com.example.tic_tac_toe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

public class MainActivity extends AppCompatActivity {


    private int count = 0;
    boolean multiFlag;

    public static int winCondition =3;

    ProfileFragment profileFragment = new ProfileFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    MainActivityData mainActivityDataViewModel;


    ThreeByThreeFragment threeByThreeFragment = new ThreeByThreeFragment();

    FourByFourFragment fourByFourFragment = new FourByFourFragment();

    FiveByFiveFragment fiveByFiveFragment = new FiveByFiveFragment();
    TopFragment topFragment = new TopFragment();




/*
    ActivityResultLauncher<Intent> profileActivityLauncher =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                Button btnProfile = findViewById(R.id.btnProfile);
              //  Result = findViewById(R.id.textView);
                if(result.getResultCode()==RESULT_OK){
                    Intent intent = result.getData();
                    String name = intent.getStringExtra("NAME");
                    curUser.setProfileName(name);
                    boolean nameFlag = intent.getBooleanExtra("NAME-FLAG",false);
                    multiFlag = intent.getBooleanExtra("MULTI-FLAG",true);
                    if (nameFlag==false){
                        btnProfile.setText("Login");
                    }
                    else{
                        btnProfile.setText(name);
                    }
                    if (multiFlag==true){
                        Result.setText("MULTI");
                    }
                    else{
                        Result.setText("AI");
                    }
                }
            }
    );*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

         mainActivityDataViewModel = new ViewModelProvider(this)
                .get(MainActivityData.class);







        mainActivityDataViewModel.clickedValue.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                if (mainActivityDataViewModel.getClickedValue() == 0) {
                    threeByThreeFragment = new ThreeByThreeFragment();
                    winCondition = settingsFragment.getWinCondition();
                    load3x3Fragment();

                } else if (mainActivityDataViewModel.getClickedValue() == 1) {
                    loadProfileFragment();
                } else if (mainActivityDataViewModel.getClickedValue() == 2) {
                    settingsFragment = new SettingsFragment();
                     loadSettingsFragment();
                }else if (mainActivityDataViewModel.getClickedValue() == 4) {
                    fourByFourFragment = new FourByFourFragment();
                    winCondition = settingsFragment.getWinCondition();
                    load4x4Fragment();

                }else if (mainActivityDataViewModel.getClickedValue() == 5) {
                    fiveByFiveFragment = new FiveByFiveFragment();
                    winCondition = settingsFragment.getWinCondition();
                    load5x5Fragment();

                }


            }

        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Notify the ViewModel about the orientation change
        mainActivityDataViewModel.setOrientation(newConfig.orientation);
    }
        void load3x3Fragment(){

        FragmentManager fm = getSupportFragmentManager();



        Fragment profileFragment = fm.findFragmentById(R.id.profile_container);
        if(profileFragment != null){
            fm.beginTransaction().remove(profileFragment).commit();
        }

        Fragment settingsFragment = fm.findFragmentById(R.id.settings_container);
        if(settingsFragment != null){
            fm.beginTransaction().remove(settingsFragment).commit();
        }



        fm.beginTransaction().replace(R.id.top_container,topFragment).commit();
        fm.beginTransaction().replace(R.id.grid_container,threeByThreeFragment).commit();


    }

    void load4x4Fragment(){
        FragmentManager fm = getSupportFragmentManager();


        Fragment profileFragment = fm.findFragmentById(R.id.profile_container);
        if(profileFragment != null){
            fm.beginTransaction().remove(profileFragment).commit();
        }

        Fragment settingsFragment = fm.findFragmentById(R.id.settings_container);
        if(settingsFragment != null){
            fm.beginTransaction().remove(settingsFragment).commit();
        }

        fm.beginTransaction().replace(R.id.top_container,topFragment).commit();
        fm.beginTransaction().replace(R.id.grid_container, fourByFourFragment).commit();

    }

    void load5x5Fragment(){
        FragmentManager fm = getSupportFragmentManager();


        Fragment profileFragment = fm.findFragmentById(R.id.profile_container);
        if(profileFragment != null){
            fm.beginTransaction().remove(profileFragment).commit();
        }

        Fragment settingsFragment = fm.findFragmentById(R.id.settings_container);
        if(settingsFragment != null){
            fm.beginTransaction().remove(settingsFragment).commit();
        }

        fm.beginTransaction().replace(R.id.top_container,topFragment).commit();
        fm.beginTransaction().replace(R.id.grid_container, fiveByFiveFragment).commit();

    }

        private void loadProfileFragment() {
        FragmentManager fm = getSupportFragmentManager();

        Fragment gridFragment = fm.findFragmentById(R.id.grid_container);
        if(gridFragment != null){
            fm.beginTransaction().remove(gridFragment).commit();
        }

        Fragment topFragment = fm.findFragmentById(R.id.top_container);
        if(topFragment != null){
            fm.beginTransaction().remove(topFragment).commit();
        }


        fm.beginTransaction().replace(R.id.profile_container,profileFragment).commit();

    }

        private void loadSettingsFragment() {
        FragmentManager fm = getSupportFragmentManager();

            Fragment threeByThreeFragment = fm.findFragmentById(R.id.grid_container);
            if(threeByThreeFragment != null){
                fm.beginTransaction().remove(threeByThreeFragment).commit();
            }

            Fragment topFragment = fm.findFragmentById(R.id.top_container);
            if(topFragment != null){
                fm.beginTransaction().remove(topFragment).commit();
            }

            Fragment profileFragment = fm.findFragmentById(R.id.profile_container);
            if(profileFragment != null){
                fm.beginTransaction().remove(profileFragment).commit();
            }

        fm.beginTransaction().replace(R.id.settings_container,settingsFragment).commit();
    }



}