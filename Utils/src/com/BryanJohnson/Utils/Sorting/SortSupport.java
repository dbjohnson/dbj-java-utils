package com.BryanJohnson.Utils.Sorting;

import java.util.ArrayList;
import java.util.List;

// Basic support functions common to all sorting routines

public class SortSupport {
    public static <T extends Comparable<T>> void swap (T [] array, int i1, int i2) {
        T temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
    }
    
    public static <T extends Comparable<T>> void swap (List<T> list, int i1, int i2) {
        T temp = list.get(i1);
        list.set(i1, list.get(i2));
        list.set(i2, temp);
    }    

    public static void swap (int [] array, int i1, int i2) {
        int temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
    }

    public static void swap (double [] array, int i1, int i2) {
        double temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
    }
    
    ////////////////////////////////
    //  AUXILLIARY SORT FUNCTIONS // 
    ////////////////////////////////
    
    // Sort any array by a given sort order
    public static void sortByIndex(double array[], int [] sortIndex)
    {
        double [] clone = array.clone();
        for (int i = 0; i < array.length; i++)
        {
            array[i] = clone[sortIndex[i]];
        }
        
    }
    
    // Sort any array by a given sort order
    public static void sortByIndex(int array[], int [] sortIndex)
    {
        int [] clone = array.clone();
        for (int i = 0; i < array.length; i++)
        {
            array[i] = clone[sortIndex[i]];
        }
    }

    // Sort any array by a given sort order
    public static void sortByIndex(Object array[], int [] sortIndex)
    {
        Object [] clone = array.clone();
        for (int i = 0; i < array.length; i++)
        {
            array[i] = clone[sortIndex[i]];
        }
    }
    
    // Sort any array by a given sort order
    public static<T extends Comparable<T>> void sortByIndex(List<T> list, int [] sortIndex)
    {
        List<T> clone = new ArrayList<T>();
        clone.addAll(list);
        for (int i = 0; i < list.size(); i++)
        {
            list.set(i,clone.get(sortIndex[i]));
        }
    }
    
    public static int [] getRandomizedSortOder(int n) 
    {
        double [] randVec = new double [n];
        
        for (int i = 0; i < n; i++) {
            randVec[i] = Math.random();
        }
        
        return QuickSort.sort(randVec);
    }
    
    public static<T extends Comparable<T>> void randomizeOrder(List<T> list) {
        sortByIndex(list, getRandomizedSortOder(list.size()));
    }
    
    public static void randomizeOrder(Object array[]) {
        sortByIndex(array, getRandomizedSortOder(array.length));
    }
    
    public static void randomizeOrder(int array[]) {
        sortByIndex(array, getRandomizedSortOder(array.length));
    }

}
