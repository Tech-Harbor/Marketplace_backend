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

            userId.setFirstname(user.getFirstname());
            userId.setLastname(user.getLastname());
            userId.setNumber(user.getNumber());
            userId.setEmail(user.getEmail());
            userId.setPassword(user.getPassword());
            userId.setProduct(user.getProduct());

        return userFactory.makeUserFactory(userRepository.save(userId));
    }

    @Override
    public void deleteByIdUser(Long id) {
        userRepository.deleteById(id);
    }
}
