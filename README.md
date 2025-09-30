LearnCardsv3 (Description created with the support of DeepSeek AI)

##Android приложение для обучения с использованием карточек (flashcards) с поддержкой изображений и текста.

##Функциональность

- Создание карточек с вопросами и ответами
- Добавление изображений к карточкам
- Режим обучения с проверкой знаний
- Локальное хранение данных
- Категории карточек
- Статистика обучения

##Скриншоты




<img width="369" height="765" alt="studio64_lyQiCz3030" src="https://github.com/user-attachments/assets/1a5a4c07-df45-437a-bce9-2b6b9051cc1d" />
<img width="348" height="755" alt="studio64_OHaW5QSBRO" src="https://github.com/user-attachments/assets/6311b167-a3e8-4d2c-925d-742d5329ebf7" />
<img width="369" height="749" alt="studio64_zT5hgppIf2" src="https://github.com/user-attachments/assets/a5c7a078-6d5a-4f34-9ac3-8aa15e8ed2f3" />




##Технологии

- **Kotlin** - язык программирования
- **Android SDK** - платформа разработки
- **View Binding** - работа с UI элементами
- **RecyclerView** - списки карточек
- **SharedPreferences** - локальное хранилище
- **Glide** - загрузка изображений
- **Gson** - работа с JSON






##Структура проекта

app/src/main/java/com/example/v3/

├──MainActivity.kt          # Главный экран

├──StudyActivity.kt         # Режим обучения

├──AddCardActivity.kt       # Добавление карточек

├──Card.kt                  # Модель данных

├──CardAdapter.kt           # Адаптер для списка

└──CardRepository.kt        # Управление данными
