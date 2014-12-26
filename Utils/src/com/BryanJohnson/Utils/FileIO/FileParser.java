package com.BryanJohnson.Utils.FileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileParser {

	public static Object castStringToPrimitive(String str) {		
		try {
			return Integer.valueOf(str);
		}
		catch (Exception ex) {}
		
		try {
			return Float.valueOf(str);
		}
		catch (Exception ex) {}
		
		return str;
	}
	
	private static int [] getColIndexes(String [] colNames, String [] selectedCols) {
		int [] indexes = new int[selectedCols.length];
		
		for (int i = 0; i < selectedCols.length; ++i) {
			for (int j = 0; j < colNames.length; ++j) {
				if (colNames[j].equalsIgnoreCase(selectedCols[i])) {
					indexes[i] = j;
					break;
				}
			}
			
		}
		
		return indexes;
	}
	
	@SuppressWarnings("unchecked")
	private static Object getColumnValues(String [] stringValues, int [] indexes) {
		Object values = null;
		if (indexes.length > 1) {
			values = new ArrayList<Object>();
			for (int idx : indexes) {
				((List<Object>)values).add(FileParser.castStringToPrimitive(stringValues[idx]));
			}
		}
		else {
			values = FileParser.castStringToPrimitive(stringValues[indexes[0]]);
		}
		
		return values;
	}
	
	public static HashMap<Object, Object> loadMap(String path, String [] keyColNames, String [] valColNames) {
		
		HashMap<Object, Object> map = new HashMap<Object, Object>(); 

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(path)));
		
			try {
				String header = br.readLine();
				
				String [] colNames = header.split("\t");
				int [] keyIndexes = FileParser.getColIndexes(colNames, keyColNames);
				int [] valIndexes = FileParser.getColIndexes(colNames, valColNames);
				
				String line = br.readLine();
				while (line != null) {
					String [] cols = line.split("\t");
					
					map.put(FileParser.getColumnValues(cols, keyIndexes),
							FileParser.getColumnValues(cols, valIndexes));
					
					line = br.readLine();
				}
						
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException("Could not find file: " + path);
		}
		
		return map;
	}
	
	public static List<Object> loadList(String path, String [] columns) {
		
		List<Object> list = new ArrayList<Object>(); 

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(path)));
		
			try {
				String header = br.readLine();
				
				String [] colNames = header.split("\t");
				int [] indexes = FileParser.getColIndexes(colNames, columns);
				
				String line = br.readLine();
				while (line != null) {
					String [] cols = line.split("\t");
					
					list.add(FileParser.getColumnValues(cols, indexes));
					
					line = br.readLine();
				}
						
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException("Could not find file: " + path);
		}
		
		return list;
	}

}
