package com.example.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"spring.cloud.consul.enabled=false", "spring.cloud.discovery.enabled=false"})
@AutoConfigureMockMvc
class ClientConsulControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateAndFetchClientWithConsulDisabledInTest() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"Moad\",\"age\":23}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Moad"));

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Moad"));
    }
}
