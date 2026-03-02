package org.iths.techstore;

import org.iths.techstore.Model.Supplier;
import org.iths.techstore.Repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SupplierIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SupplierRepository supplierRepository;

    @BeforeEach
    void cleanDb() {
        supplierRepository.deleteAll();
    }

    // Integration test for create supplier
    @Test
    @DisplayName("Create supplier successfully")
    void createSupplier() throws Exception {
        var result = mockMvc.perform(post("/suppliers")
                        .param("companyName", "Company A")
                        .param("country", "Sweden")
                        .param("contactPerson", "Ali")
                        .param("email", "a@a.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/suppliers/*"))
                .andReturn();

        assertEquals(1, supplierRepository.count());

        String redirectUrl = result.getResponse().getRedirectedUrl();
        mockMvc.perform(get(redirectUrl)).andExpect(status().isOk())
                .andExpect(content().string(containsString("Company A")));
    }

    // Integration test for get supplier by id
    @Test
    @DisplayName("Get supplier by id returns ok")
    void getSupplierByIDTest() throws Exception {
        Supplier saved = supplierRepository.save(
                new Supplier(null, "Company A", "Sweden", "Ali", "a@a.com")
        );

        mockMvc.perform(get("/suppliers/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Company A")));
    }

    // Integration test for get all suppliers
    @Test
    @DisplayName("Get all supplier from H2")
    void getAllSuppliersTest() throws Exception {
        supplierRepository.save(new Supplier(null, "Company A", "Sweden", "Ali", "a@a.com"));
        supplierRepository.save(new Supplier(null, "Company B", "Norway", "Sara", "b@b.com"));

        mockMvc.perform(get("/suppliers"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Company A")))
                .andExpect(content().string(containsString("Company B")));
    }
}
