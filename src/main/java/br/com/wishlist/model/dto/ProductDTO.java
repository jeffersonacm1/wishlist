package br.com.wishlist.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 22624256L;

    @NotBlank(message = "Product Id is required to add a new product")
    @Schema( description = "Product Id", example = "507f191e810c19729de860ea")
    private String id;

    @Schema( description = "Product name", example = "Smartphone Motorola Moto G04s 128GB Grafite 4GB RAM 6,6")
    private String name;

    @Schema( description = "Product Price", example = "1200.00")
    private BigDecimal price;

}
