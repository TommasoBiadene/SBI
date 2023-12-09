package com.example.sbi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.SingleDateSelector;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {


    MaterialButton btn;
    private TextInputEditText email,psswd,surname,name,username,passwdc;

    private RadioButton r1,r2;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        btn = findViewById(R.id.btnSU);
        auth = FirebaseAuth.getInstance();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = findViewById(R.id.editTextemailSU);
                psswd = findViewById(R.id.editTextpswSU);
                r1 = findViewById(R.id.rdb1SU);
                r2 = findViewById(R.id.rdb2SU);
                surname = findViewById(R.id.editTextsurnameSU);
                name = findViewById(R.id.editTextnamesSU);
                username = findViewById(R.id.editTextUsrSU);
                passwdc = findViewById(R.id.editTextpswconfSU);



                if(!r1.isChecked() && !r2.isChecked())
                {

                    Toast.makeText(SignInActivity.this,"you need to accept the radio button",Toast.LENGTH_SHORT).show();
                    return;
                }



                if(!TextUtils.isEmpty(email.getText()) && !TextUtils.isEmpty(psswd.getText()) && !TextUtils.isEmpty(surname.getText()) &&
                !TextUtils.isEmpty(surname.getText()) &&  !TextUtils.isEmpty(name.getText())&& !TextUtils.isEmpty(passwdc.getText())) {
                    String em = email.getText().toString(),
                            pswd = psswd.getText().toString();

                    if (Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches() && pswd.equals(passwdc.getText().toString())) {

                        auth.createUserWithEmailAndPassword(em, pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignInActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();

                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    Map<String,String> user = new HashMap<>();

                                    user.put("email",em);
                                    user.put("username",username.getText().toString());
                                    user.put("surname",surname.getText().toString());
                                    user.put("name",name.getText().toString());

                                    db.collection("users")
                                                    .add(user)
                                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                @Override
                                                                public void onSuccess(DocumentReference documentReference) {
                                                                    Toast.makeText(SignInActivity.this, "Create the table succefully id:"+documentReference.getId(), Toast.LENGTH_SHORT).show();

                                                                }
                                                            })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(SignInActivity.this, "SignUp un succefully error:"+e.toString() , Toast.LENGTH_LONG).show();

                                                                        }
                                                                    });


                                    startActivity(new Intent(SignInActivity.this, LoginActivity.class));
                                } else {
                                    Toast.makeText(SignInActivity.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                }
            }
        });
    }
}