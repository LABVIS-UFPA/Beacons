package com.example.labvis.bluetoothle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;

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

    public void drawSomething(View view, int x, int y) {
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
        // Defini a posição do rect no imageView
        Rect rect = new Rect();
        rect.left = prop * valorY;
        rect.right = rect.left + SIZE_RECT;
        rect.top = altura - (prop * valorX);
        rect.bottom = rect.top + SIZE_RECT;
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawRect(rect,paint);
        */

        // Defini a posição do rect no imageView
        Rect rect = new Rect();
        rect.left = prop * valorY;
        rect.right = rect.left + SIZE_RECT;
        rect.top = tamanhoTela - (prop * valorX);
        rect.bottom = rect.top + SIZE_RECT;
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawRect(rect,paint);


        Rect rect1 = new Rect();
        rect1.left = 0;
        rect1.right = rect1.left + SIZE_RECT;
        rect1.top = tamanhoTela;
        rect1.bottom = rect1.top + SIZE_RECT;
        Paint paint1 = new Paint();
        paint1.setColor(Color.WHITE); // wh1
        canvas.drawRect(rect1,paint1);

        Rect rect2 = new Rect();
        rect2.left = 0;
        rect2.right = rect2.left + SIZE_RECT;
        rect2.top = tamanhoTela - altura;
        rect2.bottom = rect2.top + SIZE_RECT;
        Paint paint2 = new Paint();
        paint2.setColor(Color.BLACK); // jaalee
        canvas.drawRect(rect2,paint2);

        Rect rect3 = new Rect();
        rect3.left = largura;
        rect3.right = rect3.left + SIZE_RECT;
        rect3.top = tamanhoTela;
        rect3.bottom = rect3.top + SIZE_RECT;
        Paint paint3 = new Paint();
        paint3.setColor(Color.GREEN); // gr2
        canvas.drawRect(rect3,paint3);

        view.invalidate();
    }
}
