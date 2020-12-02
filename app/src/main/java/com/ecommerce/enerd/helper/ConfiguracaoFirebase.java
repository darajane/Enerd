package com.ecommerce.enerd.helper;


//Aqui e feito a configuracao de autenticacao do firebase


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfiguracaoFirebase{

    public static StorageReference getFirebaseStorage;
    private static FirebaseAuth autenticacao;
    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth referenciaAutenticacao;
    private static StorageReference referenciaStorage;

    public static String getIdUsuario(){

        FirebaseAuth autenticacao = getFirebaseAutenticacao();
        return autenticacao.getCurrentUser().getUid();

    }


    //retorna a referencia do firebase

    public static DatabaseReference getFirebase(){
        if ( referenciaFirebase == null){
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }



    //metodo para retornar o firebaseauth

    public static FirebaseAuth getFirebaseAutenticacao(){
        if ( referenciaAutenticacao == null ) {
            referenciaAutenticacao = FirebaseAuth.getInstance();
                }
                return referenciaAutenticacao;
            }

    //Retorna instancia do FirebaseStorage
    public static StorageReference getFirebaseStorage(){
        if( referenciaStorage == null ){
            referenciaStorage = FirebaseStorage.getInstance().getReference();
        }
        return referenciaStorage;
    }

//    public static StorageReference getFirebaseStorage() {
//        return null;
//    }
//
////    public static FirebaseAuth get(){
////
////        if ( autenticacao == null ){
////             autenticacao = FirebaseAuth.getInstance();
////        }
////        return  autenticacao;
////    }

}
