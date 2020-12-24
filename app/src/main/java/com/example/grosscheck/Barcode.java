package com.example.grosscheck;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class Barcode extends AppCompatActivity implements ZBarScannerView.ResultHandler {
    private ArrayList<Product> mProductList = new ArrayList<>();

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
        addProdcuts();
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

private void addProdcuts (){

    mProductList.add(
            new Product("Ahududu Paket","20 TL","https://migros-dali-storage-prod.global.ssl.fastly.net/sanalmarket/product/27400019/27400019-8bcf2d.jpg","8690565017555")
    );
    mProductList.add(
            new Product("Erikli su 500ml","1.5 TL","https://migros-dali-storage-prod.global.ssl.fastly.net/sanalmarket/product/08062201/08062201-3aa86b-1650x1650.jpg","86811212069805")
    );
    mProductList.add(
            new Product("Kardelen su 500ml","0,75 TL","https://lh3.googleusercontent.com/proxy/l8KoZXWM6mrdwaCNYnqDC2UXEK0CchBGYybH07EC3KJ18GhGgQ1lYpiFOU0haP2gjZ8nlyHHxZw8w2JamrqCbJGJHEhCnimg2j-G87qT3Hgz","8695188000052")
    );


}

    @Override
    public void handleResult(Result rawResult ) {

       String result = rawResult.getContents().trim();

        for (Product product : mProductList) {

            if(product.getBarcode().equals(result)){
                Toast.makeText(this, product.getProductName()+product.getProductPrice(), Toast.LENGTH_LONG).show();
               // Toast.makeText(this, rawResult.getContents(), Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,"Product Not Found", Toast.LENGTH_LONG).show();

        }
    }
}
