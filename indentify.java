import java.util.Scanner;


public class indentify {
    public static void main(String[] args){
        int num1;
        Scanner input = new Scanner(System.in);

        System.out.print("Enter Number: ");
        num1 = input.nextInt();

        if(num1%2 == 0){
            System.out.println("num is an even numbers");
        }else{
            System.out.println("number is an odd numbers");
        }
    }
}
