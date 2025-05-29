import java.util.Arrays;

public class selectionSort {

    public static void main(String[] args) {
        int[] numList = {1, 9, 34, 99, 22, 56, 2, 4, 0, 33, 87, 65};
        selectionSort(numList);
        System.out.println("Sorted array: " + Arrays.toString(numList));
    }

    public static void selectionSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }
}
