package com.example.birdsoffeather;

import static org.junit.Assert.assertEquals;

import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.CoursesDao;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AddClassActivityTest {

    @Rule
    public ActivityScenarioRule rule =
            new ActivityScenarioRule<AddClassActivity>(AddClassActivity.class);

    @Test
    public void testPageTextUI() {
        ActivityScenario scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            TextView title = (TextView)  activity.findViewById(R.id.textView2);
            assertEquals("Add Classes", title.getText());
        });
    }

//    @Test
//    public void testInitialState() {
//        ActivityScenario scenario = rule.getScenario();
//        scenario.onActivity(activity -> {
//            EditText editSubject = (EditText)  activity.findViewById(R.id.editSubject);
//            assertEquals("", editSubject.getText().toString());
//            EditText editCourse = (EditText)  activity.findViewById(R.id.editCourse);
//            assertEquals("", editCourse.getText().toString());
//        });
//    }
}
