package android.myapplicationdev.com.p08_map;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import static android.myapplicationdev.com.p08_map.R.id.parent;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    LatLng poi_north = new LatLng(1.450671, 103.81527);
    LatLng poi_central = new LatLng(1.299509, 103.837);
    LatLng poi_east = new LatLng(1.367103, 103.928008);
    LatLng poi_Singapore = new LatLng(1.362424, 103.802342);
    private Spinner spinner;
    private static final String[]paths = {"NORTH", "CENTRAL", "EAST"};
    private Marker cp,np,ep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                Map<String, Integer> mMarkers = new HashMap<String, Integer>();




                UiSettings ui = map.getUiSettings();
                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);
                ui.setMyLocationButtonEnabled(true);
                map.getUiSettings().setMapToolbarEnabled(true);
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override public boolean onMarkerClick(Marker marker) {
                        if (marker == np) {
                            Toast.makeText(MainActivity.this, "HQ - North",
                                    Toast.LENGTH_SHORT).show();
                        } else if (marker == cp) {
                            Toast.makeText(MainActivity.this, "Central",
                                    Toast.LENGTH_SHORT).show();
                        } else if (marker == ep) {
                            Toast.makeText(MainActivity.this, "East",
                                    Toast.LENGTH_SHORT).show();
                        }
                    return true;
                    }});

                np = map.addMarker(new
                        MarkerOptions()
                        .position(poi_north)
                        .title("HQ - North")
                        .snippet("Block 333, Admiralty Ave 3, 765654\n Operating hours: 10am-5pm\n" +
                                "Tel:65433456")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                cp = map.addMarker(new
                        MarkerOptions()
                        .position(poi_central)
                        .title("Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542 \n" +
                                "Operating hours: 11am-8pm\n" +
                                "Tel:67788652\n")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                ep = map.addMarker(new
                        MarkerOptions()
                        .position(poi_east)
                        .title("East")
                        .snippet("Block 555, Tampines Ave 3, 287788 \n" +
                                "Operating hours: 9am-5pm\n" +
                                "Tel:66776677\"\n")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));




                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);

                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north,
                        17));
                break;
            case 1:
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central,
                        17));
                break;
            case 2:
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_east,
                        17));
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Singapore,
                10));
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        if (marker.equals(np)){
            Toast.makeText(this, "HQ - North", Toast.LENGTH_SHORT).show();
            return true;
        } else if (marker.equals(cp))  {
            Toast.makeText(this, "Central", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "East", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
