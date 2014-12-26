package com.BryanJohnson.Utils.Sorting;
import java.util.LinkedList;
import java.util.List;

//See Numerical Recipes in C for analysis and implementation details

// Implements the in-place sorting algorithm QuickSort.  Has average runtime O(n*log(n));
// Can operate on any comparable type, either in arrays or array lists.  Specific implementations
// for simple int or double arrays are provided for speed

// Returns a sorted array along with integer array describing reordering 
// (this can be used to sort another related array or list)
// Default order is ascending.  


public class QuickSort extends SortSupport {

    // once the partitions have gotten to this size, we will use insertion sort
    private static final int MIN_LENGTH = 10;
    
    
    /////////////////////////////////////
    // Simple integer array interfaces //
    /////////////////////////////////////
    
    public static int [] sort(int [] array)
    {
        return sort(array,true);
    }
    
    // sort given array in specified order
    public static int [] sort(int [] array, boolean ascending)
    {
        int [] sortIndex = new int [array.length];
        
        for (int i = 0; i < array.length; i++)
            sortIndex[i] = i;
        
        sort(array, sortIndex, ascending);
        
        return sortIndex;
    }    
    
    
    
    
    /////////////////////////////////////
    // Simple double array interfaces  //
    /////////////////////////////////////
    
    public static int [] sort(double [] array)
    {
        return sort(array,true);
    }
    
    // sort array in specified order
    public static int [] sort(double [] array, boolean ascending)
    {
        int [] sortIndex = new int [array.length];

        for (int i = 0; i < array.length; i++)
            sortIndex[i] = i;
        
        
        sort(array, sortIndex, ascending);
        
        return sortIndex;
    }    
    
    
    
    //////////////////////////////
    // Generic array interfaces //
    //////////////////////////////
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
        
        sort(array, sortIndex, ascending);
        
        return sortIndex;
    }
    
    
    
    
    //////////////////////////////
    // Generic list interfaces //
    //////////////////////////////
    public static <T extends Comparable<T>> int [] sort(List<T> list)
    {
        return sort(list, true);
    }
    
    public static <T extends Comparable<T>> int [] sort(List<T> list, boolean ascending)
    {
        int [] sortIndex = new int[list.size()];
        for (int i = 0; i < sortIndex.length; i++)
            sortIndex[i] = i;
        
        sort(list, sortIndex, ascending);

        return sortIndex;

    }
    
    
    
    
    ///////////////////////////////////////////
    // Type specific sorting implementations //
    ///////////////////////////////////////////    

    private static <T extends Comparable<T>> void sort(T [] array, int [] sortIndex, boolean ascending)
    {
        LinkedList<Integer> stack = new LinkedList<Integer>();
        
        int left = 0;
        int right = array.length - 1;
        
        while (true) {
            
            if (right - left < MIN_LENGTH) {
                InsertionSort.sort(array, sortIndex, left, right, true);
                if (stack.size() == 0) break;
                else {
                    right = stack.poll();
                    left  = stack.poll();
                }                    
            }            
            else {
                // take median of left, right, and middle elements, and place it in the 
                // left+1 element (see Numerical Recipes for details)
                int k = (left + right) >> 1;
                
                SortSupport.swap(array, k, left + 1);
                SortSupport.swap(sortIndex, k, left + 1);
                
                if (array[left].compareTo(array[right]) > 0) {
                    SortSupport.swap(array, left, right);
                    SortSupport.swap(sortIndex, left, right);
                }

                if (array[left+1].compareTo(array[right]) > 0) {
                    SortSupport.swap(array, left+1, right);
                    SortSupport.swap(sortIndex, left+1, right);
                }

                if (array[left].compareTo(array[left+1]) > 0) {
                    SortSupport.swap(array, left, left+1);
                    SortSupport.swap(sortIndex, left, left+1);
                }

                
                
                int i = left+1;
                int j = right;
                T pivotValue = array[left + 1];
                int tempSort = sortIndex[left + 1];
                
                while (true)
                {
                    do {i++;} while (array[i].compareTo(pivotValue) < 0); 
                    do {j--;} while (array[j].compareTo(pivotValue) > 0);
                    
                    if (j < i) break;
                    
                    SortSupport.swap(array, i, j);
                    SortSupport.swap(sortIndex, i, j);
        
                }
                
                array[left + 1] = array[j];
                array[j] = pivotValue;
                sortIndex[left+1] = sortIndex[j];
                sortIndex[j] = tempSort;
                
                if (right - i + 1 >= j - 1) {
                    stack.add(0,i);
                    stack.add(0,right);
                    right = j - 1;
                }
                else {
                    stack.add(0,left);
                    stack.add(0,j - 1);
                    left = i;
                }
            }   
        }
        
        // flip array if descending
        if (!ascending) {
            for (int i = 0; i < array.length / 2; i++) {
                SortSupport.swap(array, i, array.length - 1 - i);
                SortSupport.swap(sortIndex, i, array.length - 1 - i);
            }
        }
    }    
    
    
    private static <T extends Comparable<T>> void sort(List<T> list, int [] sortIndex, boolean ascending)
    {
        // TODO: See if there is any way to dump a generic list to an array for more efficient sorting    
        
        LinkedList<Integer> stack = new LinkedList<Integer>();
        
        int left = 0;
        int right = list.size() - 1;
        
        while (true) {
            
            if (right - left < MIN_LENGTH) {
                InsertionSort.sort(list, sortIndex, left, right, true);
                if (stack.size() == 0) break;
                else {
                    right = stack.poll();
                    left  = stack.poll();
                }                    
            }            
            else {
                // take median of left, right, and middle elements, and place it in the 
                // left+1 element (see Numerical Recipes for details)
                int k = (left + right) >> 1;
 
                SortSupport.swap(list, k, left + 1);
                SortSupport.swap(sortIndex, k, left + 1);

                if (list.get(left).compareTo(list.get(right)) > 0) {
                    SortSupport.swap(list, left, right);
                    SortSupport.swap(sortIndex, left, right);
                }

                if (list.get(left+1).compareTo(list.get(right)) > 0) {
                    SortSupport.swap(list, left+1, right);
                    SortSupport.swap(sortIndex, left+1, right);
                }

                if (list.get(left).compareTo(list.get(left+1)) > 0) {
                    SortSupport.swap(list, left, left+1);
                    SortSupport.swap(sortIndex, left, left+1);
                }                
                
                int i = left+1;
                int j = right;
                T pivotValue = list.get(left + 1);
                int tempSort = sortIndex[left + 1];
                
                while (true)
                {
                    do {i++;} while (list.get(i).compareTo(pivotValue) < 0); 
                    do {j--;} while (list.get(j).compareTo(pivotValue) > 0);
                    
                    if (j < i) break;
                    
                    SortSupport.swap(list, i, j);
                    SortSupport.swap(sortIndex, i, j);
        
                }
                
                list.set(left + 1, list.get(j));
                list.set(j, pivotValue);
                sortIndex[left+1] = sortIndex[j];
                sortIndex[j] = tempSort;
                
                if (right - i + 1 >= j - 1) {
                    stack.add(0,i);
                    stack.add(0,right);
                    right = j - 1;
                }
                else {
                    stack.add(0,left);
                    stack.add(0,j - 1);
                    left = i;
                }
            }   
        }
        
        // flip array if descending
        if (!ascending) {
            for (int i = 0; i < list.size() / 2; i++) {
                SortSupport.swap(list, i, list.size() - 1 - i);
                SortSupport.swap(sortIndex, i, list.size() - 1 - i);
            }
        }
        
    }
    
    private static void sort(double [] array, int [] sortIndex, boolean ascending)
    {
        LinkedList<Integer> stack = new LinkedList<Integer>();
        
        int left = 0;
        int right = array.length - 1;
        
        while (true) {
            
            if (right - left < MIN_LENGTH) {
                InsertionSort.sort(array, sortIndex, left, right, true);
                if (stack.size() == 0) break;
                else {
                    right = stack.poll();
                    left  = stack.poll();
                }                    
            }            
            else {
                // take median of left, right, and middle elements, and place it in the 
                // left+1 element (see Numerical Recipes for details)
                int k = (left + right) >> 1;
                
                SortSupport.swap(array, k, left + 1);
                SortSupport.swap(sortIndex, k, left + 1);
                
                if (array[left] > array[right]) {
                    SortSupport.swap(array, left, right);
                    SortSupport.swap(sortIndex, left, right);
                }

                if (array[left+1] > array[right]) {
                    SortSupport.swap(array, left+1, right);
                    SortSupport.swap(sortIndex, left+1, right);
                }

                if (array[left] > array[left+1]) {
                    SortSupport.swap(array, left, left+1);
                    SortSupport.swap(sortIndex, left, left+1);
                }
                
                
                int i = left+1;
                int j = right;
                double pivotValue = array[left + 1];
                int tempSort = sortIndex[left + 1];
                
                while (true)
                {
                    do {i++;} while (array[i] < pivotValue); 
                    do {j--;} while (array[j] > pivotValue);
                    
                    if (j < i) break;
                    
                    SortSupport.swap(array, i, j);
                    SortSupport.swap(sortIndex, i, j);
        
                }
                
                array[left + 1] = array[j];
                array[j] = pivotValue;
                sortIndex[left+1] = sortIndex[j];
                sortIndex[j] = tempSort;
                
                if (right - i + 1 >= j - 1) {
                    stack.add(0,i);
                    stack.add(0,right);
                    right = j - 1;
                }
                else {
                    stack.add(0,left);
                    stack.add(0,j - 1);
                    left = i;
                }
            }   
        }
        
        // flip array if descending
        if (!ascending) {
            for (int i = 0; i < array.length / 2; i++) {
                SortSupport.swap(array, i, array.length - 1 - i);
                SortSupport.swap(sortIndex, i, array.length - 1 - i);
            }
        }
    }
    
    
    
    
    private static void sort(int [] array, int [] sortIndex, boolean ascending)
    {
        LinkedList<Integer> stack = new LinkedList<Integer>();
        
        int left = 0;
        int right = array.length - 1;
        
        while (true) {
            
            if (right - left < MIN_LENGTH) {
                InsertionSort.sort(array, sortIndex, left, right, true);
                if (stack.size() == 0) break;
                else {
                    right = stack.poll();
                    left  = stack.poll();
                }                    
            }            
            else {
                // take median of left, right, and middle elements, and place it in the 
                // left+1 element (see Numerical Recipes for details)
                int k = (left + right) >> 1;
                
                SortSupport.swap(array, k, left + 1);
                SortSupport.swap(sortIndex, k, left + 1);
                
                if (array[left] > array[right]) {
                    SortSupport.swap(array, left, right);
                    SortSupport.swap(sortIndex, left, right);
                }

                if (array[left+1] > array[right]) {
                    SortSupport.swap(array, left+1, right);
                    SortSupport.swap(sortIndex, left+1, right);
                }

                if (array[left] > array[left+1]) {
                    SortSupport.swap(array, left, left+1);
                    SortSupport.swap(sortIndex, left, left+1);
                }

                
                
                int i = left+1;
                int j = right;
                int pivotValue = array[left + 1];
                int tempSort = sortIndex[left + 1];
                
                while (true)
                {
                    do {i++;} while (array[i] < pivotValue); 
                    do {j--;} while (array[j] > pivotValue);
                    
                    if (j < i) break;
                    
                    SortSupport.swap(array, i, j);
                    SortSupport.swap(sortIndex, i, j);
        
                }
                
                array[left + 1] = array[j];
                array[j] = pivotValue;
                sortIndex[left+1] = sortIndex[j];
                sortIndex[j] = tempSort;
                
                if (right - i + 1 >= j - 1) {
                    stack.add(0,i);
                    stack.add(0,right);
                    right = j - 1;
                }
                else {
                    stack.add(0,left);
                    stack.add(0,j - 1);
                    left = i;
                }
            }   
        }
        
        // flip array if descending
        if (!ascending) {
            for (int i = 0; i < array.length / 2; i++) {
                SortSupport.swap(array, i, array.length - 1 - i);
                SortSupport.swap(sortIndex, i, array.length - 1 - i);
            }
        }
    }
    
}
