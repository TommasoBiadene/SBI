package com.example.sbi;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);


        ReadData(new Mycallback() {
            @Override
            public void onCallBack(PersonData value) {
                Intent intent = new Intent(LoadActivity.this,MainActivity.class);
                intent.putExtra("email",value.getEmail());




                startActivity(intent);
                finish();
            }
        });
    }

    private void ReadData(Mycallback mycallback){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Task<QuerySnapshot> t = db.collection("users").whereEqualTo("email", "a@gmail.com").get();
        PersonData pp = new PersonData();


        db.collection("users")
                .whereEqualTo("email", "a@gmail.com").limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                mycallback.onCallBack(new PersonData(document.getString("email"),document.getString("name")
                                        ,document.getString("surname"),document.getString("username")));
                            }
                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    public  interface Mycallback{
        void onCallBack(PersonData value);
    }
}