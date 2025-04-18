package com.ebanx.takehome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class DepositTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void depositNegativeAmount() throws Exception {
        String json = """
                    {
                        "type": "deposit",
                        "destination": "100",
                        "amount": -10
                    }
                """;

        MvcResult result = mockMvc.perform(
                        post("/event")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals("invalid params", responseBody);
    }

    @Test
    void depositPositiveAmount() throws Exception {
        String json = """
                    {
                        "type": "deposit",
                        "destination": "100",
                        "amount": 10
                    }
                """;

        mockMvc.perform(
                        post("/event")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.destination.id").value("100"))
                .andExpect(jsonPath("$.destination.balance").value(10));
    }

}
