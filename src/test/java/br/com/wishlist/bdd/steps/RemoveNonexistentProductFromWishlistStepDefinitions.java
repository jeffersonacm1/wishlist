package br.com.wishlist.bdd.steps;

import br.com.wishlist.bdd.CucumberSpringConfiguration;
import br.com.wishlist.enums.WishlistErrorEnum;
import br.com.wishlist.exception.EntityNotFoundException;
import br.com.wishlist.model.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RemoveNonexistentProductFromWishlistStepDefinitions extends CucumberSpringConfiguration {

    @Autowired
    private MockMvc mvc;

    ResultActions action;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult mvcResult;

    @When("the client removes a product that does not exist in the wishlist")
    public void the_client_removes_a_product_that_does_not_exist_in_the_wishlist() throws Exception {
        ProductDTO productDTO = new ProductDTO("2", "Product 1", BigDecimal.valueOf(1000));
        this.service.addProduct("5", productDTO);

        action=mvc.perform(delete("/api/v1/wishlists/user/5/products/1")
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Then("the client receives an error message about product not found")
    public void the_client_receives_an_error_message_about_product_not_found() throws Exception {
        action.andExpect(jsonPath("message",
                Matchers.is(String.format(WishlistErrorEnum.PRODUCT_NOT_FOUND.getMessage(), "1")))
        );
    }

    @Then("the client receives a status code about product not found")
    public void the_client_receives_a_status_code_about_product_not_found() throws Exception {
        action.andExpect(status().isNotFound());
    }

}
