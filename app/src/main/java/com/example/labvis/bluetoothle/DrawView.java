package com.example.labvis.bluetoothle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;

import java.sql.Timestamp;

public class DrawView {
    private int tamanho, altura, largura;
    private int tamanhoTela = 600;
    private int valorX;
    private int valorY;
    private int prop;
    private Canvas canvas;
    private Bitmap bitmap;
    private ImageView imageView;
    private int SIZE_RECT = 20;

    public DrawView(ImageView img, int tam, int alt, int larg){
        tamanho = tam;
        prop = tamanhoTela/tam;
        imageView = img;
        altura = alt;
        largura = larg;
    }

    public void drawSomething(View view, int x, int y, int dist1, int dist2, int dist3) {
        // dist1 = jaalee,  dist2 = wh1,  dist3 = gr2
        int vWidth = imageView.getLayoutParams().width;
        int vHeight = imageView.getLayoutParams().height;

        if(x > largura){
            valorX = largura;
        }else valorX = x;

        if(y > altura){
            valorY = altura;
        }else valorY = y;

        bitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
        // Vincula o bitmap com o imageView
        imageView.setImageBitmap(bitmap);
        // Vincula o canvas com o bitmap
        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.GRAY);

        /*
        *   <----- TESTAR DIMENSÕES DA TELA ----->
        */

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        Paint paint1 = new Paint();
        paint1.setColor(Color.WHITE); //wh1
        Paint paint2 = new Paint();
        paint2.setColor(Color.BLACK); //jaalee
        Paint paint3 = new Paint();
        paint3.setColor(Color.GREEN); //gr2
        Paint paint5 = new Paint();
        paint5.setColor(Color.GRAY);

        canvas.drawCircle(0,(tamanhoTela - altura),dist1,paint2);
        canvas.drawCircle(0,tamanhoTela,dist2,paint1);
        canvas.drawCircle(largura,tamanhoTela,dist3,paint3);

        Rect rect1 = new Rect();
        rect1.left = 0;
        rect1.right = rect1.left + SIZE_RECT;
        rect1.top = tamanhoTela;
        rect1.bottom = rect1.top + SIZE_RECT;
        canvas.drawRect(rect1,paint5);

        Rect rect2 = new Rect();
        rect2.left = 0;
        rect2.right = rect2.left + SIZE_RECT;
        rect2.top = (tamanhoTela - altura);
        rect2.bottom = rect2.top + SIZE_RECT;
        canvas.drawRect(rect2,paint5);

        Rect rect3 = new Rect();
        rect3.left = largura;
        rect3.right = rect3.left + SIZE_RECT;
        rect3.top = tamanhoTela;
        rect3.bottom = rect3.top + SIZE_RECT;
        canvas.drawRect(rect3,paint5);

        // Defini a posição do rect no imageView
        Rect rect = new Rect();
        rect.left = (prop * valorY);
        rect.right = rect.left + SIZE_RECT;
        rect.top = (tamanhoTela - (prop * valorX));
        rect.bottom = rect.top + SIZE_RECT;
        canvas.drawRect(rect,paint);

        view.invalidate();
    }
}
