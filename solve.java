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
    }
}
