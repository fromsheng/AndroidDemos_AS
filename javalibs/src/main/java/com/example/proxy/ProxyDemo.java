package com.example.proxy;

/**
 * Created by caijinsheng on 17/6/22.
 */

public class ProxyDemo {

    public static void main(String[] args) {

        UserImpl target = new UserImpl("Artion");
        target.login();
        target.updateInfo();
        target.updateInfo("55555");

        JdkProxyFactory factory = new JdkProxyFactory();

        System.out.println("代理开始！");
        User userProxy = (User) factory.createProxy(target);
        userProxy.login();
        userProxy.updateInfo();
        userProxy.updateInfo("666666");
        System.out.println("代理结束！");

        Bag targetBag = new BagImpl();
        Bag bagProxy = (Bag) factory.createProxy(targetBag);

        System.out.println("代理=" + bagProxy.getColor());

//        System.out.println();
//        Dog dog = new Dog("狗");
//        dog.info();
//
//        Animal dogProxy = (Animal) factory.createProxy(dog);
//        dogProxy.info();

    }
}
