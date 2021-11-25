package br.senac.devweb.api.ecommerce.contact;

import br.senac.devweb.api.ecommerce.client.Client;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "CONTACT")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo client não pode ser nulo!")
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Client.class)
    @JoinColumn(name = "idClient")
    private Client client;

    @NotNull(message = "O campo type não pode ser nulo!")
    @Column(name = "TYPE")
    private Type type;

    @NotNull(message = "O campo recovery não pode ser nulo!")
    @Size(min = 1, max = 30, message = "O campo recovery deve conter entre 1 e 30 caracteres.")
    @Column(name = "RECOVERY")
    private String recovery;

    @NotNull(message = "O campo email não pode ser nulo!")
    @Size(min = 1, max = 30, message = "O campo email deve conter entre 1 e 30 caracteres.")
    @Column(name = "EMAIL")
    private String email;

    @NotNull(message = "O campo phone não pode ser nulo!")
    @Size(min = 1, max = 15, message = "O campo phone deve conter entre 1 e 15 caracteres.")
    @Column(name = "PHONE")
    private String phone;

    @NotNull(message = "O campo note não pode ser nulo!")
    @Size(min = 1, max = 100, message = "O campo note deve conter entre 1 e 100 caracteres.")
    @Column(name = "NOTE")
    private String note;

    public enum Type {
        COMERCIAL("comercial"),
        PARTICULAR("particular");

        private final String description;

        Type(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }
}
