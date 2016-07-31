package com.ws.apple.ayuep;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler instance;

    public static CrashHandler getInstance(){
        if(instance == null){
            instance = new CrashHandler();
        }

        return instance;
    }

    public CrashHandler(){
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
//        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
