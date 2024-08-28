package br.com.wishlist.model.entity;

import br.com.wishlist.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "wishlist")
public class Wishlist implements Serializable {

    @Serial
    private static final long serialVersionUID = 520936502681L;

    @Id
    private String id;
    private String userId;
    private Set<ProductDTO> products = new HashSet<>();

    public Wishlist(String userId) {
        this.userId = userId;
        this.products = new HashSet<>();
    }
}
