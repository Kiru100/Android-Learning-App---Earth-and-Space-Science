package com.example.capstone.Adapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.Activity.IntroductionActivity;
import com.example.capstone.Activity.LessonActivity;
import com.example.capstone.Model.ChapterInfo;
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
        holder.tvLessonDescription.setText(model.getLessonDescription());

        holder.btnDownArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvLessonDescription.setVisibility(View.VISIBLE);
                holder.btnDownArrow.setVisibility(View.GONE);
                holder.btnUpArrow.setVisibility(View.VISIBLE);

                new Handler().postDelayed(() -> {
                    holder.tvLessonDescription.setVisibility(View.GONE);
                    holder.btnDownArrow.setVisibility(View.VISIBLE);
                    holder.btnUpArrow.setVisibility(View.GONE);
                },10000);
            }
        });
        holder.btnUpArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvLessonDescription.setVisibility(View.GONE);
                holder.btnDownArrow.setVisibility(View.VISIBLE);
                holder.btnUpArrow.setVisibility(View.GONE);
            }
        });

        String LessonType= model.getLtypes();
        if(LessonType.equals("Introduction")){
            holder.ivLesson.setImageResource(R.drawable.classroom);
            holder.tvLessonDescription.setVisibility(View.GONE);
            holder.btnDownArrow.setVisibility(View.VISIBLE);
            holder.btnUpArrow.setVisibility(View.GONE);

        }else if(LessonType.equals("Lesson")){
            holder.ivLesson.setImageResource(R.drawable.reading);
            holder.tvLessonDescription.setVisibility(View.GONE);
            holder.btnDownArrow.setVisibility(View.VISIBLE);
            holder.btnUpArrow.setVisibility(View.GONE);

        }else if(LessonType.equals("Activity")){
            holder.ivLesson.setImageResource(R.drawable.development);
            holder.tvLessonDescription.setVisibility(View.GONE);
            holder.btnDownArrow.setVisibility(View.GONE);
            holder.btnUpArrow.setVisibility(View.GONE);

        }else if(LessonType.equals("Pre-Assessment")||LessonType.equals("Post-Assessment")){
            holder.ivLesson.setImageResource(R.drawable.test);
            holder.tvLessonDescription.setVisibility(View.GONE);
            holder.btnDownArrow.setVisibility(View.GONE);
            holder.btnUpArrow.setVisibility(View.GONE);

        }

        String userID= mAuth.getCurrentUser().getUid();

        final long[] numberofChilder = new long[1];
        reference.child(userID).child("Chapter_"+model.getChapterNumber()+"_Mark_as_Done").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                numberofChilder[0]=snapshot.getChildrenCount();

                if((holder.getAdapterPosition()-1)<numberofChilder[0]){
                    holder.rlGrayLesson.setVisibility(View.GONE);
                    holder.cvLessons.setOnClickListener(view -> {
                        AppCompatActivity activity = (AppCompatActivity)view.getContext();
                        //open and send data to fragment (assessment)
                        if (model.getLtypes().equals("Pre-Assessment")||model.getLtypes().equals("Post-Assessment")){
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
                            i.putExtra("ChapterNumber",model.getChapterNumber());
                            i.putExtra("LessonReference",model.getLessonReference());
                            view.getContext().startActivity(i);


                        } else if(model.getLtypes().equals("Introduction")){
                            Intent i = new Intent(view.getContext(), IntroductionActivity.class);
                            i.putExtra("introMessage",model.getIntroMessage());
                            i.putExtra("chapterObjectives",model.getChapterObjectives());
                            i.putExtra("lessonType",model.getLtypes());
                            i.putExtra("lessonName",model.getLessonName());
                            i.putExtra("chapterNumber",model.getChapterNumber());
                            i.putExtra("lessonNumber",model.getLessonNumber());
                            view.getContext().startActivity(i);

                        }
                    });
                }else{
                    RelativeLayout relativeLayout = (RelativeLayout)holder.rlGrayLesson;
                    relativeLayout.getLayoutParams().height=holder.rlLesson.getHeight();
                   holder.rlGrayLesson.setVisibility(View.VISIBLE);
                   holder.rlGrayLesson.setOnClickListener(v -> Toast.makeText(v.getContext(), "You have to finish previous lesson or activity.", Toast.LENGTH_SHORT).show());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        reference.child(userID).child("Chapter_"+model.getChapterNumber()+"_Mark_as_Done").child(model.getLessonName()).child("done").addListenerForSingleValueEvent(new ValueEventListener() {
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
        private final ImageView ivLesson;
        private final ImageView btnUpArrow,btnDownArrow;
        private final TextView tvLessonDescription;
        private final RelativeLayout rlLesson;
        private final RelativeLayout rlMainLayout;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            rlMainLayout=itemView.findViewById(R.id.rlMainLayout);
            rlLesson=itemView.findViewById(R.id.rlLesson);
            tvLessonDescription=itemView.findViewById(R.id.tvLessonDescription);
            btnUpArrow=itemView.findViewById(R.id.btnUpArrow);
            btnDownArrow=itemView.findViewById(R.id.btnDownArrow);
            ivLesson=itemView.findViewById(R.id.ivLesson);
            cbLessonDone=itemView.findViewById(R.id.cbLessonDone);
            rlGrayLesson=itemView.findViewById(R.id.rlGrayLesson);
            cvLessons=itemView.findViewById(R.id.cvLessons);
            lessonTitle=itemView.findViewById(R.id.lessonTitle);
            lessonNumber=itemView.findViewById(R.id.lessonNumber);
            tvLessonType=itemView.findViewById(R.id.tvDefinitionName);

        }
    }

}
