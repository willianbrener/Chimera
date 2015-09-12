package br.com.ueg.pids.Utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Classe respons�vel por Criar conex�o com o banco de dados da aplicacao....
 */

public class Connect {
	public static Connection connection;
	public static Statement statement;
	public static ResultSet resultSet;
	public static ResultSet resultSetAux;
	
	public static boolean getConexao() {  
	        try {  
	            // Carregando o JDBC Driver padr�o  
	        	Class.forName("org.postgresql.Driver");
	        	//System.out.println("JDBC driver carregado");
	            // Configurando a nossa conex�o com um banco de dados//  
	        	//"jdbc:postgresql://localhost:5432/lan_manager","postgres","postgres"
	            String url = "jdbc:postgresql://localhost:5432/chimera";  
	            String username = "postgres";        //nome de um usu�rio de seu BD        
	            String password = "postgres";      //sua senha de acesso  
	            connection = DriverManager.getConnection(url, username, password);
	            statement = (Statement) connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	            //System.out.println("Conectou");  
	            return true;  
	        }  catch (ClassNotFoundException e) {  //Driver n�o encontrado  
	            System.out.println("O driver expecificado nao foi encontrado.");  
	            return false;  
	        } catch (SQLException e) {  
	            //N�o conseguindo se conectar ao banco  
	            System.out.println("Nao foi possivel conectar ao Banco de Dados.");  
	            return false;  
	        }  
	 }
	 
	
	public static void close(){
		closeResultSet();
		closeResultSetAux();
		closeStatement();
		closeConnection();	
	}

	private static void closeResultSetAux() {
		try{
			if(resultSetAux!= null)
			resultSetAux.close();
		} catch(SQLException erro){
			erro.printStackTrace();
		}
	}

	private static void closeResultSet() {
		try{
			if(resultSet!= null)
			resultSet.close();
		} catch(SQLException erro){
			erro.printStackTrace();
		}
		
	}

	private static void closeStatement() {
		try{
			if(statement!= null)
			statement.close();
		} catch(SQLException erro){
			erro.printStackTrace();
		}
		
	}

	private static void closeConnection() {
		try{
			if(connection!= null)
			connection.close();
			//System.out.println("Desconectou banco");
		} catch(SQLException erro){
			erro.printStackTrace();
		}
	}
	
	/*
	 * Foi utilizado uma fun��o que devolve o ResultSet para percorrer
	 * os resultados da sql passada por parametro
	 */
	public static ResultSet setResultSet(String sql){
		try{
			resultSet = (ResultSet) statement.executeQuery(sql);
			return resultSet;
		} catch(SQLException erro){
			erro.printStackTrace();
		}
		return resultSet; 
	}
	
	/*
	 * Foi utilizado uma fun��o que devolver um inteiro com total de runs afetados
	 * utilizado para inserir e update e delete no banco onde geralmente sao afetados apenas 1
	 * posteriomente este numero � utilizado para valida��o se retorna 1 rodou de acordo com 
	 * o planejado caso 0 ocorreu erro.
	 */
	public static int runSQL(String sql){
		int quant = 0 ;
		try{
			quant= statement.executeUpdate(sql) ;
			
		}catch(SQLException erro){
			erro.printStackTrace();
		} 
		return quant;
	}
}
