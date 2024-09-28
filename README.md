# Java Spring Boot JWT Security Package

This repository contains a fully-functional security package for Spring Boot applications. It provides authentication and authorization using JSON Web Tokens (JWT), role-based access control, and customizable security configurations.

## Features

- JWT-based authentication and authorization.
- Customizable role-based access control.
- Easy integration with Spring Boot applications.
- Includes exception handling for security-related issues.
- Stateless authentication suitable for microservices and RESTful APIs.
 
## How to Use

### 1. Adding the Package to Your Project

You can include this package in your Spring Boot project by adding it as a dependency via JitPack or directly cloning this repository.

#### Option 1: Using JitPack (Recommended)

Add the following to your `pom.xml` if you are using Maven:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.davidiwezulu</groupId>
    <artifactId>spring-boot-jwt-security</artifactId>
    <version>v1.0.0</version>
</dependency>
```
#### For Gradle:
```
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.davidiwezulu:spring-boot-jwt-security:v1.0.0'
}
```
#### Option 2: Cloning the Repository
- Clone the repository:
``` 
git clone https://github.com/davidiwezulu/spring-boot-jwt-security.git
 ```
- Import the package into your project as a local module or manually copy the com.davidiwezulu.project.security package into your project.

### 2. Configuration
#### Step 1: Add JWT Properties to application.properties
Configure the JWT secret and expiration time in your application.properties file:

``` # JWT Properties
app.jwtSecret=YourSuperSecretKey
app.jwtExpirationInMs=86400000 # Token expires in 1 day
```
#### Step 2: Enable Security in Your Application
Make sure that the SecurityConfig class is properly configured to secure your application. The package provides a default security configuration, but you can extend it as needed.

Example in SecurityConfig.java:
``` @Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .apply(new JwtConfigurer(jwtTokenProvider)); // Custom JWT configuration
    }
}
```
#### Step 3: Define User and Role Entities
In your application, you will need a User entity with roles assigned. If you are following this package, the UserPrincipal and UserDetailsServiceImpl will be used to authenticate and authorize the user.

Example User.java:
``` @Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;

    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    // Getters and Setters
}
```
Example Role.java:
``` @Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName name;

    // Getters and Setters
}
```
#### Step 4: Create Authentication Endpoints
You need to create endpoints for user registration and login. The JWT token is generated during login and returned to the client.

Example AuthenticationController.java:

``` @RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
}
```
#### Step 5: Protect Endpoints with JWT and Roles

Ensure that your endpoints are protected based on roles and JWT tokens. Use @PreAuthorize or @Secured to limit access based on roles.

Example:
``` @RestController
@RequestMapping("/api/users")
public class UserController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public User getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return userService.getUserById(currentUser.getId());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
```
### 3. Example Usage
#### Example Request to Register a User
Send a POST request to /api/auth/signup with the following JSON body:

``` {
    "name": "John Doe",
    "username": "johndoe",
    "email": "johndoe@example.com",
    "password": "password123"
}
```
#### Example Request to Authenticate a User
Send a POST request to /api/auth/signin with the following JSON body:

``` {
    "username": "johndoe",
    "password": "password123"
}
```
You will receive a JWT token in the response:
``` {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "tokenType": "Bearer"
}
```
#### Accessing Protected Endpoints
To access protected endpoints, include the JWT token in the Authorization header:
``` GET /api/users/me
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

### 4. Contributing
If you'd like to contribute to this package, feel free to fork the repository and submit a pull request with your improvements.

### 5. License
This package is licensed under the MIT License. See the LICENSE file for details.

``` 
---

### Key Takeaways

- **JitPack Integration**: Using JitPack makes the package easily accessible without requiring complex setup. Users can include the dependency directly in their `pom.xml` or `build.gradle`.
  
- **Instructions for Cloning**: If JitPack is not an option, clear instructions for cloning the repository and integrating it as a local module are provided.

- **Usage Example**: Example requests for user registration, authentication, and accessing protected endpoints using JWT are detailed, making it easy for users to get started.

- **Documentation**: The `README.md` includes all necessary steps, including configuration, adding dependencies, and endpoint examples.

By following these steps, the package can be made publicly available and easy to use for developers who want to integrate JWT-based security into their Spring Boot applications.
```


