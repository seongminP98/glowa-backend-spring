package glowa.glowabackendspring.repository.favorites;

import glowa.glowabackendspring.domain.Favorites;
import glowa.glowabackendspring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
    Optional<Favorites> findOneByUserAndRestaurantAndAddress(User user, String restaurant, String address);
    List<Favorites> findAllByUser(User user);
}
