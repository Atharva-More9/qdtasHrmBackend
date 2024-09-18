package com.qdtas.controller;

import com.qdtas.dto.*;
import com.qdtas.entity.User;
import com.qdtas.exception.ResourceNotFoundException;
import com.qdtas.security.JwtHelper;
import com.qdtas.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
@Tag(name = "1. User")
public class UserController {

    @Autowired
    private UserService ussr;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtHelper jwt;

    @ApiOperation(value = "Add User", position = 1)
    @Operation(
            description = "the verification link will be sent on email.",
            summary = "1. Add User(by Admin)",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Email Already Registered",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity<?> register(@Valid @RequestBody AddUserDto rdt) {
        User su = ussr.create(rdt);
        return new ResponseEntity<>(su, HttpStatus.CREATED);
    }

    @Operation(
            description = "paste the token in token field",
            summary = "2. Verify email",
            responses = {
                    @ApiResponse(
                            description = "Verification Successful",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content

                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
            }
    )
    @GetMapping("/verify/{token}")
    public ResponseEntity<?> verifyEmail(@PathVariable("token") String token) {
        ussr.verifyEmail(token);
        return new ResponseEntity<>(new JsonMessage("Verification Successfull"), HttpStatus.OK);
    }

    @Operation(
            description = "use token for authorization",
            summary = "3. Login",
            responses = {
                    @ApiResponse(
                            description = "User and authorization token",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Bad Credentials",
                            responseCode = "401",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO ldt) {
        try {
            JwtResponse luser = ussr.login(ldt);
            return new ResponseEntity<>(luser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new JsonMessage("Bad Credentials"), HttpStatus.UNAUTHORIZED);
        }
    }

    @Hidden
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId) {
        User targetUser = ussr.getById(userId);
        return new ResponseEntity<>(targetUser, HttpStatus.OK);
    }

    @Hidden
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers(@RequestParam(value = "pgn",defaultValue = "1") int pgn,
                                         @RequestParam(value = "sz" ,defaultValue = "10") int size) {
        pgn = pgn < 0 ? 0 : pgn-1;
        size = size <= 0 ? 10 : size;
        List<User> ul = ussr.getAllUsers(pgn, size);
        return new ResponseEntity<>(ul, HttpStatus.OK);
    }

    @Operation(
            description = "Enable User(by Admin)",
            summary = "enable a user",
            responses = {
                    @ApiResponse(
                            description = "User Enabled Successfully", responseCode = "200", content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "User Not Found With Id", responseCode = "400", content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/enableUser/{userId}")
    public ResponseEntity<?> enableUserById(@PathVariable("userId") Long userId) {
        ussr.enableUser(userId);
        JsonMessage j = new JsonMessage("User Enabled Successfully");
        return new ResponseEntity<>(j, HttpStatus.OK);
    }

    @Operation(
            description = "Reset Password",
            summary = "Reset a Password With Email",
            responses = {
                    @ApiResponse(
                            description = "Temperory Password Sent To Registered Email ", responseCode = "200", content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Email Not Registered", responseCode = "400", content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestParam(name = "email") String email) {
        try {
            ussr.forgotPassword(email);
            return new ResponseEntity(new JsonMessage("a temporary password has been set to your account and you will receive it on registered mail"), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(new JsonMessage("Email Not Registered"), HttpStatus.BAD_REQUEST);
        }
    }

    @Hidden
    @PostMapping("/searchUser")
    public ResponseEntity<?> searchUser(@RequestParam("key") String keyword,
                                        @RequestParam("pgn") int pgn,
                                        @RequestParam("sz") int size) {
        return ResponseEntity.ok(ussr.searchUser(keyword, pgn, size));
    }

    @Operation(
            description = "Change Temporary Password",
            summary = "change a password with temporary password",
            responses = {
                    @ApiResponse(
                            description = "Temporary Password Changed Successfully", responseCode = "200", content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Error Changing Temporary Password", responseCode = "400", content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Invalid email or Temporary Password", responseCode = "401", content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/changeTempPassword")
    public ResponseEntity<?> changeTempPass(@RequestBody ChangePassDTO cp) {
        try{
            ussr.changeTempPassword(cp.getEmail(),cp.getOldP(),cp.getNewP());
            return new ResponseEntity(new JsonMessage("Password Changed Successfully"), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity(new JsonMessage("Invalid Old Password or email"), HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(
            description = "Change Password",
            summary = "change a password with old password",
            responses = {
                    @ApiResponse(
                            description = "Password Changed Successfully", responseCode = "200", content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Error Changing Password", responseCode = "400", content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Invalid email or Password", responseCode = "401", content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePass(@RequestBody ChangePassDTO cp) {
        try{
            ussr.changePassword(cp.getEmail(),cp.getOldP(),cp.getNewP());
            return new ResponseEntity(new JsonMessage("Password Changed Successfully"), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity(new JsonMessage("Invalid Old Password or email"), HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(
            description = "Delete a User",
            summary = "Delete a User",
            responses = {
                    @ApiResponse(
                            description = "User Deleted Successfully", responseCode = "200", content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Error Deleting a User", responseCode = "400", content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam(name = "uId",required = true) long uId){
        try{
            ussr.deleteUser(uId);
            return new ResponseEntity<>(new JsonMessage("User Deleted Successfully"), HttpStatus.OK);
        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(new JsonMessage("Error Deleting a User"), HttpStatus.OK);
        }

    }

    @PostMapping("/updateUser/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") long uId, @RequestBody UpdateUserDTO ud){
        try{
            return new ResponseEntity<>(ussr.updateUser(uId,ud), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new JsonMessage("Something went wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get User Details",
            description = "Get detailed information about a user by their ID",
            responses = {
                    @ApiResponse(
                            description = "User Details", responseCode = "200", content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "User Not Found", responseCode = "404", content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
//    @GetMapping("/userInfo/{userName}")
//    public ResponseEntity<?> getUserInfo(@PathVariable("userName") String userName) {
//        try {
//            User user = ussr.getUserByUserName(userName);
//            return new ResponseEntity<>(user, HttpStatus.OK);
//        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(new JsonMessage("User Not Found"), HttpStatus.NOT_FOUND);
//        }
//    }
    @GetMapping("/userInfo/{userName}")
    public ResponseEntity<?> getUserInfo(@PathVariable("userName") String userName) {
        List<User> users = ussr.getUserByUserName(userName);
        if (users.isEmpty()) {
            return new ResponseEntity<>(new JsonMessage("User Not Found"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Calculate Employee Performance",
            description = "Calculate the performance of an employee based on the number of days worked within a specified date range.",
            responses = {
                    @ApiResponse(
                            description = "Performance Calculation Successful", responseCode = "200", content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "User or Project Not Found", responseCode = "404", content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Invalid Date Range", responseCode = "400", content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )

    @GetMapping("/calculate-performance")
    public ResponseEntity<?> calculatePerformance(
            @RequestParam("userId") long userId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        try {
            // Convert the String parameters to LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);

            double performance = ussr.calculateEmployeePerformance(userId, start, end);
            return new ResponseEntity<>(performance, HttpStatus.OK);
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>(new JsonMessage("Invalid date format"), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new JsonMessage(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new JsonMessage("Something went wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get Total Leaves",
            description = "Retrieve the total leaves for a user by their ID",
            responses = {
                    @ApiResponse(
                            description = "Total Leaves Retrieved", responseCode = "200", content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "User Not Found", responseCode = "404", content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @GetMapping("/{userId}/totalLeaves")
    public ResponseEntity<?> getTotalLeaves(@PathVariable("userId") Long userId) {
        try {
            Integer totalLeaves = ussr.getTotalLeaves(userId);
            return new ResponseEntity<>(totalLeaves, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new JsonMessage(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/calculate-performance")
//    public ResponseEntity<?> calculatePerformance(
//            @RequestParam("userId") long userId,
//            @RequestParam("projectId") long projectId,
//            @RequestParam("startDate") String startDate,
//            @RequestParam("endDate") String endDate) {
//        try {
//            // Convert the String parameters to LocalDate
//            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
//            LocalDate start = LocalDate.parse(startDate, formatter);
//            LocalDate end = LocalDate.parse(endDate, formatter);
//
//            double performance = ussr.calculateEmployeePerformance(userId, projectId, start, end);
//            return new ResponseEntity<>(performance, HttpStatus.OK);
//        } catch (DateTimeParseException e) {
//            return new ResponseEntity<>(new JsonMessage("Invalid date format"), HttpStatus.BAD_REQUEST);
//        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(new JsonMessage(e.getMessage()), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new JsonMessage("Something went wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
