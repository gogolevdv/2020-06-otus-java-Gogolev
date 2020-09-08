package ru.otus.listener;

import ru.otus.Message;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ListenerSaver implements Listener {

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) throws IOException {

        final String TEXT_FILE = "textFile.txt";

        try (var bufferedWriter = new BufferedWriter(new FileWriter(TEXT_FILE,true))) {
            bufferedWriter.newLine();
            bufferedWriter.write("==========================");
            bufferedWriter.newLine();
            bufferedWriter.write("Старое сообщение: "+String.valueOf(oldMsg));
            bufferedWriter.newLine();
            bufferedWriter.write("Новое сообщение: "+String.valueOf(newMsg));
        }


    }
}
