package com.example.capstone.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.Fragment.DefinitionMain;
import com.example.capstone.Model.DefinitionInfo;
import com.example.capstone.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class DefinitionFirebaseAdapter extends FirebaseRecyclerAdapter<DefinitionInfo,DefinitionFirebaseAdapter.myViewHolder>
{


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public DefinitionFirebaseAdapter(@NonNull FirebaseRecyclerOptions<DefinitionInfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull DefinitionInfo model) {
        holder.tvDefinitionName.setText(model.getDefinitionName());



        holder.cvDefinition.setOnClickListener(view -> {
            AppCompatActivity activity = (AppCompatActivity)view.getContext();

            DefinitionMain Fragment =new DefinitionMain();
            Bundle bundle = new Bundle();
            bundle.putString("DefinitionName",model.getDefinitionName());
            bundle.putString("DefinitionDescription",model.getDefinitionDescription());
            bundle.putString("DefinitionImageURL",model.getDefinitionImageURL());
            Fragment.setArguments(bundle);

//            final NavController navController = Navigation.findNavController(view);
//            navController.navigate(R.id.action_definitionFragment_to_definitionmain);

            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentDefine, Fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_definition,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDefinitionName;
        private CardView cvDefinition;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDefinitionName=itemView.findViewById(R.id.tvDefinitionName);
            cvDefinition=itemView.findViewById(R.id.cvDefinition);

        }
    }
}
