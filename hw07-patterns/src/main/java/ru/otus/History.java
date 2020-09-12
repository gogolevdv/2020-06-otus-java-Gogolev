package ru.otus;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class History implements SaveToFile {
    private Message oldMsg;
    private Message newMsg;

    public History(Message oldMsg, Message newMsg) {
        this.oldMsg = oldMsg;
        this.newMsg = newMsg;
    }

    @Override
    public void SaveToFile(Message oldMsg, Message newMsg) {

        final String TEXT_FILE = "textFile.txt";

        try (var bufferedWriter = new BufferedWriter(new FileWriter(TEXT_FILE, true))) {
            bufferedWriter.newLine();
            bufferedWriter.write("==========================");
            bufferedWriter.newLine();
            bufferedWriter.write("Старое сообщение: " + String.valueOf(oldMsg));
            bufferedWriter.newLine();
            bufferedWriter.write("Новое сообщение: " + String.valueOf(newMsg));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
