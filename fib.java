import java.util.Scanner;

public class fib{
    public static void main(String[] args){
        System.out.println("Fib Seq");

        int num1 = 0;
        int num2 = 1;
        int nextNumber;
        int length;

        Scanner input = new Scanner(System.in);

        System.out.println("Enter the desire Length: ");
        length = input.nextInt();

        for(int i=0; i < length; i++){
            System.out.print(num1 + ", ");
            nextNumber = num1 + num2;
            num1 = num2;
            num2 = nextNumber;
        }
    }
}