[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/vN5dLYMT)
# DesignPatterns

Project summary:
- Inventory management system for Products, Buyers, Employees, Purchase Orders, and Invoices.
- Controllers expose CRUD endpoints; Repositories handle persistence.
- Business workflows use design patterns to keep concerns decoupled and extensible.

Design patterns and roles:
- Strategy
  - Purpose: Encapsulate CRUD and workflow differences per domain; controllers choose a strategy and call executeAdd/executeUpdate/executeDelete.
- Observer
  - Purpose: On product add, validate/save to DB and notify all buyers.
- Decorator
  - Purpose: Compose order total without modifying base cart logic.
- State
  - Purpose: Vary stock update behavior; low stock also triggers alerts.
- Command
  - Purpose: Encapsulate and asynchronously run “start server” and “send message”.
- Factory
  - Purpose: Provide shared invoker instance for messaging.
- Facade
  - Purpose: Simplify messaging and PDF subsystems for callers.

Key flows:
- Add Product: ProductStrategy.add -> Observer.Notify -> UpdateDB saves, UpdateBuyers -> NotifyBuyers -> Facade.SendMessage -> Factory -> Command (Server+Client).
- Create Order: OrderStrategy.add -> Decorator builds total -> State updates stock (and may alert) -> save total to PurchaseOrder.
- Pay Order: InvoiceStrategy.add -> mark PO paid, create Invoice -> Facade.PDFGen generates and stores PDF path.

Note: A Prototype-style class exists (ItemAPI implements Cloneable and clone).
