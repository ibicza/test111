package business.hub.cartservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Класс представляет объект пользователя.
 * Каждый пользователь имеет id, username, cart - корзина.
 */
@Getter
@Entity
@Setter
@NoArgsConstructor
@ToString
@Table(name = "users")
public class User {
    private static final int MAX_USER_NAME_LENGTH = 255;
    private static final int MAX_PRODUCT_ID_LENGTH = 255;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_users", length = MAX_PRODUCT_ID_LENGTH, nullable = false)
    private int id;

    @Column(name = "username", length = MAX_USER_NAME_LENGTH)
    @NotEmpty(message = "not NULL")
    private String username;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

}
