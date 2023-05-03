package com.github.documents.jpa.daoImpl;

import com.github.documents.dao.FileAbstractDao;
import com.github.documents.dto.restdtos.ManageAccessDto;
import com.github.documents.dto.user.UserDto;
import com.github.documents.jpa.daoImpl.FileAbstractDaoJpa;
import com.github.documents.jpa.entity.files.FileAbstract;
import com.github.documents.jpa.entity.user.User;
import com.github.documents.jpa.entityParser.user.UserParser;
import com.github.documents.jpa.exceprions.IdNotFoundException;
import com.github.documents.jpa.repository.FileAbstractRepository;
import com.github.documents.jpa.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.List.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileAbstractDaoJpaTest {

    @Mock
    private FileAbstractRepository fileAbstractRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private UserParser userParser;

    @InjectMocks
    private FileAbstractDaoJpa fileAbstractDaoJpa;
    private User mockUser;
    private FileAbstract mockFileAbstract;
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        mockUser = new User();
        mockUser.setId(1L);

        mockFileAbstract = new FileAbstract();
        mockFileAbstract.setId(1L);
        mockFileAbstract.setUserCreatedBy(mockUser);
    }

    @Test
    @DisplayName("Test checkRWAccess should return false when user doesn't have permission")
    public void testCheckRWAccessShouldReturnFalseWhenUserDoesntHavePermission() {
        // Arrange
        FileAbstract fileAbstract = new FileAbstract();
        User user = new User();
        user.setId(1L);
        when(fileAbstractRepository.findById(1L)).thenReturn(Optional.of(fileAbstract));
        when(authentication.getPrincipal()).thenReturn(user);
        // Act
        boolean hasAccess = fileAbstractDaoJpa.checkRWAccess(1L);
        // Assert
        Assertions.assertFalse(hasAccess);
    }

    @Test
    @DisplayName("Test checkRAccess should return true when user has permission")
    public void testCheckRAccessShouldReturnTrueWhenUserHasPermission() {
        // Arrange
        FileAbstract fileAbstract = new FileAbstract();
        User user = new User();
        user.setId(1L);
        when(fileAbstractRepository.findById(1L)).thenReturn(Optional.of(fileAbstract));
        when(authentication.getPrincipal()).thenReturn(user);
        //fileAbstract.setReadPermissionUsers(List.of(user));
        // Act
        boolean hasAccess = fileAbstractDaoJpa.checkRAccess(1L);
        // Assert
        Assertions.assertTrue(hasAccess);
    }

    @Test
    void testCheckRWAccessIdNotFound() {
        when(fileAbstractRepository.findById(1L)).thenThrow(new IdNotFoundException());

        assertThrows(IdNotFoundException.class, () -> fileAbstractDaoJpa.checkRWAccess(1L));
    }

    @Test
    void testCheckRWAccess_WithValidId_ShouldReturnTrue() {
        // Arrange
        Long fileId = 1L;
        User user = new User();
        user.setId(1L);

        FileAbstract fileAbstract = new FileAbstract();
        fileAbstract.setId(fileId);
        fileAbstract.setUserCreatedBy(user);

        when(fileAbstractRepository.findById(fileId)).thenReturn(Optional.of(fileAbstract));
        when(entityManager.merge(any())).thenReturn(fileAbstract);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);

        // Act
        boolean result = fileAbstractDaoJpa.checkRWAccess(fileId);

        // Assert
        assertTrue(result);
        verify(fileAbstractRepository).findById(fileId);
        verify(entityManager, times(2)).merge(any());
        verify(SecurityContextHolder.getContext().getAuthentication(), times(1)).getPrincipal();
    }

    @Test
    void testCheckRWAccess_WithInvalidId_ShouldThrowIdNotFoundException() {
        // Arrange
        Long fileId = 1L;

        when(fileAbstractRepository.findById(fileId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IdNotFoundException.class, () -> fileAbstractDaoJpa.checkRWAccess(fileId));
        verify(fileAbstractRepository).findById(fileId);
        verifyNoMoreInteractions(entityManager, SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testCheckRWAccess_WithNoAccess_ShouldReturnFalse() {
        // Arrange
        Long fileId = 1L;
        User user = new User();
        user.setId(1L);

        FileAbstract fileAbstract = new FileAbstract();
        fileAbstract.setId(fileId);

        when(fileAbstractRepository.findById(fileId)).thenReturn(Optional.of(fileAbstract));
        when(entityManager.merge(any())).thenReturn(fileAbstract);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);

        // Act
        boolean result = fileAbstractDaoJpa.checkRWAccess(fileId);

        // Assert
        assertFalse(result);
        verify(fileAbstractRepository).findById(fileId);
        verify(entityManager, times(2)).merge(any());
        verify(SecurityContextHolder.getContext().getAuthentication(), times(1)).getPrincipal();
    }
}
