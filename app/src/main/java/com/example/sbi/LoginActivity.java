package com.example.sbi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    MaterialButton btn;
    private TextInputEditText email,psswd;
    TextView text;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn = findViewById(R.id.btnLogin);
        text = findViewById(R.id.l);


        auth = FirebaseAuth.getInstance();


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignInActivity.class));
            }
        });







        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = findViewById(R.id.editTextemailLI);
                psswd = findViewById(R.id.editTextpasswdLI);


                if(!TextUtils.isEmpty(email.getText()) && !TextUtils.isEmpty(psswd.getText()) ){
                    String em = email.getText().toString(),
                            pswd = psswd.getText().toString();

                    if(Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){

                        auth.signInWithEmailAndPassword(em, pswd)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        //make function that send data

                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        intent.putExtra("email",em);




                                        startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });


                       // Toast.makeText(LoginActivity.this, email.getText()+" "+psswd.getText(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this,"wrong email format",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(LoginActivity.this,"no",Toast.LENGTH_SHORT).show();
                }



            }
        });



    }
}