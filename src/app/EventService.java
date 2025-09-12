package app;

import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class EventService {
    public Event cadastrarEvento() {
        Scanner input = new Scanner(System.in);
        String nomeEvento;

            System.out.println("Informe o nome do Evento: ");
            nomeEvento = input.nextLine();

            String enderecoEvento;
            System.out.println("Agora informe o endereço do evento no modelo (Rua, Número, Bairro, Cidade e Estado): ");
            enderecoEvento = input.nextLine();

            String dataHorario;
            System.out.println("Informe a data e horário do evento (utilize o formato 'dd/mm/aaaa hh:mm'): ");
            dataHorario = input.nextLine();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime datahora = LocalDateTime.parse(dataHorario, formato);

            System.out.println("Escolha uma categoria: ");
            for (Category c : Category.values()) {
                System.out.println(c);
            }
            String categoriaDigitada = input.nextLine();
            Category categoria = Category.valueOf(categoriaDigitada.toUpperCase());

            System.out.println("Escolha a classificação etária: ");
            for (Classification c : Classification.values()) {
                System.out.println(c);
            }
            String faixaEtaria = input.nextLine();
            Classification classificacao = Classification.valueOf(faixaEtaria.toUpperCase());

            String descricao;
            System.out.println("Insira a descrição do evento: ");
            descricao = input.nextLine();

            Event evento = new Event();
            evento.setNome(nomeEvento);
            evento.setEndereco(enderecoEvento);
            evento.setHorario(datahora);
            evento.setCategoria(categoria);
            evento.setFaixaEtaria(classificacao);
            evento.setDescricao(descricao);

            return evento;
    }
}
