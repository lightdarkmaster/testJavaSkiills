import java.util.Scanner;
import java.util.Arrays;

 class sel {
    public static void main(String[] args){
        int num[] = {1,88,66,44,33,22,45,67,88,1,4,9,2,9};
        selectionSort(num);

        System.out.println("Res: " + Arrays.toString(num));
    }

    public static void selectionSort(int arr[]){
        int arrLength = arr.length;

        for(int i =0; i < arrLength - 1; i++){
            int minIndex = i;
            for(int j = i + 1; j < arrLength; j++){
                if(arr[j] < arr[minIndex]){
                    minIndex = j;
                }
            }

            if(minIndex != i){
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }
}
