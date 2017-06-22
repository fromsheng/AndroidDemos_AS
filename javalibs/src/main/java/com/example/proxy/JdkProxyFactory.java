package com.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by caijinsheng on 17/6/22.
 */

public class JdkProxyFactory implements InvocationHandler {
    private Object target;

    //创建代理类
    public Object createProxy(Object target) {
        this.target = target;
        //取得代理对象
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    //改变委托类方法的调用逻辑
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        Object result = null;
        String methodName = method.getName();
        doBefore(methodName);
        result = method.invoke(target, args);
        doAfter(methodName);
        return result;
    }

    private void doBefore(String methodName) {
        System.out.println("JDK动态代理:前置动作……" + methodName);
    }

    private void doAfter(String methodName) {
        System.out.println("JDK动态代理:后置动作……" + methodName);
    }
}
