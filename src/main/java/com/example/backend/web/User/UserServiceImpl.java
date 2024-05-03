package com.example.backend.web.User;

import com.example.backend.web.User.store.dto.UserDTO;
import com.example.backend.web.User.store.factory.UserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.backend.utils.exception.RequestException.badRequestException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserFactory userFactory;

    @Override
    public UserDTO getByIdUser(final Long id) {
        UserEntity userId = getById(id);
        return userFactory.apply(userId);
    }

    @Override
    public UserEntity getById(final Long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public Optional<UserEntity> getByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity getByUserData(final String userData) {
        return userRepository.findByEmail(userData).orElseThrow(
                () -> badRequestException("Not userData: " + userData)
        );
    }

    @Override
    public List<UserDTO> getByAllUser() {
        return userRepository.findAll().stream()
                .map(userFactory)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateByIdUser(final Long id, final UserDTO user) {
        final UserEntity userId = getById(id);

            userId.setFirstname(user.firstname());
            userId.setLastname(user.lastname());
            userId.setPhone(user.phone());
            userId.setEmail(user.email());
            userId.setPassword(user.password());

        return userFactory.apply(userRepository.save(userId));
    }

    @Override
    public void deleteByIdUser(final Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity mySave(final UserEntity user) {
        return userRepository.save(user);
    }
}