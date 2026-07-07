package com.example.schoolsystem.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUploadPhotoFailsWithRequestBody() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/students/photo/upload")
                        .file(file)
                        .param("studentId", "1"))
                .andDo(result -> {
                    if (result.getResponse().getStatus() != 415) {
                        System.out.println("[DEBUG_LOG] Status: " + result.getResponse().getStatus());
                        System.out.println("[DEBUG_LOG] Content: " + result.getResponse().getContentAsString());
                        if (result.getResolvedException() != null) {
                            result.getResolvedException().printStackTrace();
                        }
                    }
                })
                .andExpect(status().isUnsupportedMediaType());
    }
}
