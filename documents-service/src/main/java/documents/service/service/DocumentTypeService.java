package documents.service.service;

import documents.dao.DocumentTypeDao;
import documents.dto.files.documents.DocumentTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTypeService {

    @Autowired
    DocumentTypeDao documentTypeDao;

    public List<DocumentTypeDto> getAllDocumentTypes(){
        return documentTypeDao.getAllDocumentTypes();
    }

    public DocumentTypeDto addDocumentType(DocumentTypeDto documentTypeDto){
        return documentTypeDao.addNewDocumentType(documentTypeDto);
    }

}
