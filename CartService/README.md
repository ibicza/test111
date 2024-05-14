GET /cart/{userId}: Получение текущего состояния корзины для пользователя.

POST /cart: Добавление товара в корзину.

Для добавления товара в корзину нужно, отправить json в теле

    {
    "id": 1, // id записи в таблице
    "cart": {
    "id": 1, // id card в которую добавляем товар
    "totalPrice": 0 // ценность корзины
    },
    "product": {
    "id": 1, // id товара
    "name": "nameTest",
    "description": "descriptionTest",
    "price": 12,
    "manufacturer": "manufacturerTest",
    "weight": 2
    },
    "quantity": 1 // количества именно этого товара в корзине
    }

PUT /cart/{userId}/item/{itemId}: Изменение количества конкретного товара в корзине.

DELETE /cart/{userId}/item/{itemId}: Удаление товара из корзины.

П.с. к проекту подключен swagger в нем более полная информация