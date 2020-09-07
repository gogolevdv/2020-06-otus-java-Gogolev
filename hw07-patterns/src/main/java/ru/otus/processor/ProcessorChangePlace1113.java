package ru.otus.processor;

import ru.otus.Message;

public class ProcessorChangePlace1113 implements Processor{
    @Override
    public Message process(Message message) {
        return message.toBuilder().field13(message.getField11()).field11(message.getField13()).build();
    }
}
