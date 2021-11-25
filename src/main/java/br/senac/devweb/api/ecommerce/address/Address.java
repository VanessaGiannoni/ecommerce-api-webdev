package br.senac.devweb.api.ecommerce.address;

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
@Entity(name = "ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo cliente não pode ser nulo!")
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Client.class)
    @JoinColumn(name = "idClient")
    private Client client;

    @NotNull(message = "O campo tipo não pode ser nulo!")
    @Column(name ="TYPE")
    private Type type;

    @NotNull(message = "O campo estado (UF) não pode ser nulo!")
    @Size(min = 1, max = 30, message = "O campo estado deve conter entre 1 e 30 caracteres.")
    @Column(name = "STATE")
    private String state;

    @NotNull(message = "O campo cidade não pode ser nulo!")
    @Size(min = 1, max = 30, message = "O campo cidade deve conter entre 1 e 30 caracteres.")
    @Column(name = "CITY")
    private String city;

    @NotNull(message = "O campo CEP não pode ser nulo!")
    @Size(min = 1, max = 8, message = "O campo CEP deve conter entre 1 e 8 caracteres.")
    @Column(name = "CEP")
    private String cep;

    @NotNull(message = "O campo logradouro não pode ser nulo!")
    @Size(min = 1, max = 100, message = "O campo logradouro deve conter entre 1 e 100 caracteres.")
    @Column(name = "STREET")
    private String street;

    @NotNull(message = "O campo number não pode ser nulo!")
    @Size(min = 1, max = 10, message = "O campo número deve conter entre 1 e 10 caracteres.")
    @Column(name = "NUMBER")
    private String number;

    @NotNull(message = "O campo complemento não pode ser nulo!")
    @Size(min = 1, max = 100, message = "O campo complemento deve conter entre 1 e 100 caracteres.")
    @Column(name = "COMPLEMENT")
    private String complement;

    @NotNull(message = "O campo status não pode ser nulo!")
    @Column(name = "STATUS")
    private Status status;

    public enum Status {
        ATIVO,
        INATIVO
    }

    public enum Type {
        COMERCIAL("comercial"),
        PARTICULAR("particular"),
        TERCEIRO("terceiro");

        private final String description;

        Type(String description) {
            this.description = description;
        }

        @Override public String toString() {
            return description;
        }
    }
}
