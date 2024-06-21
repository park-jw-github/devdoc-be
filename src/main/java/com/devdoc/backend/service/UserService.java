package com.devdoc.backend.service;

import com.devdoc.backend.model.Resume;
import com.devdoc.backend.model.User;
import com.devdoc.backend.dto.ResumeDTO;
import com.devdoc.backend.repository.ResumeRepository;
import com.devdoc.backend.repository.UserRepository;
import com.devdoc.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    // 회원가입 : Email 형식 검증
    public User saveUser(User user) {
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email format: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    // Resume 목록 조회 : User ID = 1 --------------------------------------------- Test Code
    public List<ResumeDTO> getAllResumesByUserIdTest() {
        int userId = 1;
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with id: " + userId);
        }

        return user.get().getResumes().stream()
                .map(resume -> new ResumeDTO(resume.getId(), resume.getTitle(), resume.getCreatedAt()))
                .collect(Collectors.toList());
    }

    // Resume 목록 조회 : 로그인 User
    public List<ResumeDTO> getAllResumesByUserId() {
        int userId = getCurrentUserId();
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with id: " + userId);
        }

        return user.get().getResumes().stream()
                .map(resume -> new ResumeDTO(resume.getId(), resume.getTitle(), resume.getCreatedAt()))
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------------------------------------
    // 호출함수
    // -------------------------------------------------------------------------------------

    // User 조회
    public User getUserByUserId(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // 로그인 Username 조회
    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // 로그인 User ID 조회
    public int getCurrentUserId() {
        String username = getCurrentUsername();
        Optional<User> user = userRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        return user.get().getId();
    }

    // 로그인 User =?= Resume 소유자 확인
    public boolean isOwner(int resumeId) {
        int userId = getCurrentUserId();
        Optional<Resume> resume = resumeRepository.findById(resumeId);

        if (resume.isEmpty()) {
            return false;
        }

        return resume.get().getUser().getId() == userId;
    }

    // Email 형식 검증
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }
}
