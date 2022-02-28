package com.example.birdsoffeather;

import static com.example.birdsoffeather.model.CSVReader.ReadCSV;
import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class CSVReaderTest {

    @Test
    public void testCSVReader() {
        List<String> expected = new ArrayList<>();
        expected.add("Bill");
        expected.add("https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0");
        expected.add("2022 WI CSE 110 Tiny (1-40)");
        String test = "Bill,,,\n" +
                "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,\n" +
                "2022,WI,CSE,110,Tiny (1-40)\n";
        assertEquals(expected, ReadCSV(test));
    }

    @Test
    public void testCSVReaderEmpty() {
        List<String> expected = new ArrayList<>();
        String test = "";
        assertEquals(expected, ReadCSV(test));
    }

    @Test
    public void testCSVReaderMultipleClasses() {
        List<String> expected = new ArrayList<>();
        expected.add("Bill");
        expected.add("https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0");
        expected.add("2022 WI CSE 110 Tiny (1-40)");
        expected.add("2021 WI CSE 110 Tiny (1-40)");
        String test = "Bill,,,\n" +
                "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,\n" +
                "2022,WI,CSE,110,Tiny (1-40)\n"
                + "2021,WI,CSE,110,Tiny (1-40)";
        assertEquals(expected, ReadCSV(test));
    }
}
