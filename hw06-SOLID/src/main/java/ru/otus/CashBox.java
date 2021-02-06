package ru.otus;

import java.util.*;
import java.util.stream.Collectors;

public class CashBox {

    private final int MIN_CASH_SLOT = 0;
    private final int MAX_CASH_SLOT = 500;

    private final Map<BankNotes, Integer> cashSlot;
//    private final HashMap<BankNotes, Integer> withdrawalArray = new HashMap<>();


    List<BankNotes> banknotes = Arrays.stream(BankNotes.values()).sorted(Comparator.reverseOrder()).collect(Collectors.toList());

    CashBox(Map<BankNotes, Integer> container) {

        this.cashSlot = container;


    }

    public int getMIN_CASH_SLOT() {
        return MIN_CASH_SLOT;
    }

    public int getMAX_CASH_SLOT() {
        return MAX_CASH_SLOT;
    }

    public Set<Map.Entry<BankNotes, Integer>> getAllCashBox() {
        return cashSlot.entrySet();
    }

    public int getFullAmount() {
        int amount = 0;
        Set<Map.Entry<BankNotes, Integer>> entries = this.getAllCashBox();

        for (HashMap.Entry<BankNotes, Integer> qq : entries) {
            amount += qq.getValue() * qq.getKey().getValue();
        }
        return amount;
    }

    public void addBankNotes(BankNotes bankNotes, int count) {


        cashSlot.put(bankNotes, cashSlot.get(bankNotes) + count);

    }

    public Set<Map.Entry<BankNotes, Integer>> withdrawal(int amount) {

        final HashMap<BankNotes, Integer> withdrawalArray = new HashMap<>();

        while (amount >= BankNotes.Ten.getValue()) {
            amount = getSlot(BankNotes.FiveThousand, amount);
            amount = getSlot(BankNotes.TwoThousand, amount);
            amount = getSlot(BankNotes.OneThousand, amount);
            amount = getSlot(BankNotes.FiveHundred, amount);
            amount = getSlot(BankNotes.TwoHundred, amount);
            amount = getSlot(BankNotes.OneHundred, amount);
            amount = getSlot(BankNotes.Fifty, amount);
            amount = getSlot(BankNotes.Ten, amount);
        }


        return withdrawalArray.entrySet();
    }

    // Выдача определенной банкноты
    private int getSlot(BankNotes bankNotes, int amount) {

        while (0 < amount && amount >= bankNotes.getValue() && cashSlot.get(bankNotes) > 0) {
            cashSlot.put(bankNotes, cashSlot.get(bankNotes) - 1);
            amount -= bankNotes.getValue();
        }
        return amount;
    }

    public int withdrawalAll() {

        cashSlot.clear();

        return getFullAmount();

    }

}
