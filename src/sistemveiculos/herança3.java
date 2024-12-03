package sistemveiculos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import conexaobdd.conexao;

//Classe com os atributos gerais que vão ser utilizados para outras classes.
class CaracteristicasVeiculo {
 // Atributos gerais
 private String nomeModelo;
 private int anoFabricacao;
 private String nomeMontadora;
 private String corVeiculo;
 private int quilometragem;

 // Método para inicializar as características do veículo
 public CaracteristicasVeiculo(String nomeModelo, int anoFabricacao, String nomeMontadora, String corVeiculo, int quilometragem) {
     setNomeModelo(nomeModelo);
     setAnoFabricacao(anoFabricacao);
     setNomeMontadora(nomeMontadora);
     setCorVeiculo(corVeiculo);
     setQuilometragem(quilometragem);
 }

 // Métodos get para obter os valores dos atributos
 public String getNomeModelo() {
     return nomeModelo;
 }

 // Métodos set para validar e alterar os atributos
 public void setNomeModelo(String nomeModelo) {
     if (nomeModelo == null || nomeModelo.trim().isEmpty()) {
         throw new IllegalArgumentException("Diga o nome do modelo do veículo");
     }
     this.nomeModelo = nomeModelo;
 }

 public int getAnoFabricacao() {
     return anoFabricacao;
 }

 public void setAnoFabricacao(int anoFabricacao) {
     int minimo = 1886;
     int maximo = LocalDate.now().getYear() + 1;
     if (anoFabricacao < minimo || anoFabricacao > maximo) {
         throw new IllegalArgumentException("O ano de fabricação precisa ser entre " + minimo + " e " + maximo);
     }
     this.anoFabricacao = anoFabricacao;
 }

 public String getNomeMontadora() {
     return nomeMontadora;
 }

 public void setNomeMontadora(String nomeMontadora) {
     if (nomeMontadora == null || nomeMontadora.trim().isEmpty()) {
         throw new IllegalArgumentException("Qual o nome da montadora do veiculo");
     }
     this.nomeMontadora = nomeMontadora;
 }

 public String getCorVeiculo() {
     return corVeiculo;
 }

 public void setCorVeiculo(String corVeiculo) {
     if (corVeiculo == null || corVeiculo.trim().isEmpty()) {
         throw new IllegalArgumentException("Qual a cor do veículo");
     }
     this.corVeiculo = corVeiculo;
 }

 public int getQuilometragem() {
     return quilometragem;
 }

 public void setQuilometragem(int quilometragem) {
     if (quilometragem < 0) {
         throw new IllegalArgumentException("Diga qual a quilometragem do veículo");
     }
     this.quilometragem = quilometragem;
 }

 // Método que formata as informações de veiculo
 public String obterDescricao() {
     return "\nModelo: " + nomeModelo + 
            "\nAno de Fabricação: " + anoFabricacao + 
            "\nMontadora: " + nomeMontadora + 
            "\nCor: " + corVeiculo + 
            "\nQuilometragem: " + quilometragem;
 }

 // Método para gerar comando SQL de inserção
 public String gerarComandoInsert() {
     return "INSERT INTO CaracteristicasVeiculo (nomeModelo, anoFabricacao, nomeMontadora, corVeiculo, quilometragem) VALUES ('" + getNomeModelo() + "', " + getAnoFabricacao() + ", '" + getNomeMontadora() + "', '" + getCorVeiculo() + "', " + getQuilometragem() + ");";
 }
}
//Classe Carro com os atributos necessários
class Carro extends CaracteristicasVeiculo { // Método herança da CaracteristicasVeiculo
// Atributos
private int quantidadePassageiros;
private String tipoFreio;
private String sistemaAirbag;

// Método para iniciar as características do carro
public Carro(String modelo, int anoFabricacao, String montadora, String cor, int km, int quantidadePassageiros, String tipoFreio, String sistemaAirbag) {
  super(modelo, anoFabricacao, montadora, cor, km); // Obtém as características da classe CaracteristicasVeiculo
  setQuantidadePassageiros(quantidadePassageiros); // Informa a quantidade de passageiros para o carro
  setTipoFreio(tipoFreio);
  setSistemaAirbag(sistemaAirbag);
}

// Método get para acessar a quantidade de passageiros
public int getQuantidadePassageiros() {
  return quantidadePassageiros;
}

// Método set para modificar a quantidade de passageiros
public void setQuantidadePassageiros(int quantidadePassageiros) {
  if (quantidadePassageiros <= 0) { // Validação: deve ser maior que 0
      throw new IllegalArgumentException("O número de passageiros do carro tem que ser maior que 0");
  }
  this.quantidadePassageiros = quantidadePassageiros;
}

public String getTipoFreio() {
  return tipoFreio;
}

public void setTipoFreio(String tipoFreio) {
  if (tipoFreio == null || tipoFreio.trim().isEmpty()) { // Validação: não pode ser vazio
      throw new IllegalArgumentException("Diga qual o tipo de freio do carro");
  }
  this.tipoFreio = tipoFreio;
}

public String getSistemaAirbag() {
  return sistemaAirbag;
}

public void setSistemaAirbag(String sistemaAirbag) {
  if (sistemaAirbag == null || sistemaAirbag.trim().isEmpty()) {
      throw new IllegalArgumentException("Qual o sistema de airbag do carro");
  }
  this.sistemaAirbag = sistemaAirbag;
}

// Método com as informações de carro formatadas
public String obterDescricao() {
  // Utiliza o método da classe com atributos comuns para ter informações e adiciona os específicos para carro
  return super.obterDescricao() + 
         " \nQuantidade de Passageiros: " + quantidadePassageiros + 
         "\nTipo de Freio: " + tipoFreio + 
         "\nSistema de Airbag: " + sistemaAirbag;
}

public void Insert(Connection conexao) {
    String sql = "INSERT INTO carro (NomeModelo, AnoFabricacao, NomeMontadora, CorVeiculo, Quilometragem, QuantidadePassageiros, TipoFreio, SistemaAirbag) VALUES( ?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setString(1, getNomeModelo());
        stmt.setInt(2, getAnoFabricacao());
        stmt.setString(3, getNomeMontadora());
        stmt.setString(4, getCorVeiculo());
        stmt.setInt(5, getQuilometragem());
        stmt.setInt(6, getQuantidadePassageiros());
        stmt.setString(7, getTipoFreio());
        stmt.setString(8, getSistemaAirbag());
        
        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("\nVeiculo salvo!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Erro ao inserir o produto: " + e.getMessage());
    }
}
}
class Moto extends CaracteristicasVeiculo { // Método herança da CaracteristicasVeiculo
 // Atributos 
 private int cc; // Capacidade do motor em cilindradas
 private double valorTorque; // Torque gerado pelo motor 

 // Método para inicializar as características da moto
 public Moto(String modelo, int anoFabricacao, String montadora, String cor, int km, int cc, double valorTorque) {
     super(modelo, anoFabricacao, montadora, cor, km); // Obtém as características da classe CaracteristicasVeiculo
     setCc(cc); // Valida a cilindrada
     setTorque(valorTorque); // Valida o torque
 }

 // Método get para acessar a cilindrada
 public int getCc() {
     return cc;
 }

 // Método set para modificar a cilindrada
 public void setCc(int cc) {
     if (cc <= 0) { // Validação: deve ser maior que 0
         throw new IllegalArgumentException("Diga a quantidade de cilindradas da motocicleta");
     }
     this.cc = cc;
 }

 public double getValorTorque() {
     return valorTorque;
 }

 public void setTorque(double valorTorque) {
     if (valorTorque < 0) { // Validação: não pode ser negativo
         throw new IllegalArgumentException("Qual o torque da motocicleta");
     }
     this.valorTorque = valorTorque;
 }

 // Método com as informações de moto formatadas
 public String obterDescricao() {
     // Utiliza o método da classe com atributos comuns para ter informações e adiciona os específicos para moto
     return String.format("Modelo: %s\nAno de Produção: %d\nFabricante: %s\nCor: %s\nQuilometragem: %d km\nCapacidade do Motor: %d cc\nTorque: %.2f kgfm",
             getNomeModelo(), getAnoFabricacao(), getNomeMontadora(), getCorVeiculo(), getQuilometragem(), getCc(), getValorTorque());
 }

 public void Insert(Connection conexao) {
	    String sql = "INSERT INTO Moto (NomeModelo, AnoFabricacao, NomeMontadora, CorVeiculo, Quilometragen, QuantidadePassageiros, TipoFreio) VALUES( ?, ?, ?, ?, ?, ?, ?)";
	    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	        stmt.setString(1, getNomeModelo());
	        stmt.setInt(2, getAnoFabricacao());
	        stmt.setString(3, getNomeMontadora());
	        stmt.setString(4, getCorVeiculo());
	        stmt.setInt(5, getQuilometragem());
	        stmt.setInt(6, getCc());
	        stmt.setDouble(7, getValorTorque());
	       
	        int rowsUpdated = stmt.executeUpdate();
	        if (rowsUpdated > 0) {
	        	System.out.println("\nVeiculo salvo!");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("Erro ao inserir o produto: " + e.getMessage());
	    }
	}
}
class Caminhao extends CaracteristicasVeiculo { // Método herança da CaracteristicasVeiculo
 // Atributos 
 private int numeroEixos; // Número total de eixos do caminhão
 private double pesoTotal; // Peso total do caminhão em toneladas

 // Método para inicializar as características de caminhão
 public Caminhao(String nomeModelo, int anoFabricacao, String nomeMontadora, String corVeiculo, int quilometragem, int numeroEixos, double pesoTotal) {
     super(nomeModelo, anoFabricacao, nomeMontadora, corVeiculo, quilometragem); // Obtém as características da classe CaracteristicasVeiculo
     setNumeroEixos(numeroEixos); // Valida o número de eixos
     setPesoTotal(pesoTotal); // Valida o peso total
 }

 // Método para ter o número de eixos
 public int getNumeroEixos() {
     return numeroEixos;
 }

 // Método para definir o número de eixos
 public void setNumeroEixos(int numeroEixos) {
     if (numeroEixos <= 0) { // Validação: deve ser maior que zero
         throw new IllegalArgumentException("Diga corretamente o número de eixos do caminhão.");
     }
     this.numeroEixos = numeroEixos;
 }

 public double getPesoTotal() {
     return pesoTotal;
 }

 public void setPesoTotal(double pesoTotal) {
     if (pesoTotal <= 0) { // Validação: deve ser maior que zero
         throw new IllegalArgumentException("Diga corretamente o peso total do caminhão.");
     }
     this.pesoTotal = pesoTotal;
 }

 // Método com as informações de caminhão formatadas
 public String obterDescricao() {
     // Formata e informa sobre o caminhão, incluindo as heranças
     return String.format("Características do Caminhão:" +
                          "\nModelo: %s" +
                          "\nAno de Fabricação: %d" +
                          "\nMontadora: %s" +
                          "\nCor: %s" +
                          "\nQuilometragem: %d km" +
                          "\nNúmero de Eixos: %d" +
                          "\nPeso Total: %.2f toneladas",
             getNomeModelo(), getAnoFabricacao(), getNomeMontadora(), getCorVeiculo(), getQuilometragem(), getNumeroEixos(), getPesoTotal());
 }

 public void Insert(Connection conexao) {
	    String sql = "INSERT INTO Caminhao (NomeModelo, AnoFabricacao, NomeMontadora, CorVeiculo, Quilometragen, NumeroEixos, PesoTotal) VALUES( ?, ?, ?, ?, ?, ?, ?)";
	    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	        stmt.setString(1, getNomeModelo());
	        stmt.setInt(2, getAnoFabricacao());
	        stmt.setString(3, getNomeMontadora());
	        stmt.setString(4, getCorVeiculo());
	        stmt.setInt(5, getQuilometragem());
	        stmt.setInt(6, getNumeroEixos());
	        stmt.setDouble(7, getPesoTotal());
	        
	        int rowsUpdated = stmt.executeUpdate();
	        if (rowsUpdated > 0) {
	        	System.out.println("\nVeiculo salvo!");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("Erro ao inserir o produto: " + e.getMessage());
	    }
	}
}

class Skate {
 // Atributos 
 private String nomeModeloSkate; // Nome do modelo do skate
 private int anoDeFabricacao; // Ano de fabricação do skate
 private String marcaSkate; // Marca do skate
 private String corSkate; // Cor do skate
 private String tipoDeRoda; // Tipo de roda do skate

 // Método para inicializar as características do skate
 public Skate(String nomeModeloSkate, int anoDeFabricacao, String marcaSkate, String corSkate, String tipoDeRoda) {
     setNomeModeloSkate(nomeModeloSkate); // Validação do nome do modelo
     setAnoDeFabricacao(anoDeFabricacao); // Validação do ano de fabricação
     setMarcaSkate(marcaSkate); // Validação da marca
     setCorSkate(corSkate); // Validação da cor
     setTipoDeRoda(tipoDeRoda); // Validação do tipo de roda
 }

 // Método para ter o modelo do skate
 public String getNomeModeloSkate() {
     return nomeModeloSkate;
 }

 // Método para definir o modelo do skate
 public void setNomeModeloSkate(String nomeModeloSkate) {
     if (nomeModeloSkate == null || nomeModeloSkate.trim().isEmpty()) { // Verifica se o modelo é vazio ou nulo
         throw new IllegalArgumentException("Diga o nome do modelo do skate.");
     }
     this.nomeModeloSkate = nomeModeloSkate;
 }

 public int getAnoDeFabricacao() {
     return anoDeFabricacao;
 }

 public void setAnoDeFabricacao(int anoDeFabricacao) {
     int minimo = 1950; // Define o limite mínimo do ano de fabricação
     int maximo = LocalDate.now().getYear() + 1; // Define o limite máximo do ano de fabricação

     if (anoDeFabricacao < minimo || anoDeFabricacao > maximo) { // Verifica se o ano está dentro dos limites
         throw new IllegalArgumentException("O ano de fabricação do skate precisa ser entre " + minimo + " e " + maximo + ".");
     }
     this.anoDeFabricacao = anoDeFabricacao;
 }

 public String getMarcaSkate() {
     return marcaSkate;
 }

 public void setMarcaSkate(String marcaSkate) {
     if (marcaSkate == null || marcaSkate.trim().isEmpty()) { // Verifica se a marca é vazia ou nula
         throw new IllegalArgumentException("Diga a marca do skate.");
     }
     this.marcaSkate = marcaSkate;
 }

 public String getCorSkate() {
     return corSkate;
 }

 public void setCorSkate(String corSkate) {
     if (corSkate == null || corSkate.trim().isEmpty()) { // Verifica se a cor é vazia ou nula
         throw new IllegalArgumentException("Qual a cor do skate.");
     }
     this.corSkate = corSkate;
 }

 public String getTipoDeRoda() {
     return tipoDeRoda;
 }

 public void setTipoDeRoda(String tipoDeRoda) {
     if (tipoDeRoda == null || tipoDeRoda.trim().isEmpty()) { // Verifica se o tipo de roda é vazio ou nulo
         throw new IllegalArgumentException("Qual é o tipo de roda do skate.");
     }
     this.tipoDeRoda = tipoDeRoda;
 }

 // Método com as informações de skate formatadas
 public String obterDescricao() {
     return String.format("Detalhes do Skate:" +
                          "\nModelo: %s" +
                          "\nAno de Fabricação: %d" +
                          "\nMarca: %s" +
                          "\nCor: %s" +
                          "\nTipo de Roda: %s",
             nomeModeloSkate, anoDeFabricacao, marcaSkate, corSkate, tipoDeRoda);
 }

 // Método para gerar comando SQL de inserção
 public void Insert(Connection conexao) {
	    String sql = "INSERT INTO Skate (NomeModeloSkate, AnoFabricacao, MarcaSkate, CorSkate, TipoDeRoda) VALUES(?, ?, ?, ?, ?)";
	    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	        stmt.setString(1, getNomeModeloSkate());
	        stmt.setInt(2, getAnoDeFabricacao());
	        stmt.setString(3, getMarcaSkate());
	        stmt.setString(4, getCorSkate());
	        stmt.setString(5, getTipoDeRoda());
	        
	        int rowsUpdated = stmt.executeUpdate();
	        if (rowsUpdated > 0) {
	        	System.out.println("\nVeiculo salvo!");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("Erro ao inserir o produto: " + e.getMessage());
	    }
	}
}

class Bicicleta {
 // Atributos 
 private String nomeModelo; // Nome do modelo da bicicleta
 private int anoFabricacao; // Ano de fabricação da bicicleta
 private String marca; // Marca da bicicleta
 private String cor; // Cor da bicicleta
 private String tipoMaterial; // Material utilizado na bicicleta
 private int totalMarchas; // Total de marchas disponíveis
 private String tipoAmortecedor; // Tipo de amortecedor da bicicleta

 // Método para inicializar as características da bicicleta
 public Bicicleta(String nomeModelo, int anoFabricacao, String marca, String cor, String tipoMaterial, int totalMarchas, String tipoAmortecedor) {
     setNomeModelo(nomeModelo); // Validação do nome do modelo
     setAnoFabricacao(anoFabricacao); // Validação do ano de fabricação
     setMarca(marca); // Validação da marca
     setCor(cor); // Validação da cor
     setTipoMaterial(tipoMaterial); // Validação do material
     setTotalMarchas(totalMarchas); // Validação do total de marchas
     setTipoAmortecedor(tipoAmortecedor); // Validação do tipo de amortecedor
 }

 // Método para ter o nome o modelo
 public String getNomeModelo() {
     return nomeModelo;
 }

 // Método para definir o nome o modelo
 public void setNomeModelo(String nomeModelo) {
     if (nomeModelo == null || nomeModelo.trim().isEmpty()) { // Verifica se o modelo é vazio ou nulo
         throw new IllegalArgumentException("Qual o nome do modelo da bicicleta.");
     }
     this.nomeModelo = nomeModelo;
 }

 public int getAnoFabricacao() {
     return anoFabricacao;
 }

 public void setAnoFabricacao(int anoFabricacao) {
     int minimo = 1860; // Define o limite mínimo do ano de fabricação
     int maximo = LocalDate.now().getYear() + 1; // Define o limite máximo do ano de fabricação

     if (anoFabricacao < minimo || anoFabricacao > maximo) { // Verifica se o ano está dentro dos limites
         throw new IllegalArgumentException("O ano de fabricação da bicicleta precisa estar entre " + minimo + " e " + maximo + ".");
     }
     this.anoFabricacao = anoFabricacao;
 }

 public String getMarca() {
     return marca;
 }

 public void setMarca(String marca) {
     if (marca == null || marca.trim().isEmpty()) { // Verifica se a marca é vazia ou nula
         throw new IllegalArgumentException("Diga a marca da bicicleta.");
     }
     this.marca = marca;
 }

 public String getCor() {
     return cor;
 }

 public void setCor(String cor) {
     if (cor == null || cor.trim().isEmpty()) { // Verifica se a cor é vazia ou nula
         throw new IllegalArgumentException("Qual a cor da bicicleta.");
     }
     this.cor = cor;
 }

 public String getTipoMaterial() {
     return tipoMaterial;
 }

 public void setTipoMaterial(String tipoMaterial) {
     if (tipoMaterial == null || tipoMaterial.trim().isEmpty()) { // Verifica se o material é vazio ou nulo
         throw new IllegalArgumentException("Qual o material da bicicleta.");
     }
     this.tipoMaterial = tipoMaterial;
 }

 public int getTotalMarchas() {
     return totalMarchas;
 }

 public void setTotalMarchas(int totalMarchas) {
     if (totalMarchas <= 0) { // Verifica se o total de marchas é menor ou igual a zero
         throw new IllegalArgumentException("Diga corretamente a quantidade de marchas da bicicleta.");
     }
     this.totalMarchas = totalMarchas;
 }

 public String getTipoAmortecedor() {
     return tipoAmortecedor;
 }

 public void setTipoAmortecedor(String tipoAmortecedor) {
     if (tipoAmortecedor == null || tipoAmortecedor.trim().isEmpty()) { // Verifica se o tipo de amortecedor é vazio ou nulo
         throw new IllegalArgumentException("Qual o tipo de amortecedor da bicicleta.");
     }
     this.tipoAmortecedor = tipoAmortecedor;
 }

 // Método com as informações de bicicleta formatadas
 public String obterDescricao() {
     return String.format("Informações da Bicicleta:" +
                          "\nModelo: %s" +
                          "\nAno de Fabricação: %d" +
                          "\nMarca: %s" +
                          "\nCor: %s" +
                          "\nMaterial: %s" +
                          "\nTotal de Marchas: %d" +
                          "\nTipo de Amortecedor: %s",
             nomeModelo, anoFabricacao, marca, cor, tipoMaterial, totalMarchas, tipoAmortecedor);
 }

 // Método para gerar comando SQL de inserção
 public void Insert(Connection conexao) {
	    String sql = "INSERT INTO Bicicleta (NomeModelo, AnoFabricacao, Marca, Cor, TipoMaterial, TotalMarchas, TipoAmortecedor) VALUES( ?, ?, ?, ?, ?, ?, ?)";
	    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	        stmt.setString(1, getNomeModelo());
	        stmt.setInt(2, getAnoFabricacao());
	        stmt.setString(3, getMarca());
	        stmt.setString(4, getCor());
	        stmt.setString(5, getTipoMaterial());
	        stmt.setInt(6, getTotalMarchas());
	        stmt.setString(7, getTipoAmortecedor());
	        
	        int rowsUpdated = stmt.executeUpdate();
	        if (rowsUpdated > 0) {
	        	System.out.println("\nVeiculo salvo!");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("Erro ao inserir o produto: " + e.getMessage());
	    }
	}
}
public class herança3 {
 public static void main(String[] args) {
	 Connection Conexao = conexao.conectar();
		if (Conexao != null) {
     // Criando informações dos veículos e atribuindo valores
     try { // Validando as informações dos veículos
         Carro carroUm = new Carro("Civic", 2025, "Honda", "Vermelho", 8000, 4, "Freio a Disco", "Dianteiro");
         Carro carroDois = new Carro("A6", 2023, "Audi", "Prata", 3000, 4, "Freio a Disco Ventilados", "Todos os lados");
         Carro carroTres = new Carro("X5", 2024, "BMW", "Branco", 100, 4, "Freio a Disco com ABS", "Dianteiro e Traseiro");

         Moto motoUm = new Moto("CB 500", 2021, "Honda", "Preta", 5000, 500, 3.00);
         Moto motoDois = new Moto("Ninja ZX-10R", 2022, "Kawasaki", "Verde", 1200, 600, 10.50);
         Moto motoTres = new Moto("MT-07", 2023, "Yamaha", "Azul", 2000, 689, 7.00);

         Caminhao caminhaoUm = new Caminhao("Volvo FH", 2018, "Volvo", "Cinza", 150000, 2, 25);
         Caminhao caminhaoDois = new Caminhao("Mercedes Actros", 2020, "Mercedes-Benz", "Preto", 60000, 2, 28);
         Caminhao caminhaoTres = new Caminhao("Scania R", 2019, "Scania", "Branco", 80000, 2, 30);

         Bicicleta bicicletaUm = new Bicicleta("Mountain Bike XT", 2022, "Trek", "Preta", "Alumínio", 21, "Dianteiro e Traseiro");
         Bicicleta bicicletaDois = new Bicicleta("Speedster", 2023, "Cannondale", "Verde Limão", "Carbono", 18, "Sem Amortecedor");
         Bicicleta bicicletaTres = new Bicicleta("Bicicleta Infantil", 2020, "Caloi", "Rosa", "Aço", 16, "Dianteiro");

         Skate skateUm = new Skate("Skate Cruiser", 2021, "Penny", "Azul Claro", "Uretano 60mm, 78A");
         Skate skateDois = new Skate("Skate Profissional", 2022, "Santa Cruz", "Laranja", "Uretano 55mm, 99A");
         Skate skateTres = new Skate("Longboard", 2020, "Loaded", "Madeira Natural", "Uretano 70mm, 80A");

         // Mostrando todas as informações detalhadas de cada veículo
         System.out.println("=== CARRO ===");
         System.out.println(carroUm.obterDescricao());
         carroUm.Insert(Conexao);
         System.out.println();

         System.out.println("=== CARRO ===");
         System.out.println(carroDois.obterDescricao());
         carroDois.Insert(Conexao);
         System.out.println();

         System.out.println("=== CARRO ===");
         System.out.println(carroTres.obterDescricao());
         carroTres.Insert(Conexao);
         System.out.println();

         System.out.println("=== MOTO ===");
         System.out.println(motoUm.obterDescricao());
         motoUm.Insert(Conexao);
         System.out.println();

         System.out.println("=== MOTO ===");
         System.out.println(motoDois.obterDescricao());
         motoDois.Insert(Conexao);
         System.out.println();

         System.out.println("=== MOTO ===");
         System.out.println(motoTres.obterDescricao());
         motoTres.Insert(Conexao);
         System.out.println();

         System.out.println("=== CAMINHÃO ===");
         System.out.println(caminhaoUm.obterDescricao());
         caminhaoUm.Insert(Conexao);
         System.out.println();

         System.out.println("=== CAMINHÃO ===");
         System.out.println(caminhaoDois.obterDescricao());
         caminhaoDois.Insert(Conexao);
         System.out.println();

         System.out.println("=== CAMINHÃO ===");
         System.out.println(caminhaoTres.obterDescricao());
         caminhaoTres.Insert(Conexao);
         System.out.println();

         System.out.println("=== BICICLETA ===");
         System.out.println(bicicletaUm.obterDescricao());
         bicicletaUm.Insert(Conexao);
         System.out.println();

         System.out.println("=== BICICLETA ===");
         System.out.println(bicicletaDois.obterDescricao());
         bicicletaDois.Insert(Conexao);
         System.out.println();

         System.out.println("=== BICICLETA ===");
         System.out.println(bicicletaTres.obterDescricao());
         bicicletaTres.Insert(Conexao);
         System.out.println();

         System.out.println("=== SKATE ===");
         System.out.println(skateUm.obterDescricao());
         skateUm.Insert(Conexao);
         System.out.println();

         System.out.println("=== SKATE ===");
         System.out.println(skateDois.obterDescricao());
         skateDois.Insert(Conexao);
         System.out.println();

         System.out.println("=== SKATE ===");
         System.out.println(skateTres.obterDescricao());
         skateTres.Insert(Conexao);
         System.out.println();
     }
     catch (IllegalArgumentException e) { // Exibindo mensagem de erro em caso de validação falha
         System.err.println("Erro: " + e.getMessage());
     }
	}
 }
}