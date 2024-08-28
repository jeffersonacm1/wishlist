package br.com.wishlist.bdd.steps;

import br.com.wishlist.bdd.CucumberSpringConfiguration;
import br.com.wishlist.exception.EntityNotFoundException;
import br.com.wishlist.model.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RemoveProductFromWishlistStepDefinitions extends CucumberSpringConfiguration {

    @Autowired
    private MockMvc mvc;

    ResultActions action;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult mvcResult;

    @When("the client removes a product from the wishlist")
    public void the_client_removes_a_product_from_the_wishlist() throws Exception {
        ProductDTO productDTO = new ProductDTO("1", "Product 1", BigDecimal.valueOf(1000));
        this.service.addProduct("4", productDTO);

        action=mvc.perform(delete("/api/v1/wishlists/user/4/products/1")
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Then("the product is removed from the wishlist successfully")
    public void the_product_is_removed_from_the_wishlist_successfully() throws Exception {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            this.service.getProductById("4", "1");
        });
    }

    @Then("the client receives a success remove status code")
    public void the_client_receives_a_successful_remove_status_code() throws Exception {
        action.andExpect(status().isNoContent());
    }

}
