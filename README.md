# airports-search-abastrychkin

Реализовано консольное Java-приложение, позволяющее быстро искать
данные из файла "airports.csv" аэропортов по вводимому пользователем тексту.

# Дополнительные вопросы по задаче и принятые решения:

Вопрос 1. Если в первой строке столбца указано значение, являющееся строкой в кавычках, (например, "\"Goroka Airport\""), то в остальный строках также будет строка в кавычках?

Ответ: да.

Вопрос 2. Если в первой строке столбца указано значение, являющееся строкой, хранящей число, (например, "-6.081689834590001"), то в остальный строках также будет строка с числом?

Ответ: да.

Вопрос 3. Возможно ли небольшое ожидание пользователя перед первым посиком (до того, как программа будет готов к поиску будет показана строка загрузки)?

Ответ: да

Вопрос 4. Можно ли предположить, что количество строк файла меньше, чем Integer.MAX_VALUE?

Ответ: да, кооличество строк файла не приближается к Integer.MAX_VALUE.

# Инструкция по запуску
1. Расположить jar-файл и файл "airports.csv" в одном каталоге. 

![изображение](https://user-images.githubusercontent.com/107584204/183878197-ddd29539-9f3c-4449-8e1f-e8f239838788.png)

2. В командной строке в выбраном каталоге ввести команду
java -jar -Xmx7m airports-search-abastrychkin.jar <Номер столбца, начиная с 1>

![изображение](https://user-images.githubusercontent.com/107584204/183879257-14ec051d-8939-4c89-86aa-168b4790c8b4.png)


3. После загрузки приложения вветси шаблон для поиска

![изображение](https://user-images.githubusercontent.com/107584204/183879435-d5aef8de-215b-488d-9a1a-bce538977381.png)

4. Далее будет выведена информация о найденных строках (или сообщение о том, что строк не найдено), а также информация о количестве найденных строк и время работы посика

![изображение](https://user-images.githubusercontent.com/107584204/183879849-807bca36-4dbd-4b96-986f-e7d1cad25d2c.png)

5. После вывода результатов будет предложено вветси новый шаблон для поиска. Для выхода из приложения необходимо набрать команду "!quit" 


