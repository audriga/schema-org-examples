package com.audriga.sml.data;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SchemaOrgExamples {

    // Folders
    public static final String EMAIL_MICRODATA = "/resources/email/microdata/";

    // SchemaOrg type filters
    public static final String TYPE_LODGING = "lodgingReservation";
    
    
        
    public static String getFileContent(String path) throws Exception {
    
        InputStream is = SchemaOrgExamples.class.getResourceAsStream(path);
        String s = new String(is.readAllBytes());;
        return s;
    
    }
    
    public static List<String> getAllFiles(String resource) throws Exception {
    
        List<String> res = new ArrayList<String>();
    
        URI uri = SchemaOrgExamples.class.getResource(resource).toURI();
        Path myPath;
        if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
            myPath = fileSystem.getPath(resource);
        } else {
            myPath = Paths.get(uri);
        }
        Stream<Path> walk = Files.walk(myPath, 1);
        for (Iterator<Path> it = walk.iterator(); it.hasNext();){
            String file = it.next().toString();
            if (file.startsWith(resource)){
                res.add(file);
            }
        }
        
        return res;
    
    }

    public static Map<String, String> getFiles(String resource, String filter) throws Exception {
    
        Map<String, String> res = new HashMap<String, String>();
    
        List<String> files = getAllFiles(resource);
        
        for (String file : files){
            if (file.contains(filter)){
                res.put(file, getFileContent(file));
            }  
        }
        
        return res;
        
    }


    // Just for testing
    public static void main(String args[]) throws Exception {

        System.out.println(getFiles(EMAIL_MICRODATA, TYPE_LODGING).keySet());

    }

}
