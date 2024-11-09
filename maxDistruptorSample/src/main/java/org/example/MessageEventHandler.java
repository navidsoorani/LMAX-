package org.example;

import com.lmax.disruptor.WorkHandler;

public class MessageEventHandler implements WorkHandler<MessageEvent> {

    private final String name;

    public MessageEventHandler(String name) {
        this.name = name;
    }

    @Override
    public void onEvent(MessageEvent messageEvent) throws Exception {
        System.out.println(" Consumer : " + name + " LongEventHandler received main " +
                messageEvent.getMessage() + " " + Thread.currentThread().getName());
    }
}