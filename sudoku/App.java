import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        int[][] tabuleiro = new int[9][9];
        gerarTabuleiroValido(tabuleiro);
        removerNumeros(tabuleiro, 40);
        jogar(tabuleiro);
    }

    public static boolean gerarTabuleiroValido(int[][] tabuleiro) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tabuleiro[i][j] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isNumeroValido(tabuleiro, i, j, num)) {
                            tabuleiro[i][j] = num;
                            if (gerarTabuleiroValido(tabuleiro)) {
                                return true;
                            }
                            tabuleiro[i][j] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static void removerNumeros(int[][] tabuleiro, int quantidade) {
        Random random = new Random();
        while (quantidade > 0) {
            int linha = random.nextInt(9);
            int coluna = random.nextInt(9);
            if (tabuleiro[linha][coluna] != 0) {
                tabuleiro[linha][coluna] = 0;
                quantidade--;
            }
        }
    }

    public static boolean isNumeroValido(int[][] tabuleiro, int linha, int coluna, int numero) {
        for (int i = 0; i < 9; i++) {
            if (tabuleiro[linha][i] == numero) {
                return false;
            }
        }

        for (int i = 0; i < 9; i++) {
            if (tabuleiro[i][coluna] == numero) {
                return false;
            }
        }

        int inicioLinha = linha - linha % 3;
        int inicioColuna = coluna - coluna % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[inicioLinha + i][inicioColuna + j] == numero) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void imprimirTabuleiro(int[][] tabuleiro) {
        System.out.println("-------------------------");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) System.out.print("| ");
                System.out.print(tabuleiro[i][j] == 0 ? "  " : tabuleiro[i][j] + " ");
            }
            System.out.println("|");
            if ((i + 1) % 3 == 0) System.out.println("-------------------------");
        }
    }

    public static void jogar(int[][] tabuleiro) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            imprimirTabuleiro(tabuleiro);

            System.out.print("Digite a linha (1-9), a coluna (1-9) e o número (1-9), ou 0 para sair: ");
            int linha = sc.nextInt() - 1;
            if (linha == -1) break;

            int coluna = sc.nextInt() - 1;
            int numero = sc.nextInt();

            if (linha >= 0 && linha < 9 && coluna >= 0 && coluna < 9 && numero >= 1 && numero <= 9) {
                if (tabuleiro[linha][coluna] == 0 && isNumeroValido(tabuleiro, linha, coluna, numero)) {
                    tabuleiro[linha][coluna] = numero;
                    System.out.println("Jogada válida!");
                } else {
                    System.out.println("Jogada inválida. Tente novamente.");
                }
            } else {
                System.out.println("Entrada inválida. Tente novamente.");
            }

            if (isTabuleiroCompleto(tabuleiro)) {
                System.out.println("Parabéns! Você resolveu o Sudoku!");
                imprimirTabuleiro(tabuleiro);
                break;
            }
        }
        sc.close();
    }

    public static boolean isTabuleiroCompleto(int[][] tabuleiro) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tabuleiro[i][j] == 0 || !isNumeroValido(tabuleiro, i, j, tabuleiro[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }
}