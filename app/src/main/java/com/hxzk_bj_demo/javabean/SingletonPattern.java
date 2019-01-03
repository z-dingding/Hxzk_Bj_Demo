package com.hxzk_bj_demo.javabean;

/**
 * 作者：created by ${zjt} on 2018/12/12
 * 描述:
 */
public class SingletonPattern {


    private SingletonPattern() {
    }

    //饿汉式,提前创建类实例
    //缺点:无论是否需要，只要类加载就进行了实例化，浪费资源
    private static SingletonPattern hungerSingleton =new SingletonPattern();

    public static SingletonPattern getHungerInstance(){
        return hungerSingleton;
    }



    //懒汉式,需要的时候创建类实例
    //缺点：没有考虑到多线程，多线程环境下无法保证单例效果，会多次执行SingletonPattern instance=new SingletonPattern()
    private static SingletonPattern lazySingleton;


    public static SingletonPattern getLazyInstance(){
      if(lazySingleton == null){
          lazySingleton =new SingletonPattern();
      }
        return lazySingleton;
    }


    //懒汉式加锁同步
    //缺点：还是会存在多次执行的可能SingletonPattern instance=new SingletonPattern()
    private static SingletonPattern syncSingleton;
    public static SingletonPattern getSyncyInstance(){
        if(syncSingleton == null){
            //在此处加锁同步比在方法出加锁同步缩小了范围，性能稍高
            synchronized (SingletonPattern.class){
                syncSingleton =new SingletonPattern();
            }

        }
        return syncSingleton;
    }


 //懒汉式双层检查(DCL,Double Check Lock)
 //缺点:不能保证按序执行，处理器会进行指令重排序优化.
    private static SingletonPattern dclSingleton;
    public static SingletonPattern getDCLInstance(){
        if(dclSingleton == null){//第一层检查
            //在此处加锁同步比在方法出加锁同步缩小了范围，性能稍高
            synchronized (SingletonPattern.class){
                if(dclSingleton == null){//第二层检查
                    dclSingleton =new SingletonPattern();
                }

            }

        }
        return dclSingleton;
    }



    //volatile+懒汉式双层检查(DCL,Double Check Lock)
    //使用volatile关键字修饰instance就可以实现正确的double check单例模式
    //可以保证在多线程环境下，变量的修改可见性
    //理由:volatile关键字修饰的变量的赋值和读取操作前后两边的大的顺序不会改变，在内存屏障前面的顺序可以交换，
    //屏障后面的也可以换序，但是不能跨越内存屏障重排执行顺序。
    private static volatile SingletonPattern volatileSingleton;
    public static SingletonPattern getVolatileInstance(){
        if(volatileSingleton == null){//第一层检查
            //在此处加锁同步比在方法出加锁同步缩小了范围，性能稍高
            synchronized (SingletonPattern.class){
                if(volatileSingleton == null){//第二层检查
                    volatileSingleton =new SingletonPattern();
                }

            }

        }
        return dclSingleton;
    }



    //其他方式-使用静态内部类实现单例模式(也是正确的方式之一)
    //理由:静态内部类在没有显示调用的时候是不会进行加载的，当执行了return 后才加载初始化
    public static SingletonPattern InnerSingletonInstance(){
        return staticSingleInstance.staticSingleton;
    }

    private static class staticSingleInstance{
        private static SingletonPattern staticSingleton=new SingletonPattern();
    }


    //枚举实现单例,十分简单
    public enum enumSingletonInstance{
        INSTANCE;
    }
}
