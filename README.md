Personal Expense & Budget Tracking Engine

A modular, command-line Java application for tracking daily cash flow, managing category budgets, and exporting transaction records to local CSV files[cite: 2, 3].

 Overview
This project provides an offline solution for logging personal finances without relying on heavy graphical interfaces or cloud services[cite: 3]. It records daily income and spending, calculates real-time balance metrics, triggers warnings when category budget caps reach 85% and 100%, and exports transaction histories to standard CSV files[cite: 2, 3].

Features
- Transaction Logging: Record income and expense entries with auto-generated timestamps, monetary amounts, categories, and descriptions[cite: 2, 3].
- Budget Threshold Alert: Define monthly spending limits per category and receive notifications at 85% capacity and 100% budget overload[cite: 2, 3].
- Financial Analytics: Compute total income, total expenditures, net savings, and category spending percentage breakdowns[cite: 2, 3].
- CSV Data Persistence: Export in-memory transactions into a local `transactions.csv` file for offline backup and spreadsheet analysis[cite: 2, 3].

 Tech Stack
- **Language**: Java (JDK 8 or higher)[cite: 2, 3]
- **Architecture**: Modular Object-Oriented structure (`model`, `service`, `util`, `test` packages)[cite: 3]
- **Interface**: Command Line Interface (CLI)[cite: 2, 3]

 Setup & Execution

 Prerequisites
Java Development Kit (JDK 8 or higher) installed on your system[cite: 2, 3]. Verify your installation:
```bash
java -version
git clone https://github.com/anika25bai11462-dotcom/PersonalExpenseTracker001.git
cd PersonalExpenseTracker001
javac -d bin src/com/tracker/*.java src/com/tracker/*/*.java
java -cp bin com.tracker.Main
java -ea -cp bin com.tracker.test.ExpenseTrackerTest
