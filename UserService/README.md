# UserService

At the start of the project, 
two text users are created with the 
following data
# first test user

Username "user" 

Password "user"

firstName "user"

lastName "user"

email "hdnhdxv@mail.ru"
roles[
    ROLE_USER]


# second test user

Username "admin" 

password "admin"

firstName "admin"

lastName "admin"

email "hdnhdxv@mail.ru"

roles[ROLE_USER , ROLE_ADMIN]


Контроллер "AddedController" предоставляет API-методы для чтения списка электронных адресов (emails) на основе списка идентификаторов пользователей (userIds).

API методы:

1. POST /list

   Описание: Получить список электронных адресов (emails) на основе списка идентификаторов пользователей (userIds).

   Параметры body: [
   1,
   2,
   3,
   4,
   5,
   6,
   7,
   8,
   9
   ]
    

   Ответ:
    - Список электронных адресов, соответствующих переданным идентификаторам пользователей.

   Пример запроса:

   GET /list
   
   body [
   1,
   2,
   3
   ]
   

   Пример ответа:

   ```
   {
      [
         "user1@example.com",
         "user2@example.com",
         "user3@example.com"
      ]
   }
   ```
   Коды ответов:
    - 200 OK: Успешный запрос, список электронных адресов возвращен в ответе.

   Зависимости:
    - Для работы контроллера требуются необходимые зависимости и конфигурации, такие как Spring Framework.

   Предварительные условия:
    - Проверьте, что список идентификаторов пользователей и соответствующие электронные адреса корректно хранятся в базе данных, чтобы контроллер возвращал актуальные данные.

   Пример использования:
    - Выполните HTTP POST-запрос, передав список идентификаторов пользователей в body. Получите список электронных адресов, соответствующих указанным идентификаторам пользователей.
