# Shop
F124021 Ирина Христова  
CITB408 Програмиране с Java

Гл. ас. д-р Христина Костадинова  

## Описание на проекта
Този проект реализира модел на магазин с управление на стоки, касиери и продажби.  
Системата поддържа:
- Доставка на стоки и актуализиране на наличностите.
- Продажби на клиенти чрез касиери.
- Издаване на касови бележки с детайли за продуктите, количества и обща цена.
- Проверка за валидност на продуктите (срок на годност, наличност и финанси на клиента).
- Съхраняване на касовите бележки във файлове и сериализация/десериализация.
- Изчисляване на разходи, приходи и печалба на магазина.

## Структура на проекта

### Основни класове

#### Cart
Количка за продукти:
- `productList` за съхранение на продуктите и количествата
- Връзка с `Shop` за проверка на наличности

Методи:
- `getProductList`
- `addProduct` – хвърля:
  - `InvalidQuantityException`  
  - `InsufficientQuantityException`  
  - `ExpiredProductException`

#### Cashier
Съдържа информация за касиера:
- `id` (константа)
- `name`
- `monthlySalary`
  
Методи:
- `getId`, `getName`, `getMonthlySalary`

#### CashRegister
Съдържа информация за каса на магазин:
- `cashier`
  
- Методи:
  - `getCashier()` 
  - `createReceipt` – издава, принтира и сериализира бележка

#### Client
Съдържа налични средства на клиента:
- `money`
  
Методи:
- `getMoney()`
- `canPay`
- `pay` – хвърля `InsufficientFundsException`

#### Product
Съдържа:
- `id` 
- `name`
- `deliveryPrice`
- `category` ProductCategory - FOOD/NON_FOOD
- `expirationDate`

Методи:
- `get` методи
- `getDaysUntilExpiration()`
- `isExpired()`

#### Receipt
Касова бележка:
- `number` 
- `cashier`
- `date`
- `entries`

Методи:
- `get` методи
- `addReceipt` – хвърля `InvalidQuantityException`
- `printReceipt`
- `saveReceiptToFile`
- `serializeReceipt`
- `writeReceipt`
- `deserializeReceipt`

#### ReceiptEntry
Съдържа:
- `Product`
- `quantity`
- `unitPrice`

Методи:
- `get` методи
- `getTotalPrice`
- `addQuantity` – хвърля `InvalidQuantityException`

#### Shop
Съдържа:
- `name`
- `stock`
- `cashiers`
- `registers`

Методи:
- `get` методи
- `getTotalDeliveryCost`, `getTotalTurnover`, `getTotalProfit`, `getProductQuantity`, `getSalaryExpenses`
- `deliverProduct` – доставка, хвърля `InvalidQuantityException`
- `processSale` – продажба, хвърля:
  - `ExpiredProductException`
  - `InsufficientQuantityException`
  - `InvalidQuantityException`
  - `InsufficientFundsException`
- `calculatePrice` – надценка и евентуална отстъпка
- `sellProduct`

#### Util
Абстрактен клас за четене на касови бележки от файл:
- Метод:
  - `readReceipt` – хвърля `ReceiptFileNotFoundException`

### Изключения
- `InvalidQuantityException`
- `InsufficientQuantityException`
- `ExpiredProductException`
- `InsufficientFundsException`
- `ReceiptFileNotFoundException`

## Тестване
Проектът е тестван с JUnit 5:
- Успешни сценарии и изключения
- Проверка на наличности, срок на годност и финанси на клиента
- Проверка на правилно изчисление на оборот, разходи и печалба
- Проверка на сериализация и десериализация на касови бележки
- Четене на файлове с касови бележки
