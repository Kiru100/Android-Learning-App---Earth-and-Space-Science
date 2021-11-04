package com.example.capstone.Fragment;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capstone.Adapter.LessonsFirebaseAdapter;
import com.example.capstone.Model.LessonInfo;
import com.example.capstone.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;



public class LessonFragment extends Fragment {
    private LessonsFirebaseAdapter fadapter;
    private TextView tvChapterNumber,tvChapterTitle;

    public LessonFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lessons, container, false);
        RecyclerView rvLessons = rootView.findViewById(R.id.rvLessons);

        tvChapterNumber=rootView.findViewById(R.id.tvChapterNumber);
        tvChapterTitle=rootView.findViewById(R.id.tvChapterTitle);

        rvLessons.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        FirebaseRecyclerOptions<LessonInfo> options;

        Bundle bundle = getArguments();
        int chapternumber=bundle.getInt("id");
        System.out.println(chapternumber);
        tvChapterNumber.setText(String.valueOf(bundle.getInt("ChapterNumber")));
        tvChapterTitle.setText(bundle.getString("ChapterName"));

                options= new FirebaseRecyclerOptions.Builder<LessonInfo>()
                        .setQuery(FirebaseDatabase.getInstance("https://capstoneproject-4b898-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                .getReference()
                                .child("Lesson")
                                .child(String.valueOf(chapternumber+1)), LessonInfo.class)
                                .build();

        fadapter = new LessonsFirebaseAdapter(options);
        rvLessons.setAdapter(fadapter);

        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        fadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        fadapter.stopListening();
    }


    //Prevent outofbound error
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
