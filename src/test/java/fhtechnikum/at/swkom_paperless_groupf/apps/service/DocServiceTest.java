package fhtechnikum.at.swkom_paperless_groupf.apps.service;

import fhtechnikum.at.swkom_paperless_groupf.apps.entity.Doc;
import fhtechnikum.at.swkom_paperless_groupf.apps.repository.DocRepository;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.xml.validation.Validator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocServiceTest {
    @Mock
    private DocRepository docRepository;

    @InjectMocks
    private DocService docService;

    private LocalValidatorFactoryBean validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.afterPropertiesSet();
        this.validator = factoryBean;
    }
/*
    @Test
    void testSaveDocument_WithValidDoc_ShouldPass() throws IOException {
        // Arrange
        ClassLoader classLoader = getClass().getClassLoader();
        File exampleFile = new File(classLoader.getResource("example.pdf").getFile());
        byte[] fileContent = Files.readAllBytes(exampleFile.toPath());

        Doc validDoc = new Doc("Valid Title", fileContent);
        when(docRepository.save(any(Doc.class))).thenReturn(validDoc);

        // Act
        Doc savedDoc = docRepository.save(validDoc);

        // Assert
        assertEquals(validDoc, savedDoc);
        verify(docRepository, times(1)).save(validDoc);
    }

    @Test
    void testSaveDocument_WithInvalidTitle_ShouldFail() throws IOException {
        // Arrange
        ClassLoader classLoader = getClass().getClassLoader();
        File exampleFile = new File(classLoader.getResource("example.pdf").getFile());
        byte[] fileContent = Files.readAllBytes(exampleFile.toPath());

        Doc invalidDoc = new Doc();
        invalidDoc.setTitle(""); // Invalid title (empty)
        invalidDoc.setFileContent(fileContent);
        invalidDoc.setUploadDate(LocalDateTime.now());

        // Act
        Set<ConstraintViolation<Doc>> violations = validator.validate(invalidDoc);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<Doc> violation = violations.iterator().next();
        assertEquals("Title can not be empty! Must be between 2 and 50 characters long.", violation.getMessage());
    }

    @Test
    void testSaveDocument_WithTooLongTitle_ShouldFail() throws IOException {
        // Arrange
        ClassLoader classLoader = getClass().getClassLoader();
        File exampleFile = new File(classLoader.getResource("example.pdf").getFile());
        byte[] fileContent = Files.readAllBytes(exampleFile.toPath());

        Doc invalidDoc = new Doc();
        invalidDoc.setTitle("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"); // Invalid title (empty)
        invalidDoc.setFileContent(fileContent);
        invalidDoc.setUploadDate(LocalDateTime.now());

        // Act
        Set<ConstraintViolation<Doc>> violations = validator.validate(invalidDoc);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<Doc> violation = violations.iterator().next();
        assertEquals("Title can not be empty! Must be between 2 and 50 characters long.", violation.getMessage());
    }

    @Test
    void testSaveDocument_WithNullTitle_ShouldFail() throws IOException {
        // Arrange
        ClassLoader classLoader = getClass().getClassLoader();
        File exampleFile = new File(classLoader.getResource("example.pdf").getFile());
        byte[] fileContent = Files.readAllBytes(exampleFile.toPath());

        Doc invalidDoc = new Doc();
        invalidDoc.setTitle(null); // Invalid title - null
        invalidDoc.setFileContent(fileContent);
        invalidDoc.setUploadDate(LocalDateTime.now());

        // Act
        Set<ConstraintViolation<Doc>> violations = validator.validate(invalidDoc);

        // Assert
        assertEquals(1, violations.size());
    }

    @Test
    void testSaveDocument_WithNullFileContent_ShouldFail() {
        // Arrange
        Doc invalidDoc = new Doc();
        invalidDoc.setTitle("Valid title");
        invalidDoc.setFileContent(null);
        invalidDoc.setUploadDate(LocalDateTime.now());

        // Act
        Set<ConstraintViolation<Doc>> violations = validator.validate(invalidDoc);

        // Assert
        assertEquals(1, violations.size());
    }

    @Test
    void testSaveDocument_WithNoValues_ShouldFail() {
        // Arrange
        Doc invalidDoc = new Doc();

        // Act
        Set<ConstraintViolation<Doc>> violations = validator.validate(invalidDoc);

        // Assert
        assertEquals(3, violations.size());
    }


 */
}