package glowa.glowabackendspring.dto.favorites;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class FavoritesInfoDto {
    private String restaurant;
    private String address;
    private String kind;

    public FavoritesInfoDto(String restaurant, String address, String kind) {
        this.restaurant = restaurant;
        this.address = address;
        this.kind = kind;
    }
}
