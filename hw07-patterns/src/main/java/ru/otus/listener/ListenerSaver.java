package ru.otus.listener;


import ru.otus.History;
import ru.otus.Message;

public class ListenerSaver implements Listener {

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {

        History history = new History(oldMsg, newMsg);

        history.SaveToFile(oldMsg, newMsg);


    }
}
