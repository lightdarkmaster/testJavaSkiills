import java.text.NumberFormat;
import java.util.Locale;

public class salaryDeduction {
    public static void main(String[] args){
        System.out.println("Budget");

        int basicSalary = 16000;
        int remainingSalary = salaryMinusTotalDeduction(basicSalary);
        int excessInSalary = remainingBalanceMinusFoodAndTransportation(remainingSalary);

        System.out.println("Summary of Deduction: ");
        System.out.println("For the Basic Salary of " + basicSalary);
        double sss = calculateSSS(basicSalary);
        double philHealth = calculatePhilHealth(basicSalary);
        double totalContribution = sss + philHealth;
        NumberFormat formatedNumber = NumberFormat.getNumberInstance(Locale.getDefault());
        String formattedTotalContribution = formatedNumber.format(totalContribution);
        System.out.println("Total Contribution: " + formattedTotalContribution);

        NumberFormat format = NumberFormat.getNumberInstance(Locale.getDefault());
        String formattedNumber = format.format(excessInSalary);
        System.out.println("Final Remaining Salary: " +"Php." +  formattedNumber + ".00");

        //Should be this:
        getFinalResult();


    }

    public static int salaryMinusTotalDeduction(int basicSalary){
        int motorLoan = 7500;
        int sss = 800;
        int philHealth = 400;
        int pagibig = 200;
        int totalDeduction = motorLoan + sss + philHealth + pagibig;
        int remainingBalance = basicSalary - totalDeduction;

        //8900

        NumberFormat format = NumberFormat.getNumberInstance(Locale.getDefault());
        String formattedNumber = format.format(remainingBalance);

        System.out.println("Remaining Balance After Deductions: " +"Php." + formattedNumber + ".00");
        return motorLoan;
    }

    public static int remainingBalanceMinusFoodAndTransportation(int remainingBalance){
        int food = 2200;
        int transportation = 2200;
        int foodAndTransportation = food + transportation;
        int excessInSalary = remainingBalance - foodAndTransportation;
        //4400

        NumberFormat format = NumberFormat.getNumberInstance(Locale.getDefault());
        String formattedNumber = format.format(excessInSalary);
        System.out.println("Excess In Salary after Food and Transportation Deduction: " +"Php."+ formattedNumber + ".00");
        return food;
    }
    public static void getFinalResult(){
        int deductions = 8900;
        int foodAndTransportation = 4400;
        int salary = 16000;
        int result = salary - deductions;
        int finalResult = result - foodAndTransportation;

        NumberFormat format = NumberFormat.getNumberInstance(Locale.getDefault());
        String formattedResult = format.format(finalResult);

        System.out.println("Answer Should be : " + "Php."+ formattedResult + ".00");
    }
    public static int calculateSSS(double basicSalary){
        double sssContribution = basicSalary * .05;
        System.out.println("SSS Contribution: " + sssContribution);
        return (int) sssContribution;
    }
    public static int calculatePhilHealth(int basicSalary){
        double philHealthContribution = basicSalary * .05;
        System.out.println("PhilHealth Contribution: " + philHealthContribution);
        return (int)philHealthContribution;
    }
}
