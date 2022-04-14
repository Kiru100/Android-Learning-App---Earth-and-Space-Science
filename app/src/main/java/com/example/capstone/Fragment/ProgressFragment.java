package com.example.capstone.Fragment;
import android.content.Context;
import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.capstone.Adapter.ProgressFirebaseAdapter;
import com.example.capstone.Model.MarkAsDoneInfo;
import com.example.capstone.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ProgressFragment extends Fragment{
    private RecyclerView rvStudentProgress;
    private String userID,chapterName;
    private int chapterNumber;
    private FirebaseUser student;
    private ProgressFirebaseAdapter fireAdapter;
    private TextView tvChapterName,tvChapterNumber2;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_progress, container, false);

        rvStudentProgress=rootView.findViewById(R.id.rvStudentProgress);
        tvChapterName=rootView.findViewById(R.id.tvChapterName);
        tvChapterNumber2=rootView.findViewById(R.id.tvChapterNumber2);

        Bundle bundle = getArguments();
        assert bundle != null;
        bundle.getString("lessonType");
        chapterName=bundle.getString("ChapterName");
        tvChapterName.setText(chapterName);

        chapterNumber=bundle.getInt("ChapterNumber");
        tvChapterNumber2.setText("Chapter "+chapterNumber);

        student = FirebaseAuth.getInstance().getCurrentUser();
        userID=student.getUid();
        System.out.println(userID);
        rvStudentProgress.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        FirebaseRecyclerOptions<MarkAsDoneInfo> options =
                new FirebaseRecyclerOptions.Builder<MarkAsDoneInfo>()
                        .setQuery(FirebaseDatabase
                                .getInstance("https://capstoneproject-4b898-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                .getReference()
                                .child("Students")
                                .child(userID)
                                .child("Chapter_"+chapterNumber+"_Mark_as_Done").orderByChild("dateMark"), MarkAsDoneInfo.class)
                        .build();
        if(options.toString().equals(null)){

        }

        fireAdapter= new ProgressFirebaseAdapter(options);
        fireAdapter.notifyDataSetChanged();
        rvStudentProgress.setAdapter(fireAdapter);



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