package br.senac.devweb.api.ecommerce.address;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface AddressRepresentation {
    @Data
    @Getter
    @Setter
    class CreateAddressRep {
        @NotNull(message = "O campo cliente não pode ser nulo!")
        private Long client;

        @NotNull(message = "O campo tipo não pode ser nulo!")
        private Address.Type type;

        @NotNull(message = "O campo estado não pode ser nulo!")
        @Size(min =1, max = 30, message = "O campo estado deve conter entre 1 e 30 caracteres.")
        private String state;

        @NotNull(message = "O campo cidade não pode ser nulo!")
        @Size(min =1, max = 30, message = "O campo cidade deve conter entre 1 e 30 caracteres.")
        private String city;

        @NotNull(message = "O campo CEP não pode ser nulo!")
        @Size(min =1, max = 8, message = "O campo CEP deve conter entre 1 e 8 caracteres.")
        private String cep;

        @NotNull(message = "O campo logradouro não pode ser nulo!")
        @Size(min =1, max = 100, message = "O campo estado deve conter entre 1 e 100 caracteres.")
        private String street;

        @NotNull(message = "O campo número não pode ser nulo!")
        private String number;

        @NotNull(message = "O campo complemento não pode ser nulo!")
        @Size(min =1, max = 100, message = "O campo complemento deve conter entre 1 e 100 caracteres.")
        private String complement;
    }

    @Data
    @Getter
    @Setter
    @Builder
    class AddressDetail {
        private ClientRepresentation.ClientDetail client;

    }
}
