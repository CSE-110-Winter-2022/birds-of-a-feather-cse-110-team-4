package com.example.birdsoffeather;
import android.content.Context;


import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.Person;
import com.example.birdsoffeather.model.db.Courses;
import com.example.birdsoffeather.model.db.PersonWithCourses;
import com.example.birdsoffeather.model.db.PersonWithCoursesDao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;



@RunWith(AndroidJUnit4.class)
public class ClassSizeDBTest {
    private PersonWithCoursesDao personWithCoursesDao;
    private PersonWithCourses personWithCourses;
    private AppDatabase db;

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
    public void addCourses() throws Exception {
        int numbOfCourses = db.coursesDao().count();
        Courses testCourse1 = new Courses(0,"0","2020 FA ECE 100 Tiny (1-40)");
        Courses testCourse2 = new Courses(1,"1","2021 FA ECE 101 Small (40-75)");

        db.coursesDao().insert(testCourse1);
        db.coursesDao().insert(testCourse2);

        int afterNum = db.coursesDao().count();
        Assert.assertEquals(numbOfCourses + 2,afterNum);

        db.coursesDao().delete(testCourse1);
        db.coursesDao().delete(testCourse2);
    }
    @Test
    public void deletedAddedCourse() throws Exception {
        Courses testCourse1 = new Courses(0,"0","ECE 100");
        Courses testCourse2 = new Courses(1,"0","ECE 101");
        Courses testCourse3 = new Courses(2,"0","ECE 109");
        Courses testCourse4 = new Courses(3,"0","ECE 111");

        db.coursesDao().insert(testCourse1);
        db.coursesDao().insert(testCourse2);
        db.coursesDao().insert(testCourse3);
        db.coursesDao().insert(testCourse4);
        db.coursesDao().delete(testCourse1);

        int numbOfCourses = db.coursesDao().count();
        Assert.assertEquals(3,numbOfCourses);

    }
}
