package com.kodenca.ms_authentication.controller;

import com.kodenca.ms_authentication.dto.request.LoginRequest;
import com.kodenca.ms_authentication.dto.request.RegisterRequest;
import com.kodenca.ms_authentication.dto.response.BusinessResponse;
import com.kodenca.ms_authentication.dto.response.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Authentication", description = "API for user authentication and registration")
public interface IAuthController {

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with the provided credentials and role. The user will be able to authenticate after successful registration."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User registered successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BusinessResponse.class),
                            examples = @ExampleObject(
                                    name = "Successful Registration",
                                    summary = "User registered successfully",
                                    value = """
                    {
                        "success": true,
                        "message": "User registered successfully",
                        "timestamp": "2025-03-25T15:57:00",
                        "data": {
                            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNjE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
                        }
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "User already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BusinessResponse.class),
                            examples = @ExampleObject(
                                    name = "User Already Exists",
                                    summary = "Username is already taken",
                                    value = """
                    {
                        "success": false,
                        "message": "User already exists: john_doe",
                        "timestamp": "2025-03-25T15:57:00",
                        "data": null
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BusinessResponse.class),
                            examples = @ExampleObject(
                                    name = "Validation Error",
                                    summary = "Invalid input data",
                                    value = """
                    {
                        "success": false,
                        "message": "Validation failed",
                        "timestamp": "2025-03-25T15:57:00",
                        "data": {
                            "userName": "Username must be between 3 and 50 characters",
                            "password": "Password must be at least 6 characters",
                            "role": "Role must be one of: USER, ADMIN, MANAGER"
                        }
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BusinessResponse.class),
                            examples = @ExampleObject(
                                    name = "Server Error",
                                    summary = "Unexpected internal error",
                                    value = """
                    {
                        "success": false,
                        "message": "Internal server error",
                        "timestamp": "2025-03-25T15:57:00",
                        "data": null
                    }
                    """
                            )
                    )
            )
    })
    ResponseEntity<BusinessResponse<AuthResponse>> register(
            @Parameter(
                    description = "User registration data",
                    required = true,
                    schema = @Schema(implementation = RegisterRequest.class),
                    examples = @ExampleObject(
                            name = "Registration Request",
                            summary = "Example user registration",
                            value = """
                    {
                        "userName": "john_doe",
                        "password": "securePassword123",
                        "role": "USER"
                    }
                    """
                    )
            )
            @Valid @RequestBody RegisterRequest request);

    @Operation(
            summary = "Authenticate user",
            description = "Authenticates a user with username and password. Returns a JWT token that must be included in the Authorization header for subsequent requests."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BusinessResponse.class),
                            examples = @ExampleObject(
                                    name = "Successful Login",
                                    summary = "User authenticated successfully",
                                    value = """
                    {
                        "success": true,
                        "message": "User logged in successfully",
                        "timestamp": "2025-03-25T15:57:00",
                        "data": {
                            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNjE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
                        }
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found or invalid credentials",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BusinessResponse.class),
                            examples = @ExampleObject(
                                    name = "Authentication Failed",
                                    summary = "Invalid username or password",
                                    value = """
                    {
                        "success": false,
                        "message": "User not found: john_doe",
                        "timestamp": "2025-03-25T15:57:00",
                        "data": null
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BusinessResponse.class),
                            examples = @ExampleObject(
                                    name = "Login Validation Error",
                                    summary = "Missing or invalid login data",
                                    value = """
                    {
                        "success": false,
                        "message": "Validation failed",
                        "timestamp": "2025-03-25T15:57:00",
                        "data": {
                            "userName": "Username is required",
                            "password": "Password is required"
                        }
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BusinessResponse.class),
                            examples = @ExampleObject(
                                    name = "Server Error During Login",
                                    summary = "Unexpected internal error",
                                    value = """
                    {
                        "success": false,
                        "message": "Internal server error",
                        "timestamp": "2025-03-25T15:57:00",
                        "data": null
                    }
                    """
                            )
                    )
            )
    })
    ResponseEntity<BusinessResponse<AuthResponse>> login(
            @Parameter(
                    description = "User login credentials",
                    required = true,
                    schema = @Schema(implementation = LoginRequest.class),
                    examples = @ExampleObject(
                            name = "Login Request",
                            summary = "Example user login",
                            value = """
                    {
                        "userName": "john_doe",
                        "password": "securePassword123"
                    }
                    """
                    )
            )
            @Valid @RequestBody LoginRequest request);
}