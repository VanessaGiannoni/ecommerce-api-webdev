package br.senac.devweb.api.ecommerce.contact;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

public interface ContactRepresentation {
    @Data
    @Getter
    @Setter
    class CreateOrUpdateContact {
        @NotNull(message = "O campo CLIENT não pode ser nulo!")
        private Long client;

        @NotNull(message = "O campo TYPE não pode ser nulo!")
        private Contact.Type type;

        @NotNull(message = "O campo RECOVERY não pode ser nulo!")
        @Size(min = 1, max = 30, message = "O campo recovery deve conter entre 1 e 30 caracteres.")
        private String recovery;

        @NotNull(message = "O campo EMAIL não pode ser nulo!")
        @Size(min = 1, max = 30, message = "O campo email deve conter entre 1 e 30 caracteres.")
        private String email;

        @NotNull(message = "O campo PHONE não pode ser nulo!")
        @Size(min = 1, max = 30, message = "O campo phone deve ter entre 1 e 30 caracateres.")
        private String phone;

        @NotNull(message = "O campo NOTE não pode ser nulo!")
        @Size(min = 1, max = 100, message = "O campo note deve conter entre 1 e 30 caracteres.")
        private String note;

    }

    @Data
    @Getter
    @Setter
    @Builder
    class ContactDetail {
        private Long id;
        private Long client;
        private Contact.Type type;
        private String recovery;
        private String email;
        private String phone;
        private String note;

        public static ContactDetail from(Contact contact) {
            return ContactDetail.builder()
                    .id(contact.getId())
                    .client(contact.getClient().getId())
                    .type(contact.getType())
                    .recovery(contact.getRecovery())
                    .email(contact.getEmail())
                    .phone(contact.getPhone())
                    .note(contact.getNote())
                    .build();
        }
    }

    @Data
    @Getter
    @Setter
    @Builder
    class ContactList {
        private Long id;
        private Long client;
        private Contact.Type type;
        private String recovery;
        private String email;
        private String phone;
        private String note;

        public static ContactRepresentation.ContactList from(Contact contact) {
            return ContactList
                    .builder()
                    .id(contact.getId())
                    .client(contact.getClient().getId())
                    .type(contact.getType())
                    .recovery(contact.getRecovery())
                    .email(contact.getEmail())
                    .phone(contact.getPhone())
                    .note(contact.getNote())
                    .build();
        }

        public static List<ContactRepresentation.ContactList> from(List<Contact> contactList) {
            return contactList
                    .stream()
                    .map(ContactRepresentation.ContactList::from)
                    .collect(Collectors.toList());
        }
    }
}
