import java.util.Scanner;


public class rec{
    public static void main(String[] args){
        //variables declarations..
        int num1 = 0;
        int num2 = 1;
        int nextNumber;
        int length;

        //Scanner input accepts length of the fib sequence..
        Scanner input = new Scanner(System.in);
            System.out.println("Enter Length: ");
            length = input.nextInt();

            //looping until length achieved
            for(int i = 1; i <=length; i++){
                System.out.print(num1 + " ");
                nextNumber = num1 + num2;
                num1 = num2;
                num2 = nextNumber;
        }
    }
}