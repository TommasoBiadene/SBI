package com.example.sbi;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.sbi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment( new HomeFragment());
        Intent intent = getIntent();

        String test = intent.getStringExtra("email");

        if(test != null)
         Log.w(TAG,test);

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
                replaceFragment(new PersonFragment());
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