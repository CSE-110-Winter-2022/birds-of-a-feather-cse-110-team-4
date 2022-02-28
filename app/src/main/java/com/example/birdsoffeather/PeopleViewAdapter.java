package com.example.birdsoffeather;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.PersonWithCourses;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class PeopleViewAdapter extends RecyclerView.Adapter<PeopleViewAdapter.ViewHolder> {
    private final List<PersonWithCourses> people;

    public PeopleViewAdapter(List<PersonWithCourses> people) {
        super();
        this.people = people;
    }



    @NonNull
    @Override
    public PeopleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.people_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewAdapter.ViewHolder holder, int position) {
        holder.setPerson(people.get(position));
    }

    @Override
    public int getItemCount() {
        return this.people.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final TextView personNameView;
        private final ImageView personAvatarView;
        private final TextView personNumClasses;
        private final TextView waving;
        private IPerson person;

        ViewHolder(View itemView) {
            super(itemView);
            this.personNameView = itemView.findViewById(R.id.person_row_name);
            this.personAvatarView = itemView.findViewById(R.id.person_row_avatar);
            this.personNumClasses = itemView.findViewById(R.id.person_row_num);
            this.waving = itemView.findViewById(R.id.waving_text);
            itemView.setOnClickListener(this);
        }



        public void setPerson(IPerson person) {
            // set person information for the recycler
            this.person = person;
            this.personNameView.setText(person.getName());
            this.personNumClasses.setText(String.valueOf((person.getCourses().size())));  // all classes, not in common for now. Fix later
            this.waving.setText("waving");
            // set profile pic in recycle
            if(person.getWaveFrom()){
                waving.setVisibility(View.VISIBLE);
            }
            else{
                waving.setVisibility(View.INVISIBLE);
            }
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        ImageView i = itemView.findViewById(R.id.person_row_avatar);
                        Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(person.getURL()).getContent());
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            public void run() {
                                i.setImageBitmap(bitmap);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, studentInfo.class);
            intent.putExtra("person_id", this.person.getId());
            context.startActivity(intent);
        }

    }
}
