public class recursion {

    public static void main(String[] args){

        int number, num = 9;
        number = value(num);
        System.out.println(num + "th value is " + number);
    }
    public static int value(int pass){
        if(pass == 1)
            return 0;
        if(pass == 2){
            return 1;
        }
        return value(pass - 1) + value(pass-2);
    }
}
