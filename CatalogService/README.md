API Catalog-Service: Управление каталогом товаров и для фильтрации и поиска

GET /products/{id}: Получение детальной информации о товаре по ID.
GET /products: Получение списка товаров с возможностью фильтрации.
POST /products: Добавление нового товара в каталог.
PUT /products/{id}: Обновление информации о товаре.
DELETE /products/{id}: Удаление товара из каталога.

Обновление настроек Catalog Service:
POST localhost:8083/actuator/refresh