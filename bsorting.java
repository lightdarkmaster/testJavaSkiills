import java.util.Arrays;

class bsorting{

    public static void main(String[] args){
        int numlist[] = {1,66,323,88,2,87,22,456,7,3,9,23};

        bsort(numlist);
        System.out.println(Arrays.toString(numlist));
    }

    public static void bsort(int arr[]){
        int length = arr.length;
        boolean swapped;

        for(int i=0; i < length; i++){
            swapped = false;
            for(int j=0; j < length - i - 1; j++){
                if(arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    swapped = true;
                }
            }
            if(!swapped){
                break;
            }
        }
    }
}