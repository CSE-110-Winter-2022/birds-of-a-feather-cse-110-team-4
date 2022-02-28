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
        String name = lines[0].split(",")[0];
        String url = lines[1].split(",")[0];
        student.add(name);
        student.add(url);
        for(int i = 2; i < lines.length; i++) {
            String[] c = lines[i].split(",");
            String newCourse = c[0]+" "+c[1]+" "+c[2]+" "+c[3]+" "+c[4];
            student.add(newCourse);
        }
        return student;
    }
}
