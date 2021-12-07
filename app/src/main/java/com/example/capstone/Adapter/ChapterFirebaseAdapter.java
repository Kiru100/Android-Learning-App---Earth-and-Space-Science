package com.example.capstone.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.capstone.Model.FirebaseMethods;
import com.example.capstone.Fragment.LessonFragment;
import com.example.capstone.Model.ChapterInfo;
import com.example.capstone.Fragment.TestFragment;
import com.example.capstone.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChapterFirebaseAdapter extends FirebaseRecyclerAdapter<ChapterInfo,ChapterFirebaseAdapter.myViewHolder> {

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private DatabaseReference studentsRef= FirebaseDatabase.getInstance("https://capstoneproject-4b898-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Students");

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */


    public ChapterFirebaseAdapter(@NonNull FirebaseRecyclerOptions<ChapterInfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull ChapterInfo model) {
        holder.tvChapterTitle.setText(model.getChapterName());
        holder.tvChapterDescription.setText(model.getChapterDescription());
        String chapterNum=String.valueOf(model.getChapterNumber());
        holder.tvChapterNumber.setText("Chapter "+chapterNum);
        Glide.with(holder.chapterImage.getContext())
                .load(model.getImageFile())
                .into(holder.chapterImage);


        final long[] LessonDoneCount = new long[1];


        String userID= mAuth.getCurrentUser().getUid();
        studentsRef.child(userID).child("Chapter_"+model.getChapterNumber()+"_Mark_as_Done").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LessonDoneCount[0]=snapshot.getChildrenCount();
                float percentage = (LessonDoneCount[0]/model.getNumberofLessons())*100;

                holder.progressText.setText("Chapter progress : "+Math.round(percentage)+"%");
                holder.progress.setProgress(Math.round(percentage));
                studentsRef.child(userID).child("Chapter_"+model.getChapterNumber()+"_Progress").setValue(Math.round(percentage));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        if(model.isAvailable()) {
            holder.rlhideLayout.setVisibility(View.INVISIBLE);
            holder.rlChapters.setOnClickListener(view -> {
                        AppCompatActivity activity = (AppCompatActivity)view.getContext();

                        LessonFragment lessonsfrags=new LessonFragment();
                        TestFragment pre=new TestFragment();

                        Bundle bundle = new Bundle();
                        bundle.putInt("id",holder.getBindingAdapterPosition());
                        bundle.putString("ChapterName",model.getChapterName());
                        bundle.putInt("ChapterNumber",model.getChapterNumber());
                        bundle.putString("LessonImage",model.getLessonChapterImageURl());
                        lessonsfrags.setArguments(bundle);
                        pre.setArguments(bundle);

                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentHome,lessonsfrags)
                                .addToBackStack(null)
                                .commit();
            });
        }
        if(!model.isAvailable()){
            holder.rlhideLayout.setVisibility(View.VISIBLE);
            holder.rlhideLayout.setOnClickListener(view ->{
                Toast.makeText(view.getContext(), "Chapter not available.", Toast.LENGTH_SHORT).show();
            });

        }
    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_items_chapters,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {


        private RelativeLayout rlhideLayout;
        private TextView tvChapterTitle;
        private CardView rlChapters;
        private ProgressBar progress;
        private ImageView chapterImage;
        private TextView tvChapterDescription;
        private TextView tvChapterNumber;
        private TextView progressText;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            progressText=itemView.findViewById(R.id.progressText);
            tvChapterDescription = itemView.findViewById(R.id.tvChapterDescription);
            tvChapterTitle = itemView.findViewById(R.id.tvChapterTitle);
            rlChapters = itemView.findViewById(R.id.rlChapters);
            progress = itemView.findViewById(R.id.progress);
            chapterImage= itemView.findViewById(R.id.chapterImage);
            tvChapterNumber=itemView.findViewById(R.id.tvChapterNumber);
            rlhideLayout=itemView.findViewById(R.id.rlhideLayout);
        }
    }


}
