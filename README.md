# Интерпретатор командной строки
Интерпретатор командной строки, написанный на Java, работающий на операционных 
системах Windows и Linux.

Поддерживаемые команды:

* cat
* cd
* echo
* pwd
* wc
* exit

Также реализовано присваивание переменных и обращение к 
переменным, а также вызов внешних команд.

## Тестирование
Для тестирования вызова внешних команд используется проект 
Summator, который можно собрать с помощью CMake командой 

```cmake ../../../Summator/Summator -DCMAKE_BUILD_TYPE=Debug -DTARGET_CPU=x86```

вызванной из src/test/resources или с помощью Makefile.

## Архитектура интерпретатора
Строка, поступающая от пользователя, передаётся объекту класса 
LineParser, который, используя класс CommandParser, разбивает
строку на отдельные команды, разделённые символом конвейера.

CommandParser читает строку до появления символа конвейера, 
подставляя значения встретившихся переменных, хранящиеся в 
классе InterpreterEnvironment.

Для каждого вида команды существует отдельный класс,
который наследуется от класса Command и реализует функцию 
execute, отвечающую за выполнение этой команды.

Для каждой команды в конвейере создаётся объект класса Command.
Для этих объектов поочерёдно вызываются функции выполнения. 
Выход каждой команды, представленный как объект класса IOData,
используется как вход для последующей в конвейере. Результат 
работы последней команды выводится на экран. В случае 
возникновения ошибки в одной из промежуточных команд выводится
сообщение об ошибке, последующие команды конвейереа не 
выполняются.

Ниже представленна диаграмма последовательностей интерпретатора.

![Диаграмма последовательностей](/diagrams/Sequence%20diagram.PNG )
