package cnu.proejct.WTIsystem;
//추가 필요 : 본인 위치 /
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private FusedLocationProviderClient mFusedLocationProviderClient; //위치값 획득용
    private final LatLng mDefaultLocation = new LatLng(36.364739, 127.344349);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    private Location mLastKnownLocation;

/*
    //런타임 권한 요청
    private void getLocationPermission(){
        if (ContextCompat.checkSelfPermission(this.getApplicationContext() ,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        mLocationPermissionGranted = false;
        switch (requestCode){
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION : {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button gotoListBT = (Button)findViewById(R.id.gotoListBT);
        gotoListBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        ListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        //맵 객체 추가
        mMap = googleMap;

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);

        //마커 추가
        LatLng CNU1 = new LatLng(36.364739, 127.344349);
        LatLng CNU2 = new LatLng(36.369448, 127.344456);

        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1
                .position(CNU1)
                .title("1번 단말")
                .snippet("해당 단말의 상세정보가 들어갑니다.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2
                .position(CNU2)
                .title("2번 단말")
                .snippet("해당 단말의 상세정보가 들어갑니다.");

        mMap.addMarker(markerOptions1).showInfoWindow();
        mMap.addMarker(markerOptions2);

        //마커클릭 이벤트 리스너
        mMap.setOnMarkerClickListener(this);

        //카메라 이동, 기본 줌 레벨 설정
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CNU1));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //updateLocationUI();
       // getDeviceLocation();

    }

    // 마커클릭 이벤트  처리
    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, marker.getTitle() + "\n" + marker.getPosition(), Toast.LENGTH_SHORT).show();
        return true;
    }
}

