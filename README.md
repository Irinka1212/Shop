# Shop

This is a simple Java-based shop management system that simulates the operations of a store, including managing products, cashiers, clients, carts, and receipts. The system supports calculating turnover, delivery costs, and profits, as well as handling exceptions like insufficient funds, expired products, or invalid quantities.

## Features

- Manage products and stock levels
- Add cashiers and cash registers
- Process client purchases through carts
- Generate and serialize receipts
- Calculate:
  - Total turnover (gross revenue from sales)
  - Delivery costs
  - Profit
- Handle exceptions:
  - `InvalidQuantityException`
  - `ExpiredProductException`
  - `InsufficientQuantityException`
  - `InsufficientFundsException`
  - `ReceiptFileNotFoundException`
