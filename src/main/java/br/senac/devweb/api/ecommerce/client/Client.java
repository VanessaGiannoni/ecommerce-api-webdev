package br.senac.devweb.api.ecommerce.client;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "CLIENT")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo NOME não pode ser nulo!")
    @Column(name = "FULLNAME")
    @Size(max = 60, min = 1, message = "O campo NOME deve conter entre 1 e 60 caracteres.")
    private String fullName;

    @NotNull(message = "O campo DATA DE NASCIMENTO não pode ser nulo!")
    @Column(name = "BIRTHDATE")
    @Past(message = "A DATA DE NASCIMENTO não pode ser maior que a data atual!")
    private LocalDate birthDate;

    @NotNull(message = "O campo USUÁRIO não pode ser nulo!")
    @Column(name = "USER")
    @Size(max = 15, min = 1, message = "O campo USUÁRIO deve conter entre 1 e 15 caracteres.")
    private String user;

    @NotNull(message = "O campo SENHA não pode ser nulo!")
    @Column(name = "PASSWORD")
    @Size(max = 16, min = 6, message = "A SENHA deve conter entre 6 e 16 caracteres.")
    private String password;

    @NotNull(message = "O campo STATUS não pode ser nulo!")
    @Column(name = "STATUS")
    private Status status;

    public enum Status {
        ACTIVE,
        INACTIVE
    }
}
