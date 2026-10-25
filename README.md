# shawarma-tests

Автотесты API сервиса Shawarma: авторизация и доменные события.

## Стек

- Java 17+
- Gradle (wrapper в проекте)
- JUnit 5, REST-assured, AssertJ
- Jackson (десериализация моделей)
- Allure для отчётов

## Структура

```
src/
├── test/java/
│   ├── clients/                # HTTP-клиенты: AuthApiClient, EventsApiClient
│   ├── factories/              # тестовые данные (AuthFactory)
│   ├── models/                 # DTO запросов и ответов
│   ├── tests/
│   │   ├── authentication/     # тесты и шаги авторизации
│   │   └── events/             # тесты и шаги доменных событий
│   └── utils/Config.java       # чтение настроек
└── test/resources/
    ├── config.properties.example   # шаблон настроек
    └── openapi.json                # спецификация API (копия)
```

## Настройка

1. Скопируй шаблон настроек:

```bash
   cp src/test/resources/config.properties.example src/test/resources/config.properties
```

2. Заполни значения в `config.properties` или передай их через переменные окружения и system properties (приоритет: `-Dkey` → `-Dshawarma.key` → env → файл):

   | Ключ | Переменная окружения | Что это |
   |---|---|---|
   | `baseUrl` | `SHAWARMA_BASE_URL` | Адрес API (по умолчанию `https://shawarma.threadqa.ru/api/v1`) |
   | `username` | `SHAWARMA_USERNAME` | Логин для позитивных сценариев |
   | `password` | `SHAWARMA_PASSWORD` | Пароль |
   | `invalidUsername` | `SHAWARMA_INVALID_USERNAME` | Заведомо неверный логин |
   | `invalidPassword` | `SHAWARMA_INVALID_PASSWORD` | Заведомо неверный пароль |

   Файл `config.properties` добавлен в `.gitignore` и в репозиторий не попадает. Учётные данные в коммиты не кладём.

## Запуск

```bash
./gradlew test
```

Отчёт Allure (собирается и открывается в браузере):

```bash
./gradlew allureServe
```

Только собрать отчёт без запуска сервера:

```bash
./gradlew allureReport
```

Если отчёт выглядит странно после нескольких прогонов — очисти старые результаты: `./gradlew clean test`.

## Спецификация

Спецификация API: `openapi.yaml` в корне репозитория, копия для тестов — `src/test/resources/openapi.json`.
