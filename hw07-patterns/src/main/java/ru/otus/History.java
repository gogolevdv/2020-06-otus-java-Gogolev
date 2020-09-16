package ru.otus;

public class History {
    private Message oldMsg;
    private Message newMsg;

    public History(Message oldMsg, Message newMsg) {
        this.oldMsg = oldMsg;
        this.newMsg = newMsg;
    }

    public Message getOldMsg() {
        return oldMsg;
    }

    public Message getNewMsg() {
        return newMsg;
    }

}
