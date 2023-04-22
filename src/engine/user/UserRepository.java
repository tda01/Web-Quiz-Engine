package engine.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
    public User findUserByEmail(String email);

}