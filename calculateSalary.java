import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public class calculateSalary {
    public static void main(String[] args){

        int baseSalary;

        System.out.println("Enter Base Salary: ");
        Scanner input = new Scanner(System.in);
        baseSalary = input.nextInt();

        double baseSalaryMinusSSS = getSSSContribution(baseSalary);
        getPhilHealthContribution(baseSalaryMinusSSS);


    }
    public static int getSSSContribution(double baseSalary){
        double sssContribution = baseSalary * 0.05;
        double minusSSSContribution = baseSalary - sssContribution;

        NumberFormat format = NumberFormat.getNumberInstance(Locale.getDefault());
        String sssReduction = format.format(minusSSSContribution);
        System.out.println("Your salary minus SSS Contribution : " + "Php." + sssReduction + ".00");
        return (int)sssContribution;
    }

    public static void getPhilHealthContribution(double baseSalaryMinusSSS){
        double philHealthContribution = baseSalaryMinusSSS * 0.05;
        double minusPhilHealthContribution = baseSalaryMinusSSS - philHealthContribution;
        System.out.println("Your Salary After PhilHealth Contribution: " + "Php." + minusPhilHealthContribution);
    }

}
