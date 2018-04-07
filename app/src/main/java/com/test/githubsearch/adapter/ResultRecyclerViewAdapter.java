package com.test.githubsearch.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.test.githubsearch.R;
import com.test.githubsearch.model.User;

import java.util.List;

/**
 * Created by Darin on 4/7/2018.
 */

public class ResultRecyclerViewAdapter extends  RecyclerView.Adapter<ResultRecyclerViewAdapter.RespoViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(User item);
    }

    private List<User> mRespoFollowers;
    private OnItemClickListener mOnItemClickListener;

    public ResultRecyclerViewAdapter(List<User> respoFollowers, OnItemClickListener onItemClickListener) {
        mRespoFollowers = respoFollowers;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RespoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_follower_grid, parent, false);
        return new RespoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RespoViewHolder holder, int position) {
        User respoFollower = mRespoFollowers.get(position);
        holder.bind(respoFollower, mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mRespoFollowers.size();
    }


    public static class RespoViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvName;
        public SimpleDraweeView mIvAvarta;
        public View mItemView;

        public RespoViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mTvName = (TextView)itemView.findViewById(R.id.tvName);
            mIvAvarta = (SimpleDraweeView)itemView.findViewById(R.id.imageView);
        }

        public void bind(final User respoFollower, final OnItemClickListener onItemClickListener) {
            mTvName.setText(respoFollower.login);
            Uri imageUri = Uri.parse(respoFollower.avatarUrl);
            mIvAvarta.setImageURI(imageUri);

            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(respoFollower);
                    }
                }
            });
        }

    }
}
