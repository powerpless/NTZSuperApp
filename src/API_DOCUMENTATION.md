# 🔐 Auth API 

## 📍 Базовый путь:


---

## 🔸 Регистрация нового пользователя

**POST** `/auth/register`

Регистрирует нового пользователя, создавая `User`, `Person` и назначая роль `ROLE_USER`.

### Тело запроса (`application/json`):
```json
{
  "username": "tima",
  "email": "tima@example.com",
  "password": "123456",
  "firstName": "Tima",
  "lastName": "Auezov",
  "middleName": "K",
  "nickName": "timakz"
}

200 OK: "Success!"

400 BAD REQUEST: если имя пользователя или email уже существуют
"Choose different name"
"Choose different email"

## 🔸 Вход пользователя (логин)
POST /auth/login

{
  "username": "tima",
  "password": "123456"
}

{
  "success": true,
  "jwt": "eyJhbGciOiJIUzI1NiIsInR...",
  "expirationTime": 1715479356000,
  "username": "tima",
  "message": "Login successful"
}

{
  "success": false,
  "jwt": null,
  "expirationTime": 0,
  "username": null,
  "message": "Invalid username or password"
}

нужно использовать в заголовке:
Authorization: Bearer <твой_токен>

# 👤 User API Документация

## 📍 Базовый путь:
/api/users


---

## 🔹 Получить пользователя по ID

**GET** `/api/users/me/{id}`

Возвращает пользователя по его `id`.

### Параметры:
- `id` (Long): идентификатор пользователя

### Пример запроса:
GET /api/users/me/1

### Ответ:
```json
{
    "id": 1,
    "username": "tima",
    "password": "$2a$10$XvJs/qKpTH89UXL0xj9XHu24Co4tW7g9VJnWqAE9bIBWZoqOIBoZ.",
    "email": "tima@mail.ru",
    "person": {
        "id": 1,
        "firstName": "Тимур",
        "lastName": "Миронов",
        "middleName": "Анатольевич",
        "nickName": "dystopia",
        "hasBeenDeleted": false
    },
    "hasBeenDeleted": false,
    "roles": [
        {
            "id": 1,
            "name": "ROLE_USER",
            "users": []
        }
    ]
}

🔹 Получить текущего пользователя по JWT
GET /api/users/jwt/me
Authorization: Bearer <твой_токен>


🔹 Обновить никнейм пользователя
PUT /api/users/update/{id}

Обновляет никнейм пользователя (через связанную сущность Person) по его id.

Параметры:
id (Long): идентификатор пользователя

Тело запроса:
{
  "newNickname": "timagreat"
}

Ответ:
200 OK: "Nickname was updated"

404 Not Found: если пользователь не найден

500 Internal Server Error: если у пользователя нет связанной Person


# 🏋️ Workout API Документация

## 📍 Базовый путь:

---

## ➕ Добавить тренировку

**POST** `/api/workout/{userId}`

Добавляет новую тренировку для пользователя.

### Параметры пути:
- `userId` — ID пользователя

### Тело запроса (JSON):
```json
{
  "exercise": "Leg Press",
  "sets": 3,
  "reps": 10,
  "weight": 80,
  "date": "2025-05-11"
}

Ответ:
{
    "id": 4,
    "exercise": "Leg Press",
    "sets": 3,
    "reps": 10,
    "weight": 80,
    "user": {
        "id": 1,
        "username": "tima",
        "password": "$2a$10$XvJs/qKpTH89UXL0xj9XHu24Co4tW7g9VJnWqAE9bIBWZoqOIBoZ.",
        "email": "tima@mail.ru",
        "person": {
            "id": 1,
            "firstName": "Тимур",
            "lastName": "Миронов",
            "middleName": "Анатольевич",
            "nickName": "dystopia",
            "hasBeenDeleted": false
        },
        "hasBeenDeleted": false,
        "roles": [
            {
                "id": 1,
                "name": "ROLE_USER",
                "users": []
            }
        ]
    },
    "date": "2025-05-11"
}


 Получить тренировку по ID
GET /api/workout/{id}

Возвращает тренировку по её ID.

Параметры пути:
id — ID тренировки

Ответ:
200 OK: возвращает объект Workout

404 Not Found: если тренировка не найдена


Получить прогресс по упражнению
GET /api/workout/progress/{userId}/{exercise}


Возвращает прогресс пользователя по определённому упражнению (например, динамику веса).
Параметры пути:
userId — ID пользователя

exercise — название упражнения (например, Squat)
GET http://localhost:8080/api/workout/progress/1/bench press
Ответ:
Прогресс по 'bench press':
- Вес: 70 кг → 85 кг (21,43%)
- Повторения: 10 → 10 (0,00%)
- Сеты: 3 → 3 (0,00%)

Если тренировок меньше двух(не включительно) то даст ответ:
200 OK: Недостаточно данных для анализа прогресса

♻️ Обновить тренировку
PUT /api/workout/{id}

Обновляет тренировку по её ID.

Параметры пути:
id — ID тренировки
{
  "exercise": "Deadlift",
  "sets": 4,
  "reps": 5,
  "weight": 100,
  "date": "2025-05-10"
}
Ответ:
200 OK: обновлённая тренировка

404 Not Found: если тренировка не найдена

-------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------
------------------------CATEGORY - DICITEM - ITEMCATALOG - CONTROLLERS--------------------------------------
-------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------


1. CATEGORY CONTROLLER 

Base URL: /api/categories

    1.1. Создать категорию
        Метод: POST
        URL: /api/categories/me
        Тело запроса (JSON):
        {
        "ruName": "Электроника",
        "engName": "Electronics"
        }
        Response: 201 created
        
    1.2. Получить все категории
        Метод: GET
        URL: /api/categories
        Response: 200 OK
    1.2.1 Получить все категории по владельцу
        Метод: GET
        URL: /api/categories/me
        Response: 200 OK

    1.3. Получить категорию по ID
        Метод: GET
        URL: /api/categories/{id}
        Параметры URL:
        id (Long) — идентификатор категории
        Тело запроса: отсутствует
        Пример ответа (200 OK):
        Ошибки:
        404 Not Found — если категории с таким id нет.

    1.4. Обновить категорию
        Метод: PUT
        URL: /api/categories/{id}
        Параметры URL:
        id (Long) — идентификатор категории
        Тело запроса (JSON):
        {
         "ruName": "String",
         "engName": "String"
        }
        Response: 200 OK
        Ошибки:
        404 Not Found — если id не найден.

    1.5. Удалить категорию
        Метод: DELETE
        URL: /api/categories/{id}
        Параметры URL:
        id (Long) — идентификатор категории
        Тело запроса: отсутствует
        Пример ответа:
        Статус 204 No Content
        Ошибки:
        404 Not Found — если id не найден.

2. ITEMCATALOG CONTROLLER

Base URL: /api/item-catalogs


        2.1. Создать запись в каталоге предметов
            Метод: POST
            URL: /api/item-catalogs
            Тело запроса (JSON):
            {
             "ruName": "Ноутбук",
             "engName": "Laptop",
              "price": 1500.0,
              "color": "Silver",
              "weight": 2.5,
              "categoryId": 5
            }  
            Пример ответа (201 Created):    
            {
              "id": 12,
              "ruName": "Ноутбук",
              "engName": "Laptop",
              "price": 1500.0,
              "color": "Silver",
              "weight": 2.5,
              "category": {
                "id": 5,
                "ruName": "Электроника",
                "engName": "Electronics"
              }
            }
            
        2.2. Получить все записи каталога
            Метод: GET          
            URL: /api/item-catalogs           
            Тело запроса: отсутствует           
            Пример ответа (200 OK):        
            [
              {
                "id": 12,
                "ruName": "Ноутбук",
                "engName": "Laptop",
                "price": 1500.0,
                "color": "Silver",
                "weight": 2.5,
                "category": { "id": 5, "ruName": "Электроника", "engName": "Electronics" }
              },
              { /* … другие записи … */ }
            ]
            
        2.2.1 Получить все записи каталога по владельцу
            Метод: GET          
            URL: /api/item-catalogs/me           
            Тело запроса: отсутствует           
            Пример ответа (200 OK):        
            [
              {
                "id": 12,
                "ruName": "Ноутбук",
                "engName": "Laptop",
                "price": 1500.0,
                "color": "Silver",
                "weight": 2.5,
                "category": { "id": 5, "ruName": "Электроника", "engName": "Electronics" }
              },
              { /* … другие записи … */ }
            ]
            
        2.3. Получить запись каталога по ID
            Метод: GET          
            URL: /api/item-catalogs/{id}            
            Параметры URL:            
            id (Long) — идентификатор записи каталога            
            Тело запроса: отсутствует            
            Пример ответа (200 OK):            
            {
              "id": 12,
              "ruName": "Ноутбук",
              "engName": "Laptop",
              "price": 1500.0,
              "color": "Silver",
              "weight": 2.5,
              "category": { "id": 5, "ruName": "Электроника", "engName": "Electronics" }
            }
            
        2.4. Обновить запись каталога
            Метод: PUT            
            URL: /api/item-catalogs/{id}            
            Параметры URL:            
            id (Long) — идентификатор записи каталога            
            Тело запроса (JSON):                    
            {
              "ruName": "Ноутбук Pro",
              "engName": "Laptop Pro",
              "price": 1800.0,
              "color": "Space Gray",
              "weight": 2.3,
              "categoryId": 5
            }             
            Пример ответа (200 OK):                       
            {
              "id": 12,
              "ruName": "Ноутбук Pro",
              "engName": "Laptop Pro",
              "price": 1800.0,
              "color": "Space Gray",
              "weight": 2.3,
              "category": { "id": 5, "ruName": "Электроника", "engName": "Electronics" }
            }
            
        2.5. Удалить запись каталога
            Метод: DELETE            
            URL: /api/item-catalogs/{id}            
            Параметры URL:            
            id (Long) — идентификатор записи каталога            
            Тело запроса: отсутствует            
            Пример ответа:            
            Статус 204 No Content


3. DICITEM CONTROLLER

Base URL: /api/dic-items


        3.1. Добавить предмет пользователю
            Метод: POST
           
            URL: /api/dic-items/me           
            Параметры URL:            
            me — owner (user)           
            Тело запроса (JSON):                  
            {
              "name": "MacBook Air",
              "photoType": "image/jpeg",
              "photoBytes": "BASE64_ENCODED_STRING",
              "itemCatalogId": 12,
              "attributes": [
                { "key": "Процессор", "value": "M2" },
                { "key": "Цвет", "value": "Серебристый" }
              ]
            }                  
            Пример ответа (201 Created):
            {
              "id": 33,
              "name": "MacBook Air",
              "hasBeenDeleted": false,
              "itemCatalog": {
                "id": 12,
                "ruName": "Ноутбук",
                "engName": "Laptop",
                /* … */
              },
              "owner": {
                "id": 7,
                "username": "ivanov",
                /* … */
              }
            }
            
        3.2. Получить DicItem по ID
            Метод: GET            
            URL: /api/dic-items/{id}            
            Параметры URL:            
            id (Long) — идентификатор DicItem            
            Тело запроса: отсутствует            
            Пример ответа (200 OK):
            {
              "id": 33,
              "name": "MacBook Air",
              "hasBeenDeleted": false,
              "itemCatalog": { "id": 12, "ruName": "Ноутбук", "engName": "Laptop", … },
              "owner":       { "id": 7,  "username": "ivanov", … }
            }
            
        3.3. Обновить DicItem
            Метод: PUT            
            URL: /api/dic-items/{id}            
            Параметры URL:            
            id (Long) — идентификатор DicItem           
            Тело запроса (JSON):
            {
              "name": "MacBook Air M2",
            }             
            Пример ответа (200 OK):
            {
              "id": 33,
              "name": "MacBook Air M2",
              /* … остальная структура как выше … */
            }
            Ошибки:            
            400 Bad Request — если name пустое или null            
            404 Not Found — если id не найден
            
        3.4. Удалить (пометить) DicItem
            Метод: DELETE            
            URL: /api/dic-items/{id}            
            Параметры URL:            
            id (Long) — идентификатор DicItem            
            Тело запроса: отсутствует            
            Пример ответа:           
            Статус 204 No Content (внутри hasBeenDeleted ставится true)           
            Ошибки:            
            404 Not Found — если id не найден

Expense & Budget API — Документация (v2)
1. Добавить трату
POST /api/expenses

Добавляет трату пользователю.
Если категория уже существует — сумма и описание обновляются.
Если по категории установлен бюджет — возвращается флаг overBudget.

🔸 Request Headers:
Content-Type: application/json

Authorization: Bearer <JWT_TOKEN>

🔸 Request Body (JSON):
{
  "amount": 300.0,
  "description": "Ужин",
  "category": "ЕДА"
}
 Response (200 OK):
{
  "expense": {
    "id": 1,
    "amount": 1200.0,
    "description": "Завтрак; Ужин",
    "category": "ЕДА"
  },
  "overBudget": true,
  "budgetLimit": 1000.0,
  "currentTotal": 1200.0
}

2. Получить все траты пользователя
GET /api/expenses

[
  {
    "id": 1,
    "amount": 1200.0,
    "description": "Завтрак; Ужин",
    "category": "ЕДА"
  },
  {
    "id": 2,
    "amount": 400.0,
    "description": "Проезд",
    "category": "ТРАНСПОРТ"
  }
]

3. Получить список доступных категорий
GET /api/expenses/categories

🔸 Response:
[
  "ЕДА",
  "ТРАНСПОРТ",
  "РАЗВЛЕЧЕНИЯ",
  "ПОДПИСКИ",
  "ПОКУПКИ",
  "ДОМ",
  "ОБРАЗОВАНИЕ",
  "МЕДИЦИНА",
  "СПОРТ",
  "ПУТЕШЕСТВИЯ"
]

4. Установить бюджет по категории
POST /api/expenses/budgets

🔸 Query Parameters:
category — категория (например: ЕДА)

amount — лимит бюджета, например: 1000

Пример:
/api/expenses/budgets?category=ЕДА&amount=1000

🔸 Response:
{
  "id": 1,
  "category": "ЕДА",
  "amount": 1000.0
}

5. Получить все бюджеты пользователя
GET /api/expenses/budgets
🔸 Response:
[
  {
    "id": 1,
    "category": "ЕДА",
    "amount": 1000.0
  },
  {
    "id": 2,
    "category": "ТРАНСПОРТ",
    "amount": 500.0
  }
]
