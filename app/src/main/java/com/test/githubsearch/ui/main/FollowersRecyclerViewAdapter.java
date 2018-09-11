package com.test.githubsearch.ui.main;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.test.githubsearch.R;
import com.test.githubsearch.data.api.model.User;

/**
 * Created by Darin on 4/7/2018.
 */

public class FollowersRecyclerViewAdapter extends  RecyclerView.Adapter<FollowersRecyclerViewAdapter.RespoViewHolder> {

    private MainPresenter mPresenter;

    public FollowersRecyclerViewAdapter(MainPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public RespoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_follower_grid, parent, false);
        return new RespoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RespoViewHolder holder, int position) {
        mPresenter.onBindFollowerItemView(holder, position);
        holder.setOnItemClickListenser(mPresenter, position);
    }

    @Override
    public int getItemCount() {
        return mPresenter.getFollowerCount();
    }


    public static class RespoViewHolder extends RecyclerView.ViewHolder implements MainContract.FollowerItemView {
        public TextView mTvName;
        public SimpleDraweeView mIvAvarta;
        public View mItemView;

        public RespoViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mTvName = itemView.findViewById(R.id.tvName);
            mIvAvarta = itemView.findViewById(R.id.imageView);
        }

        public void setOnItemClickListenser(final MainPresenter presenter, final int position) {
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.onFollowerItemClick(position);
                }
            });
        }

        @Override
        public void setName(String name) {
            mTvName.setText(name);
        }

        @Override
        public void displayAvarta(Uri imageUri) {
            mIvAvarta.setImageURI(imageUri);
        }
    }
}
