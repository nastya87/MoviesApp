package com.example.moviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

// 1. Extend this class from RecyclerView.Adapter<YOUR_CLASS_NAME.INNER_CLASS_NAME>
// 2. Create "public static" inner class INNER_CLASS_NAME (in this example: MyViewHolder)
//    which extends RecyclerView.ViewHolder
// 3. Add private variables: from Context and List types.
// 4. Implement all required methods for extending RecyclerView.
// 5. Create constructor for the class with all fields Context and List.
// 6. Define MyViewHolder and then onCreateViewHolder method.
// 7. Define onBindViewHolder method.
// 8. To display images add Glide library.
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    private Context mContext;
    private List<MovieModelClass> mData;

    public MovieAdapter(Context mContext, List<MovieModelClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.movie_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(mData.get(position).getId());
        holder.name.setText(mData.get(position).getName());

        // Using Glide library to display images:
        // https://bumptech.github.io/glide/doc/download-setup.html
        // 1. Copy Gradle repositories into project settings.gradle file (maybe you already have them).
        // 2. Add in dependencies in build.gradle this line:
        //       implementation 'com.github.bumptech.glide:glide:4.14.2'
        // Add code in this method:
        Glide.with(mContext)
                .load(mData.get(position).getImg())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView name;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id_txt);
            name = itemView.findViewById(R.id.name_txt);
            img = itemView.findViewById(R.id.imageView);
        }
    }
}
