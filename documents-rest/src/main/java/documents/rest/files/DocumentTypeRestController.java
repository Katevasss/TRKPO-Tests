package documents.rest.files;

import documents.dto.files.documents.DocumentTypeDto;
import documents.service.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/type")
public class DocumentTypeRestController {
    @Autowired
    DocumentTypeService documentTypeService;

    @GetMapping(value = "",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DocumentTypeDto> getDocument() {
        return documentTypeService.getAllDocumentTypes();
    }

    @PostMapping(value = "",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public DocumentTypeDto addNewDocumentType(@RequestBody @Valid DocumentTypeDto documentTypeDto){
        return documentTypeService.addDocumentType(documentTypeDto);
    }
}
