package com.maktab.twogame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
        final GameListFragment gameListFragment = new GameListFragment();

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
                        .remove(gameListFragment)
                        .commit();

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, TicDetailFragment)
                        .commit();
            }
        });


        Button button1 = findViewById(R.id.btn_row);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(TicDetailFragment)
                        .commit();

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, gameListFragment)
                        .commit();


            }
        });


    }
}