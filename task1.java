import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public class task1 {
    public static void main(String[] args) {
        int salary;

        Scanner input = new Scanner(System.in);
        System.out.println("Budgeting Code:");
        System.out.print("Enter Salary: ");
        salary = input.nextInt();


       int foodAndTransportation = subtractDailyExpense(salary);
       int motorcycleLoan = getMotorcycleLoan(salary);

       System.out.println("Summary");
       System.out.println("-" + foodAndTransportation);
       System.out.println("-"+ motorcycleLoan);

        int remainingBalanceMinusFoodAndTransportation = salary - foodAndTransportation;
        int finalResult = remainingBalanceMinusFoodAndTransportation - motorcycleLoan;
        System.out.println("Final Remaining Balance: " + finalResult);
    }

    public static int subtractDailyExpense(int salary) {
        int expensePerMonth = 4400;
        int remainingBalance = salary - expensePerMonth;
        NumberFormat format = NumberFormat.getNumberInstance(Locale.getDefault());
        String formattedNumber = format.format(remainingBalance);
        System.out.println("Php " + formattedNumber + ".00");
        return expensePerMonth;
    }

    public static int getMotorcycleLoan(int remainingBalance){
        int motorcycleLoan = 7500;
        int minusMotorcycleLoan = remainingBalance - motorcycleLoan;
        NumberFormat format = NumberFormat.getNumberInstance(Locale.getDefault());
        String formattedNumber = format.format(minusMotorcycleLoan);
        System.out.println("Php." + formattedNumber + ".00");
        return motorcycleLoan;
    }
}
