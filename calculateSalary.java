import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public class calculateSalary {
    public static void main(String[] args) {

        //double as variable type just to make it accept decimal values
        double baseSalary;

        System.out.print("Enter Base Salary: ");
        Scanner input = new Scanner(System.in);
        baseSalary = input.nextDouble();

        double salaryAfterSSS = getSSSContribution(baseSalary);
        getPhilHealthContribution(salaryAfterSSS);


        salaryPerHour();
        input.close();
    }

    public static double getSSSContribution(double baseSalary) {
        double sssContribution = baseSalary * 0.05;
        double minusSSSContribution = baseSalary - sssContribution;

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        System.out.println("Your salary minus SSS Contribution: " + format.format(minusSSSContribution));
        return minusSSSContribution;
    }

    public static void getPhilHealthContribution(double salaryAfterSSS) {
        double philHealthContribution = salaryAfterSSS * 0.05;
        double finalSalary = salaryAfterSSS - philHealthContribution;

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        System.out.println("Your salary after PhilHealth Contribution: " + format.format(finalSalary));
    }
    public static void salaryPerHour(){
        //Computed Rate per day
        int perDay = 620;
        int perHour = perDay / 8;//divide it by 8hrs to get the rate per hour..
        System.out.println("Salary Per Hour: " + perHour);
    }
}
