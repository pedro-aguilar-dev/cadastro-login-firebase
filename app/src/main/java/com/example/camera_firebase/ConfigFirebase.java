package com.example.camera_firebase;

import android.provider.ContactsContract;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class ConfigFirebase {
    //atributos de configuração do firebase


   private static FirebaseStorage firebaseStorage;
   private static DatabaseReference firebaseDatabase;
   private static FirebaseAuth firebaseAuth;

   //AO PRECISAR DE UMA INSTANCIA FIREBASESTORAGE CHAMAR O METODO ABAIXO
    public static FirebaseStorage getFirebaseStorage(){
       //vendo se já existe uma instancia da classe FirebaseStorage

       //se for nulo é porq a instancia da FirebaseStorage não existe ainda
       if(firebaseStorage==null){


           //DA PRIMEIRA VEZ QUE O METODO FOR CHAMADO E A INSTANCIA NÃO EXISTIR:
           //ELE IRÁ PEGAR A INSTANCIA E ARMAZENAR NA VARIAVEL/OBJETO firebaseStorage
           //APÓS E CHAMAR O METODO PELA PRIMEIRA VEZ E TER REALIZADO ESSE PROCESSO
           //A VARIAVEL/OBJETO firebaseStorage NÃO ESTARÁ MAIS NULA E SIM COM A INSTANCIA ARMAZENADA
           //SENDO ASSIM O IF SERÁ IGNORADO E IRÁ CHAMAR O RETURN
           //OU SEJA, SEMPRE IRÁ RETORNAR A MESMA INSTANCIA PEGA DA PRIMEIRA VEZ
           //COM ISSO É POSSIVEL ESTABELER UM PADRÃO DE USAR A MESMA INSTANCIA NO PROJETO TODO
           firebaseStorage = FirebaseStorage.getInstance();
       }
       return firebaseStorage;

   }

   public static FirebaseAuth getFirebaseAuth(){
        if(firebaseAuth==null){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
   }

   public static DatabaseReference getFirebaseDatabase(){
        if(firebaseDatabase==null){
            firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        }
        return firebaseDatabase;
   }

}
