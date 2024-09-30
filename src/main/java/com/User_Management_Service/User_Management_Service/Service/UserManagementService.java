package com.User_Management_Service.User_Management_Service.Service;

import com.User_Management_Service.User_Management_Service.DTO.LoginDTO;
import com.User_Management_Service.User_Management_Service.DTO.ReqRes;
import com.User_Management_Service.User_Management_Service.DTO.RegisterDTO;
import com.User_Management_Service.User_Management_Service.Entity.Users;
import com.User_Management_Service.User_Management_Service.Repository.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserManagementService {
    private final UsersRepo usersRepo;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public ReqRes register(RegisterDTO registrationRequest) {
        ReqRes resp = new ReqRes();
        try {
            Users Users = new Users();
            Users.setEmail(registrationRequest.getEmail());
            Users.setCity(registrationRequest.getCity());
            Users.setRole(registrationRequest.getRole());
            Users.setName(registrationRequest.getName());
            Users.setPhoneNumber(registrationRequest.getPhoneNumber());
            Users.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

            Users UsersResult = usersRepo.save(Users);
            if (UsersResult.getId() > 0) {
                resp.setUsers(UsersResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes login(LoginDTO loginRequest) {
        ReqRes resp = new ReqRes();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            resp.setStatusCode(200);
            resp.setToken(jwt);
            resp.setRole(user.getRole());
            resp.setRefreshToken(refreshToken);
            resp.setExpirationTime("24Hrs");
            resp.setMessage("Logged in Successfully");
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes refreshToken(ReqRes refreshTokenRequest) {
        ReqRes resp = new ReqRes();
        try {
            String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            Users users = usersRepo.findByEmail(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                resp.setStatusCode(200);
                resp.setToken(jwt);
                resp.setRefreshToken(refreshTokenRequest.getRefreshToken());
                resp.setExpirationTime("24Hrs");
                resp.setMessage("Token Refreshed Successfully");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes getAllUsers() {
        ReqRes resp = new ReqRes();
        try {
            List<Users> result = usersRepo.findAll();
            if (!result.isEmpty()) {
                resp.setUsersList(result);
                resp.setStatusCode(200);
                resp.setMessage("Successful");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("No users found");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError("Error occurred: " + e.getMessage());
        }
        return resp;
    }

    public ReqRes getUserById(Long id) {
        ReqRes resp = new ReqRes();
        try {
            Users userById = usersRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            resp.setUsers(userById);
            resp.setStatusCode(200);
            resp.setMessage("User with id " + id + " found Successfully");

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError("Error occurred: " + e.getMessage());
        }
        return resp;
    }

    public ReqRes deleteUser(Long id) {
        ReqRes resp = new ReqRes();
        try {
            Optional<Users> usersOptional = usersRepo.findById(id);
            if (usersOptional.isPresent()) {
                usersRepo.deleteById(id);
                resp.setStatusCode(200);
                resp.setMessage("User deleted Successfully");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("User not found deletion");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError("Error occurred: " + e.getMessage());
        }
        return resp;
    }

    public ReqRes updateUser(Long id, Users updateUser) {
        ReqRes resp = new ReqRes();
        try {
            Optional<Users> usersOptional = usersRepo.findById(id);
            if (usersOptional.isPresent()) {
                Users existingUser = usersOptional.get();
                existingUser.setEmail(updateUser.getEmail());
                existingUser.setName(updateUser.getName());
                existingUser.setCity(updateUser.getCity());
                existingUser.setRole(updateUser.getRole());

                if (updateUser.getPassword() != null && !updateUser.getPassword().isEmpty()) {
                    existingUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
                }
                Users savedUser = usersRepo.save(existingUser);
                resp.setUsers(savedUser);
                resp.setStatusCode(200);
                resp.setMessage("User updated Successfully");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("User not found update");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError("Error occurred: " + e.getMessage());
        }
        return resp;
    }

    public ReqRes getMyInfo(String email) {
        ReqRes resp = new ReqRes();
        try {
            Optional<Users> usersOptional = usersRepo.findByEmail(email);
            if (usersOptional.isPresent()) {
                resp.setUsers(usersOptional.get());
                resp.setStatusCode(200);
                resp.setMessage("Successfully");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("User not found");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError("Error occurred: " + e.getMessage());
        }
        return resp;
    }

    public String getEmail(long userId) {
        Optional<Users> usersOptional = usersRepo.findById(userId);
        return usersOptional.map(Users::getEmail).orElse("User not found");
    }

    public String getName(long userId) {
        Optional<Users> usersOptional = usersRepo.findById(userId);
        return usersOptional.map(Users::getName).orElse("User not found");
    }

    public List<Users> getAllUsersByRole() {
        return usersRepo.findAllByRole("CUSTOMER");
    }

    public String getPhoneNumber(long userId) {
        Optional<Users> usersOptional = usersRepo.findById(userId);
        return usersOptional.map(Users::getPhoneNumber).orElse("User not found");
    }
}
