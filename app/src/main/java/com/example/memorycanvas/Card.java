package com.example.memorycanvas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Card {

    Paint paint = new Paint();
    int color, backColor = Color.DKGRAY;
    boolean isOpened = false; //цвет карты
    float x, y, width, height;

    public Card() {

    }

    public Card(float x, float y, float width, float height, int color ) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public void draw(Canvas canvas){
        //нарисовать карту в виде цветного прямоугольника

        if(isOpened) {
            paint.setColor(color);
        }
        else{
            paint.setColor(backColor);
        }
        canvas.drawRect(x, y, x + width, y + height, paint);
    }

    public boolean flip(float touch_x, float touch_y){
        if(touch_x >= x && touch_x <= x + width && touch_y >= y && touch_y <= y + height){
            isOpened = !isOpened;
            return true;
        }
        else return false;
    }




}
