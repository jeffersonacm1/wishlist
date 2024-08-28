package br.com.wishlist.bdd.steps;

import br.com.wishlist.bdd.CucumberSpringConfiguration;
import br.com.wishlist.enums.WishlistErrorEnum;
import br.com.wishlist.model.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class AddExistingProductToWishlistStepDefinitions extends CucumberSpringConfiguration {

    @Autowired
    private MockMvc mvc;

    ResultActions action;

    @Autowired
    private ObjectMapper objectMapper;

    @When("the client adds a duplicate product to the wishlist")
    public void the_client_adds_a_duplicate_product_with_id_and_name_to_the_wishlist() throws Exception {
        ProductDTO productDTO = new ProductDTO("1", "product 1", BigDecimal.valueOf(1000));
        this.service.addProduct("1", productDTO);

        action=mvc.perform(post("/api/v1/wishlists/user/1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)));
    }

    @Then("the client receives an error message about product already exists")
    public void the_client_receives_an_error_message_about_product_already_exists() throws Exception {
        action.andExpect(jsonPath("message",
                Matchers.is(String.format(WishlistErrorEnum.PRODUCT_ALREADY_EXISTS.getMessage(), "1")))
        );
    }

    @Then("the client receives status error about product already exists")
    public void the_client_receives_status_error_about_product_already_exists() throws Exception {
        action.andExpect(status().isConflict());
    }

}
