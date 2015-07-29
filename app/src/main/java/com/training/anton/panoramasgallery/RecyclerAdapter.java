package com.training.anton.panoramasgallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.training.anton.api.model.PanoramaPhoto;

import java.util.Collections;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context mContext;
    private List<PanoramaPhoto> mPanoramasList;
    private ItemClickListener mListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.imageViewInCell);
            mTextView = (TextView) itemView.findViewById(R.id.textViewInCell);
        }
    }

    public RecyclerAdapter(ItemClickListener itemClickListener) {
        mPanoramasList = Collections.EMPTY_LIST;
        mListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v, viewHolder.getLayoutPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        PanoramaPhoto photo = getItem(i);

        Picasso.with(mContext).load(photo.getPhotoURL()).into(viewHolder.mImageView);
        viewHolder.mTextView.setText(photo.getPhotoTitle());
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getPhotoId();
    }

    private PanoramaPhoto getItem(int i) {
        return mPanoramasList.get(i);
    }

    @Override
    public int getItemCount() {
        return mPanoramasList.size();
    }

    public void updateContent(List<PanoramaPhoto> updatedList) {
        mPanoramasList = updatedList;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
