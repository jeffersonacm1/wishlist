package br.com.wishlist.bdd.steps;

import br.com.wishlist.bdd.CucumberSpringConfiguration;
import br.com.wishlist.model.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RetrieveAllItemsFromWishlistStepDefinitions extends CucumberSpringConfiguration {

    @Autowired
    private MockMvc mvc;

    ResultActions action;

    @Autowired
    private ObjectMapper objectMapper;

    @Given("the wishlist with products")
    public void populateWishlist() {
        for (int i = 1; i < 6; i++) {
            this.service.addProduct("6", new ProductDTO(String.valueOf(i), "product", BigDecimal.valueOf(100)));
        }
    }

    @When("the client retrieves all items in the wishlist")
    public void the_client_retrieves_all_items_in_the_wishlist() throws Exception {
        action=mvc.perform(get("/api/v1/wishlists/user/6/products")
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Then("the client receives a list of all items in the wishlist")
    public void the_client_receives_a_list_of_all_items_in_the_wishlist() throws Exception {
        action.andExpect(jsonPath("$.length()").value(5));
    }

    @Then("the client receives a success of get all items status code")
    public void the_client_receives_a_success_of_get_all_items_status_code() throws Exception {
        action.andExpect(status().isOk());
    }
}
