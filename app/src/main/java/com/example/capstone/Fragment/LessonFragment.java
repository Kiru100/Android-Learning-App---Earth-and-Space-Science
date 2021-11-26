package com.example.capstone.Fragment;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capstone.Adapter.LessonsFirebaseAdapter;
import com.example.capstone.Model.LessonInfo;
import com.example.capstone.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;



public class LessonFragment extends Fragment {
    private LessonsFirebaseAdapter fadapter;
    private TextView tvChapter,tvChapterTitle;
    private ImageView ivChapterImage;

    public LessonFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lessons, container, false);
        RecyclerView rvLessons = rootView.findViewById(R.id.rvLessons);

        tvChapter=rootView.findViewById(R.id.tvChapter);
        tvChapterTitle=rootView.findViewById(R.id.tvChapterTitle);
        ivChapterImage=rootView.findViewById(R.id.ivChapterImage);

        rvLessons.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        FirebaseRecyclerOptions<LessonInfo> options;

        Bundle bundle = getArguments();
        int chapterNum=bundle.getInt("id")+1;
        String ImageURL=bundle.getString("LessonImage");

        Glide.with(this).load(ImageURL).into(ivChapterImage);

        String chapterNumber="Chapter "+chapterNum;
        tvChapter.setText(chapterNumber);
        tvChapterTitle.setText(bundle.getString("ChapterName"));

                options= new FirebaseRecyclerOptions.Builder<LessonInfo>()
                        .setQuery(FirebaseDatabase.getInstance("https://capstoneproject-4b898-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                .getReference()
                                .child("Lesson")
                                .child(String.valueOf(chapterNum)), LessonInfo.class)
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
    public static class WrapContentLinearLayoutManager extends LinearLayoutManager {
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
