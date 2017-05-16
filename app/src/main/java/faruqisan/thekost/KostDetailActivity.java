package faruqisan.thekost;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class KostDetailActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView tvName;
    private TextView tvOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kost_detail);

        tvName = (TextView) findViewById(R.id.tvDetailKostName);
        tvOwner = (TextView) findViewById(R.id.tvDetailKostOwner);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        float lat = this.getIntent().getFloatExtra("lat", 0);
        float lng = this.getIntent().getFloatExtra("lng", 0);
        String kostName = this.getIntent().getStringExtra("name");
        String userId = this.getIntent().getStringExtra("user_id");

        tvName.setText(kostName);
        tvOwner.setText(userId);

        LatLng kostPosition = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(kostPosition).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(kostPosition));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kostPosition,14));
    }
}
