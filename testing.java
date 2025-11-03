import java.util.Arrays;

class testing{
    public static void main(String[] args) {
        System.out.println("Hello World");

        int num[] = {12,34,23,44,6,3,6,2,66,123};

        sortThisShit(num);
        System.out.println(Arrays.toString(num));   
    }

    public static void sortThisShit(int arr[]){
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