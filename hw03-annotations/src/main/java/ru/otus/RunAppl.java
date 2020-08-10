package ru.otus;

public class RunAppl {

    public static void main(String[] args) throws Exception {

        // Создаем объекты класса с тестами
        var aaa = new TestCalc();
        var bbb = new TestCalc();
        var ccc = new TestCalc();

        // Запускаем тесты
        RunTest.runTestCalc(aaa);
        RunTest.runTestCalc(bbb);
        RunTest.runTestCalc(ccc);

    }

}
