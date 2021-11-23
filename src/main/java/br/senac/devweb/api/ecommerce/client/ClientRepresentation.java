package br.senac.devweb.api.ecommerce.client;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public interface ClientRepresentation {
    @Data
    @Getter
    @Setter
    class CreateOrUpdateClient {
        @NotNull(message = "O campo NOME não pode ser nulo!")
        @Size(max = 60, min = 1, message = "O campo NOME deve conter entre 1 e 60 caracteres.")
        private String fullName;

        @NotNull(message = "O campo DATA DE NASCIMENTO não pode ser nulo!")
        @Past(message = "A DATA DE NASCIMENTO não pode ser maior que a data atual!")
        private LocalDate birthDate;

        @NotNull(message = "O campo USUÁRIO não pode ser nulo!")
        @Size(max = 15, min = 1, message = "O campo USUÁRIO deve conter entre 1 e 15 caracteres.")
        private String user;

        @NotNull(message = "O campo SENHA não pode ser nulo!")
        @Size(max = 16, min = 6, message = "A SENHA deve conter entre 6 e 16 caracteres.")
        private String password;
    }

    @Data
    @Getter
    @Setter
    @Builder
    class ClientDetail {
        private Long id;
        private String fullName;
        private LocalDate birthDate;
        private String user;

        public static ClientDetail from(Client client) {
            return ClientDetail.builder()
                    .id(client.getId())
                    .fullName(client.getFullName())
                    .birthDate(client.getBirthDate())
                    .user(client.getUser())
                    .build();
        }
    }

    @Data
    @Getter
    @Setter
    @Builder
    class ClientList {
        private Long id;
        private String fullName;
        private LocalDate birthDate;
        private String user;

        public static ClientRepresentation.ClientList from(Client client) {
            return ClientRepresentation
                    .ClientList
                    .builder()
                    .id(client.getId())
                    .fullName(client.getFullName())
                    .birthDate(client.getBirthDate())
                    .user(client.getUser())
                    .build();
        }

        public static List<ClientRepresentation.ClientList> from(List<Client> clientList) {
            return clientList
                    .stream()
                    .map(ClientRepresentation.ClientList::from)
                    .collect(Collectors.toList());
        }
    }
}
