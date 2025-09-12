package app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserService {
    public User cadastrarUsuario() {
        Scanner input = new Scanner(System.in);
        String nomeCompleto;

            System.out.println("Informe seu nome completo: " );
            nomeCompleto = input.nextLine();

            String[] partes = nomeCompleto.split(" ");
            String primeiroNome = partes[0];
            String email;

            System.out.println("Olá " +primeiroNome+ "! Por favor, informe seu endereço de email: ");
            email = input.nextLine();

            String numeroTelefone;
            System.out.println("Agora me informe um número de telefone (digite somente números): ");
            numeroTelefone = input.nextLine();

            String numeroCpf;
            System.out.println("Digite seu CPF (somente números): ");
            numeroCpf = input.nextLine();

            String dataNascimento;
            System.out.println("Informe sua data de nascimento (dd-mm-aaaa): ");
            dataNascimento = input.nextLine();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate nascimento = LocalDate.parse(dataNascimento, formato);

        User usuario = new User();
        usuario.setNome(nomeCompleto);
        usuario.setEmail(email);
        usuario.setTelefone(numeroTelefone);
        usuario.setCpf(numeroCpf);
        usuario.setDataNascimento(nascimento);

        return usuario;
    }
}
