package com.example.fileexplorer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.fileexplorer.FileReaderForAnyFile;
import com.example.fileexplorer.R;
import com.example.fileexplorer.dynamic_tabs.Common_activity;
import com.example.fileexplorer.fragments.overview_fragments.PicturesFragment;
import com.example.fileexplorer.fragments.overview_fragments.VideosFragment;
import com.example.fileexplorer.search_view.Detailed_view;

import java.util.Objects;

public class Home_fragment extends Fragment {
    View view;
    CardView image_icon, downloads_icon, music_icon, videos_icon,
            archive_icon, apk_icon, documents_icon, memory_icon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        String path = requireActivity().getIntent().getStringExtra("path");
        image_icon = view.findViewById(R.id.image_folder_icon);
        downloads_icon = view.findViewById(R.id.downloads_icon);
        music_icon = view.findViewById(R.id.music_folder_icon);
        videos_icon = view.findViewById(R.id.videoes_folder_icon);
        archive_icon = view.findViewById(R.id.archive_folder_icon);
        documents_icon = view.findViewById(R.id.documents_folder_icon);
        apk_icon = view.findViewById(R.id.apk_folder_icon);
        memory_icon = view.findViewById(R.id.memory_folder_icon);

        image_icon.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), PicturesFragment.class);

            String[] img_paths = new String[4];
            img_paths[0] = path + "/Download";
            img_paths[1] = path + "/DCIM";
            img_paths[2] = path + "/Pictures";
            img_paths[3] = path + "Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Images";
            intent.putExtra("pic_folder", "pics12");
            intent.putExtra("download", img_paths[0]);
            intent.putExtra("DCIM", img_paths[1]);
            intent.putExtra("Pic", img_paths[2]);
            intent.putExtra("WhatsApp", img_paths[3]);
            startActivity(intent);
        });

        downloads_icon.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), Common_activity.class);
            String path1 = "/storage/emulated/0/Download";
            intent.putExtra("path_from", path1);
            startActivity(intent);
        });

        videos_icon.setOnClickListener(view -> {
            VideosFragment videoesFragment = new VideosFragment();
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, videoesFragment).addToBackStack(null).commit();
        });

        memory_icon.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), Common_activity.class);
            String path1 = "/storage/emulated/0/";
            intent.putExtra("path_from", path1);
            startActivity(intent);
        });

        return view;
    }
}
