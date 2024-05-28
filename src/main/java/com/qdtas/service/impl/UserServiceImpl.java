package com.qdtas.service.impl;

import com.qdtas.dto.AddUserDto;
import com.qdtas.dto.JwtResponse;
import com.qdtas.dto.LoginDTO;
import com.qdtas.dto.UpdateUserDTO;
import com.qdtas.entity.Department;
import com.qdtas.entity.EmailVerification;
import com.qdtas.entity.User;
import com.qdtas.exception.EmailAlreadyRegisteredException;
import com.qdtas.exception.ResourceNotFoundException;
import com.qdtas.repository.DepartmentRepository;
import com.qdtas.repository.EmailServiceRepository;
import com.qdtas.repository.UserRepository;
import com.qdtas.security.CustomUserDetails;
import com.qdtas.security.JwtHelper;
import com.qdtas.service.UserService;
import com.qdtas.utility.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository urp;
    @Autowired
    private Environment env;
    @Autowired
    private EmailService ems;
    @Autowired
    private EmailServiceRepository erp;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    PasswordEncoder pnc;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private DepartmentRepository drp;


    @Autowired
    private PasswordEncoder penco;

    public User getById(long uId) {
        return urp.findById(uId).orElseThrow(() -> new ResourceNotFoundException("User","user_id",String.valueOf(uId)));
    }

    @Override
    public void enableUser(long userId) {
        User u = getById(userId);
        u.setEmailVerified(true);
        urp.save(u);
    }

    @Override
    public User getByEmail(String email) {
        User u = urp.findByEmail(email);
        if (u != null) {
            return u;
        } else {
            throw new ResourceNotFoundException("User","email",email);
        }
    }

    @Override
    public User create(AddUserDto rdt) {
        User savedU = null;
        try {
            User user = getByEmail(rdt.getEmail());
            if (user != null) {
                throw new EmailAlreadyRegisteredException("Email is Already Registered");
            }
        } catch (ResourceNotFoundException e) {
            User u = new User();
            Department department=new Department();
            try{
                department = drp.findById(rdt.getDeptId()).orElseThrow(() -> new ResourceNotFoundException("Department","department_id",String.valueOf(rdt.getDeptId())));
            }
            catch(Exception exception){
                department=new Department(0,"NA",new HashSet<>());
            }
            u.setUserName(rdt.getUserName());
            u.setEmail(rdt.getEmail());
            u.setPassword(pnc.encode(rdt.getPassword()));
            u.setFirstName(rdt.getFirstName());
            u.setMiddleName(rdt.getMiddleName());
            u.setLastName(rdt.getLastName());
            u.setGender(rdt.getGender());
            u.setDept(department);
            u.setJoinDate(new Date());
            u.setRole(rdt.getRole());
            u.setPhoneNumber(rdt.getPhoneNumber());
            u.setDesignation(rdt.getDesignation());
            u.setBirthDate(rdt.getBirthDate());
            u.setEmailVerified(false);
            savedU = urp.save(u);
            ems.sendVerificationEmail(savedU.getEmail());
        }
        return savedU;
    }

    public JwtResponse login(LoginDTO ld) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(
                ld.getEmail(), ld.getPassword())
        );
        User loginUser = urp.findByEmail(ld.getEmail());
        CustomUserDetails userPrincipal = new CustomUserDetails(loginUser);
        return new JwtResponse(loginUser, jwtHelper.generateToken(userPrincipal));
    }

    @Override
    public void verifyEmail(String token) {
        try {
            EmailVerification emv = erp.findById(token).orElseThrow(() -> new RuntimeException("Email Not Found"));
            User u = getByEmail(emv.getEmail());
            u.setEmailVerified(true);
            urp.save(u);
            erp.deleteById(token);
        } catch (Exception e) {

        }
    }

    public User updateEmail(String email) {
        User u =  getAuthenticatedUser();
        if (!email.equalsIgnoreCase(u.getEmail())) {
            try {
                User duplicateUser = getByEmail(email);
                if (duplicateUser != null) {
                    throw new EmailAlreadyRegisteredException("Email Already Registered With Other User");
                } else {
                    throw new ResourceNotFoundException("User","Email",email);
                }
            } catch (ResourceNotFoundException e) {
                u.setEmail(email);
                u.setEmailVerified(false);
                User updatedUser = urp.save(u);
                ems.sendVerificationEmail(email);
                return updatedUser;
            }
        } else {
            throw new EmailAlreadyRegisteredException("Email is Same as Previous one");
        }
    }

    public final User getAuthenticatedUser() {
        String authUserEmail = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        System.out.println(authUserEmail);
        return getByEmail(authUserEmail);
    }

    @Override
    public List<User> searchUser(String keyword,int pgn,int size ) {
        System.out.println(keyword);
        return urp.findByFirstNameOrLastNameLike(keyword, PageRequest.of(pgn,size))
                .stream().collect(Collectors.toList());
    }

    @Override
    public void forgotPassword(String email) {
        User user = getByEmail(email);
        if (user != null){
            String temporaryPassword = UUID.randomUUID().toString();
            System.out.println(temporaryPassword);
            user.setPassword(temporaryPassword);

            urp.save(user);
            ems.sendPasswordResetEmail(user.getEmail(), temporaryPassword);
        }
        else {
            throw new IllegalArgumentException("Error While Resetting Password");
        }
    }

    @Override
    public void changePassword(String email,String oldP,String newP){
        User u=getByEmail(email);
        if (u != null && penco.matches(oldP,u.getPassword())) {
            u.setPassword(penco.encode(newP));
            urp.save(u);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void changeTempPassword(String email,String oldP,String newP){
        User u=getByEmail(email);
         if (u != null && oldP.equals(u.getPassword())) {
            u.setPassword(penco.encode(newP));
            urp.save(u);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<User> getAllUsers(int pgn, int size) {
        return urp.findAll(   PageRequest.of(pgn, size, Sort.by(Sort.Direction.ASC, "firstName","lastName") )  )
                .stream().toList();
    }

    @Override
    public void deleteUser(long userId) {
        urp.delete(getById(userId));
    }

    @Override
    public User updateUser(long uId, UpdateUserDTO ud) {
        User u = getById(uId);

        if (ud.getUserName() != null) {
            u.setUserName(ud.getUserName());
        } else if (ud.getFirstName()!= null) {
            u.setFirstName(ud.getFirstName());
        } else if (ud.getMiddleName() != null) {
            u.setMiddleName(ud.getMiddleName());
        } else if (ud.getLastName() != null) {
            u.setLastName(ud.getLastName());
        } else if (ud.getAddress() != null) {
            u.setAddress(ud.getAddress());
        } else if (ud.getPhoneNumber() != null) {
            u.setPhoneNumber(ud.getPhoneNumber());
        } else if (ud.getEmail() != null) {
            if (u.getEmail().equals(ud.getEmail())){

            }else {
                u.setEmail(ud.getEmail());
                ems.sendVerificationEmail(ud.getEmail());
                u.setEmailVerified(false);
            }
        } else if (ud.getGender() != null) {
            u.setGender(ud.getGender());
        } else if (ud.getDesignation() != null) {
            u.setDesignation(ud.getDesignation());
        } else if (ud.getRole() != null) {
            u.setRole(ud.getRole());
        } else if (ud.getBirthDate() != null) {
            u.setBirthDate(ud.getBirthDate());
        } else if (ud.getPassword() != null) {
            u.setPassword(pnc.encode(ud.getPassword()));
        } else if (ud.getDeptId() != 0) {
            Department byId = drp.findById(ud.getDeptId()).orElseThrow(()->new ResourceNotFoundException("Department","DepatmentId",String.valueOf(ud.getDeptId())));
            u.setDept(byId);
        }

        return urp.save(u);

    }

}
