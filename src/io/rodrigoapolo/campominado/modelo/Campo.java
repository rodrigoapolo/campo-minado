package io.rodrigoapolo.campominado.modelo;

import io.rodrigoapolo.campominado.excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Campo {

    private final int linha;
    private final int coluna;
    private boolean aberto;
    private boolean minado;
    private boolean marcado;
    private List<Campo> vizinhos = new ArrayList<>();

    Campo(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
    }

    boolean adicionarVizinho(Campo vizinho){
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int detalGeral = deltaColuna + deltaLinha;

        if(detalGeral == 1 && !diagonal){
            vizinhos.add(vizinho);
            return true;
        } else if (detalGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        }
        return false;
    }

    void alternarMarcacao(){
        if(!aberto){
            marcado = !marcado;
        }
    }

    boolean abrir(){
        if(!aberto && !marcado){
            aberto = true;
            if(minado){
                throw new ExplosaoException();
            }

            if (vizinhacaSegura()){
                vizinhos.forEach(v -> v.abrir());
            }
            return true;
        }
        return false;
    }

    boolean vizinhacaSegura(){
        return vizinhos.stream().noneMatch(v -> v.minado);
    }

    void minar(){
        minado = true;
    }

    public boolean isAberto(){
        return aberto;
    }

    public boolean isFechado(){
        return !isAberto();
    }
    public boolean isMarcado() {
        return marcado;
    }
}
