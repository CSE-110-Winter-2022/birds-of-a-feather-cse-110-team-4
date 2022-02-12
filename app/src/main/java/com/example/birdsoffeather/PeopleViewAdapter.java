package com.example.birdsoffeather;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.PersonWithCourses;

import java.util.List;

public class PeopleViewAdapter extends RecyclerView.Adapter<PeopleViewAdapter.ViewHolder> {
//    private final List<? extends IPerson> people;
    private final IPerson[] people;

//    public PeopleViewAdapter(List<PersonWithCourses> people) {
//        super();
//        this.people = people;
//    }

    public PeopleViewAdapter(IPerson[] people) {
        super();
        this.people = people;
    }

    @NonNull
    @Override
    public PeopleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.people_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewAdapter.ViewHolder holder, int position) {
        holder.setPerson(people[position]);
    }

    @Override
    public int getItemCount() {
        return this.people.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final TextView personNameView;
        private final ImageView personAvatarView;
        private IPerson person;

        ViewHolder(View itemView) {
            super(itemView);
            this.personNameView = itemView.findViewById(R.id.people_row_name);
            this.personAvatarView = itemView.findViewById(R.id.people_row_avatar);
            itemView.setOnClickListener(this);
        }

        public void setPerson(IPerson person) {
            this.person = person;
            this.personNameView.setText(person.getName());
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, PersonDetailActivity.class);
            intent.putExtra("person_id", this.person.getId());
            context.startActivity(intent);
        }
    }
}
