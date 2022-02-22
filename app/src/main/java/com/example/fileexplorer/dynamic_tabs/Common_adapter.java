package com.example.fileexplorer.dynamic_tabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fileexplorer.R;

import java.io.File;
import java.util.ArrayList;

public class Common_adapter extends RecyclerView.Adapter<Common_adapter.Common_adapter_ViewHolder> {
    Context context;
    ArrayList<File> fileArrayList;

    public Common_adapter(Context context, ArrayList<File> fileArrayList) {
        this.context = context;
        this.fileArrayList = fileArrayList;
    }

    @NonNull
    @Override
    public Common_adapter_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.common_adapter_item_view, parent, false);
        return new Common_adapter_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Common_adapter_ViewHolder holder, int position) {
        File selectedFile = fileArrayList.get(position);
        
    }

    @Override
    public int getItemCount() {
        return fileArrayList.size();
    }

    public class Common_adapter_ViewHolder extends RecyclerView.ViewHolder {
        TextView timeStamp, fileName;
        ImageView vertDot;
        public Common_adapter_ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeStamp = itemView.findViewById(R.id.timeStamp_item);
            fileName = itemView.findViewById(R.id.item_folder_name);
            vertDot = itemView.findViewById(R.id.vert_three_dot_item);
        }
    }
}
