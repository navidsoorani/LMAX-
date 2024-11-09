package org.example;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class DisruptorExample {
    public static void main(String[] args) throws Exception {

        System.out.println("Thread : " + Thread.currentThread().getName());

        MessageEventFactory factory = new MessageEventFactory();

        int bufferSize = 1024;

        Disruptor<MessageEvent> disruptor = new Disruptor<>(factory, bufferSize,new  CustomThreadFactory());

        // Create a worker pool to handle the events
        MessageEventHandler[] handlers = new MessageEventHandler[100];
        for (int i = 0; i < handlers.length; i++) {
            handlers[i] = new MessageEventHandler("Consumer " + (char)('A' + i)); // Consumer A, B, C
        }

        disruptor.handleEventsWithWorkerPool(handlers);


        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<MessageEvent> ringBuffer = disruptor.getRingBuffer();

        MessageEventProducer producer = new MessageEventProducer(ringBuffer);

        Long startDate = new Date().getTime();

        // Produce some events
        for (long l = 0; l < 1000000; l++) {
            producer.produce("1234567" + new Random().nextInt(0,10000));
        }

        Long endDate = new Date().getTime();

        System.out.println(endDate-startDate);

        disruptor.shutdown();
    }
}