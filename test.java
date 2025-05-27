import java.util.Scanner;

class test{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int length;

        System.out.println("Enter Number Length: ");
        length = input.nextInt();

        for(int i = 0; i < length; i++){
            System.out.println(" ");
            for(int j = 0; j <=i; j++){
                System.out.print("*");
            }
        }
    }
}