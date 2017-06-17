package TPE3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {
	public Graph reader(String path)  {
    	Graph graph = new Graph();
    	String csvFile = path;
        String line = "";
        String csvSplitBy = ";";
        int numLine = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
            	if(numLine > 0){
                    String[] items = line.split(csvSplitBy);
                    graph.addUser(items[0].toString());
                    ArrayList<String> likes = new ArrayList<String>();
                    for(int i=1; i < items.length-1; i++){
                    	likes.add(items[i].toString());
                    }
                    graph.setRelations(items[0].toString(), likes);
            	}
            	numLine++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }
}
