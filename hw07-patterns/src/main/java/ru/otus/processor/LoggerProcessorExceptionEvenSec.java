package ru.otus.processor;

import ru.otus.Message;

import java.time.LocalDateTime;

public class LoggerProcessorExceptionEvenSec implements Processor {
    //todo: 3. Сделать процессор, который будет выбрасывать исключение в четную секунду

    private final Processor processor;

    public LoggerProcessorExceptionEvenSec(Processor processor) {
        this.processor = processor;
    }

    @Override
    public Message process(Message message) {
        LocalDateTime now = LocalDateTime.now();
        int sec = now.getSecond();
        System.out.println(sec);
        if (sec % 2 == 0) {
            try {
                throw new NullPointerException("Четная секунда!");
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        }

        return processor.process(message);
    }
}
