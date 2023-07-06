package Week3;

import java.io.*;

import static java.lang.Math.*;

public class QuickSort_Assignment {

    public static long QuickSort(int[] arr, int low, int high)
    {
        if (low >= high)
            return 0;

        /*
        * Can be anyone of the three
        * int pos = partition_PivotLast(arr,low,high);
        * int pos = partition_PivotFirst(arr,low,high);
        */
        int pos = partition_PivotMedian(arr,low,high);

        long leftCount = QuickSort(arr,low,pos - 1);
        long rightCount = QuickSort(arr,pos + 1,high);

        long compareCount = high - low;

        return leftCount + rightCount + compareCount;
    }

    private static int partition_PivotFirst(int[] arr, int left, int right)
    {
        int pivot = arr[left];
        int i = left + 1;      // I is the index before which all values are <= pivot

        // J is the index before which all values are > pivot
        // and after which the values that are not partitioned yet
        for(int j = left + 1; j <= right; j++)
        {
            if (arr[j] < pivot)
            {
                swap(arr,i,j);
                i++;
            }
        }

        swap(arr,left,(i - 1));

        return (i - 1); /* returning the partitioning position */
    }

    /*
    * Same as partition_PivotFirst
    * But here pivot is assigned as the last element
    * And the first and last element of the subarray are swapped
    */
    private static int partition_PivotLast(int[] arr, int left, int right)
    {
        int pivot = arr[right];
        swap(arr,left,right);   /* The Crucial swap */
        int i = left + 1;      // I is the index before which all values are <= pivot

        // J is the index before which all values are > pivot
        // and after which the values that are not partitioned yet
        for(int j = left + 1; j <= right; j++)
        {
            if (arr[j] < pivot)
            {
                swap(arr,i,j);
                i++;
            }
        }

        swap(arr,left,(i - 1));

        return (i - 1); /* returning the partitioning position */
    }

    /*
     * Same as partition_PivotFirst
     * But here pivot is assigned as the Median element
     * And the first and median element of the subarray are swapped
     */
    private static int partition_PivotMedian(int[] arr, int left, int right)
    {
        int medianPos = findMedian(arr,left,right);
        int pivot = arr[medianPos];
        swap(arr,left,medianPos);   /* The Crucial swap */
        int i = left + 1;      // I is the index before which all values are <= pivot

        // J is the index before which all values are > pivot
        // and after which the values that are not partitioned yet
        for(int j = left + 1; j <= right; j++)
        {
            if (arr[j] < pivot)
            {
                swap(arr,i,j);
                i++;
            }
        }

        swap(arr,left,(i - 1));

        return (i - 1); /* returning the partitioning position */
    }

    private static int findMedian(int[] arr, int low, int high)
    {
        int a = arr[low];
        int b = arr[high];

        int middle = (low + high)/2;
        int c = arr[middle];

        int minVal = min(min(a,b),c);

        int maxVal = max(max(a,b),c);

        // return the 1st index if its element is between the min & max values
        if (a != maxVal && a != minVal)
            return low;

        // return the (n-1)th index if its element is between the min & max values
        if (b != maxVal && b != minVal)
            return high;

        // return the middle index if the two conditions above fails
        return middle;
    }

    private static void swap(int[] arr,int index1,int index2)
    {
        int len = arr.length;
        if (index1 < len && index2 < len)
        {
            int temp = arr[index1];
            arr[index1] = arr[index2];
            arr[index2] = temp;
        }
    }

    private static void displayArray(int[] arr)
    {
        for (int element: arr)
        {
            System.out.print(element + " ");
        }
    }

    public static void SortAndDisplay(int[] arr)
    {
        System.out.print("Before Sorting: ");
        displayArray(arr);

        QuickSort(arr,0,arr.length - 1);

        System.out.print("\nAfter Sorting: ");
        displayArray(arr);

        System.out.println("\n");
    }

    public static void main(String[] args) throws IOException {

        FileHandler fileHandler = new FileHandler();

        // Reading the contents of the file
        String file = fileHandler.ReadFile("QuickSort.txt");

        String[] lines = file.split("\n");

        // Storing the integers into an array
        int[] arr = new int[lines.length];
        for (int i = 0; i < lines.length; i++) {
            arr[i] = Integer.parseInt(lines[i]);
        }

        //SortAndDisplay(arr);
        System.out.println("\nTotal comparisons = " + QuickSort(arr,0,arr.length - 1));
    }
}

//First -> 162085
//Second -> 164123
//Third -> 138382