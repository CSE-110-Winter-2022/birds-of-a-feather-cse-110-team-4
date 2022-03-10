package com.example.birdsoffeather.model;

import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    //Reads csv for a student, store info in returned list, pos 0: name, pos 1: url, pos 2-end: courses
    public static List<String> ReadCSV(String csv) {
        List<String> student = new ArrayList<>();
        if(csv.length() == 0) {
            return student;
        }
        String[] lines = csv.split("\n");
        String uuid = lines[0].split(",")[0];
        String name = lines[1].split(",")[0];
        String url = lines[2].split(",")[0];
        student.add(uuid);
        student.add(name);
        student.add(url);
        for(int i = 3; i < lines.length; i++) {
            String[] c = lines[i].split(",");
            if(c[1].equals("wave")) {
                student.add(c[0]);
                student.add(c[1]);
            } else {
                String newCourse = c[0]+" "+c[1]+" "+c[2]+" "+c[3]+" "+c[4];
                student.add(newCourse);
            }
        }
        return student;
    }
}