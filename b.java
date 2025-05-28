import java.util.Arrays;

public class b {

    public static void main(String[] args){
        int numList[] = {1, 9, 34, 99, 22, 56, 2, 4, 0, 33, 87, 65};
        sort(numList);
        System.out.println(Arrays.toString(numList));
    }

    public static void sort(int arr[]){
        int arrayLength = arr.length;
        boolean swapped;

        for (int i = 0; i < arrayLength - 1; i++) {
            swapped = false;

            for (int j = 0; j < arrayLength - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }
}
