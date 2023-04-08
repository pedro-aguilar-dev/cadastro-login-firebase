package com.example.camera_firebase;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import java.util.ArrayList;


public class Permissoes {
    public static void validarPermissoes(String[] permissoes, Activity activity, int requestCode){
        //VERIFICAR A VERSÃO DO ANDROID QUE O USUARIO ESTÁ UTILIZANDO
        //AS VERSÕES MAIORES OU IGUAL A 23 POSSUEM O RECURSO DE EXECUTAR AS PERMISSÕES EM TEMPO REAL
        //CASO CONTRÁRIO AS PERMISSOES IRÃO APARECER NA HORA DE INSTALAR O APLICATIVO
        if(Build.VERSION.SDK_INT >=23){
            ArrayList<String> arrayListPermissoes = new ArrayList<>();

            //VERIFICA PERMISSOES JÁ CONCEDIDAS
            //AO CHAMAR O METODO IRÁ SER RECEBIDO UM ARRAY DE STRING QUE IRÁ CONTER AS PERMISSÕES
            //O VETOR IRÁ SER PERCORRIDO E IRÁ SER VISTO QUAL DAS PERMISSOES JÁ FORAM CONCEDIDAS

            //PARA CADA INDICE(STRING PERMISSAO) NO ARRAY PERMISSAO
            //ELE PERCORRE INDICE POR INDICE E PEGANDO AS PERMISSÕES
            for (String permissao: permissoes) {

                //NO METODO CHECKSELFPERMISSION É PASSADO 2 ARGUMENTOS: O CONTEXTO E A STRING DE PERMISSÃO VINDA DO FOREACH
                //SE A PERMISSÃO JÁ FOI CONCEDIDA IRÁ RETORNAR TRUE, SE NÃO FALSE
                boolean temPermissao = ContextCompat.checkSelfPermission(activity, permissao)== PackageManager.PERMISSION_GRANTED;

                //SE ELE NÃO TIVER PERMISSAO
                //É ADICIONADO NA LISTA TODAS AS PERMISSÕES QUE AINDA NÃO FORAM CONCEDIDAS
                //É FEITO ISSO PARA VER QUAIS PERMISSÕES IRÃO SER PEDIDAS
                if(!temPermissao){
                    arrayListPermissoes.add(permissao);
                }
            }


            //SE O ARRAY NÃO ESTIVER VAZIO
            if(!arrayListPermissoes.isEmpty()) {
                //Jogando o arrayList em um Array de String
                //É preciso fazer isso, pois o requestPermissions espera receber um Array de String
                String[] vetorPermissao = new String[arrayListPermissoes.size()];
                arrayListPermissoes.toArray(vetorPermissao);
                //PEDINDO AS PERMISSÕES
                ActivityCompat.requestPermissions(activity, vetorPermissao, requestCode);
            }
        }

    }
}
