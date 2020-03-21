package com.example.memorycanvas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        button = findViewById(R.id.new_game_button);
            button.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {

                                              if(MemoryView.cards.size() == 0) {
                                                  setContentView(R.layout.activity_main);
                                              }
                                              else{
                                                  Toast toast = Toast.makeText(getApplicationContext(), "Еще рано, опустоши поле", Toast.LENGTH_SHORT);
                                                  toast.show();
                                              }

                                          }
                                      }
            );
        }

    }

