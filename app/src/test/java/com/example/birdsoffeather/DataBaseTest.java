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
public class DataBaseTest {
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
    public void testInsertAndRemovePerson() throws Exception {
        Person testPerson1 = new Person("0","Test Name 1",
                "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-P" +
                        "zLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8" +
                        "LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4" +
                        "XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0", false, false, false);
        Person testPerson2 = new Person("1","Test Name 2",
                "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSM" +
                        "Oy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKI" +
                        "PqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGl" +
                        "LLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0", false, false, false);
        db.personsWithCoursesDao().insert(testPerson1);
        db.personsWithCoursesDao().insert(testPerson2);
        int numOfPerson = db.personsWithCoursesDao().count();
        Assert.assertEquals(2, numOfPerson);


    }
    @Test
    public void addCourses() throws Exception {
        Courses testCourse1 = new Courses(0,"0","ECE 100");
        Courses testCourse2 = new Courses(1,"0","ECE 101");
        Courses testCourse3 = new Courses(2,"0","ECE 109");
        Courses testCourse4 = new Courses(3,"0","ECE 111");

        db.coursesDao().insert(testCourse1);
        db.coursesDao().insert(testCourse2);
        db.coursesDao().insert(testCourse3);
        db.coursesDao().insert(testCourse4);

        int numbOfCourses = db.coursesDao().count();
        Assert.assertEquals(4,numbOfCourses);

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
    @Test
    public  void updataPerson() throws Exception{
        Person testPerson1 = new Person("0","Test Name 1",
                "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-P" +
                        "zLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8" +
                        "LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4" +
                        "XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0", false, false, false);
        db.personsWithCoursesDao().insert(testPerson1);
        Assert.assertEquals("Test Name 1",db.personsWithCoursesDao().get("0").getName());
        Person newPerson = new Person("0","Han",
                "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-P" +
                        "zLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8" +
                        "LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4" +
                        "XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0", false, false, false);
        db.personsWithCoursesDao().update(newPerson);
        Assert.assertEquals("Han",db.personsWithCoursesDao().get("0").getName());
    }

    @Test
    public  void testFavorite() throws Exception{
        Person testPerson1 = new Person("0","Test Name 1",
                "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-P" +
                        "zLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8" +
                        "LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4" +
                        "XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0", false, false, false);
        db.personsWithCoursesDao().insert(testPerson1);
        Assert.assertEquals(false, db.personsWithCoursesDao().get("0").getFavStatus());

        Person newPerson = new Person("0","Han",
                "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-P" +
                        "zLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8" +
                        "LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4" +
                        "XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0", false, false, true);
        db.personsWithCoursesDao().update(newPerson);
        Assert.assertEquals(true,db.personsWithCoursesDao().get("0").getFavStatus());
    }
}
