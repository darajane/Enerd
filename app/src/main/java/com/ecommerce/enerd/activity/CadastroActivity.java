package com.ecommerce.enerd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ecommerce.enerd.R;
import com.ecommerce.enerd.helper.ConfiguracaoFirebase;
import com.ecommerce.enerd.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private Button botaoCadastrar;
    private ProgressBar progressBar;

    private Usuario usuario;

    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializarComponentes();

        //funcionalidade fazer cadastro
        progressBar.setVisibility(View.GONE);
        botaoCadastrar.setOnClickListener(v -> {

            progressBar.setVisibility(View.VISIBLE);
            String textoNome = campoNome.getText().toString();
            String textoEmail = campoEmail.getText().toString();
            String textoSenha = campoSenha.getText().toString();

            if ( !textoNome.isEmpty() ) {
                if ( !textoEmail.isEmpty() ) {
                    if ( !textoSenha.isEmpty() ) {

                        usuario = new Usuario();
                        usuario.setNome( textoNome );
                        usuario.setEmail( textoEmail );
                        usuario.setSenha( textoSenha );
                        cadastrar( usuario );

                    }else{
                        Toast.makeText(CadastroActivity.this,
                                "Preencha a senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CadastroActivity.this,
                            "Preencha o e-mail!",
                            Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(CadastroActivity.this,
                        "Preencha o Usuario!",
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

    //Metodo responsavel por fazer o cadastro do usuario com todos os dados e suas validacoes

    public void abrirLogin(View view) {
        Intent c = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity( c );
    }
    //metodo para cadastrar o usuario com email senha, seus dados em geral
    public void cadastrar( Usuario usuario){

        //autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(
                this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if ( task.isSuccessful() ){

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(CadastroActivity.this,
                                    "Cadastro Realizado com Sucesso",
                                    Toast.LENGTH_LONG).show();

                            startActivity( new Intent(getApplicationContext(), LoginActivity.class));
                            finish();

                        }else {

                            progressBar.setVisibility( View.GONE );

                            String erroExcecao = "";
                            try {
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e) {
                                erroExcecao = "Digite uma senha mais Forte!";
                            }catch (FirebaseAuthEmailException e) {
                                erroExcecao = "Por favor, Digite um e-mail valido";
                            }catch (FirebaseAuthUserCollisionException e){
                                erroExcecao = "Esta conta ja foi cadastrada";
                            }catch (Exception e) {
                                erroExcecao = "ao cadastrar usuario: " + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(CadastroActivity.this,
                                    "Erro: " + erroExcecao ,
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                }
        );

    }

    public void inicializarComponentes() {

        campoNome       = findViewById( R.id.editCadastroNome );
        campoEmail      = findViewById( R.id.editCadastroLogin);
        campoSenha      = findViewById( R.id.editCadastroLoginSenha);
        botaoCadastrar  = findViewById( R.id.buttonLogin);
        progressBar     = findViewById( R.id.progressBarCadastro);

        campoNome.requestFocus();
    }
}
