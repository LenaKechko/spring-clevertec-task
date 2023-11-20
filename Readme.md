В приложении реализоваты основные crud-операции: сохранение, удаление, поиск, изменение.
Работа с базой данных осуществлялась с помощью JDBC. 
Характеристики подключения располагаются в resource/database.properties.
Для работы использовалось кэширование, дабы сократить нагрузку на бд.
Алгоритм кэширования(LFU или LRU) и размер кэша находятся в resource/application.yml.  

Недостатком программы является то, что на сервере PostgreSQL необходимо вручную создать 
базу данных zoodb, для ее заполнения можно воспользоваться небольшим файлом в resource/zoodb.sql

В качестве предметной области для базы данных был выбран зоопарк (сведения о животном).

Архитектура сущности:
UUID id - Уникальный идентификатор. Генерируется базой. Является уникальным и не может быть null
String name - Наименование животного. До 15 символов, не может быть null.
String typeOfAnimal - Вида животного. До 15 символов
String classOfAnimal - Класс животного. До 15 символов
double weight - Вес животного. Единица измерения кг. Не может быть отрицательным и нулевым
double height - Высота животного. Единица измерения м. Не может быть отрицательным и нулевым
double speed - Скорость животного. Единица измерения км/ч. Не может быть отрицательным и нулевым

На поля предусмотрена валидация на уровне dto.

Инструкция по запуску приложения.
Для работы необходимо база данных, которая определена файлом zoodb.sql. 
Запуск приложения осуществляется с помощью класса Main. 

---------------------------------

Операция вывода всех данных выводит на консоль список животных в формате DTO, т.е. без id

Пример работы:

AnimalDto(name=Слон, typeOfAnimal=Хоботные, classOfAnimal=Млекопитающие, weight=6640.0, height=6.8, speed=40.0)
AnimalDto(name=Бегемот, typeOfAnimal=Парнокопытные, classOfAnimal=Млекопитающие, weight=3000.0, height=1.5, speed=35.0)
AnimalDto(name=Лев, typeOfAnimal=Кошачие, classOfAnimal=Млекопитающие, weight=200.0, height=1.2, speed=80.0)
AnimalDto(name=Жираф, typeOfAnimal=Парнокопытные, classOfAnimal=Млекопитающие, weight=1200.0, height=6.1, speed=55.0)
AnimalDto(name=Носорог, typeOfAnimal=Непарнокопытные, classOfAnimal=Млекопитающие, weight=2900.0, height=2.0, speed=45.0)

---------------------------------

Добавление животного. Для этого необходимо создать dto-объект и выполнить операцию create
Операция возвращает id созданного объекта.

Пример работы:
Добавляемое животное
AnimalDto(name=Animal, typeOfAnimal=type, classOfAnimal=class, weight=1000.0, height=1.5, speed=40.0)

Содержимое таблицы в бд после операции
AnimalDto(name=Слон, typeOfAnimal=Хоботные, classOfAnimal=Млекопитающие, weight=6640.0, height=6.8, speed=40.0)
AnimalDto(name=Бегемот, typeOfAnimal=Парнокопытные, classOfAnimal=Млекопитающие, weight=3000.0, height=1.5, speed=35.0)
AnimalDto(name=Лев, typeOfAnimal=Кошачие, classOfAnimal=Млекопитающие, weight=200.0, height=1.2, speed=80.0)
AnimalDto(name=Жираф, typeOfAnimal=Парнокопытные, classOfAnimal=Млекопитающие, weight=1200.0, height=6.1, speed=55.0)
AnimalDto(name=Носорог, typeOfAnimal=Непарнокопытные, classOfAnimal=Млекопитающие, weight=2900.0, height=2.0, speed=45.0)
AnimalDto(name=Animal, typeOfAnimal=type, classOfAnimal=class, weight=1000.0, height=1.5, speed=40.0)

---------------------------------

Изменение данных происходит по уникальному идентификатору. Предусмотрена валидация новым данных 
на этапе их ввода.
Пример работы:
Новое животное AnimalDto(name=Страус, typeOfAnimal=type, classOfAnimal=class, weight=1000.0, height=1.5, speed=40.0)
id получен на предыдущем этапе

Обновляется элемент и записывается в бд

Содержимое таблицы после операции:
AnimalDto(name=Слон, typeOfAnimal=Хоботные, classOfAnimal=Млекопитающие, weight=6640.0, height=6.8, speed=40.0)
AnimalDto(name=Бегемот, typeOfAnimal=Парнокопытные, classOfAnimal=Млекопитающие, weight=3000.0, height=1.5, speed=35.0)
AnimalDto(name=Лев, typeOfAnimal=Кошачие, classOfAnimal=Млекопитающие, weight=200.0, height=1.2, speed=80.0)
AnimalDto(name=Жираф, typeOfAnimal=Парнокопытные, classOfAnimal=Млекопитающие, weight=1200.0, height=6.1, speed=55.0)
AnimalDto(name=Носорог, typeOfAnimal=Непарнокопытные, classOfAnimal=Млекопитающие, weight=2900.0, height=2.0, speed=45.0)
AnimalDto(name=Страус, typeOfAnimal=type, classOfAnimal=class, weight=1000.0, height=1.5, speed=40.0)


---------------------------------

Удаление записи осуществляется по уникальному идентификатору.

Пример работы:
id получен на предыдущих этапах.

Результат работы операции
AnimalDto(name=Слон, typeOfAnimal=Хоботные, classOfAnimal=Млекопитающие, weight=6640.0, height=6.8, speed=40.0)
AnimalDto(name=Бегемот, typeOfAnimal=Парнокопытные, classOfAnimal=Млекопитающие, weight=3000.0, height=1.5, speed=35.0)
AnimalDto(name=Лев, typeOfAnimal=Кошачие, classOfAnimal=Млекопитающие, weight=200.0, height=1.2, speed=80.0)
AnimalDto(name=Жираф, typeOfAnimal=Парнокопытные, classOfAnimal=Млекопитающие, weight=1200.0, height=6.1, speed=55.0)
AnimalDto(name=Носорог, typeOfAnimal=Непарнокопытные, classOfAnimal=Млекопитающие, weight=2900.0, height=2.0, speed=45.0)

Были написаны тесты:
Для валидации:
-проверка с корректными результатами -> true
-проверка, когда отсутствует имя -> ValidatorException
-проверка, когда имя больше указанных символов (15 символов) -> ValidatorException
-проверка, когда цифровые значения отрицательные или ноль-> ValidatorException

Для кэширования проверка всех операций:
-поиск элемента ключу
-добавление элемента в незаполненный кэш
-добавление элемента в полный кэш, с проверкой на неизменнные величины хранилища
-удаление элемента

Для класса-сервиса:
-проверка добавление элемента в бд
-проверка нахождения элемента в бд
-проверка отсутствия элемента в бд с последующим выбросом исключения AnimalNotFoundException
-проверка на удаление элемента из таблицы бд
-проверка на изменение элемента в бд
-проверка на возвращение не пустого списка с учетом заполненной бд