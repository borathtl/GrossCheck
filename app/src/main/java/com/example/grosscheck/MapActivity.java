package com.example.grosscheck;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    private GoogleMap mMap;
    private double lat;
    private double lng;
    private PlacesClient placesClient;
    private ArrayList<NearShops> mapList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getNearShops() {
        addMarkets();
        addMarker(mapList);
//        Places.initialize(this, getString(R.string.api_key));
//        placesClient = Places.createClient(this);
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        placesClient.fetchPlace(new FetchPlaceRequest() {
//            @NonNull
//            @Override
//            public String getPlaceId() {
//                return null;
//            }
//
//            @NonNull
//            @Override
//            public List<Place.Field> getPlaceFields() {
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public AutocompleteSessionToken getSessionToken() {
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public CancellationToken getCancellationToken() {
//                return null;
//            }
//        });
//        OkHttpClient client = new OkHttpClient();
//        RequestBody formBody = new FormBody.Builder()
//                .add("message", "message")
//                .build();
//        String url = "https://maps.googleapis.com/maps/api/place/search/json?location=" + lat + "," + lng +
//                "&rankby=distance&types=supermarket&key=" + R.string.api_key;
//        Request request = new Request.Builder()
//                .url(url)
//                .post(formBody)
//                .build();
//        try {
//            Response response = client.newCall(request).execute();
//            Log.d("MapActivity:getNearShop", response.body().string());
//            addMarkets();
//            addMarker(mapList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void addMarker(List<NearShops> nearShops) {
        if (nearShops != null) {
            for (NearShops nearShop : nearShops) {
                LatLng marker = new LatLng(nearShop.getLat(), nearShop.getLng());
                mMap.addMarker(new MarkerOptions().position(marker).title(nearShop.getName()));
            }
            mMap.animateCamera(CameraUpdateFactory.zoomTo(8.0f));
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    if (marker == null) return null;
                    else {
                        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.map_info_view, null, false);
                        TextView tvWindowTitle = view.findViewById(R.id.tv_windowTitle);
                        tvWindowTitle.setText(marker.getTitle());
                        return view;
                    }
                }
            });
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    if (marker != null) {
                        LatLng latLng = marker.getPosition();
                        String uri = "http://maps.google.com/maps?daddr=" + latLng.latitude + "," + latLng.longitude;
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MapActivity.this.getApplicationContext().startActivity(intent, null);
                    }
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (requestPermission()) {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setOnMyLocationClickListener(this);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(39.88381570558217, 32.75953358337418))); // Center of Turkey
            enableLocation();
            getUserLocation();
            getNearShops();
        }
    }

    private void enableLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            requestPermission();
        }
    }

    private boolean requestPermission() {
        String[] requests = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, requests, 100);
        }
        return true;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        if (requestPermission()) {
            enableLocation();
        } else {
            permissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    private void getUserLocation() {
        Geocoder geocoder;
        String bestProvider;
        List<Address> user = null;
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        bestProvider = lm.getBestProvider(criteria, false);
        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(bestProvider);

        if (location == null) {
            Toast.makeText(this, "Location Not found", Toast.LENGTH_LONG).show();
        } else {
            geocoder = new Geocoder(this);
            try {
                user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                lat = (double) user.get(0).getLatitude();
                lng = (double) user.get(0).getLongitude();
                Log.d("UserLocation: ", " DDD lat: " + lat + ",  longitude: " + lng);
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showMissingPermissionError() {
        Toast.makeText(this, "Izinler verilmedi", Toast.LENGTH_LONG).show();
    }

    private void addMarkets() {
        mapList.add(new NearShops("5M Migros", 39.883788726447705, 32.759561710574914));
        mapList.add(new NearShops("M Migros", 39.90914242691494, 32.75562107569221));
        mapList.add(new NearShops("M Migros", 39.892498812065014, 32.72159962947619));
        mapList.add(new NearShops("Migros Jet", 39.89280036094995, 32.721359000754845));
        mapList.add(new NearShops("3M Migros", 39.863807754155445, 32.682727793503354));
        mapList.add(new NearShops("5M Migros", 39.888290825612785, 32.81130471362993));
        mapList.add(new NearShops("Migros Hipermarket", 39.91291345885097, 32.808984528605954));
        mapList.add(new NearShops("M Migros", 39.87689008806944, 32.67063928472218));
        mapList.add(new NearShops("M Migros", 39.86310176835671, 32.81097134536258));
        mapList.add(new NearShops("3M Migros", 39.89397105981946, 32.679343170966405));

        mapList.add(new NearShops("CarrefourSA", 39.90932293379205, 32.75255833135734));
        mapList.add(new NearShops("CarrefourSA", 39.903607215253295, 32.75296661322397));
        mapList.add(new NearShops("CarrefourSA", 39.90086663313203, 32.75970326402327));
        mapList.add(new NearShops("CarrefourSA", 39.9106539230779, 32.778178018488035));
        mapList.add(new NearShops("CarrefourSA", 39.89734286648178, 32.79767347761936));
        mapList.add(new NearShops("CarrefourSA", 39.884342515821984, 32.80777845381832));
        mapList.add(new NearShops("CarrefourSA", 39.91785647525365, 32.81767928907163));
        mapList.add(new NearShops("CarrefourSA", 39.91871759927144, 32.82339523520437));
        mapList.add(new NearShops("CarrefourSA", 39.92497999364948, 32.82257867147113));
        mapList.add(new NearShops("CarrefourSA", 39.8837159306506, 32.80951365173914));
        mapList.add(new NearShops("CarrefourSA", 39.8518308531442, 32.81206541340554));
        mapList.add(new NearShops("CarrefourSA", 39.85065544429915, 32.8254366445375));
        mapList.add(new NearShops("CarrefourSA", 39.874159796895626, 32.858609546200746));
        mapList.add(new NearShops("CarrefourSA", 39.87752809436165, 32.8570784892009));
        mapList.add(new NearShops("CarrefourSA", 39.88990320854822, 32.83931822800273));
        mapList.add(new NearShops("CarrefourSA", 39.88379425411342, 32.828907040403806));
        mapList.add(new NearShops("CarrefourSA", 39.89593330911278, 32.878105005332074));
        mapList.add(new NearShops("CarrefourSA", 39.90164966780643, 32.856670207334275));
        mapList.add(new NearShops("CarrefourSA", 39.898204248421685, 32.853199811467974));
        mapList.add(new NearShops("CarrefourSA", 39.902902504631754, 32.84921906326838));
        mapList.add(new NearShops("CarrefourSA", 39.91879588273611, 32.82462008080425));
        mapList.add(new NearShops("CarrefourSA", 39.91785647525365, 32.81859792327153));

        mapList.add(new NearShops("Şok Market", 39.9577237797119, 32.60998661035415));
        mapList.add(new NearShops("Şok Market", 39.93763425908375, 32.647559867888965));
        mapList.add(new NearShops("Şok Market", 39.94664572959074, 32.63965984963805));
        mapList.add(new NearShops("Şok Market", 39.94726713154028, 32.66710899586839));
        mapList.add(new NearShops("Şok Market", 39.955056333615964, 32.73512159114813));
    }

}
