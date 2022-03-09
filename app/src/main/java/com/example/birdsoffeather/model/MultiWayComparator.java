package com.example.birdsoffeather.model;

import com.example.birdsoffeather.model.db.PersonWithCourses;

import java.util.Comparator;

public class MultiWayComparator implements Comparator {

    private String option;
    public MultiWayComparator(String option) {
        this.option = option;
    }

    //Helper function for comparator
    private String[] courseDetails(String course) {
        String[] splitStr = course.split(" ");
        return splitStr;
    }

    //Helper function for comparator
    public float recentPriorityScore(PersonWithCourses person) {
        float score = 0;
        for (int i =0; i < person.getCourses().size(); i++) {
            String course = person.getCourses().get(i);
            String[] courseDetails = courseDetails(course);

            // Grab Relevant Class Details
            int year = Integer.parseInt(courseDetails[0]);
            int quarterOffset = 0;
            if (courseDetails[1].equals("FA")) {
                quarterOffset = -3;
            } else if (courseDetails[1].equals("SS1") || courseDetails[1].equals("SS2") || courseDetails[1].equals("SSS")) {
                quarterOffset = -2;
            } else if (courseDetails[1].equals("SP")) {
                quarterOffset = -1;
            } else if (courseDetails[1].equals("WI")) {
                quarterOffset = 0;
            }

            // Assuming this is for WI 2022
            int yearDif = 2022 - year;
            int age = yearDif * 4 + quarterOffset;

            if (age == 0) {
                score = 5;
            } else if (age == 1) {
                score = 4;
            } else if (age == 2) {
                score = 3;
            } else if (age == 3) {
                score = 2;
            } else if (age >= 4) {
                score = 1;
            }
        }
        return score;
    }

    //Helper function for comparator
    public float sizePriorityScore(PersonWithCourses person) {
        float score = 0;
        for (int i =0; i < person.getCourses().size(); i++) {
            String course = person.getCourses().get(i);
            String[] courseDetails = courseDetails(course);

            // Grab Relevant Class Details
            String size = courseDetails[4];

            // Calculate weight
            if (size.equals("Tiny")) {
                score += 1;
            } else if (size.equals("Small")) {
                score += .33;
            } else if (size.equals("Medium")) {
                score += .18;
            } else if (size.equals("Large")) {
                score += .1;
            } else if (size.equals("Huge")) {
                size += .06;
            } else if (size.equals("Gigantic")) {
                score += .03;
            }
        }

        return score;
    }

    @Override
    public int compare(Object p1, Object p2) {
        //If waving from that student, prioritize
        if( ((PersonWithCourses)p1).getWaveFrom() )
            return -1;
        else if( ((PersonWithCourses)p2).getWaveFrom() )
            return 1;
        if (option.equals("Prioritize Recent")) {
            return sizePriorityScore((PersonWithCourses) p1) < sizePriorityScore((PersonWithCourses) p2) ? -1 : 1;
        } else if (option.equals("Prioritize Small Classes")) {
            return recentPriorityScore((PersonWithCourses)p1) < recentPriorityScore((PersonWithCourses)p2) ? -1 : 1;
        }
        //Default Option
        return (((PersonWithCourses)p2).getCourses().size() - ((PersonWithCourses)p1).getCourses().size());
    }


}
