import java.util.Scanner;

public class fib {
    public static void main(String[] args){

        Scanner input = new Scanner(System.in);
        int firstTerm, secondTerm, length;

        System.out.println("Enter the first Term: ");
        firstTerm = input.nextInt();

        System.out.println("Enter second term: ");
        secondTerm = input.nextInt();

        System.out.println("Enter length: ");
        length = input.nextInt();

        for(int i=1; i<=length; i++){
            System.out.print(firstTerm + ", ");
            int nextTerm = firstTerm + secondTerm;
            firstTerm = secondTerm;
            secondTerm = nextTerm;
        }

    }
}
