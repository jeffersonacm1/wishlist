package br.com.wishlist.bdd.steps;

import br.com.wishlist.bdd.CucumberSpringConfiguration;
import br.com.wishlist.model.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddProductToWishlistStepDefinitions extends CucumberSpringConfiguration {

    @Autowired
    private MockMvc mvc;

    ResultActions action;

    @Autowired
    private ObjectMapper objectMapper;

    @When("the client adds a product to the wishlist")
    public void the_client_adds_a_product_to_the_wishlist() throws Exception {
        ProductDTO productDTO = new ProductDTO("1", "Product 1", BigDecimal.valueOf(1000));

        action=mvc.perform(post("/api/v1/wishlists/user/2/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)));
    }

    @Then("the product is added to the wishlist successfully")
    public void the_product_is_added_to_the_wishlist_successfully() throws Exception {
        ProductDTO productDTO = this.service.getProductById("2", "1");
        Assertions.assertNotNull(productDTO);
    }

    @Then("the client receives a successful inclusion return status code")
    public void the_client_receives_a_successful_inclusion_return_status_code() throws Exception {
        action.andExpect(status().isCreated());
    }

}
