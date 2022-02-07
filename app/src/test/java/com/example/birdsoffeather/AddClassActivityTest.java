package com.example.birdsoffeather;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AddClassActivityTest {
    @Rule
    public ActivityScenarioRule rule =
            new ActivityScenarioRule<AddClassActivity>(AddClassActivity.class);

    @Test
    public void test1() {
        ActivityScenario scenario = rule.getScenario();
        scenario.onActivity(activity -> {

        });
    }
}
