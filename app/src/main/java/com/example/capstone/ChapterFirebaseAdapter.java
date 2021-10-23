package com.example.capstone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ChapterFirebaseAdapter extends FirebaseRecyclerAdapter<ChapterInfo,ChapterFirebaseAdapter.myViewHolder> {

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
        holder.progress.setProgress(model.getChapterProgress());
        Glide.with(holder.chapterImage.getContext())
                .load(model.getImageFile())
                .into(holder.chapterImage);


        holder.rlChapters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                LessonFragment lessonsfrags=new LessonFragment();

                PreTest pre=new PreTest();


                Bundle bundle = new Bundle();
                bundle.putInt("id",holder.getBindingAdapterPosition());
                bundle.putString("ChapterName",model.getChapterName());
                bundle.putInt("ChapterNumber",model.getChapterNumber());
                lessonsfrags.setArguments(bundle);
                pre.setArguments(bundle);


                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentHome,lessonsfrags)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_items_chapters,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        private FirebaseMethods mFirebaseMethods;
        private TextView tvChapterTitle;
        private CardView rlChapters;
        private ProgressBar progress;
        private ImageView chapterImage;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            mFirebaseMethods = new FirebaseMethods(itemView.getContext());
            tvChapterTitle = itemView.findViewById(R.id.tvChapterTitle);
            rlChapters = itemView.findViewById(R.id.rlChapters);
            progress = itemView.findViewById(R.id.progress);
            chapterImage= itemView.findViewById(R.id.chapterImage);
        }
    }


}
