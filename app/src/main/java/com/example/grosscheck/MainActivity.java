package com.example.grosscheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button withoutLogin;
    private boolean flag= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.login);
        withoutLogin = (Button) findViewById(R.id.withoutLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            flag=true;
            openMainPage();
            }
        });

        withoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=false;
                openMainPage();
            }
        });
    }

   public void openMainPage()
    {
        Intent intent = new Intent(this, MainPage.class);
        intent.putExtra("LoginStatus", this.flag);
        startActivity(intent);
    }
}
