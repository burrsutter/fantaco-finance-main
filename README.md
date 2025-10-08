# Fantaco Finance API

A comprehensive Finance REST API built with Java 21, Spring Boot 3.2, Maven, PostgreSQL, and containerized with Docker and Kubernetes.

## Features

- **Order Management**: Track and retrieve order history
- **Invoice Management**: Manage invoice history and status
- **Dispute Management**: Handle duplicate charge disputes
- **Receipt Management**: Find and manage lost receipts
- **RESTful API**: Clean, well-documented REST endpoints
- **Database Integration**: PostgreSQL with JPA/Hibernate
- **Containerization**: Docker with UBI9/OpenJDK base image
- **Kubernetes Ready**: Complete K8s deployment configurations

## Technology Stack

- **Java**: 21
- **Spring Boot**: 3.2.0
- **Build Tool**: Maven
- **Database**: PostgreSQL
- **Container**: Docker with UBI9/OpenJDK
- **Orchestration**: Kubernetes

## API Endpoints

### 1. Get Order History
**POST** `/api/finance/orders/history`

Retrieve order history for a specific customer with optional date filtering.

**Request Body:**
```json
{
  "customerId": "CUST-001",
  "startDate": "2024-01-01T00:00:00",
  "endDate": "2024-01-31T23:59:59",
  "limit": 50
}
```

**Response:**
```json
{
  "success": true,
  "message": "Order history retrieved successfully",
  "data": [
    {
      "id": 1,
      "orderNumber": "ORD-001",
      "customerId": "CUST-001",
      "totalAmount": 299.99,
      "status": "DELIVERED",
      "orderDate": "2024-01-15T10:30:00",
      "createdAt": "2024-01-15T10:30:00"
    }
  ],
  "count": 1
}
```

### 2. Get Invoice History
**POST** `/api/finance/invoices/history`

Retrieve invoice history for a specific customer with optional date filtering.

**Request Body:**
```json
{
  "customerId": "CUST-001",
  "startDate": "2024-01-01T00:00:00",
  "endDate": "2024-01-31T23:59:59",
  "limit": 50
}
```

**Response:**
```json
{
  "success": true,
  "message": "Invoice history retrieved successfully",
  "data": [
    {
      "id": 1,
      "invoiceNumber": "INV-001",
      "orderId": 1,
      "customerId": "CUST-001",
      "amount": 299.99,
      "status": "PAID",
      "invoiceDate": "2024-01-15T10:35:00",
      "dueDate": "2024-02-15T10:35:00",
      "paidDate": "2024-01-16T09:20:00"
    }
  ],
  "count": 1
}
```

### 3. Start Duplicate Charge Dispute
**POST** `/api/finance/disputes/duplicate-charge`

Create a new duplicate charge dispute for an order.

**Request Body:**
```json
{
  "customerId": "CUST-001",
  "orderId": 1,
  "description": "Charged twice for the same order",
  "reason": "Payment processor error caused duplicate charge"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Duplicate charge dispute started successfully",
  "data": {
    "id": 1,
    "disputeNumber": "DISP-ABC12345",
    "orderId": 1,
    "customerId": "CUST-001",
    "disputeType": "DUPLICATE_CHARGE",
    "status": "OPEN",
    "description": "Charged twice for the same order",
    "reason": "Payment processor error caused duplicate charge",
    "disputeDate": "2024-01-22T14:30:00"
  }
}
```

### 4. Find Lost Receipt
**POST** `/api/finance/receipts/find-lost`

Find or create a lost receipt record for an order.

**Request Body:**
```json
{
  "customerId": "CUST-001",
  "orderId": 1
}
```

**Response:**
```json
{
  "success": true,
  "message": "Lost receipt found/created successfully",
  "data": {
    "id": 1,
    "receiptNumber": "RCPT-ABC12345",
    "orderId": 1,
    "customerId": "CUST-001",
    "status": "LOST",
    "receiptDate": "2024-01-15T10:40:00"
  }
}
```

### 5. Health Check
**GET** `/api/finance/health`

Check the health status of the API.

**Response:**
```json
{
  "status": "UP",
  "service": "Fantaco Finance API",
  "timestamp": "2024-01-30T15:30:00"
}
```


## Local Development

### Prerequisites
- Java 21
- Maven 3.6+
- PostgreSQL 12+

### Setup Database
```bash
# Create database
createdb fantaco_finance

# Create user
psql -d fantaco_finance -c "CREATE USER fantaco_user WITH PASSWORD 'fantaco_password';"
psql -d fantaco_finance -c "GRANT ALL PRIVILEGES ON DATABASE fantaco_finance TO fantaco_user;"
```

### Run Application
```bash
# Build the project
mvn clean package

# Run the application
mvn spring-boot:run
```

The API will be available at `http://localhost:8082`

# Podman and Kubernetes Deployment

### Build Docker Image
```bash
# Build the application
mvn clean package
```



```bash
brew install podman 
podman machine start

podman login quay.io
```


```bash
podman build --arch amd64 --os linux -t quay.io/burrsutter/fantaco-finance-main:1.0.0 -f deployment/Dockerfile .
```

```bash
podman run -d \
  --name fantaco-finance-main \
  -p 8082:8082 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/fantaco_finance \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  quay.io/burrsutter/fantaco-finance-main:1.0.0
```

```bash
curl $FIN_URL/api/finance/health
```

```bash
podman push quay.io/burrsutter/fantaco-finance-main:1.0.0
```

```bash
oc new-project fantaco
```

because I am using the docker.io postgres image

```bash
oc adm policy add-scc-to-user anyuid -z default
```

Deploy Postgres

```bash
oc apply -f deployment/kubernetes/postgres/deployment.yaml
oc apply -f deployment/kubernetes/postgres/service.yaml
```

Deploy the application

```bash
oc apply -f deployment/kubernetes/application/configmap.yaml
oc apply -f deployment/kubernetes/application/secret.yaml
oc apply -f deployment/kubernetes/application/deployment.yaml
oc apply -f deployment/kubernetes/application/service.yaml
```

```bash
oc get pods
```

```bash
oc get services
```

```bash
oc expose service fantaco-finance-service
```

```bash
export FIN_URL=http://$(oc get routes -n fantaco -l app=fantaco-finance-main -o jsonpath="{range .items[*]}{.status.ingress[0].host}{end}")
echo $FIN_URL
```

```bash
curl $FIN_URL/api/finance/health
```

## Testing with cURL

```bash
export FIN_URL=http://localhost:8082
```

### 1. Health Check
```bash
curl -X GET $FIN_URL/api/finance/health
```

### 2. Get Order History
```bash
curl -sS-X POST $FIN_URL/api/finance/orders/history \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST-001",
    "startDate": "2024-01-01T00:00:00",
    "endDate": "2024-01-31T23:59:59",
    "limit": 10
  }' | jq
```

### 3. Get Invoice History
```bash
curl -sS -X POST $FIN_URL/api/finance/invoices/history \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST-001",
    "startDate": "2024-01-01T00:00:00",
    "endDate": "2024-01-31T23:59:59",
    "limit": 10
  }' | jq
```

### 4. Start Duplicate Charge Dispute
```bash
curl -sS -X POST $FIN_URL/api/finance/disputes/duplicate-charge \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST-001",
    "orderId": 1,
    "description": "Charged twice for the same order",
    "reason": "Payment processor error caused duplicate charge"
  }' | jq
```

### 5. Find Lost Receipt
```bash
curl -sS -X POST $FIN_URL/api/finance/receipts/find-lost \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST-001",
    "orderId": 1
  }' | jq
```


## Database Schema

The application uses the following main entities:

- **Orders**: Customer orders with status tracking
- **Invoices**: Invoice management with payment status
- **Disputes**: Dispute tracking for various types
- **Receipts**: Receipt management with file storage

Sample data is automatically loaded on startup via `data.sql`.

## Configuration

### Environment Variables
- `SPRING_DATASOURCE_URL`: Database connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `SPRING_PROFILES_ACTIVE`: Active Spring profile

### Application Properties
Configuration is managed through `application.yml` with profiles for different environments.

## Monitoring and Health Checks

The application includes:
- Health check endpoint at `/api/finance/health`
- Kubernetes liveness and readiness probes
- Actuator endpoints for monitoring
- Structured logging with configurable levels

## Security

- Non-root user in Docker container
- Security context in Kubernetes
- Input validation on all endpoints
- SQL injection protection via JPA

## License

This project is licensed under the MIT License.
