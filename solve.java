public class solve {
    public static void main(String[] args){
        int number, num=9;
        String me = "th";
        number=value(num);
        System.out.println(num + me+ " Value is " + number);
    }
    static int value(int pass){
        if(pass==1)
            return 0;
        if(pass==2)
            return 1;

        return value(pass-1) + value(pass-2);
//        value(2) = value (1) = 0 =1=0 = 1
//        value(3) = value(2) + value(1) = 1 + 0 = 1
//        value(4) = value(3) + value(2) = 1 + 1 = 2
//        value(5) = value(4) + value(3) = 2 + 1 = 3
//        value(6) = value(5) + value(4) = 3 + 2 = 5
//        value(7) = value(6) + value(5) = 5 + 3 = 8
//        value(8 ) = value(7) + value(6) = 8 + 5 = 13
//        value(9) = value(8 ) + value(7) = 13 + 8 = 21
    }
}
