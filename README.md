# Spring Boot with OpenTelemetry and SigNoz

This project demonstrates the integration of Spring Boot with OpenTelemetry for distributed tracing, using SigNoz as the observability backend. The entire stack is containerized using Docker Compose.

## Project Components

1. **Spring Boot Application**

   - Java 17
   - Spring Boot 3.2.0
   - REST API with `/api/hello` endpoint
   - Artificial delay to make traces more visible

2. **OpenTelemetry**

   - Java Agent for automatic instrumentation
   - OTLP exporter configured to send telemetry data to the collector

3. **SigNoz Stack**
   - ClickHouse for storage
   - Query Service for processing telemetry data
   - Frontend for visualization
   - OpenTelemetry Collector for receiving and processing telemetry data

## Prerequisites

- Docker and Docker Compose installed
- Git (optional, for cloning the repository)

## Getting Started

### Starting the Stack

1. Clone or download this repository

2. Navigate to the project directory:

   ```bash
   cd spring-open-telemetry
   ```

3. Start the entire stack using Docker Compose:

   ```bash
   docker-compose up -d
   ```

   This will start the Spring Boot application, SigNoz, and all required components.

4. Wait for all services to start (this may take a few minutes, especially on first run)

### Accessing the REST Endpoint

Once the stack is running, you can access the Spring Boot application's REST endpoint:

```
http://localhost:8080/api/hello
```

This endpoint has an artificial delay of 500ms to make traces more visible in SigNoz.

### Accessing the SigNoz Dashboard

The SigNoz dashboard is available at:

```
http://localhost:3301
```

Default credentials:

- Email: admin@signoz.io
- Password: admin

### Viewing Sample Trace Data

1. Open the SigNoz dashboard at http://localhost:3301
2. Navigate to the "Traces" section in the left sidebar
3. You should see traces from the Spring Boot application
4. To generate more traces, make additional requests to the `/api/hello` endpoint
5. Click on any trace to view detailed information, including:
   - Span details
   - Duration
   - Tags and attributes
   - Service dependencies

## Project Structure

```
├── app/                           # Spring Boot application
│   ├── src/                       # Source code
│   ├── build.gradle               # Gradle build configuration
│   └── Dockerfile                 # Docker configuration for the app
├── docker-compose.yml             # Docker Compose configuration
├── otel-collector-config.yaml     # OpenTelemetry Collector configuration
├── clickhouse-config.xml          # ClickHouse configuration
└── clickhouse-users.xml           # ClickHouse users configuration
```

## Stopping the Stack

To stop all services:

```bash
docker-compose down
```

To stop all services and remove volumes (this will delete all data):

```bash
docker-compose down -v
```

## Troubleshooting

- If you don't see any traces in SigNoz, make sure all services are running:

  ```bash
  docker-compose ps
  ```

- Check the logs of the OpenTelemetry Collector:

  ```bash
  docker-compose logs otel-collector
  ```

- Check the Spring Boot application logs:
  ```bash
  docker-compose logs spring-app
  ```

## Additional Information

- The Spring Boot application is configured to send traces, metrics, and logs to the OpenTelemetry Collector
- The OpenTelemetry Collector is configured to export data to SigNoz
- The artificial delay in the `/api/hello` endpoint makes traces more visible in the SigNoz UI
