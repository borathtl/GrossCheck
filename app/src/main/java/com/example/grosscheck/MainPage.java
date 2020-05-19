package com.example.grosscheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MainPage extends AppCompatActivity {

   private TextView welcomeText;
   private boolean flag;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
     GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
     if (acct != null) {
         String personName = acct.getDisplayName();
         String personGivenName = acct.getGivenName();
         String personFamilyName = acct.getFamilyName();
         String personEmail = acct.getEmail();
         String personId = acct.getId();
         Uri personPhoto = acct.getPhotoUrl();
     }

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
