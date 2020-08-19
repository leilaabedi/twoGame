package com.maktab.twogame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        //create an add fragment transaction for CrimeDetailFragment

        final TicDetailFragment TicDetailFragment = new TicDetailFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, TicDetailFragment)
                .commit();

        Button button = findViewById(R.id.btn_tic);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, TicDetailFragment)
                        .commit();
            }
        });

        /*
        Button button1 = findViewById(R.id.btn_row);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(crimeDetailFragment)
                        .commit();
            }
        });
        */


    }
}