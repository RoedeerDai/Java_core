package com.roedeer.spring.aop.demo3;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Roedeer on 11/24/2018.
 */
public class ProxyFactory {
    private Object targetObject;    //目标对象
    private BeforeAdvice beforeAdvice;  //前置增强
    private AfterAdvice afterAdvice;    //后置增强

    /**
     * 用来生成代理对象
     * @return
     */
    public Object createProxy() {
        /**
         * 3大参数
         */
        ClassLoader loader = this.getClass().getClassLoader();
        Class[] interfaces = targetObject.getClass().getInterfaces();
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                /**
                 * 调用代理对象的方法时都会执行这里的方法
                 */
                //执行前置增强
                if (beforeAdvice != null) {
                    beforeAdvice.before();
                }
                Object result = method.invoke(targetObject,args);   //执行目标对象的方法
                //执行后置增强
                if (afterAdvice != null) {
                    afterAdvice.after();
                }
                //返回目标对象的返回值
                return result;
            }
        };
        /**
         * 得到代理对象
         */
        Object proxyObject = Proxy.newProxyInstance(loader, interfaces, handler);
        return proxyObject;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    public BeforeAdvice getBeforeAdvice() {
        return beforeAdvice;
    }

    public void setBeforeAdvice(BeforeAdvice beforeAdvice) {
        this.beforeAdvice = beforeAdvice;
    }

    public AfterAdvice getAfterAdvice() {
        return afterAdvice;
    }

    public void setAfterAdvice(AfterAdvice afterAdvice) {
        this.afterAdvice = afterAdvice;
    }
}
