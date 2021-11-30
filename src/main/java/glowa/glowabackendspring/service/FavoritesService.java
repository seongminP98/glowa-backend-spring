package glowa.glowabackendspring.service;

import glowa.glowabackendspring.domain.Favorites;
import glowa.glowabackendspring.domain.User;
import glowa.glowabackendspring.dto.favorites.FavoritesInfoDto;
import glowa.glowabackendspring.exception.FavoritesException;
import glowa.glowabackendspring.exception.LoginException;
import glowa.glowabackendspring.repository.favorites.FavoritesRepository;
import glowa.glowabackendspring.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final UserRepository userRepository;

    @Transactional
    public Favorites add(FavoritesInfoDto favoritesInfo, User me) {
        Optional<User> userMe = userRepository.findById(me.getId());
        if(userMe.isEmpty()) {
            throw new LoginException("잘못된 사용자입니다. 다시 로그인해주세요.");
        }
        Optional<Favorites> alreadyFavorites = favoritesRepository.findOneByUserAndRestaurantAndAddress(userMe.get(), favoritesInfo.getRestaurant(), favoritesInfo.getAddress());
        if(alreadyFavorites.isPresent()) {
            throw new FavoritesException("이미 추가한 장소입니다.");
        }
        Favorites favorites = new Favorites(userMe.get(), favoritesInfo.getRestaurant(), favoritesInfo.getAddress(), favoritesInfo.getKind());
        return favoritesRepository.save(favorites);
    }
}
