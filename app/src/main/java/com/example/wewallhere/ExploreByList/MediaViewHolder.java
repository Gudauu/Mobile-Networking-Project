package com.example.wewallhere.ExploreByList;

import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import com.example.wewallhere.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MediaViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageViewMedia;
    public VideoView videoViewMedia;
    public ImageView imageViewProfilePic;
    public TextView textViewTitle;
    public TextView textViewUploader;
    public TextView textViewDate;
    public MediaController mediaController;


    public MediaViewHolder(@NonNull View itemView) {
        super(itemView);
        imageViewMedia = itemView.findViewById(R.id.imageViewMedia);
        imageViewProfilePic = itemView.findViewById(R.id.imageViewProfilePic);
        textViewTitle = itemView.findViewById(R.id.textViewTitle);
        textViewUploader = itemView.findViewById(R.id.textViewUploader);
        textViewDate = itemView.findViewById(R.id.textViewDate);
        videoViewMedia = itemView.findViewById(R.id.videoViewMedia);


    }
}

