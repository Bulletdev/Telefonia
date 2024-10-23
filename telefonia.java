import java.util.Arrays;
import java.util.Scanner;

// Classe para representar um serviço
class Servico {
    private double valor;

    public Servico(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }
}

// Classe para representar o combo de serviços
class ComboServicos {
    private Servico[] servicos;
    private double desconto1, desconto2, desconto3;
    private double descontoAdicional = 20.00;

    public ComboServicos(Servico[] servicos, double[] descontos) {
        this.servicos = servicos;
        this.desconto1 = descontos[0];
        this.desconto2 = descontos[1];
        this.desconto3 = descontos[2];
    }

    // Método para calcular o valor total do combo com descontos
    public double calcularValorTotal() {
        // Conta quantos serviços foram contratados (com valor maior que 0)
        int servicosContratados = (int) Arrays.stream(servicos)
                .filter(servico -> servico.getValor() > 0)
                .count();

        // Define o desconto com base no número de serviços contratados
        double desconto = 0;
        if (servicosContratados == 1) {
            desconto = desconto1;
        } else if (servicosContratados == 2) {
            desconto = desconto2;
        } else if (servicosContratados == 3) {
            desconto = desconto3;
        }

        // Calcula o valor total dos serviços contratados
        double valorTotal = Arrays.stream(servicos)
                .filter(servico -> servico.getValor() > 0)
                .mapToDouble(Servico::getValor)
                .sum();

        // Aplica o desconto progressivo
        double valorComDesconto = valorTotal * (1 - desconto / 100);

        // Aplica o desconto adicional de R$ 20,00 se todos os três serviços foram contratados
        if (servicosContratados == 3) {
            valorComDesconto -= descontoAdicional;
        }

        return valorComDesconto;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Leitura da primeira linha de entrada
        String[] entrada1 = scanner.nextLine().trim().split(",");
        double[] valoresServicos = Arrays.stream(entrada1)
                .mapToDouble(Double::parseDouble)
                .toArray();

        // Leitura da segunda linha de entrada
        String[] entrada2 = scanner.nextLine().trim().split(",");
        double[] descontos = Arrays.stream(entrada2)
                .mapToDouble(Double::parseDouble)
                .toArray();

        // Criando objetos Servico
        Servico[] servicos = new Servico[valoresServicos.length];
        for (int i = 0; i < valoresServicos.length; i++) {
            servicos[i] = new Servico(valoresServicos[i]);
        }

        // Criando o combo de serviços
        ComboServicos combo = new ComboServicos(servicos, descontos);

        // Calculando e imprimindo o valor total com desconto
        double valorTotal = combo.calcularValorTotal();
        System.out.printf("%.2f\n", valorTotal);

        scanner.close();
    }
}
