Приложение представляет из себя работу с rest-запросами. 

При запуске приложения поднимается база данных (в случае ее отсутствия).
В приложении реализованы основные rest-запросы (подробнее о crud-операциях описано ниже):
1. GET:  localhost/servlet-task/animals - вывод животных (по умолчанию первых 20) 
         localhost/servlet-task/animals?page={page} - вывод животных на соответствующей странице (каждая страница по 20 животных)
         localhost/servlet-task/animals/{uuid} или localhost/servlet-task/animals?uuid={uuid} - вывод животного по uuid
2. POST localhost/servlet-task/animals - создание животного по полученному json
3. PUT localhost/servlet-task/animals/{uuid} или localhost/servlet-task/animals?uuid={uuid} - изменение животного по заданному uuid и по полученному json
       localhost/servlet-task/animals/{uuid}?name={name}&typeOfAnimal={typeOfAnimal}&classOfAnimal={classOfAnimal}&weight={weight}&height={height}&speed={speed} или
       localhost/servlet-task/animals?uuid={uuid}&name={name}&typeOfAnimal={typeOfAnimal}&classOfAnimal={classOfAnimal}&weight={weight}&height={height}&speed={speed} -
        изменение животного по параметрам
4. DELETE localhost/servlet-task/animals/{uuid} или localhost/servlet-task/animals?uuid={uuid} - удаление животного по заданному uuid

Дополнительно предусмотрено изменение кодировки для корректного отображения пришедших данных на русском

В приложении реализованы основные crud-операции: сохранение, удаление, поиск, изменение.
Работа с базой данных осуществлялась с помощью JDBC. 
Характеристики подключения располагаются в resource/database.properties.
Для работы использовалось кэширование, дабы сократить нагрузку на бд.
Алгоритм кэширования(LFU или LRU) и размер кэша находятся в resource/application.yml.  

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

---------------------------------

Операция вывода всех данных выводит на консоль список животных в формате DTO, т.е. без id

Пример работы:

AnimalDto(name=Слон, typeOfAnimal=Хоботные, classOfAnimal=Млекопитающие, weight=6640.0, height=6.8, speed=40.0)
AnimalDto(name=Бегемот, typeOfAnimal=Парнокопытные, classOfAnimal=Млекопитающие, weight=3000.0, height=1.5, speed=35.0)
AnimalDto(name=Лев, typeOfAnimal=Кошачие, classOfAnimal=Млекопитающие, weight=200.0, height=1.2, speed=80.0)
AnimalDto(name=Жираф, typeOfAnimal=Парнокопытные, classOfAnimal=Млекопитающие, weight=1200.0, height=6.1, speed=55.0)
AnimalDto(name=Носорог, typeOfAnimal=Непарнокопытные, classOfAnimal=Млекопитающие, weight=2900.0, height=2.0, speed=45.0)

---------------------------------

Полученние данных по id животного. При полученнии данных дополнительно созданиется pdf-файла с данными об искомой сущности, 
в моем случае данные о животном по его идентификационному номеру.
Данные сохраняются по пути C:\Temp, что определено в конфигурацинном файле application.yml.
В имени файла содержится дата и время его создания.

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

Приложение покрыто соответствующими тестами.

В работе использованы паттерны:
1. Подключение к базе данных в виде паттерна Singleton.
2. В классе Writer реализован паттерн Strategy, также и в CacheProxyAspect реализовано нечто вроде Factory.