package ru.otus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KeyBoard {

    public String getDataFromKeyboard() throws IOException {

        String dataFromKeyBoard;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        dataFromKeyBoard = reader.readLine();

        if (!dataFromKeyBoard.equals("exit")) {
            try {
                Integer.valueOf(dataFromKeyBoard);
            } catch (NumberFormatException e) {
                dataFromKeyBoard = "exit";
            }
        }

        return dataFromKeyBoard;
    }

}
