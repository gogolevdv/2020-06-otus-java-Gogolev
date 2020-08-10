package ru.otus;

public class TestCalc {
    private double x, y, sum;
    public boolean result = false;

    @Before
    public void beforetestCalc() {

        x = Math.random();
        y = Math.random();

    }

    @Test
    public void testCalc() {

        Calc calc = new Calc();

        sum = calc.sum(x, y);

        if (sum == x + y) result = true;

    }

    @After
    public void aftertestCalc() {

    }

}
