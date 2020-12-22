package com.example.grosscheck;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import dmax.dialog.SpotsDialog;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class Barcode extends AppCompatActivity implements ZBarScannerView.ResultHandler {
    @Override
    protected void onResume(){
        super.onResume();

        String[] requests = new String[]{
                Manifest.permission.CAMERA
        };

        startZBar();
        // cameraView.start();
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    this,
                    requests,
                    100
            );
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        //barcode
        zbar_scannerView = (ZBarScannerView)findViewById((R.id.zbar_scannerView));
        startZBar();
        //barcode


    }


    @Override
    protected void onPause(){
        super.onPause();
        stopZBar();
        //cameraView.stop();
    }
    //barcode
    private ZBarScannerView

            zbar_scannerView;


    //barcode
    private void startZBar()

    {
        zbar_scannerView.setResultHandler(this);
        zbar_scannerView.startCamera();
    }
    //barcode
    private void stopZBar()
    {
        zbar_scannerView.stopCamera();
    }



    @Override
    public void handleResult(Result rawResult ) {

        rawResult.getContents();
        Toast.makeText(this, rawResult.getContents(), Toast.LENGTH_LONG).show();



    }
}
