import java.util.Scanner;
//odd or even
public class detect {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int number;
        String answer = "";

        do {
            System.out.println("Enter Number: ");
            number = sc.nextInt();
            sc.nextLine();

            if (number % 2 == 0) {
                System.out.println("number is even");
            } else {
                System.out.println("number is odd");
            }

            System.out.println("Do you want to continue? (yes/no)");
            answer = sc.nextLine().toLowerCase();

        } while (!answer.equalsIgnoreCase("no"));
    }
}
