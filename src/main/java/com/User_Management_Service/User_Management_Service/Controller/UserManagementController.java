package com.User_Management_Service.User_Management_Service.Controller;

import com.User_Management_Service.User_Management_Service.DTO.LoginDTO;
import com.User_Management_Service.User_Management_Service.DTO.ReqRes;
import com.User_Management_Service.User_Management_Service.DTO.RegisterDTO;
import com.User_Management_Service.User_Management_Service.Entity.Users;
import com.User_Management_Service.User_Management_Service.Service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "User Management Controller")
@RestController
@RequiredArgsConstructor
public class UserManagementController {
    private final UserManagementService userManagementService;

    @PostMapping("/auth/register")
    @Operation(
            description = "Register User",
            summary = "Register User",
            responses = {
                    @ApiResponse(
                            description = "Successful",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    public ResponseEntity<ReqRes> register(@RequestBody RegisterDTO reg) {
        return ResponseEntity.ok(userManagementService.register(reg));
    }

    @PostMapping("/auth/login")
    @Operation(
            description = "User Login",
            summary = "User Login",
            responses = {
                    @ApiResponse(
                            description = "Successful",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    public ResponseEntity<ReqRes> login(@RequestBody LoginDTO req) {
        return ResponseEntity.ok(userManagementService.login(req));
    }

    @PostMapping("/auth/refresh")
    @Operation(
            description = "Token Refresh",
            summary = "Token Refresh",
            responses = {
                    @ApiResponse(
                            description = "Successful",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req) {
        return ResponseEntity.ok(userManagementService.refreshToken(req));
    }

    @GetMapping("/admin/get-all-users")
    @Operation(
            description = "Get All Users for Admin",
            summary = "Get All Users",
            responses = {
                    @ApiResponse(
                            description = "Successful",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    public ResponseEntity<ReqRes> getAllUsers() {
        return ResponseEntity.ok(userManagementService.getAllUsers());
    }

    @GetMapping("/admin/get-user/{userId}")
    @Operation(
            description = "Get User",
            summary = "Get Users",
            responses = {
                    @ApiResponse(
                            description = "Successful",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    public ResponseEntity<ReqRes> getUserById(@PathVariable long userId) {
        return ResponseEntity.ok(userManagementService.getUserById(userId));
    }

    @PutMapping("/admin/update-user/{userId}")
    @Operation(
            description = "Update User",
            summary = "Update User",
            responses = {
                    @ApiResponse(
                            description = "Successful",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    public ResponseEntity<ReqRes> updateUser(@PathVariable long userId, @RequestBody Users req) {
        return ResponseEntity.ok(userManagementService.updateUser(userId, req));
    }

    @DeleteMapping("/admin/delete-user/{userId}")
    @Operation(
            description = "Delete User",
            summary = "Delete User",
            responses = {
                    @ApiResponse(
                            description = "Successful",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    public ResponseEntity<ReqRes> deleteUser(@PathVariable long userId) {
        return ResponseEntity.ok(userManagementService.deleteUser(userId));
    }

    @GetMapping("/user/view-profile/{userId}")
    @Operation(
            description = "View Profile",
            summary = "View Profile",
            responses = {
                    @ApiResponse(
                            description = "Successful",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    public ResponseEntity<ReqRes> viewProfile(@PathVariable long userId) {
        return ResponseEntity.ok(userManagementService.getUserById(userId));
    }

    @PutMapping("/user/update-profile/{userId}")
    @Operation(
            description = "Update Profile",
            summary = "Update Profile",
            responses = {
                    @ApiResponse(
                            description = "Successful",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    public ResponseEntity<ReqRes> updateProfile(@PathVariable long userId, @RequestBody Users req) {
        return ResponseEntity.ok(userManagementService.updateUser(userId, req));
    }

    @GetMapping("/service/get-email/{userId}")
    @Operation(
            description = "Get Email",
            summary = "Get Email",
            responses = {
                    @ApiResponse(
                            description = "Successful",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    public String getEmail(@PathVariable long userId) {
        return userManagementService.getEmail(userId);
    }

    @GetMapping("/service/get-phoneNumber/{userId}")
    @Operation(
            description = "Get Phone Number",
            summary = "Get Phone Number",
            responses = {
                    @ApiResponse(
                            description = "Successful",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    public String getPhoneNumber(@PathVariable long userId) {
        return userManagementService.getPhoneNumber(userId);
    }

    @GetMapping("/service/get-name/{userId}")
    @Operation(
            description = "Get Name",
            summary = "Get Name",
            responses = {
                    @ApiResponse(
                            description = "Successful",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    public String getName(@PathVariable long userId) {
        return userManagementService.getName(userId);
    }

    @GetMapping("/service/get-all-customers")
    @Operation(
            description = "Get All Customers",
            summary = "Get All Customers",
            responses = {
                    @ApiResponse(
                            description = "Successful",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    public List<Users> getAllUsersByRole() {
        return userManagementService.getAllUsersByRole();
    }
}
