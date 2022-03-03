package com.example.birdsoffeather;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.Person;
import com.example.birdsoffeather.model.db.PersonWithCourses;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class PeopleViewAdapter extends RecyclerView.Adapter<PeopleViewAdapter.ViewHolder> {
    private final List<PersonWithCourses> people;
    private Context context;
    private AppDatabase db;

    public PeopleViewAdapter(List<PersonWithCourses> people, Context context, AppDatabase db) {
        super();
        this.people = people;
        this.context = context;
        this.db = db;
    }




    @NonNull
    @Override
    public PeopleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.people_row, parent, false);
        return new ViewHolder(view, this.db);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewAdapter.ViewHolder holder, int position) {
        holder.setPerson(people.get(position), this.context);
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
        private final ImageView personFavoriteView;
        private IPerson person;
        private AppDatabase db;

        ViewHolder(View itemView, AppDatabase db) {
            super(itemView);
            this.personNameView = itemView.findViewById(R.id.person_row_name);
            this.personAvatarView = itemView.findViewById(R.id.person_row_avatar);
            this.personNumClasses = itemView.findViewById(R.id.person_row_num);
            this.waving = itemView.findViewById(R.id.waving_text);
            this.db = db;
            this.personFavoriteView = itemView.findViewById(R.id.person_row_fav);

            this.personFavoriteView.setOnClickListener( (view) -> {
                toggleFavorite();
            });;

            itemView.setOnClickListener(this);
        }

        public void toggleFavorite(){
            //TODO
            // update backend user with id=id

            Drawable fav_filled_drawable = this.itemView.getContext().getDrawable(R.drawable.ic_star_filled);
            Drawable fav_unfilled_drawable = this.itemView.getContext().getDrawable(R.drawable.ic_star_unfilled);
            // make one drawable with whatever is currently in user with id's favorite

            ImageView f = this.itemView.findViewById(R.id.person_row_fav);

            if(f.getTag() == "Filled"){
                Person newPerson = new Person(person.getId(), person.getName(), person.getURL(), person.getWaveTo(), person.getWaveFrom(), false);
                db.personsWithCoursesDao().update(newPerson);
                f.setImageDrawable(fav_unfilled_drawable);
                f.setTag("Unfilled");
            }else{
                Person newPerson = new Person(person.getId(), person.getName(), person.getURL(), person.getWaveTo(), person.getWaveFrom(), true);
                db.personsWithCoursesDao().update(newPerson);
                f.setImageDrawable(fav_filled_drawable);
                f.setTag("Filled");
            }

        }

        public void setPerson(IPerson person, Context context) {
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

            // TODO: Fetch current favorite status here
            ImageView f = itemView.findViewById(R.id.person_row_fav);
            Drawable curr_fav_drawable;

            if(this.person.getFavStatus()){
                curr_fav_drawable = context.getDrawable(R.drawable.ic_star_filled);
                f.setTag("Filled");
            }else{
                curr_fav_drawable = context.getDrawable(R.drawable.ic_star_unfilled);
                f.setTag("Unfilled");
            }

            f.setImageDrawable(curr_fav_drawable);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        ImageView i = itemView.findViewById(R.id.person_row_avatar);
                        Bitmap bitmap_i = BitmapFactory.decodeStream((InputStream) new URL(person.getURL()).getContent());



                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            public void run() {
                                i.setImageBitmap(bitmap_i);

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            // TODO: render whatever is currently in favorite from backend


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
