package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;

import java.io.*;
import java.util.*;

public class Converter {
     /*
        
        Consider the following CSV data, a portion of a database of episodes of
        the classic "Star Trek" television series:
        
        "ProdNum","Title","Season","Episode","Stardate","OriginalAirdate","RemasteredAirdate"
        "6149-02","Where No Man Has Gone Before","1","01","1312.4 - 1313.8","9/22/1966","1/20/2007"
        "6149-03","The Corbomite Maneuver","1","02","1512.2 - 1514.1","11/10/1966","12/9/2006"
        
        (For brevity, only the header row plus the first two episodes are shown
        in this sample.)
    
        The corresponding JSON data would be similar to the following; tabs and
        other whitespace have been added for clarity.  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings and which values should be encoded as integers, as
        well as the overall structure of the data:
        
    */
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String result = "{}"; // default return value; replace later!
        
        try {
        
            //Read the CSV data
            CSVReader reader = new CSVReader(new StringReader(csvString));
            
            //Read the first row as the headings
            String[] headings = reader.readNext();
            
            //Lists to stroe the data
            List<String> prodNums = new ArrayList<>();
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
                prodNums.add(row[0]);
                
                //Create a list for episode data
                List<Object> episode = new ArrayList<>();
                
                //Skip production zero and Convert seasons and episode into ints
                for(int i = 1; i < row.length; i++){
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
            JsonObject json = new JsonObject();
            
            json.put("Prodnums", prodNums);
            json.put("ColHeadings", colHeadings);
            json.put("Data", data);
            
            //Convert the JSON object to a string
            result = Jsoner.serialize(json);
            
            reader.close();
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
            
            JsonObject json = (JsonObject) Jsoner.deserialize(jsonString);
            
            //Get the different parts of the JSON object
            List<String> prodNums = (List<String>) json.get("ProdNums");
            List<String> colHeadings = (List<String>) json.get("ColHeadings");
            List<List<Object>> data = (List<List<Object>>) json.get("Data");
            
            //Create a list to hold all CSV rows
            List<String[]> csvData = new ArrayList<>();
            
            //Create the header row
            String[] headings = new String[colHeadings.size()];
            
            for(int i = 0; i < colHeadings.size(); i++){
                headings[i] = colHeadings.get(i);
            }
            
            csvData.add(headings);
            
            //Create each data row
            for(int i = 0; i < data.size(); i++){
                List<Object> episode = data.get(i);
                
                String[] row = new String[colHeadings.size()];
                
                //Production number goes in the first column
                row[0] = prodNums.get(i);
                
                //Add the remaining data
                for(int j = 0; j < episode.size(); j++){
                    row[j + 1] = episode.get(j).toString();
                }
                
                csvData.add(row);
            }
            
            //Use OpenCSV to create the CSV string
            StringWriter writer = new StringWriter();
            
            CSVWriter csvWriter = new CSVWriter(writer);
            
            csvWriter.writeAll(csvData);
            
            csvWriter.close();
            
            result = writer.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.trim();
        
    }
    
}
