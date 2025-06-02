import java.util.Arrays;

public class sort{
    public static void main(String[] args){
        int numList[] = {12,5,77,22,89,34,88,4,9,6,73,99};
        selSort(numList);

        System.out.println(Arrays.toString(numList));
    }
    public static void selSort(int arr[]){
        int arrLength = arr.length;
        int minIndex;

        for(int i=0; i < arrLength; i++){
            minIndex = i;
            for(int j = i +1;j<arrLength; j++){
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