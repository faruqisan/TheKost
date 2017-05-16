package faruqisan.thekost;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

import Adapters.KostListAdapter;
import Beans.Kost;

public class MainActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private KostListAdapter kostListAdapter;
    private ProgressBar pbLoadingKostList;

    private ListView listViewMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pbLoadingKostList = (ProgressBar) findViewById(R.id.pbLoadKostList);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        listViewMain = (ListView) findViewById(R.id.listViewMain);
        pbLoadingKostList.setVisibility(View.VISIBLE);
        loadKosts();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateKostActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemLogout:
                mAuth.signOut();
                finish();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadKosts(){
        Query kostQuery = myRef.child("kosts");
        kostQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LinkedList<Kost> kosts = new LinkedList<Kost>();
                for (DataSnapshot kostSnapshot : dataSnapshot.getChildren()) {
                    Kost kost = kostSnapshot.getValue(Kost.class);
                    Log.d("kost name", kost.getName());
                    kosts.add(kost);
                }
                pbLoadingKostList.setVisibility(View.GONE);
                kostListAdapter = new KostListAdapter(MainActivity.this,kosts);
                listViewMain.setAdapter(kostListAdapter);

                listViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Kost kost =  (Kost) listViewMain.getAdapter().getItem(position);
                        Log.d("kost lat", String.valueOf(kost.getLat()));
                        Intent intent = new Intent(MainActivity.this, KostDetailActivity.class);
                        intent.putExtra("lat", kost.getLat());
                        intent.putExtra("lng", kost.getLng());
                        intent.putExtra("name", kost.getName());
                        intent.putExtra("user_id", kost.getUser_id());
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }
}
