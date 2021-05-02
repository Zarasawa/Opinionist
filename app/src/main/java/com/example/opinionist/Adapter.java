package com.example.opinionist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ItemClickListener itemClickListener;
    private LayoutInflater layoutInflater;
    private List<Comment> data;

    Adapter(Context context, List<Comment> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
        this.itemClickListener = (ItemClickListener) context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.comment_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = data.get(position);
        holder.textTitle.setText(comment.getComment());
        holder.textLikes.setText(comment.getLikes().toString());

        holder.button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                itemClickListener.showReplies(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textLikes;
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textLikes = itemView.findViewById(R.id.textLikes);
            button = itemView.findViewById(R.id.button);
        }
    }

}
