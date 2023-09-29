package com.example.userrestservice.controller;

import com.example.userrestservice.dto.DateRange;
import com.example.userrestservice.dto.UserDto;
import com.example.userrestservice.dto.UserSavingDto;
import com.example.userrestservice.exception.BigSizeException;
import com.example.userrestservice.exception.EntityValidationException;
import com.example.userrestservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDto>> getUsers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id") String sort) {

        if (size > 100) {
            throw new BigSizeException("You can get maximum 100 customers at one time");
        }

        Page<UserDto> allCustomers = userService.getAllUsers(PageRequest.of(page, size, Sort.by(sort)));
        return ResponseEntity.ok(allCustomers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/in-range")
    public ResponseEntity<List<UserDto>> getUsersInBirthdayRange(@RequestBody @Valid DateRange dateRange, Errors errors) {
        if (errors.hasErrors()) {
            throw new EntityValidationException("Validation failed");
        }
        List<UserDto> usersInRange = userService.getAllUsersInRange(dateRange);
        return ResponseEntity.ok(usersInRange);
    }

    @PostMapping
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserSavingDto userDto, Errors errors) {
        if (errors.hasErrors()) {
            throw new EntityValidationException("Validation failed");
        }
        UserDto savedUser = userService.registerUser(userDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserSavingDto userSavingDto) {
        UserDto updatedUserDto = userService.updateUser(id, userSavingDto);
        return ResponseEntity.ok(updatedUserDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
