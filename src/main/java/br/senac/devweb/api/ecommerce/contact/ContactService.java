package br.senac.devweb.api.ecommerce.contact;

import br.senac.devweb.api.ecommerce.client.Client;
import br.senac.devweb.api.ecommerce.exceptions.NotFoundException;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

    public Contact save(ContactRepresentation.CreateOrUpdateContact contactRepresentation, Client client) {
        return this.contactRepository.save(
                Contact.builder()
                        .client(client)
                        .type(contactRepresentation.getType())
                        .recovery(contactRepresentation.getRecovery())
                        .email(contactRepresentation.getEmail())
                        .phone(contactRepresentation.getPhone())
                        .note(contactRepresentation.getNote())
                        .build()
        );
    }

    public Contact getContactById(Long id) {
        BooleanExpression filter = QContact.contact.id.eq(id);

        return this.contactRepository.findOne(filter).orElseThrow(() -> new NotFoundException(("Contato não encontrado!")));
    }

    public Page<Contact> getAllContacts(Predicate filter, Pageable pageable) {
        return this.contactRepository.findAll(filter, pageable);
    }

    public Contact update(Long id, ContactRepresentation.CreateOrUpdateContact contactRepresentation, Client client) {
        Contact oldContact = this.getContactById(id);

        Contact newContact = oldContact.toBuilder()
                .client(client)
                .type(contactRepresentation.getType())
                .recovery(contactRepresentation.getRecovery())
                .email(contactRepresentation.getEmail())
                .phone(contactRepresentation.getEmail())
                .note(contactRepresentation.getNote())
                .build();

        return this.contactRepository.save(newContact);
    }

    public void delete(Long id) {
        Contact contact = this.getContactById(id);

        this.contactRepository.delete(contact);
    }
}
