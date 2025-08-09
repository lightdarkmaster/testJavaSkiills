import java.util.Scanner;

/**
 * This program repeatedly asks the user to enter a number,
 * determines whether the number is odd or even,
 * and continues until the user chooses to stop.
 *
 * Author: [Your Name]
 * Date: [Date]
 */
public class detect {
    public static void main(String[] args) {
        // Create a Scanner object to read user input
        Scanner sc = new Scanner(System.in);

        int number;          // Variable to store the number entered by the user
        String answer = "";  // Variable to store the user's response to continue or stop

        // Repeat until the user answers "no"
        do {
            // Prompt the user to enter a number
            System.out.println("Enter Number: ");
            number = sc.nextInt(); // Read an integer from the user
            sc.nextLine();         // Consume the leftover newline character

            // Check if the number is even or odd using the modulus operator (%)
            if (number % 2 == 0) {
                System.out.println("Number is even");
            } else {
                System.out.println("Number is odd");
            }

            // Ask if the user wants to continue
            System.out.println("Do you want to continue? (yes/no)");
            answer = sc.nextLine().toLowerCase(); // Convert input to lowercase for consistency

        } while (!answer.equalsIgnoreCase("no")); // Loop until user types "no"

        // Close the scanner to prevent resource leaks
        sc.close();
    }
}
