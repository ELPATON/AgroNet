package com.example.AgroNet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText email, senha;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);
        pb = findViewById(R.id.pb);
    }

    @Override
    public void onStart(){
        super.onStart();

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
        if(usuarioAtual != null){
            chamarPerfil();
        }
    }

    public void logar(View v){
        String mail = email.getText().toString();
        String password = senha.getText().toString();

        if(mail.isEmpty() || password.isEmpty()){
            Snackbar snac = Snackbar.make
                    (v, "Preencha todos os campos", Snackbar.LENGTH_SHORT);
            snac.setBackgroundTint(Color.WHITE);
            snac.setTextColor(Color.BLACK);
            snac.show();
        } else {
            logarUsuario(v);
        }
    }

    private void logarUsuario(View v) {
        String mail = email.getText().toString();
        String password = senha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(mail, password).
        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    pb.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            chamarPerfil();
                        }
                    }, 3000);
                } else {
                    String erro;
                    try {
                        throw task.getException();
                    } catch (Exception e){
                        erro = "Email ou senha incorretos";
                    }
                    Snackbar snac = Snackbar.make
                            (v, erro, Snackbar.LENGTH_SHORT);
                    snac.setBackgroundTint(Color.WHITE);
                    snac.setTextColor(Color.BLACK);
                    snac.show();
                }
            }
        });
    }

    private void chamarPerfil() {
        Intent i = new Intent(getApplicationContext(), TelaPerfil.class);
        startActivity(i);
    }

    public void chamarCadastro(View v){
            Intent i = new Intent(getApplicationContext(), TelaCadastro.class);
            startActivity(i);
    }
}