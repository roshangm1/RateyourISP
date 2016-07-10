package roshan.info.np.rateyourisp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;
import roshan.info.np.rateyourisp.R;
import roshan.info.np.rateyourisp.models.Post;
import roshan.info.np.rateyourisp.models.Rate;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private String rev;
    private int pos;
    private FloatingActionButton fab;
    private RatingBar ratingOverall1, ratingOverall2, ratingOverall3, ratingOverall4, averageRate;
    private TextView aveRateNum;


    FancyButton button;

    DatabaseReference reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapseLayout);
        assert collapsingToolbar != null;
        collapsingToolbar.setTitle("Rate my ISP");

        button = (FancyButton) findViewById(R.id.all_reviews);


        assert button != null;
        button.setOnClickListener(this);

        //Custom fragment for dialog
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customView = inflater.inflate(R.layout.write_reviews_fragment, null);

        final TextView reviewText = (TextView) customView.findViewById(R.id.review_text);
        final RatingBar rate1 = (RatingBar) customView.findViewById(R.id.rate1);
        final RatingBar rate2 = (RatingBar) customView.findViewById(R.id.rate2);
        final RatingBar rate3 = (RatingBar) customView.findViewById(R.id.rate3);
        final RatingBar rate4 = (RatingBar) customView.findViewById(R.id.rate4);
        //End

        TextView nameOfIsp = (TextView) findViewById(R.id.isp_name);
        TextView despOfIsp = (TextView) findViewById(R.id.isp_desp);

        assert nameOfIsp != null;
        nameOfIsp.setText(getIntent().getStringExtra("name"));
        assert despOfIsp != null;
        despOfIsp.setText(getIntent().getStringExtra("desp"));

        ratingOverall1 = (RatingBar) findViewById(R.id.rating1);
        ratingOverall2 = (RatingBar) findViewById(R.id.rating2);
        ratingOverall3 = (RatingBar) findViewById(R.id.rating3);
        ratingOverall4 = (RatingBar) findViewById(R.id.rating4);

        averageRate = (RatingBar) findViewById(R.id.aveRate);
        aveRateNum = (TextView) findViewById(R.id.aveRateNum);

        fab = (FloatingActionButton) findViewById(R.id.fab_post);

        pos = getIntent().getIntExtra("pos", 0);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog.Builder dialog = new MaterialDialog.Builder(DetailActivity.this);
                dialog
                        .customView(customView, true)
                        .positiveText("Submit")
                        .negativeText("Cancel")
                        .cancelable(false)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                float ave, r1, r2, r3, r4;
                                rev = reviewText.getText().toString();
                                if (!rev.equals("")) {
                                    r1 = rate1.getRating();
                                    r2 = rate2.getRating();
                                    r3 = rate3.getRating();
                                    r4 = rate4.getRating();
                                    ave = (r1 + r2 + r3 + r4) / 4;
                                    writeNewPost(r1, r2, r3, r4, rev, ave);

                                } else {
                                    Toast.makeText(DetailActivity.this, "Please write a review first!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();


            }
        });


        reference2 = FirebaseDatabase.getInstance().getReference().child("isp_lists").child(String.valueOf(pos)).child("rates");

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Rate rate = dataSnapshot.getValue(Rate.class);
                getSharedPreferences("total_rate", MODE_PRIVATE).edit().putInt("rateCount", rate.rateCount).apply();
                getSharedPreferences("total_rate", MODE_PRIVATE).edit().putFloat("rate1", rate.rate1).apply();
                getSharedPreferences("total_rate", MODE_PRIVATE).edit().putFloat("rate2", rate.rate2).apply();
                getSharedPreferences("total_rate", MODE_PRIVATE).edit().putFloat("rate3", rate.rate3).apply();
                getSharedPreferences("total_rate", MODE_PRIVATE).edit().putFloat("rate4", rate.rate4).apply();
                getSharedPreferences("total_rate", MODE_PRIVATE).edit().putFloat("aveRate", rate.aveRate).apply();

                Log.d("Debug", String.valueOf(rate.aveRate / rate.rateCount));


                ratingOverall1.setRating(rate.rate1 / rate.rateCount);
                ratingOverall2.setRating(rate.rate2 / rate.rateCount);
                ratingOverall3.setRating(rate.rate3 / rate.rateCount);
                ratingOverall4.setRating(rate.rate4 / rate.rateCount);

                averageRate.setRating(rate.aveRate / rate.rateCount);
                aveRateNum.setText(String.format(Locale.ENGLISH, "%.2f", rate.aveRate / rate.rateCount));

                button.setText("All reviews (" + String.valueOf(rate.rateCount) + ")");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.all_reviews) {
            startActivity(new Intent(this, AllReviews.class).putExtra("key", pos).putExtra("name", getIntent().getStringExtra("name")));
        }
    }

    private void writeNewPost(float r1, float r2, float r3, float r4, String rev, float ave) {
        float ovrate1 = r1 + getSharedPreferences("total_rate", MODE_PRIVATE).getFloat("rate1", 0);
        float ovrate2 = r2 + getSharedPreferences("total_rate", MODE_PRIVATE).getFloat("rate2", 0);
        float ovrate3 = r3 + getSharedPreferences("total_rate", MODE_PRIVATE).getFloat("rate3", 0);
        float ovrate4 = r4 + getSharedPreferences("total_rate", MODE_PRIVATE).getFloat("rate4", 0);
        ave = ave + getSharedPreferences("total_rate", MODE_PRIVATE).getFloat("aveRate", 0);

        int rateCount = getSharedPreferences("total_rate", MODE_PRIVATE).getInt("rateCount", 0) + 1;

        Post post = new Post(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), ovrate1, ovrate2, ovrate3, ovrate4, r1, r2, r3, r4, rateCount, rev, ave, FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
        Map<String, Object> postValues = post.toMap();

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("isp_lists").child(String.valueOf(pos)).child("reviews");
        reference1.push().setValue(postValues);

        Map<String, Object> postRateValues = post.toMapRate();
        reference2.setValue(postRateValues);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

}
