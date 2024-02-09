package com.example.backend.web.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserFactory userFactory;

    @Override
    public UserDTO getByIdUser(Long id) {
        UserEntity userId = userRepository.getReferenceById(id);
        return userFactory.makeUserFactory(userId);
    }

    @Override
    public Optional<UserEntity> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDTO> getByAllUser() {
        return userRepository.findAll().stream()
                .map(userFactory::makeUserFactory)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateByIdUser(Long id, UserEntity user) {
        UserEntity userId = userRepository.getReferenceById(id);

        UserEntity userSave = UserEntity.builder()
                .firstname(userId.getFirstname())
                .lastname(userId.getLastname())
                .number(userId.getNumber())
                .email(userId.getEmail())
                .role(userId.getRole())
                .password(userId.getPassword())
                .product(userId.getProduct())
                .build();

        return userFactory.makeUserFactory(userRepository.save(userSave));
    }

    @Override
    public void deleteByIdUser(Long id) {
        userRepository.deleteById(id);
    }
}
