package com.ecommerce.enerd.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ecommerce.enerd.R;
import com.ecommerce.enerd.helper.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class AnunciosActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);

        //Configuracoes
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        //autenticacao.signOut();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if( autenticacao.getCurrentUser() == null ) {//verificacao de usuario deslogado
            menu.setGroupVisible(R.id.group_deslogado, true);

        }else { //verificacao de usuario logado
            menu.setGroupVisible(R.id.group_logado, true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch ( item.getItemId() ){
            case R.id.menu_cadastrar:
                startActivity( new Intent(getApplicationContext(), LoginActivity.class));
                break;
            case R.id.menu_sair:
                autenticacao.signOut();
                invalidateOptionsMenu();
                break;
            case R.id.menu_anuncios :
                startActivity(new Intent(getApplicationContext(), MeusAnunciosActivity.class));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }

        return super.onOptionsItemSelected(item);
    }
}