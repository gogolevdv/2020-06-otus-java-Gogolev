package ru.otus;

class TestCalc {

    private double x, y, sum;
    public boolean result = false;

    private void setX(double x) {
        this.x = x;
    }

    private void setY(double y) {
        this.y = y;
    }

    private void setSum(double sum) {
        this.sum = sum;
    }

    public boolean isResult() {
        return result;
    }

    public TestCalc(double[] aa) {

        this.x = aa[0];
        this.y = aa[1];
        this.sum = aa[2];
    }

    @Before
    public void beforetestCalc() {

        this.setX(x);
        this.setY(y);
        this.setSum(sum);

    }

    @Test
    public void testCalc() {

        Calc calc = new Calc();

        if (sum == calc.sum(x, y)) result = true;

    }

    @After
    public void aftertestCalc() {

    }

}
