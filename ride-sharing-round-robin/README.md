# Ride Sharing Platform - Round Robin Load Balancing

A simple Spring Boot + Spring Cloud practical project for STS. The API Gateway forwards `/rides/**` requests to the `RIDE-SERVICE` discovered through Eureka. Spring Cloud LoadBalancer uses its Round Robin algorithm by default.

## Technology
- Java 17
- Spring Boot 3.4.5
- Spring Cloud 2024.0.1
- Spring Cloud Netflix Eureka Server/Client
- Spring Cloud Gateway
- Spring Cloud LoadBalancer

## Projects and ports
| Project | Port | Purpose |
|---|---:|---|
| eureka-server | 8761 | Service registry |
| ride-service instance 1 | 8081 | Ride API |
| ride-service instance 2 | 8082 | Same Ride API, second instance |
| ride-service instance 3 | 8083 | Same Ride API, third instance |
| api-gateway | 8080 | Entry point and load balancer |

## Import into Spring Tool Suite (STS)
1. Extract this ZIP file.
2. In STS choose **File -> Import -> Maven -> Existing Maven Projects**.
3. Browse to the extracted `ride-sharing-round-robin` folder and select its root `pom.xml` (the ZIP now includes a parent Maven project).
4. Ensure all three modules are detected: `eureka-server`, `ride-service`, and `api-gateway`.
5. Click **Finish** and wait for Maven dependencies to download. Do not double-click the ZIP or open the folder as a plain Java project.

## Run the applications
Run in this order:

1. Run `EurekaServerApplication` from `eureka-server` as **Spring Boot App**.
2. Run `RideServiceApplication` from `ride-service` with program argument `--server.port=8081`.
3. Run the same `RideServiceApplication` again with program argument `--server.port=8082`.
4. Run the same `RideServiceApplication` a third time with program argument `--server.port=8083`.
5. Run `ApiGatewayApplication` from `api-gateway` as **Spring Boot App**.

### Configure multiple Ride Service instances in STS
- Right-click `ride-service` -> **Run As -> Run Configurations...**
- Select **Spring Boot App**, create/duplicate a launch configuration for each instance.
- On the **Arguments** tab, put one of these in **Program arguments**:
  - `--server.port=8081`
  - `--server.port=8082`
  - `--server.port=8083`
- Apply and run each configuration. Ensure each instance uses a different port.

## Test
Open the Eureka dashboard: http://localhost:8761

Wait until three `RIDE-SERVICE` instances are listed. Then send repeated GET requests to:

`http://localhost:8080/rides/test`

Responses include the port of the instance that handled the request. You should see the ports rotate across 8081, 8082, and 8083. The first instance may vary, and load-balancer selection is performed per request.

You can also test a ride request with:
- Method: `POST`
- URL: `http://localhost:8080/rides`
- Body: raw text, for example `Pickup: Central Station, Drop: Airport`

## Notes
- The `lb://RIDE-SERVICE` route means Gateway resolves healthy/registered instances from Eureka and asks Spring Cloud LoadBalancer to choose one.
- Round Robin is the default selection algorithm; no custom load-balancer code is required.
- When an instance stops, Eureka removes it after its heartbeat/lease detection period. Requests during failure detection may fail; this demo does not provide instantaneous failover or retry guarantees.
- This is a learning demo, not a production-ready ride booking system. It has no database, authentication, payment, or real driver matching.
