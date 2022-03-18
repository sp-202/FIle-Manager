package com.example.fileexplorer.dynamic_tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
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
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fileexplorer.R;
import com.example.fileexplorer.search_view.Detailed_view;
import com.example.fileexplorer.search_view.FileListModel;
import com.example.fileexplorer.search_view.FolderListProvider;
import com.example.fileexplorer.util.DataHolder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Common_adapter extends RecyclerView.Adapter<Common_adapter.Common_adapter_ViewHolder> {
    private static final String TAG = "myApp789";
    Context context;
    ArrayList<FileListModel> fileArrayList;
    ArrayList<FolderListProvider> folderArrayList;

    public Common_adapter(Context context, ArrayList<FileListModel> fileArrayList, ArrayList<FolderListProvider> folderArrayList) {
        this.context = context;
        this.fileArrayList = fileArrayList;
        this.folderArrayList = folderArrayList;
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

        Path filePath = Paths.get(fileArrayList.get(position).getRelativePath());
        File selectedFile = filePath.toFile();
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

                try {
                    Glide.with(context).load(selectedFile).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.fileIcon_img);
                } catch (Exception e) {
                    Log.d(TAG, "onBindViewHolder: " + e.toString());
                }
            } else if (selectedFile.getName().toLowerCase().endsWith("svg")){
                holder.fileIcon_img.setImageResource(R.drawable.ic_twotone_photo_24);
            }
            else {
                holder.fileIcon_img.setImageResource(R.drawable.ic_file_icon);
            }


        }
        holder.itemClick.setOnClickListener(view -> {
            if (selectedFile.isFile()) {
                try {
                    if (selectedFile.getName().endsWith(".pdf")) {
                        File file = new File(selectedFile.getAbsolutePath());
                        Intent viewPdf = new Intent(Intent.ACTION_VIEW);
                        viewPdf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri URI = FileProvider.getUriForFile(context.getApplicationContext(), context.getApplicationContext().getPackageName() + ".provider", file);
                        Log.d(TAG, "onCreate: " + URI);
                        viewPdf.setDataAndType(URI, "application/pdf");
                        viewPdf.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        context.startActivity(viewPdf);
                    } else {
                        if (selectedFile.getName().endsWith(".jpg")
                                || selectedFile.getName().endsWith(".jpeg")
                                || selectedFile.getName().endsWith(".png")) {
                            Intent intent = new Intent();
                            Uri uri = Uri.fromFile(selectedFile);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri, "image/*");
                            intent.putExtra("mimeType", "image/*");
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            context.startActivity(Intent.createChooser(intent, null));
                        } else if (selectedFile.getName().endsWith(".mkv")
                                || selectedFile.getName().endsWith(".avi")
                                || selectedFile.getName().endsWith(".mp4")) {
                            Intent intent = new Intent();
                            Uri uri = Uri.fromFile(selectedFile);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri, "video/*");
                            intent.putExtra("mimeType", "video/*");
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            context.startActivity(Intent.createChooser(intent, null));
                        } else if (selectedFile.getName().endsWith(".mp3")
                                || selectedFile.getName().endsWith(".amr")
                                || selectedFile.getName().endsWith(".mp2")
                                || selectedFile.getName().endsWith(".wav")) {
                            Intent intent = new Intent();
                            Uri uri = Uri.fromFile(selectedFile);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri, "audio/*");
                            intent.putExtra("mimeType", "audio/*");
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            context.startActivity(Intent.createChooser(intent, null));
                        } else if (selectedFile.getName().endsWith(".docx")
                                || selectedFile.getName().endsWith(".doc")) {
                            Intent intent = new Intent();
                            Uri uri = Uri.fromFile(selectedFile);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                            intent.putExtra("mimeType", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            context.startActivity(Intent.createChooser(intent, null));
                        } else if (selectedFile.getName().endsWith(".xlsx")
                                || selectedFile.getName().endsWith(".xls")
                                || selectedFile.getName().endsWith(".csv")) {
                            Intent intent = new Intent();
                            Uri uri = Uri.fromFile(selectedFile);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                            intent.putExtra("mimeType", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            context.startActivity(Intent.createChooser(intent, null));
                        } else if (selectedFile.getName().endsWith(".ppt")) {
                            Intent intent = new Intent();
                            Uri uri = Uri.fromFile(selectedFile);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri, "application/vnd.ms-powerpoint*");
                            intent.putExtra("mimeType", "application/vnd.ms-powerpoint*");
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            context.startActivity(Intent.createChooser(intent, null));
                        } else if (selectedFile.getName().endsWith(".html")
                                || selectedFile.getName().endsWith(".css")
                                || selectedFile.getName().endsWith(".txt")) {
                            Intent intent = new Intent();
                            Uri uri = Uri.fromFile(selectedFile);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri, "text/*");
                            intent.putExtra("mimeType", "text/*");
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            context.startActivity(Intent.createChooser(intent, null));
                        } else if (selectedFile.getName().endsWith(".json")) {
                            Intent intent = new Intent();
                            Uri uri = Uri.fromFile(selectedFile);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri, "application/json");
                            intent.putExtra("mimeType", "application/json");
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            context.startActivity(Intent.createChooser(intent, null));
                        } else if (selectedFile.getName().endsWith(".js")) {
                            Intent intent = new Intent();
                            Uri uri = Uri.fromFile(selectedFile);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri, "application/javascript");
                            intent.putExtra("mimeType", "application/javascript");
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            context.startActivity(Intent.createChooser(intent, null));
                        } else {
                            Intent intent = new Intent();
                            Uri uri = Uri.fromFile(selectedFile);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri, "*/*");
                            intent.putExtra("mimeType", "*/*");
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            context.startActivity(Intent.createChooser(intent, null));
                        }
                    }


                } catch (Exception e) {
                    Log.d(TAG, "onBindViewHolder: " + e.toString());
                }
            } else {

                Intent intent = new Intent(context, Common_activity.class);
                String path = selectedFile.getAbsolutePath();
                intent.putExtra("path_from", path);
                Log.d(TAG, "onBindViewHolder: " + path);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
        holder.vertDot.setOnClickListener(view -> {
            Context context1 = new ContextThemeWrapper(context, R.style.CustomPopupMenuStyle);
            PopupMenu popupMenu = new PopupMenu(context1, holder.vertDot);
            popupMenu.inflate(R.menu.file_folder_options_menu);
            if (selectedFile.isDirectory()) {
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
        TextView timeStamp, fileName, vertDot;
        ImageView fileIcon_img;
        LinearLayout folderIcon, itemClick, fileIcon;

        public Common_adapter_ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeStamp = itemView.findViewById(R.id.timeStamp_item);
            fileName = itemView.findViewById(R.id.item_folder_name);
            vertDot = itemView.findViewById(R.id.vert_three_dot_item);
            fileIcon = itemView.findViewById(R.id.file_folder_icon);
            folderIcon = itemView.findViewById(R.id.folder_icon_item_view);
            fileIcon_img = itemView.findViewById(R.id.file_icon_view_item);
            itemClick = itemView.findViewById(R.id.common_adapter_item_view);
        }
    }
}
