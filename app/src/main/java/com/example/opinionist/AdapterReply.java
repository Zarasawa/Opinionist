package com.example.opinionist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterReply  extends RecyclerView.Adapter<AdapterReply.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Comment> data;

    AdapterReply(Context context, List<Comment> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.reply_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = data.get(position);
        holder.textTitle.setText(comment.getComment());
        holder.textLikes.setText(comment.getLikes().toString());

        holder.replybuttonUp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
               Log.i("Opinionist","upClick");

                //commentInterface.upvote(comment.getID(), comment.getLikes() + 1);
            }
        });

/***
        holder.button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                v.getContext().startActivity( new Intent(v.getContext(), Replies.class).putExtra("Topic", comment.getID()));
            }
        });
***/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textLikes;
        Button button;
        Button replybuttonUp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textLikes = itemView.findViewById(R.id.textLikes);
            button = itemView.findViewById(R.id.button);
            replybuttonUp = itemView.findViewById(R.id.replybuttonUp);
        }
    }


}
