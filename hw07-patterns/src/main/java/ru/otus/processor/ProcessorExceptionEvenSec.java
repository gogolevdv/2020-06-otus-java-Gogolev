package ru.otus.processor;

import ru.otus.Message;

import java.time.LocalDateTime;

public class ProcessorExceptionEvenSec implements Processor {
    @Override
    public Message process(Message message) {

        LocalDateTime now = LocalDateTime.now();
        int sec = now.getSecond();
        System.out.println(sec);
        if (!(sec % 2 == 0)) {
            try {
                throw new NullPointerException("Нечетная секунда!");
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        }

        return message.toBuilder().build();
    }
}


