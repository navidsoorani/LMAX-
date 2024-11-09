package org.example;

import java.util.concurrent.ThreadFactory;

class CustomThreadFactory implements ThreadFactory {

    // newThread is a factory method
    // provided by ThreadFactory
    public Thread newThread(Runnable command)
    {
        return new Thread(command);
    }
}