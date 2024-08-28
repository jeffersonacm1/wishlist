package br.com.wishlist.bdd.steps;

import br.com.wishlist.bdd.CucumberSpringConfiguration;
import br.com.wishlist.enums.WishlistErrorEnum;
import br.com.wishlist.model.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddProductToFullWishlistStepDefinitions extends CucumberSpringConfiguration {

    @Autowired
    private MockMvc mvc;

    ResultActions action;

    @Autowired
    private ObjectMapper objectMapper;

    @Given("the wishlist already full")
    public void fullWishlist() {
        for (int i = 1; i < 21; i++) {
            this.service.addProduct("3", new ProductDTO(String.valueOf(i), "product", BigDecimal.valueOf(100)));
        }
    }

    @When("the client adds a product on full wishlist")
    public void the_client_adds_a_product_on_full_wishlist() throws Exception {
        ProductDTO productDTO = new ProductDTO("21", "Product 21", BigDecimal.valueOf(1000));

        action=mvc.perform(post("/api/v1/wishlists/user/3/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)));
    }

    @Then("the client receives an error message about wishlist limit reached")
    public void the_client_receives_an_error_message_about_wishlist_limit_reached() throws Exception {
        action.andExpect(jsonPath("message",
                Matchers.is(WishlistErrorEnum.WISHLIST_LIMIT_REACHED.getMessage()))
        );
    }

    @Then("the client receives a status about wishlist limit reached")
    public void the_client_receives_a_status_about_wishlist_limit_reached() throws Exception {
        action.andExpect(status().isConflict());
    }
}
