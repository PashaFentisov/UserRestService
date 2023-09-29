package com.example.userrestservice.service;

import com.example.userrestservice.dto.DateRange;
import com.example.userrestservice.dto.UserDto;
import com.example.userrestservice.dto.UserSavingDto;
import com.example.userrestservice.entity.User;
import com.example.userrestservice.exception.EntityValidationException;
import com.example.userrestservice.exception.UnderageException;
import com.example.userrestservice.mapper.UserMapper;
import com.example.userrestservice.mapper.UserSavingMapper;
import com.example.userrestservice.repository.UserRepository;
import com.example.userrestservice.util.UserProperties;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserSavingMapper userSavingMapper;
    private final UserProperties userProperties;
    private static final String USER_ERROR_MESSAGE = "User with id %s doesn't exist";
    @Transactional(readOnly = true)
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }
    @Transactional
    public UserDto registerUser(UserSavingDto userDto) {
        int age = Period.between(userDto.getBirthDate(), LocalDate.now()).getYears();
        if (age < userProperties.getAge()) {
            throw new UnderageException("User must be older than " + userProperties.getAge() + " y o");
        }
        User savedUser = userRepository.save(userSavingMapper.toEntity(userDto));
        return userMapper.toDto(savedUser);
    }

    @Transactional
    public UserDto updateUser(Long id, UserSavingDto userSavingDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_ERROR_MESSAGE, id)));

        Optional.ofNullable(userSavingDto.getBirthDate()).ifPresent(user::setBirthDate);
        Optional.ofNullable(userSavingDto.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(userSavingDto.getFirstName()).ifPresent(user::setFirstName);
        Optional.ofNullable(userSavingDto.getLastName()).ifPresent(user::setLastName);
        Optional.ofNullable(userSavingDto.getPhoneNumber()).ifPresent(user::setPhoneNumber);
        return userMapper.toDto(user);
    }
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_ERROR_MESSAGE, id)));
        return userMapper.toDto(user);
    }
    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_ERROR_MESSAGE, id)));
        userRepository.delete(user);
    }
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsersInRange(DateRange dateRange) {
        if(dateRange.getFrom().isAfter(dateRange.getTo())){
            throw new EntityValidationException("from date must be less than to date");
        }
        return userRepository.findUsersInDateRange(dateRange.getFrom(), dateRange.getTo())
                .stream()
                .map(userMapper::toDto).toList();
    }
}
