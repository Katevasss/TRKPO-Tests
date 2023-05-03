package documents.jpa.entityParser.files;

import documents.dao.UserDao;
import documents.dto.files.documents.DocumentDto;
import documents.jpa.entity.files.catalogues.Catalogue;
import documents.jpa.entity.files.documents.Document;
import documents.jpa.entity.files.documents.DocumentType;
import documents.jpa.entity.files.documents.PriorityEnum;
import documents.jpa.entity.user.User;
import documents.jpa.exceprions.IdNotFoundException;
import documents.jpa.repository.CatalogueRepository;
import documents.jpa.repository.DocumentRepository;
import documents.jpa.repository.DocumentTypeRepository;
import documents.jpa.repository.UserRepository;
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
