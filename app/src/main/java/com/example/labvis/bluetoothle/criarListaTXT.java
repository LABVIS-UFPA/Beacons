package com.example.labvis.bluetoothle;

import android.os.Environment;
import android.widget.ArrayAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class criarListaTXT {

    protected void addLista(ArrayAdapter arrayAdapter) throws IOException {
        //Nome do diretorio = Relatorio
        File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Relatorio");
        if (!root.exists()) {
            root.mkdir();
        }
        File gpxfile = new File(root, "TesteArquivo.txt"); //Nome do Arquivo x no diretorio root
        FileWriter writer = new FileWriter(gpxfile);

        for(int i = 0; i<arrayAdapter.getCount(); i++){
            writer.append(arrayAdapter.getItem(i)+"\r\n");
        }
        writer.close();
    }
}
