package com.example.birdsoffeather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdsoffeather.model.db.Courses;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ClassViewAdapter extends RecyclerView.Adapter<ClassViewAdapter.ViewHolder> {

    private List<Courses> classes;
    private final Consumer<Courses> onClassRemoved;
    private Boolean deleteEnable;

    public ClassViewAdapter(Boolean deleteEnable,List <Courses> classes, Consumer<Courses> onClassRemoved) {
        this.classes = classes;
        this.onClassRemoved = onClassRemoved;
        this.deleteEnable = deleteEnable;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View classesView = inflater.inflate(R.layout.classes_row,parent,false);
        ViewHolder viewHolder;
        if(deleteEnable) {
            viewHolder = new ViewHolder(classesView, this::removeClass, onClassRemoved);
        }
        else {
            viewHolder = new ViewHolder(classesView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setCourses(classes.get(position));
        Courses currClass = classes.get(position);
        String[] splitStr = currClass.course.split(" ");

        TextView yrTextView = holder.year;
        yrTextView.setText(splitStr[0]);
        TextView qtTextView = holder.quarter;
        qtTextView.setText(splitStr[1]);
        TextView sjTextView = holder.subject;
        sjTextView.setText(splitStr[2]);
        TextView courTextView = holder.course;
        courTextView.setText(splitStr[3]);
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    public void addClass(Courses newClass) {
        this.classes.add(newClass);
        this.notifyItemInserted(this.classes.size()-1);
    }

    public void removeClass(int position) {
        this.classes.remove(position);
        this.notifyItemRemoved(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView year, quarter, subject, course;
        private Courses courses;

        public ViewHolder(View itemView, Consumer<Integer> removeClass,Consumer<Courses> onClassRemoved) {
            super(itemView);
            year = (TextView) itemView.findViewById(R.id.year_row_txt);
            quarter = (TextView) itemView.findViewById(R.id.quarter_row_txt);
            subject = (TextView) itemView.findViewById(R.id.subject_row_txt);
            course = (TextView) itemView.findViewById(R.id.course_row_txt);
            itemView.findViewById(R.id.removeClassButton).setOnClickListener((view) -> {
                removeClass.accept(this.getAdapterPosition());
                onClassRemoved.accept(courses);
            });
        }
        public ViewHolder(View itemView) {
            super(itemView);
            year = (TextView) itemView.findViewById(R.id.year_row_txt);
            quarter = (TextView) itemView.findViewById(R.id.quarter_row_txt);
            subject = (TextView) itemView.findViewById(R.id.subject_row_txt);
            course = (TextView) itemView.findViewById(R.id.course_row_txt);
            Button deleteButton = (Button)itemView.findViewById(R.id.removeClassButton);
            deleteButton.setVisibility(itemView.INVISIBLE);
        }

        public void setCourses(Courses courses) {
            this.courses = courses;
        }
    }
}
