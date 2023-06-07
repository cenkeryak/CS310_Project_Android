package com.example.workoutwarrior_anil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class ExerciseRecyclerAdapter extends RecyclerView.Adapter<ExerciseRecyclerAdapter.viewHolder>{

    AdapterListener listener;
    Context context;
    List<Exercise> data;

    public ExerciseRecyclerAdapter(Context context,List<Exercise> data,AdapterListener listener){
        this.context=context;
        this.data=data;
        this.listener=listener;
    }

    interface AdapterListener{
        public void listenClick(Exercise obj);
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root=LayoutInflater.from(context).inflate(R.layout.exercise_row,parent,false);
        return new viewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.exerciseName.setText(data.get(position).getName());
        holder.exerciseMuscle.setText(data.get(position).getMuscle());
        holder.exerciseDifficulty.setText(data.get(position).getDifficulty());

        Exercise obj=data.get(position);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.listenClick(obj);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;
        TextView exerciseMuscle;
        TextView exerciseDifficulty;

        View root;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName=itemView.findViewById(R.id.exerciseName);
            exerciseMuscle=itemView.findViewById(R.id.exerciseMuscle);
            exerciseDifficulty=itemView.findViewById(R.id.exerciseDifficulty);
            root=itemView.findViewById(R.id.rowContainer);

        }
    }
}
