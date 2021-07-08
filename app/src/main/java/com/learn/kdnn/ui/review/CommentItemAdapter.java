package com.learn.kdnn.ui.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.kdnn.R;
import com.learn.kdnn.model.Comment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class CommentItemAdapter extends RecyclerView.Adapter<CommentItemAdapter.MyViewHolder> {

    private Context context;
    private List<Comment> comments;


    @Setter
    private OnRemoveComment onRemoveComment;

    public CommentItemAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @NotNull
    @Override
    public CommentItemAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_entry, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CommentItemAdapter.MyViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.tvUsername.setText(comment.getUsername());
        holder.tvCmtContent.setText(comment.getCommentText());

    }

    @Override
    public int getItemCount() {
        if (this.comments == null || this.comments.isEmpty()) {
            return 0;
        }
        return this.comments.size();
    }

    public Comment getComment(int pos) {
        return comments.get(pos);
    }

    public void addComment(Comment comment) {
        this.comments.add(0, comment);
        this.notifyItemInserted(0);
        this.notifyItemRangeChanged(0, comments.size() - 1);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvCmtContent, tvOptions;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.cmtUsername);
            tvCmtContent = itemView.findViewById(R.id.cmtContent);
            tvOptions = itemView.findViewById(R.id.cmtOptions);

            tvOptions.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(context, tvOptions, GravityCompat.START);
                popupMenu.inflate(R.menu.cmt_menu);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.cmt_item_rm) {
                        if (onRemoveComment != null) {
                            onRemoveComment.removeComment(getAdapterPosition());
                        }
                        return true;
                    }
                    return false;
                });
            });
        }


    }

    public Comment removeComment(int position) {
        Comment cmt = comments.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, comments.size() );
        return cmt;
    }

    public interface OnRemoveComment {
        void removeComment(int position);
    }
}

