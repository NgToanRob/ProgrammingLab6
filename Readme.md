<p align="center">
  <img src="doc/Picture1.png" />
</p>
<p align="center" style ="font-size: 24px"><em>Факультет мехатроники и робототехники</em></p>

<p align="center" style ="font-size: 24px"><strong>Лабораторная работа №6 </br>
По дисциплине : «Программирование»</br>
Вариант 665598</strong>
</p>
<p align="left">Преподаватель: <strong>Максимова Марина Михайловна</strong></br>
Выполнил: <strong>Нгуен Тоан</strong></br>
Группа: <strong>R3137</strong>
</p>

# Лабораторная работа #6

## 1. Текст задания.
### Требования
<div id="_pbportletlab6_WAR_pbportlet_pb-lab6-text"> <p>Разделить программу из <a href="https://se.ifmo.ru/courses/programming#lab5">лабораторной работы №5</a> на клиентский и серверный модули. Серверный модуль должен осуществлять выполнение команд по управлению коллекцией. Клиентский модуль должен в интерактивном режиме считывать команды, передавать их для выполнения на сервер и выводить результаты выполнения.</p> <p><b>Необходимо выполнить следующие требования:</b></p><ul><li>Операции обработки объектов коллекции должны быть реализованы с помощью Stream API с использованием лямбда-выражений.</li><li>Объекты между клиентом и сервером должны передаваться в сериализованном виде.</li><li>Объекты в коллекции, передаваемой клиенту, должны быть отсортированы по названию</li><li>Клиент должен корректно обрабатывать временную недоступность сервера.</li><li>Обмен данными между клиентом и сервером должен осуществляться по протоколу TCP</li><li>Для обмена данными на сервере необходимо использовать <b>потоки ввода-вывода</b></li><li>Для обмена данными на клиенте необходимо использовать <b>сетевой канал</b></li><li>Сетевые каналы должны использоваться в неблокирующем режиме.</li></ul><p></p> <p><b>Обязанности серверного приложения:</b></p><ul><li>Работа с файлом, хранящим коллекцию.</li><li>Управление коллекцией объектов.</li><li>Назначение автоматически генерируемых полей объектов в коллекции.</li><li>Ожидание подключений и запросов от клиента.</li><li>Обработка полученных запросов (команд).</li><li>Сохранение коллекции в файл при завершении работы приложения.</li><li>Сохранение коллекции в файл при исполнении специальной команды, доступной только серверу (клиент такую команду отправить не может).</li></ul><b>Серверное приложение должно состоять из следующих модулей (реализованных в виде одного или нескольких классов):</b><ul><li>Модуль приёма подключений.</li><li>Модуль чтения запроса.</li><li>Модуль обработки полученных команд.</li><li>Модуль отправки ответов клиенту.</li></ul>Сервер должен работать в <b>однопоточном</b> режиме.<p></p> <p><b>Обязанности клиентского приложения:</b></p><ul><li>Чтение команд из консоли.</li><li>Валидация вводимых данных.</li><li>Сериализация введённой команды и её аргументов.</li><li>Отправка полученной команды и её аргументов на сервер.</li><li>Обработка ответа от сервера (вывод результата исполнения команды в консоль).</li><li>Команду <code>save</code> из клиентского приложения необходимо убрать.</li><li>Команда <code>exit</code> завершает работу клиентского приложения.</li></ul><b>Важно! </b> Команды и их аргументы должны представлять из себя объекты классов. Недопустим обмен "простыми" строками. Так, для команды add или её аналога необходимо сформировать объект, содержащий тип команды и объект, который должен храниться в вашей коллекции.<p></p> <p><b>Дополнительное задание: </b><br>Реализовать логирование различных этапов работы сервера (начало работы, получение нового подключения, получение нового запроса, отправка ответа и т.п.) с помощью <b>Logback</b></p></div>
### Class diagram
![class diagram](doc/Lab5_inteliJ.png)

## 2. Sources
**Структура папок**

Рабочая область по умолчанию содержит две папки, где:

- [`src`](https://github.com/NgToanRob/ProgrammingLab5/tree/main/src): папка для хранения исходников
- [`lib`](https://github.com/NgToanRob/ProgrammingLab5/tree/main/lib): папка для сохранения зависимостей

Между тем, скомпилированные выходные файлы будут созданы в папке [`bin`](https://github.com/NgToanRob/ProgrammingLab5/tree/main/bin) по умолчанию.

> Если вы хотите настроить структуру папок, откройте [`.vscode/settings.json`](https://github.com/NgToanRob/ProgrammingLab5/tree/main/.vscode) и обновите там соответствующие настройки.

## 3. Results
```linux
The collection has been uploaded successfully!
$ show
Organization ID: 1 (added 2022-03-15 00:25:56.282836700)
 Name: Toan's plaza
 Coordinates: X:12 Y:21.0
 Annual turnover: 100000
 Organization Type: OPEN_JOINT_STOCK_COMPANY
 Official address: Address [street=null, zipCode=nom]
$ info
Collection Information:
 Type: java.util.ArrayList
 Number of elements: 1
 Date of the last save: in this session has not yet occurred
 Date of last initialization: 2022-03-15 00:27:03.471676300 
$ help
help                                 display help on available commands
info                                 print information about the collection to standard output (type, initialization date, number of elements, etc.)
show                                 display all items in the collection
add {element}                        add a new element to the collection
update <ID> {element}                update the value of the collection element whose id is equal to the given one
remove_by_id <ID>                    remove item from collection by ID
clear                                clear the collection
save                                 save collection to file
exit                                 terminate program (without saving to file)
execute_script <file_name>           execute script from specified file
add_if_min {element}                 update the value of the collection element whose id is equal to the given one
remove_greater {element}             remove from the collection all elements greater than the specified
history                              display history of used commands
average_of_annual_turnover           read and execute the script from the specified file
count_greater_than_official_address  print the number of elements whose officialAddress field value is greater than the specified one
filter_greater_than_type  <OrganizationType>display elements whose organization type field value is equal to the given one
$
```

## 4. Conclusion

Во время выполнения данной лабораторной работы мы закрепили принципы SOLID, собственные исключения и многое другое. Также мы научились использовать Javadoc, работать с потоками, файлами, интерфейсами Comparable и Comparator. Узнали что такое сериализация и десериализация.
