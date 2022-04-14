package com.example.capstone.Fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.capstone.Adapter.ChapterFirebaseAdapter;
import com.example.capstone.Model.ChapterInfo;
import com.example.capstone.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    private ChapterFirebaseAdapter fireAdapter;
    private RecyclerView rvChapters;
    private DatabaseReference reference;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        rvChapters = rootView.findViewById(R.id.rvChapters);


        reference=FirebaseDatabase.getInstance("https://capstoneproject-4b898-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Students");
        reference.keepSynced(true);



        rvChapters.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        //firebase
        FirebaseRecyclerOptions<ChapterInfo> options =
                new FirebaseRecyclerOptions.Builder<ChapterInfo>()
                        .setQuery(FirebaseDatabase
                                .getInstance("https://capstoneproject-4b898-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                .getReference()
                                .child("Chapter"), ChapterInfo.class)
                        .build();

        fireAdapter= new ChapterFirebaseAdapter(options);
        fireAdapter.notifyDataSetChanged();
        rvChapters.setAdapter(fireAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        fireAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        fireAdapter.stopListening();
    }

    //Prevent out of Bound error
    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        //... constructor
        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {

            }
        }
    }

}
