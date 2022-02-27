package com.example.fileexplorer.dynamic_tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fileexplorer.R;
import com.example.fileexplorer.search_view.Detailed_view;
import com.example.fileexplorer.util.DataHolder;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onBindViewHolder(@NonNull Common_adapter_ViewHolder holder, int position) {
        File selectedFile = fileArrayList.get(position);
        holder.fileName.setText(selectedFile.getName());
        Date lastModified = new Date(selectedFile.lastModified());
        @SuppressLint("SimpleDateFormat")
        Format f = new SimpleDateFormat("dd MMM");
        String date = f.format(lastModified);
        holder.timeStamp.setText(date);

        if (selectedFile.isDirectory()) {
            holder.fileIcon.setVisibility(View.GONE);
            holder.folderIcon.setVisibility(View.VISIBLE);
        } else if (selectedFile.isFile()) {
            holder.fileIcon.setVisibility(View.VISIBLE);
            holder.folderIcon.setVisibility(View.GONE);
            if (selectedFile.getName().toLowerCase().endsWith(".pdf")) {
                holder.fileIcon_img.setImageResource(R.drawable.ic_icon_pdf_file);
            } else if (selectedFile.getName().toLowerCase().endsWith(".jpg")
                    || selectedFile.getName().toLowerCase().endsWith(".jpeg")
                    || selectedFile.getName().toLowerCase().endsWith(".png")) {
                holder.fileIcon_img.setImageResource(R.drawable.ic_twotone_photo_24);
            } else if (selectedFile.getName().toLowerCase().endsWith(".xml")) {
                holder.fileIcon_img.setImageResource(R.drawable.ic_icon_xml_file);
            } else if (selectedFile.getName().toLowerCase().endsWith(".js")) {
                holder.fileIcon_img.setImageResource(R.drawable.ic_icon_js_file);
            } else if (selectedFile.getName().toLowerCase().endsWith(".xlsx")
                    || selectedFile.getName().toLowerCase().endsWith(".csv")) {
                holder.fileIcon_img.setImageResource(R.drawable.ic_icon_xlsx_file);
            } else if (selectedFile.getName().toLowerCase().endsWith(".docx")) {
                holder.fileIcon_img.setImageResource(R.drawable.ic_home_icon);
            } else if (selectedFile.getName().toLowerCase().endsWith(".ppt")) {
                holder.fileIcon_img.setImageResource(R.drawable.ic_icon_ppt_file);
            } else if (selectedFile.getName().toLowerCase().endsWith(".c")
                    || selectedFile.getName().toLowerCase().endsWith(".cpp")
                    || selectedFile.getName().toLowerCase().endsWith(".h")
                    || selectedFile.getName().toLowerCase().endsWith(".c++")
                    || selectedFile.getName().toLowerCase().endsWith(".cxx")) {
                holder.fileIcon_img.setImageResource(R.drawable.ic_icon_cpp_file);
            } else if (selectedFile.getName().toLowerCase().endsWith(".html")) {
                holder.fileIcon_img.setImageResource(R.drawable.ic_icon_html_2_file);
            } else if (selectedFile.getName().toLowerCase().endsWith(".css")) {
                holder.fileIcon_img.setImageResource(R.drawable.ic_icon_css_file);
            } else if (selectedFile.getName().toLowerCase().endsWith(".rar")
                    || selectedFile.getName().toLowerCase().endsWith(".zip")) {
                holder.fileIcon_img.setImageResource(R.drawable.ic_icon_zip_folder);
            } else if (selectedFile.getName().toLowerCase().endsWith(".apk")) {
                holder.fileIcon_img.setImageResource(R.drawable.ic_baseline_android_24);
            } else {
                holder.fileIcon_img.setImageResource(R.drawable.ic_file_icon);
            }

        }
        holder.vertDot.setOnClickListener(view -> {
            Context context1 = new ContextThemeWrapper(context, R.style.CustomPopupMenuStyle);
            PopupMenu popupMenu = new PopupMenu(context1, holder.vertDot);
            popupMenu.inflate(R.menu.file_folder_options_menu);
            if (selectedFile.isDirectory()){
                popupMenu.getMenu().findItem(R.id.menu_file_open_with).setVisible(false);
                popupMenu.getMenu().findItem(R.id.menu_file_share).setVisible(false);
                popupMenu.getMenu().findItem(R.id.menu_file_addTo_favourite).setVisible(false);
                popupMenu.getMenu().findItem(R.id.menu_file_moveTo_trash).setVisible(false);
                popupMenu.getMenu().findItem(R.id.menu_file_moveTo_safeFolder).setVisible(false);
                popupMenu.getMenu().findItem(R.id.menu_file_file_info).setTitle("Folder info");
            }
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_file_selected:
                        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_file_copy_to:
                        Toast.makeText(context, "copy", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_file_file_info:
                        Toast.makeText(context, "file info", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, Detailed_view.class);
                        DataHolder.getInstance().setData(selectedFile.getAbsolutePath());
                        context.startActivity(intent);
                        break;
                    case R.id.menu_file_delete_permanently:
                        Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return fileArrayList.size();
    }

    public class Common_adapter_ViewHolder extends RecyclerView.ViewHolder {
        TextView timeStamp, fileName;
        ImageView vertDot, fileIcon_img;
        CardView fileIcon;
        LinearLayout folderIcon;

        public Common_adapter_ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeStamp = itemView.findViewById(R.id.timeStamp_item);
            fileName = itemView.findViewById(R.id.item_folder_name);
            vertDot = itemView.findViewById(R.id.vert_three_dot_item);
            fileIcon = itemView.findViewById(R.id.file_folder_icon);
            folderIcon = itemView.findViewById(R.id.folder_icon_item_view);
            fileIcon_img = itemView.findViewById(R.id.file_icon_view_item);

        }
    }
}
