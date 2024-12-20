    package com.qdtas.service.impl;

    import com.qdtas.dto.AddUserDto;
    import com.qdtas.dto.JwtResponse;
    import com.qdtas.dto.LoginDTO;
    import com.qdtas.dto.UpdateUserDTO;
    import com.qdtas.entity.*;
    import com.qdtas.exception.EmailAlreadyRegisteredException;
    import com.qdtas.exception.ResourceNotFoundException;
    import com.qdtas.repository.*;
    import com.qdtas.security.CustomUserDetails;
    import com.qdtas.security.JwtHelper;
    import com.qdtas.service.TaskService;
    import com.qdtas.service.UserService;
    import com.qdtas.utils.Role;
    import com.qdtas.utils.SubRole;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.core.env.Environment;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Sort;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.time.LocalDate;
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
        private EmploymentStatusRepository esrp;

        @Autowired
        private JobCategoryRepository jrp;

        @Autowired
        private JobRepository jobrp;

        @Autowired
        private ProjectRepository prp;
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private TaskService taskService;


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
        public void disableUser(long userId) {
            User u = getById(userId);
            u.setEmailVerified(false);
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
        public void updateTotalLeaves(Long userId, int newTotalLeaves) {
            User user = getById(userId);
            user.setTotalLeaves(newTotalLeaves);
            urp.save(user); // Save the user with updated total leaves
        }

        @Override
        public int getTotalUserCount() {
            return (int)urp.count();
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
                JobCategory jobCategory =new JobCategory();
                EmploymentStatus employmentStatus =new EmploymentStatus();
                Job job = new Job();
                try{
                    department = drp.findById(rdt.getDeptId()).orElseThrow(() -> new ResourceNotFoundException("Department","department_id",String.valueOf(rdt.getDeptId())));
                    jobCategory = jrp.findById(rdt.getJobCategoryId()).orElseThrow(() -> new ResourceNotFoundException("JobCategory","jobCategory_id",String.valueOf(rdt.getJobCategoryId())));
                    employmentStatus = esrp.findById(rdt.getEmploymentStatusId()).orElseThrow(() -> new ResourceNotFoundException("EmploymentStatus","employmentStatus_id",String.valueOf(rdt.getEmploymentStatusId())));
                    job = jobrp.findById(rdt.getJobId()).orElseThrow(() -> new ResourceNotFoundException("Job","job_id",String.valueOf(rdt.getJobId())));
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
                u.setAddress(rdt.getAddress());
                u.setDept(department);
                u.setJoinDate(new Date());
                u.setRole(Role.ROLE_USER.name());
                u.setSubRole(rdt.getSubRole());
                u.setPhoneNumber(rdt.getPhoneNumber());
                u.setJobId(job);
                u.setJobCategoryId(jobCategory);
                u.setEmploymentStatusId(employmentStatus);
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
            return urp.findAll(   PageRequest.of(pgn, size, Sort.by(Sort.Direction.DESC, "userId") )  )
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
            }
            if (ud.getFirstName() != null) {
                u.setFirstName(ud.getFirstName());
            }
            if (ud.getMiddleName() != null) {
                u.setMiddleName(ud.getMiddleName());
            }
            if (ud.getLastName() != null) {
                u.setLastName(ud.getLastName());
            }
            if (ud.getAddress() != null) {
                u.setAddress(ud.getAddress());
            }
            if (ud.getPhoneNumber() != null) {
                u.setPhoneNumber(ud.getPhoneNumber());
            }
            if (ud.getEmail() != null) {
                if (!u.getEmail().equals(ud.getEmail())) {
                    u.setEmail(ud.getEmail());
                    ems.sendVerificationEmail(ud.getEmail());
                    u.setEmailVerified(false);
                }
            }
            if (ud.getGender() != null) {
                u.setGender(ud.getGender());
            }
            if (ud.getJobId() != 0) {
                Job byId = jobrp.findById(ud.getJobId())
                        .orElseThrow(() -> new ResourceNotFoundException("Job", "job_id", String.valueOf(ud.getJobId())));
                u.setJobId(byId);
            }
            if (ud.getEmploymentStatusId() != 0) {
                EmploymentStatus byId = esrp.findById(ud.getEmploymentStatusId())
                        .orElseThrow(() -> new ResourceNotFoundException("EmploymentStatus", "employmentStatus_id", String.valueOf(ud.getEmploymentStatusId())));
                u.setEmploymentStatusId(byId);
            }
            if (ud.getJobCategoryId() != 0) {
                JobCategory byId = jrp.findById(ud.getJobCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("JobCategory", "jobCategory_id", String.valueOf(ud.getJobCategoryId())));
                u.setJobCategoryId(byId);
            }
            if (ud.getRole() != null) {
                u.setRole(ud.getRole());
            }
            if (ud.getSubRole() != null) {
                u.setSubRole(ud.getSubRole());
            }
            if (ud.getBirthDate() != null) {
                u.setBirthDate(ud.getBirthDate());
            }
            if (ud.getPassword() != null) {
                u.setPassword(pnc.encode(ud.getPassword()));
            }
            if (ud.getDeptId() != 0) {
                Department byId = drp.findById(ud.getDeptId())
                        .orElseThrow(() -> new ResourceNotFoundException("Department", "department_id", String.valueOf(ud.getDeptId())));
                u.setDept(byId);
            }

            return urp.save(u);
        }


        @Override
        public User userInfo(long uId) {
            return urp.findById(uId).orElseThrow(() -> new ResourceNotFoundException("User","user_id",String.valueOf(uId)));
        }

    //    @Override
    //    public List<User> getUserByUserName(String username) {
    //
    //        User u = urp.findByUserName(username);
    //        if (u != null) {
    //            return (List<User>) u;
    //        } else {
    //            throw new ResourceNotFoundException("User","username",username);
    //        }
    //    }

        @Override
        public List<User> getUserByUserName(String username) {
            List<User> users = urp.findByUserName(username);
            if (users.isEmpty()) {
                throw new ResourceNotFoundException("User", "username", username);
            }
            return users;
        }

    //    @Override
    //    public double calculateEmployeePerformance(long userId, long projectId, LocalDate startDate, LocalDate endDate) {
    //        User user = urp.findById(userId)
    //                .orElseThrow(() -> new ResourceNotFoundException("User", "UserId", String.valueOf(userId)));
    //
    //        Project project = prp.findById(projectId)
    //                .orElseThrow(() -> new ResourceNotFoundException("Project", "ProjectId", String.valueOf(projectId)));
    //
    //        if (!project.getTeam().contains(user)) {
    //            throw new ResourceNotFoundException("User", "UserId", "User is not assigned to this project");
    //        }
    //
    //        long totalDaysWorked = calculateDaysWorked(startDate, endDate);
    //
    //        // Calculate percentile based on the total days worked
    //        double percentile = calculatePercentile(totalDaysWorked);
    //
    //        return percentile;
    //    }
    //
    //    private long calculateDaysWorked(LocalDate startDate, LocalDate endDate) {
    //        return ChronoUnit.DAYS.between(startDate, endDate);
    //    }
    //
    //    private double calculatePercentile(long totalDaysWorked) {
    //        // Replace with your logic to calculate percentile
    //        // For example, if 30 days is the maximum days worked in a project
    //        long maxDays = 30;
    //        return (double) totalDaysWorked / maxDays * 100;
    //    }

        @Override
        public double calculateEmployeePerformance(long userId, LocalDate startDate, LocalDate endDate) {
            userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "UserId", String.valueOf(userId)));

            // Fetch tasks completed by the user within the date range
            List<Task> completedTasks = taskService.getTasksByStatusAndDateRange(userId, "COMPLETED", startDate, endDate);

            // Calculate performance (example: based on the number of tasks completed)
            return calculatePerformanceFromTasks(completedTasks);
        }

        private double calculatePerformanceFromTasks(List<Task> tasks) {
            // Example calculation: return the number of completed tasks
            return tasks.size();
        }

        @Override
        public Integer getTotalLeaves(Long userId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "UserId", String.valueOf(userId)));
            return user.getTotalLeaves();
        }



    }
