package com.ahamed.fintracker.service;

import com.ahamed.fintracker.dto.UserRequestDto;
import com.ahamed.fintracker.dto.UserResponseDto;
import com.ahamed.fintracker.exception.ResourceNotFoundException;
import com.ahamed.fintracker.model.User;
import com.ahamed.fintracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto createUser(UserRequestDto request) {
        log.info("Creating user with email: {}", request.getEmail());
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return toResponseDto(userRepository.save(user));
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto request) {
        User existing = getUserEntityById(id);
        existing.setName(request.getName());
        existing.setEmail(request.getEmail());
        existing.setPassword(request.getPassword());
        return toResponseDto(userRepository.save(existing));
    }

    @Transactional
    public UserResponseDto partialUpdate(Long id, UserRequestDto request) {
        User existing = getUserEntityById(id);
        if (request.getName() != null) existing.setName(request.getName());
        if (request.getEmail() != null) existing.setEmail(request.getEmail());
        if (request.getPassword() != null) existing.setPassword(request.getPassword());
        return toResponseDto(userRepository.save(existing));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        return toResponseDto(getUserEntityById(id));
    }

    @Transactional
    public void deleteUser(Long id) {
        User existing = getUserEntityById(id);
        userRepository.delete(existing);
    }

    private User getUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    private UserResponseDto toResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}