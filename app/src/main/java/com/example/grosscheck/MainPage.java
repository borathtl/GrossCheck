package com.example.grosscheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainPage extends AppCompatActivity {

   private TextView welcomeText;
   private boolean flag;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        welcomeText= findViewById(R.id.welcome);
        Intent recieveIntent = getIntent();
        flag = recieveIntent.getBooleanExtra("LoginStatus",false);

        if(flag==true)
        {
            welcomeText.setText("Hello LoggedIN user!");

        }
        else
        {
            welcomeText.setText("Hello visitor!");
        }


    }
}
