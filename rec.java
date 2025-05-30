import java.util.Scanner;
public class rec {
    public static void main(String[] args){

        Scanner input = new Scanner(System.in);
        int length;
        System.out.println("Enter A number: ");
        length = input.nextInt();

        for(int i=1; i <=length; i++){
            System.out.println(i);

        }
    }
}
