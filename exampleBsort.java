import java.util.Arrays;

public class exampleBsort {
    public static void main(String[] args){
        int numList[] = {1, 9, 34, 99, 22, 56, 2, 4, 0, 33, 87, 65};
        bsort.bubbleSort(numList, 0);
        System.out.println(Arrays.toString(numList));

    }

    public void bubbleSort(int arr[]){

        int arrLength = arr.length;
        boolean swapped;

        for(int i=0; i < arrLength; i++){
            swapped = false;
            for(int j=0; j < arrLength - i -1; j++){
                if(arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    swapped = true;
                }
            }
        }

    }
}
