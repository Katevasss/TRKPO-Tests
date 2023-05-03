package com.github.documents.jpa.entityParser.files;

import com.github.documents.dao.UserDao;
import com.github.documents.dto.files.documents.DocumentDto;
import com.github.documents.jpa.entity.files.catalogues.Catalogue;
import com.github.documents.jpa.entity.files.documents.Document;
import com.github.documents.jpa.entity.files.documents.DocumentType;
import com.github.documents.jpa.entity.files.documents.PriorityEnum;
import com.github.documents.jpa.entity.user.User;
import com.github.documents.jpa.exceprions.IdNotFoundException;
import com.github.documents.jpa.repository.CatalogueRepository;
import com.github.documents.jpa.repository.DocumentRepository;
import com.github.documents.jpa.repository.DocumentTypeRepository;
import com.github.documents.jpa.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentParserTest {

    @Mock
    private CatalogueRepository catalogueRepository;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DocumentTypeRepository documentTypeRepository;

    @Mock
    private UserDao userDaoJpa;

    @Mock
    private ConcreteDocumentParser concreteDocumentParser;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private DocumentParser documentParser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEtoDTO_NullDocument_ReturnsNull() {
        // Arrange
        Document document = null;

        // Act
        DocumentDto result = documentParser.EtoDTO(document);

        // Assert
        assertNull(result);
    }

    @Test
    void testFromList_EmptyList_ReturnsEmptyDocumentDtoList() {
        // Arrange
        List<Document> documentList = new ArrayList<>();

        // Act
        List<DocumentDto> result = documentParser.fromList(documentList);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
