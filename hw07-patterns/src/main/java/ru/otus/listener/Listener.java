package ru.otus.listener;

import ru.otus.Message;

import java.io.IOException;

public interface Listener {

    void onUpdated(Message oldMsg, Message newMsg) throws IOException;

    //todo: 4. Сделать Listener для ведения истории: старое сообщение - новое
}
