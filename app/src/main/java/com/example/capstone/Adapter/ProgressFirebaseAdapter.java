package com.example.capstone.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.capstone.Model.MarkAsDoneInfo;
import com.example.capstone.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ProgressFirebaseAdapter extends FirebaseRecyclerAdapter<MarkAsDoneInfo,ProgressFirebaseAdapter.myViewHolder>{


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProgressFirebaseAdapter(@NonNull FirebaseRecyclerOptions<MarkAsDoneInfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MarkAsDoneInfo model) {


        holder.tvDateDone.setText(model.getDateMark());
        holder.tvLessonName.setText(model.getLessonNameDone());
       if(model.getType().equals("activity")){ holder.tvScore.setVisibility(View.VISIBLE);
        holder.tvScore.setText("Score : " + model.getLessonScore());
       }else{
           holder.tvScore.setVisibility(View.GONE);
       }

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_progress_list,parent,false);
        return new myViewHolder(view);
    }


    public static class myViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDateDone;
        private final TextView tvLessonName;
        private final TextView tvScore;
        private final CardView cvProgress;
        public myViewHolder(View view) {
            super(view);
            tvLessonName=view.findViewById(R.id.tvLessonName);
            tvDateDone=view.findViewById(R.id.tvDateDone);
            tvScore=view.findViewById(R.id.tvScore);
            cvProgress=view.findViewById(R.id.cvProgress);
        }
    }
}
