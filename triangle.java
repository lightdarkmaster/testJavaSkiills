import java.util.Scanner;

public class triangle {
    public static void main(String[] args){
        int length;

        Scanner input = new Scanner(System.in);

        System.out.println("Enter Length: ");
        length = input.nextInt();

        for(int i = 0; i < length; i++){
            System.out.println(" ");
            for(int j = 0; j < i + 1; j++){
                System.out.print("*");
            }
        }
    }
}
