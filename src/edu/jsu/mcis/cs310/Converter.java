package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;

import java.io.*;
import java.util.*;

public class Converter {
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String result = "{}"; // default return value; replace later!
        
        try {
        
            //Read the CSV data
            CSVReader reader = new CSVReader(new StringReader(csvString));
            
            //Read the first row as the headings
            String[] headings = reader.readNext();
            
            //Lists to stroe the data
            List<String> proNums = new ArrayList<>();
            List<String> colHeadings = new ArrayList<>();
            List<List<Object>> data = new ArrayList<>();
            
            //Add the column headings to the list
            for (String heading : headings){
                colHeadings.add(heading);
            }
            
            //Read each remaining CSV row
            String[] row;
            
            while((row = reader.readNext()) != null){
                //First column is production number
                prodNum.add(row[0]);
                
                //Create a list for episode data
                List<Object> episode = new ArrayList<>();
                
                //Skip production zero and Convert seasons and episode into ints
                for(i = 1; i < row.length; i++){
                    if(i == 2 || i == 3){
                        episode.add(Integer.parseInt(row[i]));
                    }
                    
                    else{
                        episode.add(row[i]);
                    }
                }
                
                data.add(episode);
            }
            
            //Create a Json object
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.trim();
        
    }
    
    @SuppressWarnings("unchecked")
    public static String jsonToCsv(String jsonString) {
        
        String result = ""; // default return value; replace later!
        
        try {
            
            // INSERT YOUR CODE HERE
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.trim();
        
    }
    
}
