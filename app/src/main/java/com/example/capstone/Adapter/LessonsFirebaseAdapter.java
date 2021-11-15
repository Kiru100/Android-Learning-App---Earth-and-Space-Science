package com.example.capstone.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.Activity.IntroductionActivity;
import com.example.capstone.Activity.LessonActivity;
import com.example.capstone.Model.LessonInfo;
import com.example.capstone.R;
import com.example.capstone.Fragment.TestFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class LessonsFirebaseAdapter extends FirebaseRecyclerAdapter<LessonInfo,LessonsFirebaseAdapter.myViewHolder> {

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
                testFragment.setArguments(bundle);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLessons, testFragment)
                        .addToBackStack(null)
                        .commit();

                //open and send data to activity
            } else if(model.getLtypes().equals("Lesson")){
                Intent i = new Intent(view.getContext(), LessonActivity.class);
                i.putExtra("lessonType",model.getLtypes());
                i.putExtra("lessonName",model.getLessonName());
                i.putExtra("lessonNumber",model.getLessonNumber());
                i.putExtra("YoutubeURL",model.getYoutubeURL());
                view.getContext().startActivity(i);
                //TODO: send data to fragment

            } else if(model.getLtypes().equals("Introduction")){

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


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            cvLessons=itemView.findViewById(R.id.cvLessons);
            lessonTitle=itemView.findViewById(R.id.lessonTitle);
            lessonNumber=itemView.findViewById(R.id.lessonNumber);
            tvLessonType=itemView.findViewById(R.id.tvLessonType);
        }
    }

}
