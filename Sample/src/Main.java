
public class Main{

    public static void main(String[] args){
        function();
    }

    public static void function(){
        Dog dog = new Dog();
        String name = dog.name;
        int age = dog.age;
        String address = dog.getAddress();

        System.out.println("Dog Name: " + name);
        System.out.println("Dog Age: " + age);
        System.out.println("Dog Address : " + address);
    }
}