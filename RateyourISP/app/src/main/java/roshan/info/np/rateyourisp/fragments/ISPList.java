package roshan.info.np.rateyourisp.fragments;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import roshan.info.np.rateyourisp.activities.AboutUs;
import roshan.info.np.rateyourisp.activities.DetailActivity;
import roshan.info.np.rateyourisp.extras.MyApplication;
import roshan.info.np.rateyourisp.models.MyList;
import roshan.info.np.rateyourisp.R;
import roshan.info.np.rateyourisp.interfaces.ClickListener;
import roshan.info.np.rateyourisp.adapters.ISPListAdapter;
import roshan.info.np.rateyourisp.models.Post;


/**
 * A simple {@link Fragment} subclass.
 */
public class ISPList extends Fragment implements ValueEventListener {

    private RecyclerView recyclerViewISP;
    private ArrayList<MyList> list = new ArrayList<>();
    private LinearLayout errorLayout;
    ProgressBar progress;

    ISPListAdapter ispListAdapter;
    DatabaseReference databaseReference;


    public ISPList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_isplist, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        fillRecy();
        addOneTimeListener();

    }

    private void fillRecy() {
        ispListAdapter = new ISPListAdapter(getActivity());

        ispListAdapter.setOnRateClickListener(new ClickListener() {
            @Override
            public void itemClicked(View view, int position, String name, String desp) {
                startActivity(new Intent(getActivity(), DetailActivity.class)
                        .putExtra("pos", position)
                        .putExtra("name", name)
                        .putExtra("desp", desp));

            }
        });
        recyclerViewISP.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewISP.setAdapter(ispListAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewISP = (RecyclerView) view.findViewById(R.id.recy_isp);
        errorLayout = (LinearLayout) view.findViewById(R.id.errorNews);
        progress = (ProgressBar) view.findViewById(R.id.progressbarNews);


        errorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOneTimeListener();
            }
        });
    }


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            ispListAdapter.addToTop(child.getValue(MyList.class));
        }
        progress.setVisibility(View.GONE);

    }
/*

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            MyList list = dataSnapshot.getValue(MyList.class);
            ispListAdapter.addToTop(list);
        progress.setVisibility(View.GONE);
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
    */

    @Override
    public void onCancelled(DatabaseError databaseError) {
        progress.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    private void addOneTimeListener() {
        errorLayout.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        DatabaseReference reference = databaseReference.child("isp_lists");
        reference.addListenerForSingleValueEvent(this);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu,menu);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.info) {
            startActivity(new Intent(getActivity(), AboutUs.class));

        }
        return super.onOptionsItemSelected(item);

    }

}

