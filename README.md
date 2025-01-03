# Bank Account System

This is a bank account system built with Spring Boot. The system supports deposit and withdrawal transactions, and it includes an API for managing transactions and accounts. The application uses Docker to run MongoDB and Gradle as the build tool.

### Prerequisites

Before running the application, ensure you have the following installed:
- **Docker** (for running MongoDB)
- **Gradle** (for building and running the application)
- **Postman** (for API testing, with the provided `postman_collection.json`)

## Setup
### Step 1: Clone the repository
Clone the repository to your local machine:

```bash
git clone https://github.com/yourusername/bank-account-system.git
cd bank-account-system
```

### Step 2: Run MongoDB with Docker
Start MongoDB using Docker by running the following command:

```bash
docker-compose up -d
```
This will start MongoDB on the default port `27017`. Make sure that `docker-compose.yml` is set up correctly to link your application with the MongoDB container.

### Step 3: Build and Run the Application with Gradle
To build and run the application, use the following Gradle commands:
1. Build the project:
```bash 
gradle build
```
2. Run the application:
```bash 
gradle bootRun
```
The application will start on port `8080` by default.

### Step 4: Import the Postman Collection
To test the API, import the provided `postman_collection.json` into Postman. This collection includes requests for creating transactions, depositing, withdrawing, and handling errors.

1. Open Postman.
2. Click on the "Import" button.
3. Select the `postman_collection.json` file from the project folder.
4. The collection will appear in Postman, and you can use it to send requests to the running API.

## Endpoints
### POST /transactions
Create a new transaction (deposit or withdrawal).
**Request Body**:
```json
{
  "accountId": "1",
  "type": "deposit", // or "withdrawal"
  "amount": 500
}
```
**Responses**:
- **200 OK**: Transaction successfully created.
- **400 Bad Request**: Invalid transaction type or insufficient funds for withdrawal.

## Testing
You can run the tests using Gradle:
```bash
gradle test
```
This will execute all unit tests and ensure that the system works correctly.

## Troubleshooting
- Ensure that Docker is running before trying to connect to MongoDB.
- If the application fails to start, check if the MongoDB container is up and accessible.
- If the Gradle build fails, verify that your environment has Gradle properly installed.