package sample;
import java.io.*;
import java.util.*;

public class dataCollector {

    public static Map<String,Integer> getData() throws IOException {
        File inFile = new File("F:/Software_Sys_Dev_Int/csci2020u_MokshShaunak_Bhavsar/lab07/src/sample/weatherwarnings-2015.csv");
        Map<String, Integer> frequency = new TreeMap<>();
        if (inFile.exists()){
            BufferedReader input = new BufferedReader(new FileReader(inFile));
            String line = null;
            while((line = input.readLine()) != null){
                String data = line.split(",")[5];
                if (frequency.containsKey(line.split(",")[5])){
                    int freq = frequency.get(line.split(",")[5]);
                    frequency.put(line.split(",")[5], freq+1);
                }else{
                    frequency.put(line.split(",")[5],1);
                }
            }
        }
        return frequency;
    }
}
