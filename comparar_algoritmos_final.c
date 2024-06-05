#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// junta as duas metades do vetor após a ordenação
void merge(int vetor[], int inicio, int meio, int fim) {
    int z, x, c;
    int n1 = meio - inicio + 1;
    int n2 = fim - meio;

    // aloca a memoria para os vetores temporarios
    int *esquerda = (int *)malloc(n1 * sizeof(int));
    int *direita = (int *)malloc(n2 * sizeof(int));

    // copia os elementos para os vetores temporarios
    for (z = 0; z < n1; z++)
        esquerda[z] = vetor[inicio + z];
    for (x = 0; x < n2; x++)
        direita[x] = vetor[meio + 1 + x];

    // junta os dois temporarios no original
    z = 0;
    x = 0;
    c = inicio;
    while (z < n1 && x < n2) {
        if (esquerda[z] <= direita[x]) {
            vetor[c] = esquerda[z];
            z++;
        } else {
            vetor[c] = direita[x];
            x++;
        }
        c++;
    }

    // copia os elementos da esquerda e da direita, caso haja algum ainda
    while (z < n1) {
        vetor[c] = esquerda[z];
        z++;
        c++;
    }

    while (x < n2) {
        vetor[c] = direita[x];
        x++;
        c++;
    }

    // libera a memoria dos vetores temporarios
    free(esquerda);
    free(direita);
}

// algoritmo merge sort recursivo
void mergeSort(int vetor[], int inicio, int fim) {
    if (inicio < fim) {
        int meio = inicio + (fim - inicio) / 2;
        // para a esquerda
        mergeSort(vetor, inicio, meio);
        // para a direita
        mergeSort(vetor, meio + 1, fim);
        // une as duas metades ja ordenadas
        merge(vetor, inicio, meio, fim);
    }
}

// algoritmo insertionSort
void insertionSort(int vetor[], int n) {
    int r, chave, t;
    for (r = 1; r < n; r++) {
        chave = vetor[r];
        t = r - 1;

        while (t >= 0 && vetor[t] > chave) {
            vetor[t + 1] = vetor[t];
            t = t - 1;
        }
        vetor[t + 1] = chave;
    }
}

// gera um vetor com valores inteiros aleatorios ate um milhao
void vetorAleatorio(int vetor[], int tamanho) {
    for (int i = 0; i < tamanho; i++) {
        vetor[i] = rand() % 1000000;
    }
}

// mede o tempo da funcao insertionSort
double medir_tempo_insertion(void (*sort_function)(int[], int), int vetor[], int n) {
    clock_t inicio, fim;
    inicio = clock();
    sort_function(vetor, n);
    fim = clock();
    return ((double)(fim - inicio)) / CLOCKS_PER_SEC;
}

// mede o tempo da funcao mergeSort
double medir_tempo_merge(void (*sort_function)(int[], int, int), int vetor[], int l, int r) {
    clock_t inicio, fim;
    inicio = clock();
    sort_function(vetor, l, r);
    fim = clock();
    return ((double)(fim - inicio)) / CLOCKS_PER_SEC;
}

// gera o tamanho dos vetores que serao usados
int main() {
    srand(time(NULL)); // Inicializa a semente para o gerador de números aleatórios
    int tamanhos[] = {1000, 10000, 100000, 1000000};
    int n_tamanhos = sizeof(tamanhos) / sizeof(tamanhos[0]);

    // loop para chamar todos os vetores
    for (int i = 0; i < n_tamanhos; i++) {
        int tamanho = tamanhos[i];
        int *vetor = (int *)malloc(tamanho * sizeof(int));
        int *vetor_copia = (int *)malloc(tamanho * sizeof(int));

        // gera os valores aleatorios inteiros
        vetorAleatorio(vetor, tamanho);
        for (int j = 0; j < tamanho; j++) {
            vetor_copia[j] = vetor[j];
        }

        // mede o tempo enquanto chama as duas funcoes para todos os vetores
        double tempo_insertion = medir_tempo_insertion(insertionSort, vetor, tamanho);
        for (int j = 0; j < tamanho; j++) {
            vetor[j] = vetor_copia[j];
        }
        double tempo_merge = medir_tempo_merge(mergeSort, vetor, 0, tamanho - 1);

        printf("Tamanho do vetor: %d\n", tamanho);
        printf("Tempo Insertion Sort: %f segundos\n", tempo_insertion);
        printf("Tempo Merge Sort: %f segundos\n\n", tempo_merge);

        free(vetor);
        free(vetor_copia);
    }

    return 0;
}
