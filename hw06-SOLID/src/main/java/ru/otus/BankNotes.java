package ru.otus;

public enum BankNotes {

    Ten(10),
    FiveThousand(5000),
    TwoThousand(2000),
    OneThousand(1000),
    FiveHundred(500),
    TwoHundred(200),
    OneHundred(100),
    Fifty(50);

    private final int value;

    BankNotes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public BankNotes getBankNote(int value) {
        switch (value) {
            case 10:
                return BankNotes.Ten;
            case 50:
                return BankNotes.Fifty;
            case 100:
                return BankNotes.OneHundred;
            case 200:
                return BankNotes.TwoHundred;
            case 500:
                return BankNotes.FiveHundred;
            case 1000:
                return BankNotes.OneThousand;
            case 2000:
                return BankNotes.TwoThousand;
            case 5000:
                return BankNotes.FiveThousand;
        }
        return null;
    }
}
