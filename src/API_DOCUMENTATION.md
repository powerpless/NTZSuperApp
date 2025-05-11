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
