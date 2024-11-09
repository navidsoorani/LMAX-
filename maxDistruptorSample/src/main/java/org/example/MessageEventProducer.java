package org.example;

import com.lmax.disruptor.RingBuffer;

public class MessageEventProducer {
    private final RingBuffer<MessageEvent> ringBuffer;

    public MessageEventProducer(RingBuffer<MessageEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void produce(String value) {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try {
            MessageEvent event = ringBuffer.get(sequence); // Get the event
            event.setMessage(value); // Fill with data
        } finally {
            System.out.println("Thread " + Thread.currentThread().getName() + " produced value " + value);
            ringBuffer.publish(sequence); // Publish the event
        }
    }
}