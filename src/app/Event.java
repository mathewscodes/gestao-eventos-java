package app;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private String nome; // nome do evento
    private String endereco; // endereço do evento
    private LocalDateTime horario; // horário do evento
    private Category categoria; // categoria do evento
    private Classification faixaEtaria; // classificação etária do evento
    private String descricao; // descrição do evento
    private List<User> participantes = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public Category getCategoria() {
        return categoria;
    }

    public void setCategoria(Category categoria) {
        this.categoria = categoria;
    }

    public Classification getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(Classification faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<User> getParticipantes() { return participantes;}

    public void adicionarParticipantes(User usuario) { participantes.add(usuario); }
}
