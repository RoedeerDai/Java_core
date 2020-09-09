package com.roedeer.DbUtils;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 4/21/2020 4:54 PM
 **/
//public class Test {
//}

abstract class Base{
    abstract void print();
    Base(){
        print();
    }

}
class Child extends Base{
    int i = 1;
    @Override
    void print() {
        System.out.println(i);
    }
}
public class Test {
    public static  void main(String[] args){
        Child b = new Child();
        b.print();
    }
}
