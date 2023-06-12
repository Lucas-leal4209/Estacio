package EconoMarket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Interface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem vindo ao Sistema EconoMarket:");
        System.out.println("Selecione uma opção:");
        System.out.println("1. Criar tabela"); // Opção para criar a tabela no banco de dados
        System.out.println("2. Inserir dados"); // Opção para inserir dados na tabela
        System.out.println("3. Consultar dados"); // Opção para consultar dados na tabela
        System.out.println("4. Listar produtos expirando"); // Opção para listar produtos com data de validade próxima
        System.out.println("5. Atualizar dados"); // Opção para atualizar dados na tabela
        System.out.println("6. Deletar tabela"); // Opção para deletar a tabela do banco de dados
        System.out.println("7. Apagar dados"); // Opção para apagar todos os dados da tabela
        System.out.println("0. Sair"); // Opção para sair da aplicação

        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                criarTabela(); // Chama o método para criar a tabela
                break;
            case 2:
                inserirDados(); // Chama o método para inserir dados na tabela
                break;
            case 3:
                consultarDados(); // Chama o método para consultar dados na tabela
                break;
            case 4:
                listarProdutosExpirando(); // Chama o método para listar produtos com data de validade próxima
                break;
            case 5:
                atualizarDados(); // Chama o método para atualizar dados na tabela
                break;
            case 6:
                deletarTabela(); // Chama o método para deletar a tabela do banco de dados
                break;
            case 7:
                apagarDados(); // Chama o método para apagar todos os dados da tabela
                break;
            case 0:
                System.out.println("Encerrando a aplicação..."); // Mensagem de encerramento
                break;
            default:
                System.out.println("Opção inválida. Encerrando a aplicação..."); // Mensagem de opção inválida
        }
    }

  
    public static void criarTabela() {
        // Comando SQL para criar a tabela no banco de dados
        String SQLcriarTabela = "CREATE TABLE produtosSupermercado (id int, nomeProduto VARCHAR(60), lote VARCHAR(20), validade DATE, codigoBarras int, preco DECIMAL(10, 2))";
        String driver = "jdbc:postgresql://127.0.0.1:5432/ProdutosSupermercado";
        Statement st = null;

        try (Connection conn = DriverManager.getConnection(driver, "postgres", "password")) {
            if (conn != null) {
                System.out.println("Conectado ao Banco de Dados com sucesso.");
            } else {
                System.out.println("Falha ao conectar ao Banco de Dados.");
            }

            System.out.println("Criando tabela, aguarde...");

            st = conn.createStatement();
            st.executeUpdate(SQLcriarTabela); // Executa o comando SQL para criar a tabela

            System.out.println("Tabela criada com sucesso!");
        } catch (SQLException e) {
            // Tratamento de exceção em caso de erros SQL
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                // Tratamento de exceção em caso de erros ao fechar o statement
                System.err.format("Error closing statement: %s\n%s", e.getSQLState(), e.getMessage());
            }
        }
    }


    public static void inserirDados() {
        String driver = "jdbc:postgresql://127.0.0.1:5432/ProdutosSupermercado";
        Statement st = null;

        try (Connection conn = DriverManager.getConnection(driver, "postgres", "password")) {
            if (conn != null) {
                System.out.println("Conectado ao Banco de Dados com sucesso.");
            } else {
                System.out.println("Falha ao conectar ao Banco de Dados.");
            }

            Scanner scanner = new Scanner(System.in);

            // Solicitação dos dados a serem inseridos
            System.out.println("Digite o ID:");
            int id = scanner.nextInt();

            scanner.nextLine();

            System.out.println("Digite o nome do produto:");
            String nomeProduto = scanner.nextLine();

            System.out.println("Digite o lote:");
            String lote = scanner.nextLine();

            System.out.println("Digite a data de validade (AAAA-MM-DD):");
            String validade = scanner.nextLine();

            System.out.println("Digite o código de barras:");
            int codigoBarras = scanner.nextInt();

            System.out.println("Digite o preço:");
            double preco = scanner.nextDouble();

            // Montagem do comando SQL para inserir os dados na tabela
            String SQLinserirDados = "INSERT INTO produtosSupermercado (id, nomeProduto, lote, validade, codigoBarras, preco) VALUES (" + id + ", '" + nomeProduto + "', '" + lote + "', '" + validade + "', " + codigoBarras + ", " + preco + ")";

            System.out.println("Inserindo dados na tabela...");

            st = conn.createStatement();
            st.executeUpdate(SQLinserirDados); // Executa o comando SQL para inserir os dados

            System.out.println("Dados inseridos!");
        } catch (SQLException e) {
            // Tratamento de exceção em caso de erros SQL
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                // Tratamento de exceção em caso de erros ao fechar o statement
                System.err.format("Error closing statement: %s\n%s", e.getSQLState(), e.getMessage());
            }
        }
    }


    public static void consultarDados() {
        String SQLConsultarDados = "SELECT * FROM produtosSupermercado";
        String driver = "jdbc:postgresql://127.0.0.1:5432/ProdutosSupermercado";
        Statement st;
        ResultSet result;

        try (Connection conn = DriverManager.getConnection(driver, "postgres", "password")) {
            if (conn != null) {
                System.out.println("Conectado ao Banco de Dados com sucesso.");
            } else {
                System.out.println("Falha ao conectar ao Banco de Dados.");
            }

            System.out.println("Consultando dados na tabela...");

            st = conn.createStatement();
            result = st.executeQuery(SQLConsultarDados); // Executa a consulta SQL e retorna os resultados

            while (result.next()) {
                // Itera sobre cada linha do resultado e exibe os dados
                System.out.println("-----------------");
                System.out.println("ID: " + result.getInt("id"));
                System.out.println("Nome do Produto: " + result.getString("nomeProduto"));
                System.out.println("Lote: " + result.getString("lote"));
                System.out.println("Validade: " + result.getDate("validade"));
                System.out.println("Código de Barras: " + result.getInt("codigoBarras"));
                System.out.println("Preço: " + result.getDouble("preco"));
            }

            result.close();
            st.close();
        } catch (SQLException e) {
            // Tratamento de exceção em caso de erros SQL
            e.printStackTrace();
        }
    }

    public static void atualizarDados() {
        String driver = "jdbc:postgresql://127.0.0.1:5432/ProdutosSupermercado";
        Statement st = null;

        try (Connection conn = DriverManager.getConnection(driver, "postgres", "password")) {
            if (conn != null) {
                System.out.println("Conectado ao Banco de Dados com sucesso.");
            } else {
                System.out.println("Falha ao conectar ao Banco de Dados.");
            }

            // Listar os dados atuais antes de atualizar
            String SQLlistarDados = "SELECT id, nomeProduto FROM produtosSupermercado";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQLlistarDados);

            System.out.println("ID\tNome do Produto");
            while (rs.next()) {
                // Itera sobre cada linha do resultado e exibe os dados atuais
                int id = rs.getInt("id");
                String nomeProduto = rs.getString("nomeProduto");
                System.out.println(id + "\t" + nomeProduto);
            }
            rs.close();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Digite o ID do produto para ser modificado:");
            int id = scanner.nextInt();

            scanner.nextLine();

            System.out.println("Digite o novo nome do produto:");
            String novoNomeProduto = scanner.nextLine();

            System.out.println("Digite o novo lote:");
            String novoLote = scanner.nextLine();

            System.out.println("Digite a nova data de validade (AAAA-MM-DD):");
            String novaValidade = scanner.nextLine();

            System.out.println("Digite o novo código de barras:");
            int novoCodigoBarras = scanner.nextInt();

            System.out.println("Digite o novo preço:");
            double novoPreco = scanner.nextDouble();

            String SQLatualizarDados = "UPDATE produtosSupermercado SET nomeProduto = '" + novoNomeProduto + "', lote = '" + novoLote + "', validade = '" + novaValidade + "', codigoBarras = " + novoCodigoBarras + ", preco = " + novoPreco + " WHERE id = " + id;

            System.out.println("Atualizando dados na tabela...");

            // Executa o comando SQL para atualizar os dados
            st.executeUpdate(SQLatualizarDados);

            System.out.println("Dados atualizados!");

        } catch (SQLException e) {
            // Tratamento de exceção em caso de erros SQL
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                // Tratamento de exceção ao fechar o Statement
                System.err.format("Error closing statement: %s\n%s", e.getSQLState(), e.getMessage());
            }
        }
    }

    public static void deletarTabela() {
        // Nome da tabela a ser deletada
        String tableName = "produtosSupermercado";
        // URL do banco de dados
        String driver = "jdbc:postgresql://127.0.0.1:5432/ProdutosSupermercado";

        try (Connection conn = DriverManager.getConnection(driver, "postgres", "password")) {
            if (conn != null) {
                System.out.println("Conectado ao Banco de Dados com sucesso.");
            } else {
                System.out.println("Falha ao conectar ao Banco de Dados.");
            }

            System.out.println("Deletando Tabela...");

            // Criação do objeto Statement para executar comandos SQL
            Statement statement = conn.createStatement();
            // Comando SQL para deletar a tabela, se ela existir
            String sql = "DROP TABLE IF EXISTS " + tableName;
            // Executa o comando SQL para deletar a tabela
            statement.executeUpdate(sql);

            System.out.println("Tabela Deletada com Sucesso.");

            // Fecha o Statement
            statement.close();
        } catch (SQLException e) {
            // Tratamento de exceção em caso de erros SQL
            e.printStackTrace();
        }
    }

    public static void listarProdutosExpirando() {
        // URL do banco de dados
        String driver = "jdbc:postgresql://127.0.0.1:5432/ProdutosSupermercado";
        Statement st;
        ResultSet result;

        try (Connection conn = DriverManager.getConnection(driver, "postgres", "password")) {
            if (conn != null) {
                System.out.println("Conectado ao Banco de Dados com sucesso.");
            } else {
                System.out.println("Falha ao conectar ao Banco de Dados.");
            }

            // Obtém a data atual
            LocalDate dataAtual = LocalDate.now();
            // Calcula a data limite (14 dias a partir da data atual)
            LocalDate dataLimite = dataAtual.plusDays(14);
            // Define o formato da data para exibição
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Consulta os produtos que expiram em menos de 14 dias
            String SQLConsultarDados = "SELECT * FROM produtosSupermercado WHERE validade BETWEEN '"
                    + dataAtual.format(formatter) + "' AND '" + dataLimite.format(formatter) + "'";
            System.out.println("Consultando produtos que expiram em menos de 14 dias...");

            st = conn.createStatement();
            result = st.executeQuery(SQLConsultarDados);

            while (result.next()) {
                System.out.println("-----------------");
                System.out.println("Código de Barras: " + result.getString("codigoBarras"));
                System.out.println("Nome: " + result.getString("nomeProduto"));
                System.out.println("Data de Validade: " + result.getString("validade"));
            }

            result.close();
            st.close();
        } catch (SQLException e) {
            // Tratamento de exceção em caso de erros SQL
            e.printStackTrace();
        }
    }

    public static void apagarDados() {
        // URL do banco de dados
        String driver = "jdbc:postgresql://127.0.0.1:5432/ProdutosSupermercado";
        Statement st = null;

        try (Connection conn = DriverManager.getConnection(driver, "postgres", "password")) {
            if (conn != null) {
                System.out.println("Conectado ao Banco de Dados com sucesso.");
            } else {
                System.out.println("Falha ao conectar ao Banco de Dados.");
            }

            // Listar os produtos existentes
            String SQLlistarDados = "SELECT id, nomeProduto FROM produtosSupermercado";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQLlistarDados);

            System.out.println("ID\tNome do Produto");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nomeProduto = rs.getString("nomeProduto");
                System.out.println(id + "\t" + nomeProduto);
            }
            rs.close();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Digite o ID do produto a ser excluído:");
            int id = scanner.nextInt();

            // Cria a instrução SQL para apagar os dados do produto com o ID fornecido
            String SQLapagarDados = "DELETE FROM produtosSupermercado WHERE id = " + id;
            System.out.println("Excluindo dados da tabela...");

            st = conn.createStatement();
            // Executa a instrução SQL para apagar os dados
            int rowsAffected = st.executeUpdate(SQLapagarDados);

            if (rowsAffected > 0) {
                System.out.println("Dados excluídos com sucesso!");
            } else {
                System.out.println("Nenhum dado correspondente ao ID fornecido foi encontrado.");
            }
        } catch (SQLException e) {
            // Tratamento de exceção em caso de erros SQL
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                System.err.format("Error closing statement: %s\n%s", e.getSQLState(), e.getMessage());
            }
        }
    }
}
