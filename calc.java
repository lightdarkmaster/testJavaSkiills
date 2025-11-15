import java.util.Scanner;

class calc{
    public static void main(String[] args) {
        System.out.println("This is a sample Calcualtor");

        double num1, num2, result, sum, difference, product, quotient;


        try{
            try (Scanner input = new Scanner(System.in)) {
                System.out.print("Enter Num1: ");
                num1 = input.nextDouble();

                System.out.print("Enter Num2: ");
                num2 = input.nextDouble();
            }
            sum = num1 + num2;
            difference = num1 - num2;
            product = num1 * num2;
            quotient = num1 / num2;

            result = sum + difference + product + quotient;// Not Necessary..

            System.out.println("Sum: " + sum);
            System.out.println("Difference: " + difference);
            System.out.println("Product: " + product);
            System.out.println("Quotient: " + quotient);

            System.out.print("Result: " + result);

            
        }catch(Exception e){
            System.out.println(e);
        }
    }
}