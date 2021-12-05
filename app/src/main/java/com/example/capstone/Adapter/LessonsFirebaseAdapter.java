package com.example.capstone.Adapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.Activity.IntroductionActivity;
import com.example.capstone.Activity.LessonActivity;
import com.example.capstone.Model.LessonInfo;
import com.example.capstone.Model.Student;
import com.example.capstone.R;
import com.example.capstone.Fragment.TestFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LessonsFirebaseAdapter extends FirebaseRecyclerAdapter<LessonInfo,LessonsFirebaseAdapter.myViewHolder> {

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private DatabaseReference reference=FirebaseDatabase.getInstance("https://capstoneproject-4b898-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Students");



    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public LessonsFirebaseAdapter(@NonNull FirebaseRecyclerOptions<LessonInfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull LessonInfo model) {
        holder.lessonTitle.setText(model.getLessonName());
        holder.lessonNumber.setText(model.getLessonNumber());
        holder.tvLessonType.setText(model.getLtypes());

        String userID= mAuth.getCurrentUser().getUid();
        reference.child(userID).child("Chapter_"+model.getChapterNumber()+"_Mark_as_Done").child(model.getLessonName()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(Objects.equals(snapshot.getValue(), true)){
                    holder.cbLessonDone.setChecked(true);
                }else{
                    holder.cbLessonDone.setChecked(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       // holder.cbLessonDone.setChecked();

        if(model.isAvailable()){
        holder.rlGrayLesson.setVisibility(View.INVISIBLE);
        holder.cvLessons.setOnClickListener(view -> {

            AppCompatActivity activity = (AppCompatActivity)view.getContext();
            //open and send data to fragment (assessment)
            if (model.getLtypes().equals("Pre-Assessment")){
                TestFragment testFragment =new TestFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id",holder.getBindingAdapterPosition());
                bundle.putString("lessonType",model.getLtypes());
                bundle.putString("lessonName",model.getLessonName());
                bundle.putInt("ChapterNumber",model.getChapterNumber());
                bundle.putInt("testNumber",model.getTestItemNumber());
                bundle.putString("TestNode",model.getTestName());
                bundle.putString("chapterImageURL",model.getLessonChapterImageURl());
                bundle.putString("testName",model.getTestName());
                testFragment.setArguments(bundle);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLessons, testFragment)
                        .addToBackStack(null)
                        .commit();

                //open and send data to Lesson Activity
            }else if (model.getLtypes().equals("Activity")){
                TestFragment testFragment =new TestFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id",holder.getBindingAdapterPosition());
                bundle.putString("lessonType",model.getLtypes());
                bundle.putString("lessonName",model.getLessonName());
                bundle.putInt("ChapterNumber",model.getChapterNumber());
                bundle.putInt("testNumber",model.getTestItemNumber());
                bundle.putString("TestNode",model.getTestName());
                bundle.putString("chapterImageURL",model.getLessonChapterImageURl());
                bundle.putString("testName",model.getTestName());
                testFragment.setArguments(bundle);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLessons, testFragment)
                        .addToBackStack(null)
                        .commit();

                //open and send data to Lesson Activity
            }  else if(model.getLtypes().equals("Lesson")){
                markAsDone(model.getChapterNumber(),model.getLessonName());
                Intent i = new Intent(view.getContext(), LessonActivity.class);
                i.putExtra("lessonType",model.getLtypes());
                i.putExtra("lessonName",model.getLessonName());
                i.putExtra("lessonNumber",model.getLessonNumber());
                i.putExtra("YoutubeURL",model.getYoutubeURL());
                i.putExtra("lessonFirstLineString",model.getFirstLineLessonLecture());
                i.putExtra("firstLessonImage",model.getFirstLessonImage());
                i.putExtra("secondLineLessonLecture",model.getSecondLineLessonLecture());
                i.putExtra("firstFigureNumber",model.getFirstFigureNumber());
                i.putExtra("youtubeTitle",model.getYoutubeVideoTitle());
                i.putExtra("secondLessonImage",model.getSecondLessonImage());
                i.putExtra("secondFigureNumber",model.getSecondFigureNumber());
                i.putExtra("lessonThirdLineString",model.getThirdLineLessonLecture());
                view.getContext().startActivity(i);
                //TODO: send data to activity

            } else if(model.getLtypes().equals("Introduction")){
                markAsDone(model.getChapterNumber(),model.getLessonName());
                Intent i = new Intent(view.getContext(), IntroductionActivity.class);
                i.putExtra("introMessage",model.getIntroMessage());
                i.putExtra("chapterObjectives",model.getChapterObjectives());
                i.putExtra("lessonType",model.getLtypes());
                i.putExtra("lessonName",model.getLessonName());
                i.putExtra("chapterNumber",model.getChapterNumber());
                i.putExtra("lessonNumber",model.getLessonNumber());
                view.getContext().startActivity(i);
                //TODO: send data to fragment
            }
        });
        }
        if(!model.isAvailable()){
            holder.rlGrayLesson.setVisibility(View.VISIBLE);
            holder.rlGrayLesson.setOnClickListener(view ->{
                Toast.makeText(view.getContext(), "Lesson not available.", Toast.LENGTH_SHORT).show();
            });
        }
    }

    //Mark lesson as donne to student progress
    public void markAsDone(int ChapterNumber,String LessonName){
        String userID= mAuth.getCurrentUser().getUid();
        reference.child(userID).child("Chapter_"+ChapterNumber+"_Mark_as_Done").child(LessonName).setValue(true);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewlessons,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        private final TextView lessonTitle;
        private final TextView lessonNumber;
        private final TextView tvLessonType;
        private final CardView cvLessons;
        private final RelativeLayout rlGrayLesson;
        private final CheckBox cbLessonDone;



        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            cbLessonDone=itemView.findViewById(R.id.cbLessonDone);
            rlGrayLesson=itemView.findViewById(R.id.rlGrayLesson);
            cvLessons=itemView.findViewById(R.id.cvLessons);
            lessonTitle=itemView.findViewById(R.id.lessonTitle);
            lessonNumber=itemView.findViewById(R.id.lessonNumber);
            tvLessonType=itemView.findViewById(R.id.tvDefinitionName);
        }
    }

}
