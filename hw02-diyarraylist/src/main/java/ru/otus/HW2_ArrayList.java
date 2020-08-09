package ru.otus;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HW2_ArrayList {


    public static void main(String[] args) {

        // Создаем два массива объектов класса DIYarrayList
        List<Cat> cat_1 = new DIYarrayList<>();
        List<Cat> cat_2 = new DIYarrayList<>();

        // Создаем объекты Cat
        Cat One = new Cat("white", 9, 20);
        Cat Two = new Cat("green", 4, 10);
        Cat Three = new Cat("red", 2, 13);
        Cat Four = new Cat("brown", 3, 14);
        Cat Five = new Cat("red", 5, 11);
        Cat Six = new Cat("orange", 7, 15);
        Cat Seven = new Cat("yellow", 9, 17);
        Cat Eight = new Cat("blue", 8, 19);
        Cat Nine = new Cat("black", 6, 12);
        Cat Ten = new Cat("pink", 4, 14);

        Cat Eleven = new Cat("grey", 3, 16);
        Cat Twelve = new Cat("purple", 2, 18);
        Cat Thirteen = new Cat("white", 1, 20);
        Cat Fourteen = new Cat("green", 4, 11);
        Cat Fifteen = new Cat("red", 6, 13);
        Cat Sixteen = new Cat("brown", 8, 15);
        Cat Seventeen = new Cat("orange", 3, 17);
        Cat Eighteen = new Cat("yellow", 7, 19);
        Cat Nineteen = new Cat("blue", 9, 12);
        Cat Twenty = new Cat("black", 5, 14);
        Cat TwentyOne = new Cat("pink", 2, 16);

        //Создаем компараторы для проверки метода sort
        Comparator<Cat> catColorComparator = Comparator.comparing(Cat::getColor);
        Comparator<Cat> catAgeComparator = Comparator.comparing(Cat::getAge);
        Comparator<Cat> catLengthComparator = Comparator.comparing(Cat::getLength);


        // Проверяем работу метода Collections.addAll(Collection<? super T> c, T... elements)
        Collections.addAll(cat_1, One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve,
                Thirteen, Fourteen, Fifteen, Sixteen, Seventeen, Eighteen, Nineteen, Twenty, TwentyOne);

        // Увеличиваем размер массива cat_2 до длины, необходимой для проверки метода copy
        for (int i = 0; i < 21; i++)
            cat_2.add(cat_1.get(0));

        // Проверяем метод Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
        Collections.copy(cat_2, cat_1);

        // Проверяем метод Collections.static <T> void sort(List<T> list, Comparator<? super T> c)
        Collections.sort(cat_2, catAgeComparator);
        System.out.println("===== Age Sort =======");
        for (Cat qq : cat_2) System.out.println(qq.age);

        Collections.sort(cat_2, catColorComparator);
        System.out.println("===== Color Sort =======");
        for (Cat qq : cat_2) System.out.println(qq.color);

        Collections.sort(cat_2, catLengthComparator);
        System.out.println("===== Length Sort =======");
        for (Cat qq : cat_2) System.out.println(qq.length);

    }

}
