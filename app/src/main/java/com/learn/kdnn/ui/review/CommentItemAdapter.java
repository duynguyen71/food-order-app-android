package com.learn.kdnn.ui.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.kdnn.R;
import com.learn.kdnn.model.Comment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class
CommentItemAdapter extends RecyclerView.Adapter<CommentItemAdapter.MyViewHolder> {

    private Context context;
    private List<Comment> comments;
    private LayoutInflater inflater;

    @NonNull
    @NotNull
    @Override
    public CommentItemAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.comment_entry, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CommentItemAdapter.MyViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.tvUsername.setText(comment.getUser().getFullName());
        holder.tvCmtContent.setText(comment.getCommentText());
    }

    @Override
    public int getItemCount() {
        if(this.comments!=null){
            return this.comments.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername,tvCmtContent;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.cmtUsername);
            tvCmtContent = itemView.findViewById(R.id.cmtContent);
        }
    }
}
