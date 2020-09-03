package ru.otus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CashBox {

    private final int MIN_CASH_SLOT = 0;
    private final int MAX_CASH_SLOT = 500;

    private final Map withdrawalArray = new HashMap<BankNotes, Integer>();
    private final Map cashSlot = new HashMap<BankNotes, Integer>();

    CashBox() {
        cashSlot.put(BankNotes.FiveThousand, 0);
        cashSlot.put(BankNotes.TwoThousand, 0);
        cashSlot.put(BankNotes.OneThousand, 0);
        cashSlot.put(BankNotes.FiveHundred, 0);
        cashSlot.put(BankNotes.TwoHundred, 0);
        cashSlot.put(BankNotes.OneHundred, 0);
        cashSlot.put(BankNotes.Fifty, 0);
        cashSlot.put(BankNotes.Ten, 0);
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

        cashSlot.put(bankNotes, count);

    }

    public Set<Map.Entry<BankNotes, Integer>> withdrawal(int amount) {

        withdrawalArray.clear();

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

        int count = 0;

        while (0 < amount && amount >= bankNotes.getValue() && (Integer) cashSlot.get(bankNotes) > 0) {
            cashSlot.put(bankNotes, (Integer) cashSlot.get(bankNotes) - 1);
            withdrawalArray.put(bankNotes, count += 1);
            amount -= bankNotes.getValue();
        }
        return amount;
    }

    public int withdrawalAll() {

        cashSlot.clear();

        return getFullAmount();

    }

}
