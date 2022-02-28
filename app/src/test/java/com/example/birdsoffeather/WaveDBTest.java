package com.example.birdsoffeather;

import static org.junit.Assert.assertEquals;

import android.content.Context;


import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.birdsoffeather.model.FakedMessageListener;
import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.Person;
import com.example.birdsoffeather.model.db.Courses;
import com.example.birdsoffeather.model.db.PersonWithCourses;
import com.example.birdsoffeather.model.db.PersonWithCoursesDao;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@RunWith(AndroidJUnit4.class)
public class WaveDBTest {
    private PersonWithCoursesDao personWithCoursesDao;
    private PersonWithCourses personWithCourses;
    private AppDatabase db;
    private static String found;
    private MessageListener messageListener;
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = AppDatabase.useTestSingleton(context);
        personWithCoursesDao = db.personsWithCoursesDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void testWave() throws Exception {
        Person testPerson1 = new Person(0,"Test Name 1",
                "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-P" +
                        "zLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8" +
                        "LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4" +
                        "XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0", false, false);
        Person testPerson2 = new Person(1,"Test Name 2",
                "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSM" +
                        "Oy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKI" +
                        "PqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGl" +
                        "LLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0", false, false);
        db.personsWithCoursesDao().insert(testPerson1);
        db.personsWithCoursesDao().insert(testPerson2);
        int numOfPerson = db.personsWithCoursesDao().count();
        assertEquals(2, numOfPerson);

        MessageListener realListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                found = new String(message.getContent());
            }

            @Override
            public void onLost(@NonNull Message message) {
            }

        };
        String info = "Test Name 1"+","+"Test Name 2";
        this.messageListener = new FakedMessageListener(realListener, info);
        testPerson2.waveTo = true;
        this.messageListener.onFound(new Message(info.getBytes(StandardCharsets.UTF_8)));
        assertEquals(testPerson2.waveTo,true);
    }
}