package com.example.capstone.Fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.capstone.Adapter.ChapterFirebaseAdapter;
import com.example.capstone.Model.ChapterInfo;
import com.example.capstone.Model.Student;
import com.example.capstone.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private ChapterFirebaseAdapter fireAdapter;
    private RecyclerView rvChapters;
    private ShimmerFrameLayout shimmerFrameLayout,shimmerFrameLayout1,shimmerFrameLayout2;
    private boolean isLoaded;
    private TextView tvWelcomeName;
    private FirebaseUser student;
    private DatabaseReference reference;
    private String userID;



    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_home, container, false);

       //TODO: Prevent fragment refresh when navigation is clicked

        rvChapters = rootView.findViewById(R.id.rvChapters);


        shimmerFrameLayout=rootView.findViewById(R.id.shimmerFrameLayout);
        shimmerFrameLayout1=rootView.findViewById(R.id.shimmerFrameLayout1);
        shimmerFrameLayout2=rootView.findViewById(R.id.shimmerFrameLayout2);



        student = FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance("https://capstoneproject-4b898-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Students");
        userID=student.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student studentProfile= snapshot.getValue(Student.class);
                if(studentProfile!=null){
                    String sfname=studentProfile.getSFname();


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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
