package siem.user;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    EntityManager entityManager;
    public List<User> getUsers() {
        return userRepository.listAll();
    }

    public Optional<User> findProfileById(long id) {
        return userRepository.findByIdOptional(id);
    }

    @Transactional
    public Boolean create(User user) {
        userRepository.persist(user);
        return user.isPersistent();
    }

    @Transactional
    public User update(User user) {
        return userRepository.update(user).orElseThrow(() -> new InvalidParameterException("Profile not found"));
    }

    @Transactional
    public boolean delete(long id) {
        return userRepository.deleteById(id);
    }
}
