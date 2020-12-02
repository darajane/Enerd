package com.ecommerce.enerd.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ecommerce.enerd.R;
import com.ecommerce.enerd.helper.ConfiguracaoFirebase;
import com.ecommerce.enerd.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button botaoEntrar;
    private ProgressBar progressBar;

    private Usuario usuario;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarComponentes();

        //Funcionalidade fazer login

        progressBar.setVisibility( View.GONE );
        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if (!textoEmail.isEmpty()) {
                    if (!textoSenha.isEmpty()) {

                        usuario = new Usuario();
                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textoSenha);
                        validarLogin(usuario);

                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Preencha a Senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Preencha o E-mail!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void validarLogin( Usuario usuario ){

        progressBar.setVisibility( View.VISIBLE );
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){
                        progressBar.setVisibility( View.GONE);
                        startActivity( new Intent(getApplicationContext(), AnunciosActivity.class));
                        finish();
                }else {
                    Toast.makeText( LoginActivity.this,
                            "Erro ao Fazer Login",
                            Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility( View.GONE);
                }
            }
        });

    }

    public void abrirCadastro(View view) {
        Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity( i );
    }

    public void inicializarComponentes() {

        campoEmail      = findViewById( R.id.editCadastroLogin);
        campoSenha      = findViewById( R.id.editCadastroLoginSenha);
        botaoEntrar     = findViewById( R.id.buttonLogin);
        progressBar     = findViewById( R.id.progressBarLogin);

        campoEmail.requestFocus();
    }
}