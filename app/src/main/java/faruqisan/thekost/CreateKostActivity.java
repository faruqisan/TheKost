package faruqisan.thekost;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Beans.Kost;

public class CreateKostActivity extends AppCompatActivity implements OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;
    private Button btnPickLocation;
    private EditText etKostLocation;
    private EditText etKostName;
    private final int PLACE_PICKER_REQUEST = 1;
    private Place pickedPlace;
    private FloatingActionButton fabSavekost;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("kosts");
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_kost);

        mAuth = FirebaseAuth.getInstance();

        etKostLocation = (EditText) findViewById(R.id.etKostLocation);
        etKostName = (EditText) findViewById(R.id.etInputKostName);

        btnPickLocation = (Button) findViewById(R.id.btnPickLocation);
        btnPickLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(CreateKostActivity.this),PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        fabSavekost = (FloatingActionButton) findViewById(R.id.fabSaveKost);
        fabSavekost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveKost();
            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data,this);
                pickedPlace = place;
                etKostLocation.setText(place.getName());
                Log.d("from global var",place.getName().toString());
            }
        }
    }

    private void saveKost(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String kostName = etKostName.getText().toString();
        LatLng pickedLatLng = pickedPlace.getLatLng();
        Log.d("raw latlng", String.valueOf(pickedPlace.getLatLng()));
        Log.d("raw lat", String.valueOf(pickedLatLng.latitude));
        Log.d("raw lng", String.valueOf(pickedLatLng.longitude));
        double lat = pickedLatLng.latitude;
        double lng = pickedLatLng.longitude;
        String userId = user.getUid();

        Kost kost = new Kost();
        kost.setLat(lat);
        kost.setLng(lng);
        kost.setName(kostName);
        kost.setUser_id(userId);
//        myRef.push().setValue(kost);
        myRef.push().setValue(kost).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateKostActivity.this,"Kost Created ! ", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
