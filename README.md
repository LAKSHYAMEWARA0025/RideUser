🚖 Ride Sharing Backend (Spring Boot + MongoDB + JWT)

A mini Ride Sharing backend system built using Spring Boot, MongoDB Atlas, JWT Authentication, Role-Based Authorization, DTO Validation, and Global Exception Handling.

This project supports:

User Registration & Login

JWT-based authentication

Ride Request creation

Driver viewing pending requests

Driver accepting rides

Completing rides (User or Driver)

Clean architecture with Services + Repositories

src/main/java/org/example/rideshare/
│
├── controller/
│     ├── AuthController.java
│     ├── RideController.java
│     └── DriverRideController.java
│
├── service/
│     ├── AuthService.java
│     └── RideService.java
│
├── repository/
│     ├── UserRepository.java
│     └── RideRepository.java
│
├── model/
│     ├── User.java
│     └── Ride.java
│
├── dto/
│     ├── RegisterRequest.java
│     ├── LoginRequest.java
│     ├── AuthResponse.java
│     ├── CreateRideRequest.java
│     └── RideResponse.java
│
├── config/
│     ├── SecurityConfig.java
│     ├── JwtUtil.java
│     ├── JwtAuthenticationFilter.java
│     └── CustomUserDetailsService.java
│
├── exception/
│     ├── GlobalExceptionHandler.java
│     ├── NotFoundException.java
│     └── BadRequestException.java
│
└── util/
      └── RideStatus.java

🛠️ Technologies Used

Java 17

Spring Boot 3.4.12

Spring Security

JWT (jjwt 0.11.5)

MongoDB Atlas

Validation (Jakarta)

Lombok (optional)

This Ride Sharing backend provides a complete set of REST APIs for user and driver interactions using secure JWT authentication. Users and drivers can register and log in using the /api/auth/register and /api/auth/login endpoints, with passwords encrypted and JWT tokens issued upon successful login. A user with the ROLE_USER role can create a new ride request through the /api/v1/rides API, view their own ride history using /api/v1/user/rides, and later complete a ride via /api/v1/rides/{rideId}/complete. Drivers with the ROLE_DRIVER role can view all pending ride requests using /api/v1/driver/rides/requests and accept a specific ride using /api/v1/driver/rides/{rideId}/accept, after which the ride is assigned to them and marked as accepted. Both users and drivers can complete the accepted ride through the shared completion endpoint. All APIs are protected using JWT tokens sent in the Authorization: Bearer <token> header, and robust validation plus global exception handling ensures clear error messages and consistent behavior across the application.
