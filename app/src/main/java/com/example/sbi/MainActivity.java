package com.example.sbi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.sbi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment( new HomeFragment());

        binding.bottomNavigationView.setSelectedItemId(R.id.home);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

           if (item.getItemId() == R.id.forum){
                replaceFragment(new ForumFragment());
            }else if(item.getItemId() == R.id.home){
               replaceFragment(new HomeFragment());
           }else if(item.getItemId() == R.id.messaggi){
               replaceFragment(new MessageFragment());
           }else if(item.getItemId() == R.id.ricerca){
               replaceFragment(new SearchFragment());
           }else if(item.getItemId() == R.id.profilo){

           }
            return  true;
        });
    }

    private void replaceFragment(Fragment f){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout,f);
        ft.commit();
    }
}