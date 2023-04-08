package com.example.camera_firebase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


public class LoginActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText emailEditText = findViewById(R.id.emailText);
        final EditText senhaEditText = findViewById(R.id.SenhaText);
        final Button loginButton = findViewById(R.id.loginButton);
        final TextView textViewCadastro =  findViewById(R.id.CadastrotextView);

        textViewCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent novaTela = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(novaTela);
            }
        });
    }
}
