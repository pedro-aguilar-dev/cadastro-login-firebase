package com.example.camera_firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URI;

public class MainActivity extends AppCompatActivity {
            ImageView imageViewUsuario;
            EditText editTextNome;
            Button buttonAdicionar;
            String[] permissoes = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
            AlertDialog.Builder msg;
            private final int CAMERA = 1;
            private final int GALERIA = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        msg = new AlertDialog.Builder(MainActivity.this);
        msg.setNegativeButton("Cancelar", null);
        msg.setMessage("Você precisa conceder ao menos uma permissão");


        imageViewUsuario = findViewById(R.id.imageViewUsuario);
        editTextNome = findViewById(R.id.editTextNome);
        buttonAdicionar = findViewById(R.id.buttonAdicionar);


        imageViewUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Permissoes.validarPermissoes(permissoes, MainActivity.this, 1);
                int permissaoCamera = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);

                //SE A PERMISSÃO FOI CONCEDIDA
                if (permissaoCamera == PackageManager.PERMISSION_GRANTED) {
                    msg.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //ABRINDO A CAMERA
                            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            //A DIFERENÇA DESSE STARTACTIVITYFORRESULT E O STARTACTIVITY NORMAL É QUE ESSE ESPERA UM RESULTADO AO ABRIR A TELA
                            //O STARTACTIVITY NORMAL APENAS ABRE A NOVA TELA
                            startActivityForResult(intentCamera, CAMERA);
                        }
                    });

                }


                int permissaoGaleria = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                if (permissaoGaleria == PackageManager.PERMISSION_GRANTED) {
                    msg.setNeutralButton("Galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intentGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intentGaleria, GALERIA);
                        }
                    });
                }

                //SE AS DUAS PERMISSEÕES FOREM CONCEDIDAS
                //SE NÃO ELE IRÁ CAIR NO msg.show() do metedo requestPermission()
                if (permissaoCamera == PackageManager.PERMISSION_GRANTED && permissaoGaleria == PackageManager.PERMISSION_GRANTED) {
                    msg.show();
                }

                msg.show();
            }
        });

    }


    //PEGANDO O RESULTADO DE QUAL DAS TELAS ENTRE GALERIA E CAMERA O USUARIO ABRIU
    //SE A CAMERA FOR ESCOLHIDA A FOTO TIRADA IRÁ CAIR NESSE METODO NA VARIAVEL DATA
    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        if(resultCode==RESULT_OK) {
            if (requestCode == CAMERA) {
                bitmap = (Bitmap) data.getExtras().get("data");
            }

            else if (requestCode == GALERIA) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else
                Toast.makeText(this, "Erro ao carregar imagem", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Erro ao carregar imagem", Toast.LENGTH_SHORT).show();

        if(bitmap!=null)
        imageViewUsuario.setImageBitmap(bitmap);
    }

    //RETORNA TODAS AS PERMISSÕES QUE FORAM CONCEDIDAS OU NÃO
    //ESSE METODO É CHAMADO LOGO APÓS UMA PERMISSÃO FOR CONCEDIDA
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //O ARRAY DE INT grantResult é  um array que guarda se a permissão foi concedida ou não
        //POR EXEMPLO, A PERMISSÃO CAMERA ESTÁ NO INDICE 0 DO ARRAY STRING PERMISSION
        //A PERMISSÃO CAMERA  TAMBÉM ESTÁ NO INDICE 0 DO ARRAY DE INT grandResult
        //O ARRAY DE INT IRÁ DEFINIR SE A PERMISSÃO SERÁ 0  OU -1
        //0 SIGNIFICA QUE A PERMISSÃO FOI CONCEDIDA E -1 Q NÃO FOI CONCEDIDA

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int i = 0; i<permissions.length; i++){

            //SE A PERMISSÃO DE CAMERA FOI CONCEDIDA
            if(permissions[i].equals("android.permission.CAMERA") && grantResults[i]==0){
                //FAZER DETERMINADA ACÃO
                msg.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intentCamera, CAMERA);

                    }
                });

            }

            //SE A PERMISSÃO DE ACESSAR GALERIA FOI CONCEDIDA
            if(permissions[i].equals("android.permission.READ_EXTERNAL_STORAGE") && grantResults[i]==0){
                //FAZER DETERMINADA ACÃO
                msg.setNeutralButton("Galeria", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intentGaleria, GALERIA);
                    }
                });

            }
        }
        msg.show();
    }
}