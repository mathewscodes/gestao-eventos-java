package app;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1 - Cadastrar Usuário");
            System.out.println("2 - Cadastrar Evento");
            System.out.println("3 - Listar Eventos");
            System.out.println("4 - Participar de Evento");
            System.out.println("5 - Meus Eventos");
            System.out.println("6 - Cancelar Participação");
            System.out.println("0 - Sair");
            System.out.println("\nEscolha uma opção: ");

            opcao = input.nextInt(); // a opção será o próximo número digitado
            input.nextLine();

            Scanner scanner = new Scanner(System.in);
            User usuario = null;
            Repository.carregarParticipacoesDoArquivo();
            switch (opcao) {
                case 1:
                    System.out.println("Você escolheu: Cadastrar Usuário");
                    UserService userService = new UserService();
                    usuario = userService.cadastrarUsuario();
                    Repository.salvarUsuario(usuario);
                    Repository.salvarUsuariosNoArquivo();
                    System.out.println("O usuário " + usuario.getNome() + " foi cadastrado com sucesso.");
                    break;
                case 2:
                    System.out.println("Você escolheu: Cadastrar Evento");
                    EventService eventService = new EventService();
                    Event evento = eventService.cadastrarEvento();
                    Repository.salvarEvento(evento);
                    Repository.salvarEventosNoArquivo();
                    System.out.println("O evento " + evento.getNome() + " foi cadastrado com sucesso.");
                    break;
                case 3:
                    System.out.println("Você escolheu: Listar Eventos");
                    Repository.carregarEventosDoArquivo();
                    List<Event> lista = Repository.listarEventos();

                        if (lista.isEmpty()) {
                            System.out.println("Não há eventos cadastrados.");
                        } else {
                            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                            for (Event e : lista) {
                                System.out.println("--------------------------");
                                System.out.println("Nome: " + e.getNome());
                                System.out.println("Endereço: " + e.getEndereco());
                                System.out.println("Data/Horário: " + e.getHorario().format(formato));
                                System.out.println("Categoria: " + e.getCategoria());
                                System.out.println("Classificação: " + e.getFaixaEtaria());
                                System.out.println(("Descrição: " + e.getDescricao()));
                            }
                            System.out.println("--------------------------");
                        }
                    break;
                case 4:
                    System.out.println("Você escolheu: Participar de Evento");
                    Repository.carregarEventosDoArquivo();
                    Repository.listarEventos();
                    List<Event> listar = Repository.listarEventos();

                        if (listar.isEmpty()) {
                            System.out.println("Não há eventos cadastrados.");
                        } else {
                            System.out.println("Os seguintes eventos estão disponíveis: ");
                            for (int i = 0; i < listar.size(); i++) {
                                System.out.println((i + 1) + " - " + listar.get(i).getNome());
                            }

                            System.out.print("Digite o número do evento que deseja participar: ");
                            int escolha = scanner.nextInt();
                            scanner.nextLine();

                            if (escolha < 1 || escolha > listar.size()) {
                                System.out.println("Opção inválida");
                            } else {
                                Event eventoSelecionado = listar.get(escolha - 1);
                                System.out.print("Digite seu CPF: ");
                                String cpf = scanner.nextLine();
                                Repository.carregarUsuariosDoArquivo();
                                usuario = Repository.buscarUsuarioPorCpf(cpf);

                                if (usuario == null) {
                                    System.out.println("Usuário não encontrado. Cadastre-se primeiro.");
                                } else {
                                    eventoSelecionado.getParticipantes().add(usuario);
                                    Repository.salvarParticipacaoNoArquivo(usuario, eventoSelecionado);
                                    System.out.println("Usuário " + usuario.getNome() + " foi adicionado ao evento.");
                                }
                            }
                        }
                    break;
                case 5:
                    System.out.println("Você escolheu: Meus Eventos");
                    System.out.print("Digite seu CPF: ");
                    String cpfBusca = scanner.nextLine();

                    Repository.carregarUsuariosDoArquivo();
                    Repository.carregarEventosDoArquivo();
                    Repository.carregarParticipacoesDoArquivo();

                    User usuarioAtual = Repository.buscarUsuarioPorCpf(cpfBusca);

                    if (usuarioAtual == null) {
                        System.out.println("Usuário não encontrado. Cadastre-se primeiro.");
                    } else {
                        List<Event> listaEventos = Repository.listarEventos();
                        List<Event> meusEventos = new ArrayList<>();

                        for (Event e : listaEventos) {
                            for (User u : e.getParticipantes()) {
                                if (u.getCpf().equals(usuarioAtual.getCpf())) {
                                    meusEventos.add(e);
                                    break;
                                }
                            }
                        }

                        if (meusEventos.isEmpty()) {
                            System.out.println("Você ainda não está participando de nenhum evento.");
                        } else {
                            System.out.println("Você está participando dos seguintes eventos:");
                            for (Event e : meusEventos) {
                                System.out.println("--------------------------");
                                System.out.println("Nome: " + e.getNome());
                                System.out.println("Endereço: " + e.getEndereco());
                                System.out.println("Data/Horário: " + e.getHorario());
                                System.out.println("Categoria: " + e.getCategoria());
                                System.out.println("Classificação: " + e.getFaixaEtaria());
                                System.out.println("Descrição: " + e.getDescricao());
                            }
                            System.out.println("--------------------------");
                        }
                    }
                    break;
                case 6:
                    System.out.println("Você escolheu: Cancelar Participação");
                    Repository.carregarEventosDoArquivo();
                    Repository.carregarUsuariosDoArquivo();
                    Repository.carregarParticipacoesDoArquivo();

                    System.out.print("Digite seu CPF: ");
                    String cpfCancel = scanner.nextLine();
                    User usuarioCancel = Repository.buscarUsuarioPorCpf(cpfCancel);

                    if (usuarioCancel == null) {
                        System.out.println("Usuário não encontrado.");
                    } else {
                        List<Event> eventosDoUsuario = Repository.listarEventosPorUsuario(usuarioCancel.getCpf());

                        if (eventosDoUsuario.isEmpty()) {
                            System.out.println("Você não está participando de nenhum evento.");
                        } else {
                            System.out.println("Eventos que você está participando:");
                            for (int i = 0; i < eventosDoUsuario.size(); i++) {
                                System.out.println((i + 1) + " - " + eventosDoUsuario.get(i).getNome());
                            }

                            System.out.print("Digite o número do evento que deseja cancelar: ");
                            int escolhaCancel = scanner.nextInt();
                            scanner.nextLine();

                            if (escolhaCancel < 1 || escolhaCancel > eventosDoUsuario.size()) {
                                System.out.println("Opção inválida.");
                            } else {
                                Event eventoCancelado = eventosDoUsuario.get(escolhaCancel - 1);
                                Repository.removerParticipacao(usuarioCancel.getCpf(), eventoCancelado);

                                System.out.println("Participação no evento '" + eventoCancelado.getNome() + "' cancelada com sucesso!");
                            }
                        }
                    }
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente");
            }
        } while (opcao != 0);

        input.close();
    }
}
