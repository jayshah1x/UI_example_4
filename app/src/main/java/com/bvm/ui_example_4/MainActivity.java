package com.bvm.ui_example_4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static EditText Username, Password;
    private static Button Login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        Login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        Username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                    Editable var = Username.getText();
                    if(var.equals("Username")){
                        Username.setText("");

                    }
                }
            }
        });
    }
}
