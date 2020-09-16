package ru.otus;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaverToFile implements Save {

    History historyRec;

    public SaverToFile(History historyRec) {
        this.historyRec = historyRec;
    }

    @Override
    public void save(History historyRec) {
        final String TEXT_FILE = "textFile.txt";

        try (var bufferedWriter = new BufferedWriter(new FileWriter(TEXT_FILE, true))) {
            bufferedWriter.newLine();
            bufferedWriter.write("==========================");
            bufferedWriter.newLine();
            bufferedWriter.write("Старое сообщение: " + historyRec.getOldMsg());
            bufferedWriter.newLine();
            bufferedWriter.write("Новое сообщение: " + historyRec.getNewMsg());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
