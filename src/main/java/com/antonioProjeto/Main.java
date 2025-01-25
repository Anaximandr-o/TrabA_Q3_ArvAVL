package com.antonioProjeto;
import java.util.*;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Main {
    public static void main(String[] args) {Random gerador = new Random();
        for(int i = 0; i < 100; i++){
            int qtdNos = gerador.nextInt(100, 1000);
            DescriptiveStatistics stats = new DescriptiveStatistics();
            for(int x = 0; x <100; x++){
                ArvAVL arvore = new ArvAVL();
                for(int j = 0; j < qtdNos; j++){
                    int no = gerador.nextInt(1,10000);
                    arvore.inserir(no);
                }
                arvore.removeLaura(qtdNos);
                stats.addValue(arvore.obterAltura());
            }
            System.out.println("Quantidade de nós - "+ qtdNos + " Altura esperada - " + ((Math.log(qtdNos)/Math.log(2)) * 1.44 - 0.328) +" Média das alturas - "+ stats.getMean() + " Desvio padrão: " + stats.getStandardDeviation());
        }
    }
}