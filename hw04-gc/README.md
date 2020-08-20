Выводы:

Проверялись три GC:

G1
Parallel
Serial

Запускались соответственно:
java -Xms768m -Xmx768m -XX:+HeapDumpOnOutOfMemoryError -XX:+UseG1GC -jar gradleHW4-0.1.jar
java -Xms768m -Xmx768m -XX:+HeapDumpOnOutOfMemoryError -XX:+UseParallelGC -jar gradleHW4-0.1.jar
java -Xms768m -Xmx768m -XX:+HeapDumpOnOutOfMemoryError -XX:+UseSerialGC -jar gradleHW4-0.1.jar

Оценивались по времени работы и загрузке CPU

Результаты:

|_________| Время работы, сек | CPU, %   
|G1 ______| 85 ________________ | 70 - 80  
|Parallel _| 375 ________________| 90       
|Serial ___| 310 ________________| 15       

Выводы:

По соотношению время работы/загрузка CPU лучший результат показал Serial