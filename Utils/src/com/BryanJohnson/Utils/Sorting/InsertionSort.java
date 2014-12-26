package com.BryanJohnson.Utils.Sorting;

import java.util.List;

//See Numerical Recipes in C for analysis and implementation details

//Implements the in-place sorting algorithm InsertionSort.  Has runtime O(n^2), and thus should
//only be used for small arrays / lists (N < 20).  For larger lists, use QuickSort;
//Can operate on any comparable type, either in arrays or array lists.  Specific implementations
//for simple int or double arrays are provided for speed

//Returns a sorted array along with integer array describing reordering 
//(this can be used to sort another related array or list)
//Default order is ascending.  


public class InsertionSort extends SortSupport {

    /////////////////////////////////
    // Simple int array interface  //
    /////////////////////////////////
    public static int [] sort(int [] array) 
    {
        return sort(array, true);
    }    

    public static int [] sort(int [] array, boolean ascending) 
    {
        int [] sortIndex = new int[array.length];
        for (int i = 0; i < sortIndex.length; i++) {
            sortIndex[i] = i;
        }
        
        sort(array, sortIndex, 0, array.length-1, ascending);

        return sortIndex;
    }    
    
    ///////////////////////////////////
    // Simple double array interface //
    ///////////////////////////////////
    public static int [] sort(double [] array) 
    {
        return sort(array, true);
    }      
    
    public static int [] sort(double [] array, boolean ascending) 
    {
        int [] sortIndex = new int[array.length];
        for (int i = 0; i < sortIndex.length; i++) {
            sortIndex[i] = i;
        }
        
        return sort(array, sortIndex, 0, array.length-1, ascending);
    }    
    
    
    /////////////////////////////
    // Generic array interface //
    /////////////////////////////
    public static <T extends Comparable<T>> int [] sort(T [] array)
    {
        return sort(array, true);
    }
    
    public static <T extends Comparable<T>> int [] sort(T [] array, boolean ascending)
    {
        
        int [] sortIndex = new int[array.length];
        for (int i = 0; i < sortIndex.length; i++) {
            sortIndex[i] = i;
        }
        
        sort(array, sortIndex, 0, array.length-1, ascending);
        
        
        return sortIndex;
    }    
    
    
    /////////////////////////////
    // Generic list interface //
    /////////////////////////////
    public static <T extends Comparable<T>> int [] sort(List<T> list)
    {
        return sort(list, true);
    }
    
    public static <T extends Comparable<T>> int [] sort(List<T> list, boolean ascending)
    {
        
        int [] sortIndex = new int[list.size()];
        for (int i = 0; i < sortIndex.length; i++) {
            sortIndex[i] = i;
        }
        
        sort(list, sortIndex, 0, list.size()-1, ascending);
        
        return sortIndex;
    }      
    
    
    ///////////////////////////////////////////
    // Type specific sorting implementations //
    ///////////////////////////////////////////      
    
    public static <T extends Comparable<T>> int [] sort(T [] array, int [] sortIndex, int left, int right, boolean ascending)
    {
        int i, j;

        
        for (j = left; j <= right; j++) {
            T a = array[j];
            int k = sortIndex[j];
            for (i = j-1; i >= left; i--) {
                if  (array[i].compareTo(a) <= 0) {
                    break;
                }
                array[i+1] = array[i];
                sortIndex[i+1] = sortIndex[i];
            }
            array[i+1] = a;
            sortIndex[i+1] = k;
        }

        // flip array if descending
        if (!ascending) {
            for (i = 0; i < array.length / 2; i++) {
                swap(array, i, array.length - 1 - i);
                swap(sortIndex, i, array.length - 1 - i);
            }
        }
        
        return sortIndex;
    }
    
    
    public static <T extends Comparable<T>> void sort(List<T> list, int [] sortIndex, int left, int right, boolean ascending)
    {
        // TODO: See if there is any way to dump a generic list to an array for more efficient sorting    
        
        int i, j;

        for (j = left; j <= right; j++) {
            
            T a = list.get(j);
            int k = sortIndex[j];
            for (i = j-1; i >= left; i--) {
                if  (list.get(i).compareTo(a) <= 0) {
                    break;
                }
                list.set(i+1,list.get(i));
                sortIndex[i+1] = sortIndex[i];
            }
            list.set(i+1, a);
            sortIndex[i+1] = k;
        }
        
        // flip array if descending
        if (!ascending) {
            for (i = 0; i < list.size() / 2; i++) {
                swap(list, i, list.size() - 1 - i);
                swap(sortIndex, i, list.size() - 1 - i);
            }
        }
        
    }
    
    

    
    public static void sort(int [] array, int [] sortIndex, int left, int right, boolean ascending)
    {
        int i, j;

        
        for (j = left; j <= right; j++) {
            
            int a = array[j];
            int k = sortIndex[j];
            for (i = j-1; i >= left; i--) {
                if (array[i] <= a) {
                    break;
                }
                array[i+1] = array[i];
                sortIndex[i+1] = sortIndex[i];
            }
            array[i+1] = a;
            sortIndex[i+1] = k;
        }
        
        // flip array if descending
        if (!ascending) {
            for (i = 0; i < array.length / 2; i++) {
                swap(array, i, array.length - 1 - i);
                swap(sortIndex, i, array.length - 1 - i);
            }
        }
    }
    

    
    public static int [] sort(double [] array, int [] sortIndex, int left, int right, boolean ascending)
    {
        int i, j;

        
        for (j = left; j <= right; j++) {
            
            double a = array[j];
            int k = sortIndex[j];
            for (i = j-1; i >= left; i--) {
                if (array[i] <= a) {
                    break;
                }
                array[i+1] = array[i];
                sortIndex[i+1] = sortIndex[i];
            }
            array[i+1] = a;
            sortIndex[i+1] = k;
        }

        // flip array if descending
        if (!ascending) {
            for (i = 0; i < array.length / 2; i++) {
                swap(array, i, array.length - 1 - i);
                swap(sortIndex, i, array.length - 1 - i);
            }
        }
                
        return sortIndex;
    }    
}
