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

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ClassViewAdapter extends RecyclerView.Adapter<ClassViewAdapter.ViewHolder> {

    private List<AddClassActivity.Class> classes;
    private final Consumer<AddClassActivity.Class> onClassRemoved;

    public ClassViewAdapter(List<AddClassActivity.Class> classes, Consumer<AddClassActivity.Class> onClassRemoved) {
        this.classes = classes;
        this.onClassRemoved = onClassRemoved;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        System.out.print("Test Change");
        LayoutInflater inflater = LayoutInflater.from(context);
        View classesView = inflater.inflate(R.layout.classes_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(classesView, this::removeClass, onClassRemoved);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddClassActivity.Class currClass = classes.get(position);

        TextView yrTextView = holder.year;
        yrTextView.setText(currClass.getYear());
        TextView qtTextView = holder.quarter;
        qtTextView.setText(currClass.getQuarter());
        TextView sjTextView = holder.subject;
        sjTextView.setText(currClass.getSubject());
        TextView courTextView = holder.course;
        courTextView.setText(currClass.getCourse());
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    public void addClass(AddClassActivity.Class newClass) {
        this.classes.add(newClass);
        this.notifyItemInserted(this.classes.size()-1);
    }

    public void removeClass(int position) {
        this.classes.remove(position);
        this.notifyItemRemoved(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView year, quarter, subject, course;
        private AddClassActivity.Class classs;

        public ViewHolder(View itemView, Consumer<Integer> removeClass,Consumer<AddClassActivity.Class> onClassRemoved) {
            super(itemView);
            year = (TextView) itemView.findViewById(R.id.year_row_txt);
            quarter = (TextView) itemView.findViewById(R.id.quarter_row_txt);
            subject = (TextView) itemView.findViewById(R.id.subject_row_txt);
            course = (TextView) itemView.findViewById(R.id.course_row_txt);
            itemView.findViewById(R.id.removeClassButton).setOnClickListener(view -> {
                removeClass.accept(this.getAdapterPosition());
                onClassRemoved.accept(classs);
            });
        }
    }
}
