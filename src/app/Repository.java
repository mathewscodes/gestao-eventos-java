package app;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static List<Event> eventos = new ArrayList<>();
    private static List<User> usuarios = new ArrayList<>();

    public static void salvarEvento(Event evento) {
        eventos.add(evento);
    }

    public static List<Event> listarEventos() {
        return eventos;
    }

    public static void salvarUsuario(User usuario) {
        usuarios.add(usuario);
    }
    public static List<User> listarUsuarios() {
        return usuarios;
    }

    public static void salvarEventosNoArquivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("events.data"))) {
            for (Event evento : eventos) {
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                writer.write(
                evento.getNome() + ";" +
                    evento.getEndereco() + ";" +
                    evento.getHorario().format(formato) + ";" +
                    evento.getCategoria() + ";" +
                    evento.getFaixaEtaria() + ";" +
                    evento.getDescricao()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar eventos: " + e.getMessage());
        }
    }

    public static void carregarEventosDoArquivo() {
        eventos.clear();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try (BufferedReader reader = new BufferedReader(new FileReader("events.data"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");

                // cada posição corresponde ao que você gravou no arquivo
                String nome = partes[0];
                String endereco = partes[1];
                LocalDateTime dataHora = LocalDateTime.parse(partes[2], formato);
                Category categoria = Category.valueOf(partes[3]);
                Classification faixaEtaria = Classification.valueOf(partes[4]);
                String descricao = partes[5];

                // cria o evento e adiciona na lista
                Event evento = new Event();
                evento.setNome(nome);
                evento.setEndereco(endereco);
                evento.setHorario(dataHora);
                evento.setCategoria(categoria);
                evento.setFaixaEtaria(faixaEtaria);
                evento.setDescricao(descricao);

                eventos.add(evento);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar eventos: " + e.getMessage());
        }
    }

    public static void salvarUsuariosNoArquivo() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.data", true))) {
            for (User usuario : usuarios) {
                writer.write(
                        usuario.getNome() + ";" +
                                usuario.getEmail() + ";" +
                                usuario.getTelefone() + ";" +
                                usuario.getCpf() + ";" +
                                usuario.getDataNascimento().format(formato)
                );
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    public static void carregarUsuariosDoArquivo() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try (BufferedReader reader = new BufferedReader(new FileReader("users.data"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");

                // cada posição corresponde ao que você gravou no arquivo
                String nome = partes[0];
                String email = partes[1];
                String telefone = partes[2];
                String cpf = partes[3];
                LocalDate datanascimento = LocalDate.parse(partes[4], formato);

                // cria o evento e adiciona na lista
                User usuario = new User();
                usuario.setNome(nome);
                usuario.setEmail(email);
                usuario.setTelefone(telefone);
                usuario.setCpf(cpf);
                usuario.setDataNascimento(datanascimento);

                usuarios.add(usuario);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    public static User buscarUsuarioPorCpf(String cpf) {
        for (User u : usuarios) {
            if (u.getCpf().equals(cpf)) {
                return u;
            }
        }
        return null;
    }

    public static void salvarParticipacaoNoArquivo(User usuario, Event evento) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("participacoes.data", true))) {
            writer.write(usuario.getCpf() + ";" + evento.getNome());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar participação: " + e.getMessage());
        }
    }

    public static void salvarParticipacao(String cpf, Event evento) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("participacoes.data", true))) {
            writer.write(cpf + ";" + evento.getNome());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar participação: " + e.getMessage());
        }
    }

    public static List<String[]> carregarParticipacoesDoArquivo() {
        List<String[]> participacoes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("participacoes.data"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 2) {
                    participacoes.add(partes);
                }
            }
        } catch (IOException e) {
        }
        return participacoes;
    }

    public static List<Event> listarEventosPorUsuario(String cpf) {
        List<Event> eventosUsuario = new ArrayList<>();
        List<String[]> participacoes = carregarParticipacoesDoArquivo();

        for (String[] p : participacoes) {
            String cpfParticipante = p[0];
            String nomeEvento = p[1];

            if (cpfParticipante.equals(cpf)) {
                for (Event e : eventos) {
                    if (e.getNome().equals(nomeEvento)) {
                        eventosUsuario.add(e);
                    }
                }
            }
        }
        return eventosUsuario;
    }

    public static void removerParticipacao(String cpf, Event evento) {
        List<String[]> participacoes = carregarParticipacoesDoArquivo();
        List<String[]> novasParticipacoes = new ArrayList<>();

        for (String[] p : participacoes) {
            String cpfParticipante = p[0];
            String nomeEvento = p[1];

            if (!(cpfParticipante.equals(cpf) && nomeEvento.equals(evento.getNome()))) {
                novasParticipacoes.add(p);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("participacoes.data"))) {
            for (String[] p : novasParticipacoes) {
                writer.write(p[0] + ";" + p[1]);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao remover participação: " + e.getMessage());
        }
    }
}
