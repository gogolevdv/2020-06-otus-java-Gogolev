Выводы:

Проверялись три GC:

G1
Parallel
Serial

Запускались соответственно:
java -Xms768m -Xmx768m -XX:+HeapDumpOnOutOfMemoryError -XX:+UseG1GC -jar gradleHW4-0.1.jar
java -Xms768m -Xmx768m -XX:+HeapDumpOnOutOfMemoryError -XX:+UseParallelGC -jar gradleHW4-0.1.jar
java -Xms768m -Xmx768m -XX:+HeapDumpOnOutOfMemoryError -XX:+UseSerialGC -jar gradleHW4-0.1.jar

Оценивались по времени выплнения полезной работы

Результаты:

|_________| Время работы, сек | Количество циклов заполнения массива   
|G1 ______| 113 ________________ | 2571  
|Parallel _| 442 ________________| 2571       
|Serial ___| 263 ________________| 2571       

Выводы:

Для данного приложения меньшее время выполнения полезной работы показал G1
Кроме того, паузы при сборке мусора у G1 меньше чем у двух других GC.

Таким образом по вышеописанным причинам G1 лучше 