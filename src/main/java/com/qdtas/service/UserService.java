package com.qdtas.service;

import com.qdtas.dto.AddUserDto;
import com.qdtas.dto.JwtResponse;
import com.qdtas.dto.LoginDTO;
import com.qdtas.dto.UpdateUserDTO;
import com.qdtas.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    public User create(AddUserDto rdt);

    public JwtResponse login(LoginDTO ld);

    public User getByEmail(String email);

    void verifyEmail(String token);

    public User getAuthenticatedUser();

    public User getById(long uId);

    public void enableUser(long userId);

    public List<User> searchUser(String keyword , int pgn,int sizes);

    public void forgotPassword(String email);

    public void changePassword(String email,String oldP,String newP);

    public void changeTempPassword(String email,String oldP,String newP);

    public List<User> getAllUsers(int pgn,int size);

    public void deleteUser(long userId);

    public User updateUser(long uId, UpdateUserDTO ud);

    public User userInfo(long uId);

    public List<User> getUserByUserName(String username);

    public double calculateEmployeePerformance(long userId, LocalDate startDate, LocalDate endDate);



}
