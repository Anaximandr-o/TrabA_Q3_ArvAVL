package com.antonioProjeto;
import java.util.*;

public class ArvAVL {

    static class Node {
        int chave, altura;
        Node esquerda, direita;

        public Node(int chave) {
            this.chave = chave;
            this.altura = 1;
        }
    }

    private Node raiz;

    // Função utilitária para obter a altura da árvore
    private int altura(Node node) {
        return node == null ? 0 : node.altura;
    }

    // Função utilitária para obter o fator de balanceamento de um nó
    private int obterBalanceamento(Node node) {
        return node == null ? 0 : altura(node.esquerda) - altura(node.direita);
    }

    // Rotação à direita na subárvore enraizada em y
    private Node rotacaoDireita(Node y) {
        Node x = y.esquerda;
        Node T2 = x.direita;

        // Realiza a rotação
        x.direita = y;
        y.esquerda = T2;

        // Atualiza as alturas
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;

        // Retorna a nova raiz
        return x;
    }

    // Rotação à esquerda na subárvore enraizada em x
    private Node rotacaoEsquerda(Node x) {
        Node y = x.direita;
        Node T2 = y.esquerda;

        // Realiza a rotação
        y.esquerda = x;
        x.direita = T2;

        // Atualiza as alturas
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;

        // Retorna a nova raiz
        return y;
    }

    public boolean inserir(int chave) {
        int[] duplicada = new int[]{0};
        raiz = inserir(raiz, chave, duplicada);
        return duplicada[0] == 0;
    }

    private Node inserir(Node node, int chave, int[] duplicada) {
        if (node == null) {
            return new Node(chave);
        }


        if (chave < node.chave) {
            node.esquerda = inserir(node.esquerda, chave, duplicada);
        } else if (chave > node.chave) {
            node.direita = inserir(node.direita, chave, duplicada);
        } else {
            // Chaves duplicadas não são permitidas na Árvore AVL
            duplicada[0] = 1;
            return node;
        }

        // Atualiza a altura do nó ancestral
        node.altura = 1 + Math.max(altura(node.esquerda), altura(node.direita));

        // Obtém o fator de balanceamento para verificar se o nó ficou desbalanceado
        int balanceamento = obterBalanceamento(node);

        // Casos de desbalanceamento

        // Caso Esquerda-Esquerda
        if (balanceamento > 1 && chave < node.esquerda.chave) {
            return rotacaoDireita(node);
        }

        // Caso Direita-Direita
        if (balanceamento < -1 && chave > node.direita.chave) {
            return rotacaoEsquerda(node);
        }

        // Caso Esquerda-Direita
        if (balanceamento > 1 && chave > node.esquerda.chave) {
            node.esquerda = rotacaoEsquerda(node.esquerda);
            return rotacaoDireita(node);
        }

        // Caso Direita-Esquerda
        if (balanceamento < -1 && chave < node.direita.chave) {
            node.direita = rotacaoDireita(node.direita);
            return rotacaoEsquerda(node);
        }

        // Retorna o nó sem alterações
        return node;
    }

    // Função utilitária para imprimir a travessia em pré-ordem da árvore
    public void preOrdem() {
        preOrdem(raiz);
        System.out.println();
    }

    private void preOrdem(Node node) {
        if (node != null) {
            System.out.print(node.chave + " ");
            preOrdem(node.esquerda);
            preOrdem(node.direita);
        }
    }

    public int obterAltura() {
        return altura(raiz);
    }

    public boolean contem(int chave) {
        return get(chave, raiz) != null;
    }

    private Node get(int x, Node no)
    {
        if(no == null)
            return null;

        if(x < no.chave){
            return get(x, no.esquerda);
        }
        else if(x > no.chave){
            return get(x, no.direita);
        }
        else
            return no;
    }

    private Node valorMinimo(Node no) {
        Node atual = no;

        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }

        return atual;
    }

    public void delete(int chave) {
        raiz = delete(raiz, chave);
    }

    private Node delete(Node no, int chave) {
        if (no == null) {
            return null;
        }

        if (chave < no.chave) {
            no.esquerda = delete(no.esquerda, chave);
        } else if (chave > no.chave) {
            no.direita = delete(no.direita, chave);
        } else {
            if (no.esquerda == null && no.direita == null) {
                return null;
            }

            if (no.esquerda == null) {
                return no.direita;
            } else if (no.direita == null) {
                return no.esquerda;
            }

            Node sucessor = valorMinimo(no.direita);
            no.chave = sucessor.chave; // Substitui a chave do nó atual pelo sucessor
            no.direita = delete(no.direita, sucessor.chave); // Remove o sucessor
        }

        no.altura = Math.max(altura(no.esquerda), altura(no.direita)) + 1;

        int balanceamento = obterBalanceamento(no);

        if (balanceamento > 1 && obterBalanceamento(no.esquerda) >= 0) {
            return rotacaoDireita(no);
        }

        if (balanceamento > 1 && obterBalanceamento(no.esquerda) < 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && obterBalanceamento(no.direita) <= 0) {
            return rotacaoEsquerda(no);
        }

        if (balanceamento < -1 && obterBalanceamento(no.direita) > 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public void removeLaura(int qtdNos){
        Random gerador = new Random();
        for(int i = 0; i < (qtdNos * qtdNos); i++){
            int antigoNo;
            do{
                antigoNo = gerador.nextInt(1,10000);
            }while(!this.contem(antigoNo));
            this.delete(antigoNo);
            int novoNo;
            do{
                novoNo = gerador.nextInt(1,10000);
            }while(this.contem(novoNo));
            this.inserir(novoNo);
        }
    }
}