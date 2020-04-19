package com.bvm.ui_example_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private static EditText Username, Password, Temp;
    private static Button Login, register;
    private static String pass_var, user_var;
    private static Button Login_btn, Register_btn;
    private static String username, password1, password;
    private static CheckBox remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        Login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        Login_btn = findViewById(R.id.login);
       // Temp = findViewById(R.id.password1);
        Register_btn = findViewById(R.id.register);
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if(checkbox.equals("true")){
            Intent intent = new Intent(MainActivity.this,Second.class);
        }
        else if(checkbox.equals("false")){
            Toast.makeText(MainActivity.this, "Please Sign In", Toast.LENGTH_SHORT).show();
        }
        checkElement();
        remember = findViewById(R.id.remember);
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor =  preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Checked", Toast.LENGTH_SHORT).show();

                }else if(!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor =  preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Found that", Toast.LENGTH_SHORT).show();
                validate();
            }
        });



    }

    private void validate() {

        if(validatePassword() || validateUsername())
        {

            checkFirebase();
        }
    }

    private void checkFirebase() {
         username = Username.getText().toString();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("Login Info").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(username)) {
                    password = snapshot.child(username).child("pass").getValue().toString();
                    password1 = Password.getText().toString();
                    if(password.equals(password1)){
                        Intent intent = new Intent(MainActivity.this, Second.class);
                        startActivity(intent);
                }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private Boolean validateUsername(){
        String username = Username.getText().toString();
        if(username.isEmpty())
        {
            Username.setError("Field cannot be empty");
            return false;
        }
        else{
            Username.setError(null);
            return true;
        }

    }
    private Boolean validatePassword(){
        String username = Password.getText().toString();
        if(username.isEmpty())
        {
            Password.setError("Field cannot be empty");
            return false;
        }
        else{
            Password.setError(null);
            return true;
        }

    }

    /*private  Boolean validatePassword(){
        String password = Password.getText().toString();
        if(password.isEmpty()){
            Username.setError("Field cannot be empty");
            return false;
        }
        else if(password.matches("$@_-&%!^")){
            Username.setError("Password cannot contain special character $@_-&^%!");
            return false;
        }
        else{
            Username.setError(null);
            return true;
        }
    }*/

    private void checkElement() {

        Username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    user_var = Username.getText().toString();
                    if(user_var.equals("                      Username"))
                    {
                        Username.setText("");
                    }
                }
            }
        });
        Password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    pass_var = Password.getText().toString();
                    if(pass_var.equals("                      Password"))
                    {
                        Password.setText("");
                    }
                }
            }
        });
    }
}
