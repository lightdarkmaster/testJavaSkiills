import java.util.Scanner;

class calc{
    public static void main(String[] args) {
        System.out.println("This is a sample Calculator");

        double num1, num2, result, sum, difference, product, quotient;


        try{
            try (Scanner input = new Scanner(System.in)) {
                System.out.print("Enter Num1: ");
                num1 = input.nextDouble();

                System.out.print("Enter Num2: ");
                num2 = input.nextDouble();

                sum = calculateSum(num1, num2);
                difference = calculateDifference(num1, num2);
                product = calculateProduct(num1, num2);
                quotient = calculateQuotient(num1, num2);

                result = calculateResult(sum, difference, product, quotient);

                System.out.println("Sum: " + sum);
                System.out.println("Difference: " + difference);
                System.out.println("Product: " + product);
                System.out.println("Quotient: " + quotient);

                System.out.print("Result: " + result);
            }

        }catch(Exception e){
            System.out.println(e);
        }
    }

    private static double calculateSum(double num1, double num2){
        return num1 + num2;
    }

    private static double calculateDifference(double num1, double num2){
        return num1 - num2;
    }

    private static double calculateProduct(double num1, double num2){
        return num1 * num2;
    }

    private static double calculateQuotient(double num1, double num2){
        return num1 / num2;
    }

    private static double calculateResult(double sum, double difference, double product, double quotient){
        return sum + difference + product + quotient;
    }
}
