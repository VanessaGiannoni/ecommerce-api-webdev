package br.senac.devweb.api.ecommerce.contact;


import br.senac.devweb.api.ecommerce.client.Client;
import br.senac.devweb.api.ecommerce.client.ClientService;
import br.senac.devweb.api.ecommerce.utils.Pagination;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/contact")
@AllArgsConstructor
public class ContactController {
    private ContactService contactService;
    private ClientService clientService;

    @PostMapping("/")
    public ResponseEntity<ContactRepresentation.ContactDetail> createContact(
            @Valid @RequestBody ContactRepresentation.CreateOrUpdateContact contactRepresentation
    ) {
        Client client = this.clientService.getOneClient(contactRepresentation.getClient());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ContactRepresentation.ContactDetail.from(
                        this.contactService.save(contactRepresentation, client))
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactRepresentation.ContactDetail> updateContact(
            @PathVariable("id") Long id,
            @Valid @RequestBody ContactRepresentation.CreateOrUpdateContact createOrUpdateContact
            ) {

        Client client = this.clientService.getOneClient(createOrUpdateContact.getClient());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ContactRepresentation.ContactDetail.from(
                                this.contactService.update(id, createOrUpdateContact, client)
                        )
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactRepresentation.ContactDetail> getContactById(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ContactRepresentation.ContactDetail.from(
                        this.contactService.getContactById(id)
                )
        );
    }

    @GetMapping("/")
    public ResponseEntity<Pagination> getAllContacts(
            @QuerydslPredicate(root = Contact.class) Predicate filters,
            @Valid @RequestParam(name = "selectedPage", defaultValue = "1") Integer selectedPage,
            @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize
            ) {
        if (selectedPage < 1) {
            throw new IllegalArgumentException("O número da página não pode ser menor que 1");
        }

        Pageable pageRequest = PageRequest.of(selectedPage-1, pageSize);

        Page<Contact> contactPage = this.contactService.getAllContacts(filters, pageRequest);

        Pagination pagination = Pagination.builder()
                .content(ContactRepresentation.ContactList.from(contactPage.getContent()))
                .selectedPage(selectedPage)
                .pageSize(pageSize)
                .pageCount(contactPage.getTotalPages())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(pagination);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ContactRepresentation.ContactDetail> deleteContact(
            @PathVariable("id") Long id
    ) {
        this.contactService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
