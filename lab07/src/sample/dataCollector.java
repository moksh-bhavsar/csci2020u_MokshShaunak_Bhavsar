package sample;
import java.io.*;
import java.util.*;

public class dataCollector {

    public static Map<String,Integer> getData() throws IOException {
        // creating new file stream
        File inFile = new File("F:/Software_Sys_Dev_Int/csci2020u_MokshShaunak_Bhavsar/lab07/src/sample/weatherwarnings-2015.csv");

        Map<String, Integer> frequency = new TreeMap<>();

        if (inFile.exists()){
            BufferedReader input = new BufferedReader(new FileReader(inFile));
            String line = null;
            while((line = input.readLine()) != null){
                String data = line.split(",")[5];       // splitting line in array of string with "," as delimiter and using 6th value

                // checking if the map contains the word already or not
                if (frequency.containsKey(data)){
                    // update the value of the key, if already contained
                    int freq = frequency.get(data);
                    frequency.put(data, freq+1);
                }else{
                    // put the (key,value) pair in the map
                    frequency.put(data,1);
                }
            }
        }
        return frequency;
    }
}
