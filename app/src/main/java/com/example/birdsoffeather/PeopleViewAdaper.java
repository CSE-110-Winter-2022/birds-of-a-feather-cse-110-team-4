package com.example.birdsoffeather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.PersonWithCourses;

import java.util.List;

public class PeopleViewAdaper extends RecyclerView.Adapter<PeopleViewAdaper.ViewHolder> {
    private final List<? extends IPerson> people;

    public PeopleViewAdaper(List<PersonWithCourses> people) {
        super();
        this.people = people;
    }

    @NonNull
    @Override
    public PeopleViewAdaper.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.people_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewAdaper.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
