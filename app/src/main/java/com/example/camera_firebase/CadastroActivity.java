package com.example.camera_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        final EditText nomeEditText = findViewById(R.id.nomeText);
        final EditText emailEditText = findViewById(R.id.emailText2);
        final EditText senhaEditText = findViewById(R.id.senhaText2);
        final Button CadastrarButton = findViewById(R.id.CadastrarButton);



        CadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //acessar firebase
                //acessando o metodo e pegando uma instancia
                firebaseAuth = ConfigFirebase.getFirebaseAuth();

                //CRIANDO USUARIO
                firebaseAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(),
                        senhaEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //POSSVEIS  PROBLEMAS AO CADASTRAR:
                        //ERRO NO EMAIL OU SENHA
                        //O EMAIL É UNICO, NÃO PODE SER CADASTRADO DOIS IGUAIS

                        //SE A TAREFA FOI CONCLUIDA COM SUCESSO E O USUARIO FOI CADASTRADO
                        if(task.isSuccessful()){
                            Toast.makeText(CadastroActivity.this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            try {
                                throw task.getException();

                                //SENHA FRACA
                            } catch (FirebaseAuthWeakPasswordException ex) {
                                Toast.makeText(CadastroActivity.this, "Senha Fraca", Toast.LENGTH_SHORT).show();
                            }
                            //EMAIL INCORRETO
                            catch (FirebaseAuthEmailException ex) {
                                Toast.makeText(CadastroActivity.this, "Padrão de Email Incorreto", Toast.LENGTH_SHORT).show();
                            }
                            //Usuário já cadastrado
                            catch (FirebaseAuthUserCollisionException ex) {
                                Toast.makeText(CadastroActivity.this, "Usuário já Cadastrado", Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception ex) {
                                Toast.makeText(CadastroActivity.this, "Erro ao Cadastrar: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }
}