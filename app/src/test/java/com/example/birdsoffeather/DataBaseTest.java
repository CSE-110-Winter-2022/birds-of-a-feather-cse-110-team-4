package com.example.birdsoffeather;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.CoursesDao;
import com.example.birdsoffeather.model.db.PersonWithCoursesDao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;



@RunWith(AndroidJUnit4.class)
public class DataBaseTest {
    private PersonWithCoursesDao personWithCoursesDao;
    private CoursesDao coursesDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();

        db = AppDatabase.singleton(context);
        personWithCoursesDao = db.personsWithCoursesDao();
        coursesDao = db.coursesDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void testInsertPersonAndNote() throws Exception {

        int numOfPerson = db.personsWithCoursesDao().count();
        System.out.println(numOfPerson);
        Assert.assertEquals(2, numOfPerson);

    }

}
