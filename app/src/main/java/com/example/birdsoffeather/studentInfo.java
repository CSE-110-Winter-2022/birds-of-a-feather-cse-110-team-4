package com.example.birdsoffeather;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.birdsoffeather.model.FakedMessageListener;
import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.Courses;
import com.example.birdsoffeather.model.db.Person;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class studentInfo extends AppCompatActivity {
    //Recycler View
    private RecyclerView classesRecyclerView;
    private RecyclerView.LayoutManager classesRecyclerViewManager;
    //Data base
    private AppDatabase db;
    private IPerson person;
    private IPerson sender;
    private String personId;
    private int ownerId;
    private  String name;
    private List<Courses> courses;
    private String imageURL;
    private boolean mocking;
    private static String found;
    private MessageListener messageListener;

    //Adapter
    private ClassViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        Intent intent = getIntent();
        personId = intent.getStringExtra("person_id");
        //Getting data from data base (db)
        db = AppDatabase.singleton(this);
        person = db.personsWithCoursesDao().get(personId);
        name = person.getName();
        courses = db.coursesDao().gerForPerson(personId);
        imageURL = person.getURL();
        TextView nameView = findViewById(R.id.studentName);
        nameView.setText(name);//Set name for user
        //Adapter View
        classesRecyclerView = (RecyclerView) findViewById(R.id.studentClassList);
        adapter = new ClassViewAdapter(true,false,courses, (course)-> {
        });
        classesRecyclerView.setAdapter(adapter);
        classesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Displaying image
        ImageView i = (ImageView) findViewById(R.id.profile_picture_view);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ImageView i = (ImageView)findViewById(R.id.profile_picture_view);
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageURL).getContent());
                    runOnUiThread(new Runnable() {

                        @Override
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

    public void backToList(View view) {
        finish();
    }

    public void waveonClick(View view) {
        MessageListener realListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                found = new String(message.getContent());
                Log.d(TAG, "Message found" + found);
            }

            @Override
            public void onLost(@NonNull Message message) {
                Log.d(TAG, "Message lost" + found);
            }

        };
        String info = "wave\n" + name + "\n" + db.personsWithCoursesDao().get("0").getName();
        this.messageListener = realListener;
        Nearby.getMessagesClient(this).publish(new Message(info.getBytes()));
        Nearby.getMessagesClient(this).subscribe(messageListener);
        Button wave = findViewById(R.id.waveButton);
        wave.setText("waved");
        wave.setEnabled(false);
        Toast.makeText(this, "Waved to"+name+"!", Toast.LENGTH_SHORT).show();
    }

}