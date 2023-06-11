package siem.user;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.text.WordUtils;

import java.util.List;
import java.util.Optional;
@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    public void persist(User user) {
        var message = WordUtils.capitalize(user.getUserState());
        user.setUserState(message);
        PanacheRepository.super.persist(user);
    }

    @Override
    public User findById(Long id) {
        return PanacheRepository.super.findById(id);
    }

    public List<User> findByMessage(String userState) {
        return this.list("userState", WordUtils.capitalize(userState));
    }

    public Optional<User> update(User user) {
        final var id = user.getId();
        var savedOpt = this.findByIdOptional(id);
        if (savedOpt.isEmpty()) {
            return Optional.empty();
        }

        var saved = savedOpt.get();
        saved.setUserState(user.getUserState());

        return Optional.of(saved);
    }
}
