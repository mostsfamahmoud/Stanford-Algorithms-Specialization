package Week2;

import java.io.*;

public class CountInversions {

    public static void main(String[] args) throws IOException {

        FileHandler fileHandler = new FileHandler();

        // Reading the contents of the file
        String file = fileHandler.ReadFile("IntegerArray.txt");

        String[] lines = file.split("\n");

        // Storing the integers into an array
        int[] arr = new int[lines.length];
        for (int i = 0; i < lines.length; i++) {
            arr[i] = Integer.parseInt(lines[i]);
        }

        System.out.println(sortAndCount(arr, 0, lines.length - 1));

    }

    public static long sortAndCount(int[] arr, int start, int end) {
        if (start == end)
            return 0;

        int mid = (start + end) / 2;

        long leftCount = sortAndCount(arr, start, mid);
        long rightCount = sortAndCount(arr, mid + 1, end);

        long splitCount = mergeAndCount(arr, start, mid, end);

        return leftCount + rightCount + splitCount;
    }

    private static long mergeAndCount(int[] arr, int low, int mid, int high) {
        if (low >= high)
            return 0;

        int i = low, j = mid + 1, k = 0;

        long count = 0;

        int size = (high - low) + 1;

        int[] mergedArr = new int[size];

        while (i <= mid && j <= high) {

            if (arr[i] < arr[j])    // No Inversion
                mergedArr[k++] = arr[i++];
            else                    // Inversion
            {
                mergedArr[k++] = arr[j++];
                count += (mid - i) + 1;    // Counting the inversions from index i to the end of the first half
            }
        }

        // Copying the remaining of the two halves
        while (i <= mid) {
            mergedArr[k++] = arr[i++];
        }

        while (j <= high) {
            mergedArr[k++] = arr[j++];
        }

        // Copying the contents of the auxiliary array back into the original one
        for (i = 0; i < size; i++) {
            arr[low + i] = mergedArr[i];
        }

        return count;
    }
}


