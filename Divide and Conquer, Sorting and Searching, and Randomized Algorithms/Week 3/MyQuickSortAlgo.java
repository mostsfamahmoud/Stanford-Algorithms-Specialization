package Week3;

public class MyQuickSortAlgo {

    public static void main(String[] args) {

        int[] arr = {3,8,2,5,1,4,7,6};
        SortAndDisplay(arr);

        int[] arr1 = {5, 2, 9, 1, 7};
        SortAndDisplay(arr1);

        int[] arr2 = {1, 2, 3, 4, 5};
        SortAndDisplay(arr2);

        int[] arr3 = {5, 4, 3, 2, 1};
        SortAndDisplay(arr3);
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

    private static void displayArray(int[] arr)
    {
        for (int element: arr)
        {
            System.out.print(element + " ");
        }
    }

    public static void QuickSort(int[] arr, int low, int high)
    {
        if (low < high)
        {
            int pos = partition(arr,low,high);

            QuickSort(arr,low,pos - 1);
            QuickSort(arr,pos + 1,high);
        }
    }


    private static int partition(int[] arr, int left, int right)
    {
        int pivot = arr[left];   /* Select first element of the array as Pivot */
        int i = left;        /* Index pointer points to the start of the array */
        int j = right;           /* Index pointer points to the end of the array */

        while(i <= j)
        {
            do {
                i++;
                /* To avoid index out of bound exception as (i) can exceed the upper bound of the array
                   For example the input array = {8,6,7} */
                if (i > right)
                    break;

            }while(arr[i] <= pivot);     /* To make index pointer (i) stop at index that its element is Greater than pivot */

            /* To make index pointer (j) stop at index that its element is Smaller than or Equal pivot */
            while(arr[j] > pivot)
                j--;

            /* After i,j Stop at the required indexes , then swap their elements */
            if (i < j)
                swap(arr, i , j);
        }

        /* As we jumped out of that while loop then i became greater than or equal j
         * So Swap our pivot with element at index j as that element we know for sure that it's smaller that pivot
         * So swap it to the small elements side
         */
        swap(arr,left,j);

        /* returning the partitioning position */
        return j;
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
}