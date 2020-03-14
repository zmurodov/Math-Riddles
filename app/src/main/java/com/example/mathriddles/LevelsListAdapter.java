package com.example.mathriddles;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mathriddles.models.Level;

import java.util.List;

public class LevelsListAdapter extends RecyclerView.Adapter<LevelsListAdapter.ViewHolder> {
    private List<Level> levels;
    private Context context;
    private OnItemClickListener listener;

    public LevelsListAdapter(Context context, List<Level> levels, OnItemClickListener listener) {
        this.context = context;
        this.levels = levels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Level level = levels.get(position);
        holder.bind(level);
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.level_no);
        }

        public void bind(final Level level) {
            id.setText(level.getId() + "");
            if(level.getId() <= 5){
                itemView.setBackgroundResource(R.drawable.enter_btn2);
                id.setTextColor(Color.WHITE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(level);
                }
            });
        }
    }

    interface OnItemClickListener {
        void onClick(Level level);
    }

}
