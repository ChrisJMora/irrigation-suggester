package com.example.demo.application.services;

import com.example.demo.domain.exceptions.admin.UserNameOrEmailAreadyExistsException;
import com.example.demo.domain.exceptions.admin.NoSuchUserExistsException;
import com.example.demo.domain.models.UserEntity;
import com.example.demo.domain.models.WrappedEntity;
import com.example.demo.application.ports.services.UserService;
import com.example.demo.infrastructure.utils.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.infrastructure.repositories.UserRepository;

import java.util.*;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public WrappedEntity<UserEntity> saveUser(UserEntity user) {
        Map<String, String> details = new HashMap<>();
        if (ClientUsernameIsValid(user.getName())) {
            details.put("username", "The username already exists ");
        }
        if (UserEmailIsValid(user.getEmail())) {
            details.put("email", "The email already exists");
        }
        if (details.isEmpty()) {
            user.setPassword(AuthenticationUtil.hashPassword(user.getPassword()));
            return new WrappedEntity<>(userRepository.save(user));
        } else {
            throw new UserNameOrEmailAreadyExistsException("Invalid " +
                    "input", details);
        }
    }

    @Override
    public WrappedEntity<List<UserEntity>> getAllUsers() {
        return new WrappedEntity<>(userRepository.findAll());
    }

    @Override
    public WrappedEntity<UserEntity> getUser(String username, String email) {
        return new WrappedEntity<>(getUserByNameOrEmail(username, email));
    }

    @Override
    public WrappedEntity<UserEntity> updateUser(String username,
                                                String email, UserEntity user) {
        Map<String, String> details = new HashMap<>();  // Error details
        UserEntity _user = getUserByNameOrEmail(username, email);

        // Check if the username is different from the previous one, then
        // verify if is valid.
        if (username != null && !_user.getName().equals(username)) {
            if (ClientUsernameIsValid(user.getName())) {
                details.put("username", "The username already exists ");
            }
        }

        // Check if the email is different from the previous one, then verify
        // if is valid.
        if (email != null && !_user.getEmail().equals(email)) {
            if (UserEmailIsValid(user.getEmail())) {
                details.put("email", "The email aready exists");
            }
        }

        if (details.isEmpty()) {
            _user.setName(user.getName());
            _user.setEmail(user.getEmail());
//            _user.setFirstName(user.getFirstName());
//            _user.setLastName(user.getLastName());
            return new WrappedEntity<>(userRepository.save(_user));
        } else {
            throw new UserNameOrEmailAreadyExistsException("Invalid " +
                    "input", details);
        }
    }

    @Override
    public void deleteUser(String username, String email) {
        UserEntity user = getUserByNameOrEmail(username, email);
        userRepository.delete(user);
    }

    private boolean ClientUsernameIsValid(String username) {
        Optional<UserEntity> _user = userRepository.findByName(username);
        return _user.isPresent();
    }

    private boolean UserEmailIsValid(String email) {
        Optional<UserEntity> _user = userRepository.findByEmail(email);
        return _user.isPresent();
    }

    private UserEntity getUserByNameOrEmail(String username, String email) {
        Optional<UserEntity> user =
                userRepository.findByNameOrEmail(username, email);
        if (user.isEmpty()) {
            throw new NoSuchUserExistsException("No user was found");
        } else {
            return user.get();
        }
    }
}
