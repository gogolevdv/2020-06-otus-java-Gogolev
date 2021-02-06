package ru.otus;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static ru.otus.BankNotes.*;

public class ATM {

    private final CashBox myCashBox;
    private final Screen myATMScreen = new Screen();
    private final KeyBoard myKeyBoard = new KeyBoard();
    List<BankNotes> banknotes = Arrays.stream(values()).sorted(Comparator.reverseOrder()).collect(Collectors.toList());

    public ATM(){

        Map<BankNotes, Integer> cashSlot = new HashMap<>();

        for (BankNotes bankNotes : banknotes) {

            cashSlot.put(bankNotes, 0);

        }

        myCashBox = new CashBox(cashSlot);

    }

    public void initATM() throws IOException, InterruptedException {
        myATMScreen.showMessage("ATM initialization.....");

        Thread.sleep(5000);

        mainMenu();

    }

    public void mainMenu() throws IOException {

        String command;

        do {

            myATMScreen.showMessage("Главное меню");
            myATMScreen.showMessage("Введите ");
            myATMScreen.showMessage("\"1\" для внесения средств");
            myATMScreen.showMessage("\"2\" для снятия средств");
            myATMScreen.showMessage("\"3\" чтобы снять все");
            myATMScreen.showMessage("\"4\" для проверки доступных средств");
            myATMScreen.showMessage("\"exit\" для выключения банкомата");

            command = myKeyBoard.getDataFromKeyboard();

            switch (command) {
                case "1" -> {
                    myATMScreen.showMessage("Нажата 1");
                    refillMenu();
                }
                case "2" -> {
                    myATMScreen.showMessage("Нажата 2");
                    withdrawalMenu();
                }
                case "3" -> {
                    myATMScreen.showMessage("Нажата 3");
                    withdrawalAll();
                }
                case "4" -> {
                    myATMScreen.showMessage("Нажата 4");
                    viewCashSlots();
                }
            }
        } while (!command.equals("exit"));


    }

    public void refillMenu() throws IOException {

        String parBankNotes;

        String countBankNotes = "0";

        boolean gettrue = false;

        do {
            do {
                myATMScreen.showMessage("Введите номинал купюр: ");

                parBankNotes = myKeyBoard.getDataFromKeyboard();

                for (BankNotes bankNotes : values()) {
                    gettrue = gettrue | parBankNotes.equals(String.valueOf(bankNotes.getValue()));
                }

            } while (!gettrue && !parBankNotes.equals("exit"));

            if (!parBankNotes.equals("exit")) {
                gettrue = false;

                do {
                    myATMScreen.showMessage("Введите количество купюр(от 1 до 500): ");

                    countBankNotes = myKeyBoard.getDataFromKeyboard();

                    if (!countBankNotes.equals("exit")) {
                        gettrue = (Integer.parseInt(countBankNotes) > myCashBox.getMIN_CASH_SLOT()) && (Integer.parseInt(countBankNotes) <= myCashBox.getMAX_CASH_SLOT());
                    }

                } while (!gettrue && !countBankNotes.equals("exit"));

                myATMScreen.showMessage("Номинал купюры: " + parBankNotes + "; Количество купюр: " + countBankNotes);

                if (!countBankNotes.equals("exit"))
                    addMoneyToCashSlot(Arrays.stream(values()).findFirst().get().getBankNote(Integer.parseInt(parBankNotes)), Integer.parseInt(countBankNotes));

            }
        } while (!parBankNotes.equals("exit") && !countBankNotes.equals("exit"));

    }

    public void withdrawalMenu() throws IOException {

        String amount = "0";

        boolean gettrue = false;

        do {

            myATMScreen.showMessage("Введите сумму: ");
            amount = myKeyBoard.getDataFromKeyboard();
            if (!amount.equals("exit")) {
                gettrue = Integer.parseInt(amount) >= 10;
            }

        } while (!gettrue && !amount.equals("exit"));

        if (!amount.equals("exit"))
            withdrawal(Integer.parseInt(amount));
    }

    public void addMoneyToCashSlot(BankNotes banknote, Integer count) {

        myATMScreen.showMessage("Add moneytoSlot...");

        myCashBox.addBankNotes(banknote, count);

    }

    public void withdrawal(int amount) {

        if (checkAmount(amount)) {  // Проверям, есть ли деньги

            // Выдаем деньги
            Set<Map.Entry<BankNotes, Integer>> entries = myCashBox.withdrawal(amount);

            myATMScreen.showMessage("=====  Выдано: ========");

            for (HashMap.Entry<BankNotes, Integer> qq : entries) {

                myATMScreen.showMessage(qq.getKey() + "   " + qq.getValue());

            }

            myATMScreen.showMessage("=========================================================");

        } else {
            myATMScreen.showMessage("Денег нет!!!!");
        }

    }

    public void withdrawalAll() {
        myATMScreen.showMessage("Выдано: " + myCashBox.withdrawalAll() + " рублей!");
    }

    public boolean checkAmount(int amount) {

        return amount <= myCashBox.getFullAmount();

    }

    public void viewCashSlots() {

        Set<Map.Entry<BankNotes, Integer>> entries = myCashBox.getAllCashBox();

        for (HashMap.Entry<BankNotes, Integer> qq : entries) {

            myATMScreen.showMessage(qq.getKey() + "   " + qq.getValue());

        }

        myATMScreen.showMessage("Возможная сумма к выдаче: " + myCashBox.getFullAmount());
    }

}
