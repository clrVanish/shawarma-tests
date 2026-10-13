# shawarma-tests

Автотесты API сервиса Shawarma: проверка авторизации.

## Стек

- Java 17+
- Gradle (wrapper в проекте)
- JUnit 5, REST-assured, AssertJ
- Allure для отчётов

## Структура

```
src/
├── test/java/
│   ├── tests/authentication/   # тесты и шаги для авторизации
│   └── utils/Config.java       # чтение настроек
└── test/resources/
    ├── config.properties.example   # шаблон настроек
    └── openapi.json                # спецификация API
```

## Настройка

1. Скопируй шаблон настроек:

```bash
   cp src/test/resources/config.properties.example src/test/resources/config.properties
```

2. Заполни значения в `config.properties` или передай их через переменные окружения (приоритет: system property → env → файл):

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

Отчёт Allure:

```bash
./gradlew test
allure serve build/allure-results
```

Для `allure serve` нужен установленный Allure CLI.

## Спецификация

Описание эндпоинтов: `src/test/resources/openapi.json`. Чек-лист сценариев: `automation-checklist.md`.

<!-- TODO: добавить после появления файла в репозитории -->