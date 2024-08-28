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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RetrieveSpecificItemFromWishlistStepDefinitions extends CucumberSpringConfiguration {

    @Autowired
    private MockMvc mvc;

    ResultActions action;

    @Autowired
    private ObjectMapper objectMapper;

    @When("the client retrieves a specific product from the wishlist")
    public void the_client_retrieves_a_specific_product_from_the_wishlist() throws Exception {
        ProductDTO productDTO = new ProductDTO("1", "Product 1", BigDecimal.valueOf(1000));
        this.service.addProduct("7", productDTO);

        action=mvc.perform(get("/api/v1/wishlists/user/7/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)));
    }

    @Then("the client receives the item details")
    public void the_client_receives_the_item_details() throws Exception {
        action.andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Product 1"))
                .andExpect(jsonPath("$.price").value(1000));
    }

    @Then("the client receives a success codestatus of get the item")
    public void the_client_receives_a_success_codestatus_of_get_the_item() throws Exception {
        action.andExpect(status().isOk());
    }

}



