import java.util.Arrays;
public class bsorting {
    public static void main(String[] args){
        int numberList[] = {1,99,789,56,22,3,77,6,5};

        bubbleSort(numberList);

        String res = Arrays.toString(numberList);
        System.out.print(res);


    }
    public static void bubbleSort(int arr[]){
        int arrLength = arr.length;
        boolean swapped;

        for(int i=0; i < arrLength -1; i++){
            swapped = false;
            for(int j=0; j< arrLength -  i - 1; j++){
                if(arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    swapped = true;
                }
            }if(!swapped){
                break;
            }
        }
    }
}
