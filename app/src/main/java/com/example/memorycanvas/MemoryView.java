package com.example.memorycanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//Сгенерировать поле 2*n карт(<8)
// при у каждого цвета по паре карт()
//удалить карты (тогда ArrayList)

public class MemoryView extends View {

    int n = 4; // пары карт
    static ArrayList<Card> cards = new ArrayList<>();
    int width = 200;
    int height = 300;
    int distance = 50;
    int openedCards = 0;
    final int PAUSE_LENGTH = 1; //в сек
    boolean isOnPauseNow = false;

   Button newGameButton;



    public MemoryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        makeNewCards();

    }

    public MemoryView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN && !isOnPauseNow){

            for (Card card : cards) {
                if(openedCards == 0) {
                    if(card.flip(x, y)) {

                        openedCards++;
                        invalidate();
                        return true;
                    }

                }
                if (openedCards == 1){
                    //Перевернуть карту с задержкой
                    if(card.flip(x, y)) {
                        openedCards++;

                        ///запустить задержку
                        invalidate();
                        PauseTask task = new PauseTask();
                        task.execute(PAUSE_LENGTH);
                        checkOpenedCards(card);

                        return true;
                    }

                }
            }
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = canvas.getWidth();
        height = canvas.getHeight();

        Paint paint = new Paint();

        for(Card card : cards){
            card.draw(canvas);
        }


    }


    class PauseTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            Log.d("PAUSE", "PAUSE STARTED");
            try {
                Thread.sleep(integers[0] * 1000); //Передаем число секунд ожидания
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("PAUSE", "PAUSE ENDED");
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            for (Card card : cards){
                if (card.isOpened){
                    card.isOpened = false;
                }
            }
            openedCards = 0;
            isOnPauseNow = false;

            invalidate();


        }
    }


    public void checkOpenedCards(Card newOpenedCard){
        for (int i = 0; i < cards.size(); i++){

            if (newOpenedCard == cards.get(i)) continue;

            if( (cards.get(i).isOpened) &&
                    (newOpenedCard.color == cards.get(i).color) &&
                    (openedCards == 2)){
                cards.remove(cards.get(i));
                cards.remove(newOpenedCard);

            }
        }

        if(cards.size() == 0){
            toTheEnd();

        }
    }


    public void toTheEnd(){

        Toast toast =Toast.makeText(getContext(), "CONGRATULATIONS", Toast.LENGTH_LONG);
        toast.show();

    }


    void makeNewCards(){

        Random random = new Random();
        for (int i = 0; i < 2; i++){
                for (int j = 0; j < n; j++){
                    cards.add(new Card(width*j+distance*j, height*i + distance*i, width, height, Color.MAGENTA));
                }
        }

        for(int i = 0; i < cards.size(); i+=2){
            int red = random.nextInt(255);
            int green = random.nextInt(255);
            int blue = random.nextInt(255);
            cards.get(i).color = Color.rgb(red, green, blue);
            cards.get(i+1).color = Color.rgb(red, green, blue);

        }


        Collections.shuffle(cards);

    }






}
