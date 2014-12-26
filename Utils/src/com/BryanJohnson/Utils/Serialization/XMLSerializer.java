package com.BryanJohnson.Utils.Serialization;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;

public class XMLSerializer {
    private static XStream xStream = new XStream();
    
    public static <T> void save(T obj, String fileName)  
    {
        
        try {
            FileOutputStream os = new FileOutputStream(fileName);
            xStream.toXML(obj, os);
            os.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            throw new RuntimeException("File not found: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unhandled IO exception");
        }

    }
    

    @SuppressWarnings("unchecked")
    public static <T> T loadFromFile(String fileName)  {
        T obj = null;
        try {
            FileInputStream is = new FileInputStream(fileName); 
            obj = (T)(xStream.fromXML(is));
            is.close();
        }
        catch (IOException e) { 
        }
        catch (Exception ex) {
        }
        
        return obj;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T fromByteArray(byte [] array)  {
        T obj = null;
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(array);
            obj = (T)(xStream.fromXML(is));
            is.close();
        }
        catch (IOException e) { 
        }
        catch (Exception ex) {
        }
        
        return obj;
    }

    
    public static String toXML(Object obj)  
    {
        return xStream.toXML(obj); 
    }

}
