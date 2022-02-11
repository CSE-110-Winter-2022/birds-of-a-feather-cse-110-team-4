package com.example.birdsoffeather;

import static org.junit.Assert.assertEquals;

import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(AndroidJUnit4.class)
public class AddClassActivityTestTwo {
    @Rule
    public ActivityScenarioRule rule =
            new ActivityScenarioRule<AddClassActivity>(AddClassActivity.class);

    @Test
    public void testInitialListSize() {
        ActivityScenario scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            RecyclerView courseList = (RecyclerView) activity.findViewById(R.id.classesRecyclerView);
            int size = courseList.getAdapter().getItemCount();
            assertEquals(0, size);
        });
    }

}
