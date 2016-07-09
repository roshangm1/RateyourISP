package roshan.info.np.rateyourisp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import roshan.info.np.rateyourisp.R;
import roshan.info.np.rateyourisp.adapters.ReviewAdapter;
import roshan.info.np.rateyourisp.models.Post;

public class AllReviews extends AppCompatActivity implements ChildEventListener {

    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;
    private DatabaseReference reference;
    private ArrayList<String> key = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reviews);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference=database.getReference().child("isp_lists").child(String.valueOf(getIntent().getIntExtra("key",0))).child("reviews");

        recyclerView= (RecyclerView) findViewById(R.id.recy_review);

        TextView title = (TextView) findViewById(R.id.toolbar_allreviews_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_review);
        setSupportActionBar(toolbar);

        assert title != null;
        title.setText(getIntent().getStringExtra("name"));


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.close);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        fillRecy();
        addListener();

    }

    private void addListener() {
        reference.addChildEventListener(this);
    }

    private void fillRecy() {
        reviewAdapter= new ReviewAdapter(this);
        recyclerView.setAdapter(reviewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }



    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Post newPost = dataSnapshot.getValue(Post.class);
        reviewAdapter.addToTop(newPost);
        key.add(dataSnapshot.getKey());
        Log.d("Debug",key.toString());

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
