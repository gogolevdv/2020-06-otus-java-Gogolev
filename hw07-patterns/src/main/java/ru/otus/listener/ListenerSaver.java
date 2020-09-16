package ru.otus.listener;


import ru.otus.History;
import ru.otus.Message;
import ru.otus.SaverToFile;

public class ListenerSaver implements Listener {

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {

        History history = new History(oldMsg, newMsg);

        SaverToFile toFile = new SaverToFile(history);

        toFile.save(history);


    }
}
