package br.com.ueg.pids.Model;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * @param <TipoPK>
 */
/**
 * @author Guiliano
 *
 * @param <TipoPK>
 */
public abstract class Table<TipoPK> {

	/**
	 * @return retorno uma string com o nome da pk
	 */
	public abstract String getPKName();

	/**
	 * M�todo utilizado para conhecer o valor da chave prim�ria
	 * 
	 * @return retorna a chave prim�ria
	 */
	public abstract TipoPK getPK();

	/**
	 * M�todo utilizado para atribuir valor para chave prim�ria
	 * 
	 * @param pk
	 *            valor da chave prim�ria
	 */
	public abstract void setPK(TipoPK pk);

	/**
	 * @return retorna o Nome da tabela em String
	 */
	public abstract String getTableName();

	/**
	 * @return retorna o nome das colunas da tabela separada por virgula
	 */
	public String getTableColumnNames() {
		String retorno = "";
		if (this.validateColumnNames()) {
			//troca espa�o para nada para evitar problemas no formato retornado
			//metodo getColumnNames
			retorno = this.getColumnNames().replace(" ", "");
		}
		return retorno;
	}

	/**
	 * Metodo que retorna o nome das colunas separadaos por virgula, esse metodo
	 * � utilizado pelo GetTableColumnNames que faz algumas valida��es antes de
	 * invocar o getColumnNames
	 * 
	 * @return
	 */
	public abstract String getColumnNames();

	/**
	 * Metodo recebe o valor das colunas da tabela na mesma ordem que o
	 * getColumnNames retorna e valida antes de passar o valor para o
	 * SetColumnsValues como vetor de string
	 * 
	 * @param values
	 *            String contendo os valores separados por virgula para atribuir
	 *            aos atributos
	 */
	public void setTableColumnsValues(String values) {
		if (this.validateColumnValues(values)) {
			String[] valores = values.concat(" ").split(",");

			this.setColumnsValues(valores);

		}
	}

	/**
	 * Metodo recebe o valor das colunas da tabela na mesma ordem que o
	 * getColumnNames retorna
	 * 
	 * @param values
	 *            String contendo os valores separados por virgula para atribuir
	 *            aos atributos
	 */
	public abstract void setColumnsValues(String[] values);

	/**
	 * Metodo retorna o valor das colunas da entidade que ser� utilizado para
	 * salvar os dados deve ser separado por virgula e seguir a mesma ordem o
	 * getTableColumns
	 * 
	 * @return
	 */
	public String getTableColumnValues() {
		String retorno = "";
		if (this.validateColumnValues(this.getColumnValues())) {
			retorno = this.getColumnValues();
		}
		return retorno;

	}

	/**
	 * Metodo que retorna o valor das colunas separadaos por virgula, esse
	 * metodo � utilizado pelo GetTableColumnValues que faz algumas valida��es
	 * antes de invocar o getColumnValues
	 * 
	 * @return
	 */
	public abstract String getColumnValues();

	/**
	 * Metodo retorna o n�mero de colunas definido baseado no nome das coluna
	 * 
	 * @return
	 */
	public int getColumnCount() throws RuntimeException {
		if (this.validateColumnNames()) {
			String[] split = this.getTableColumnNames().split(",");
			return split.length;
		} else {
			return 0;
		}
	}

	/**
	 * @return true se o metodo getColumnNames() estiver definido corretamente
	 * @throws RuntimeException
	 *             dispar� exece��o caso o nome das colunas n�o for valido
	 */
	private boolean validateColumnNames() throws RuntimeException {
		if (this.getColumnNames() != null && this.getColumnNames().length() > 3
				&& this.getColumnNames().indexOf(",") > 0) {
			return true;
		} else {
			// gerando erro de tempo de execu��o para informar ao programador
			// onde est� o problema na implementa��o.
			throw new RuntimeException(
					"Nome das colunas n�o foi definido: metodo getColumnNames() em "
							+ this.getClass().getName()
							+ ". N�o retorna string no format 'coluna1,coluna2'");
		}
	}

	/**
	 * @return true se o metodo getColumnNames() estiver definido corretamente
	 * @throws RuntimeException
	 *             dispar� exece��o caso o nome das colunas n�o for valido
	 */
	private boolean validateColumnValues(String values) throws RuntimeException {
		if (this.validateColumnNames() && values != null
				&& values.indexOf(",") >= 0
				&& values.concat(" ").split(",").length == this.getColumnCount()) {
			return true;
		} else {
			// gerando erro de tempo de execu��o para informar ao programador
			// onde est� o problema na implementa��o.
			throw new RuntimeException(
					"M�todo getColumnValues() em "
							+ this.getClass().getName()
							+ ". N�o retorna string com os valores no format 'valorcoluna1,valorcoluna2'"+
							"\nFomato esperado:"+this.getTableColumnNames()+
							"\nValor informado:"+this.getColumnValues());
		}
	}

	public abstract void resetFields();
	
	/**
	 * Retorna o nome das colunas obrigatorias separada por virgula sem espa�o
	 * fazendo a valida��o se os nomes definidos est�o corretos.
	 * @return
	 */
	public String getTableMandatoryFields() throws RuntimeException{
		
		String[] validColumns = this.getTableColumnNames().split(",");
		String mandatoryColumns = this.getMandatoryFields();
		
		mandatoryColumns = mandatoryColumns.replace(" ", "");
		String[] mandatoryColumnsVector = mandatoryColumns.split(",");
		ArrayList<String> validColumnsArray = new ArrayList<String>(Arrays.asList(validColumns));
		
		//valida se os campos especificados existem 
		ArrayList<String> mandatoryColumnsArray = new ArrayList<String>(Arrays.asList(mandatoryColumnsVector));
		for (String column : mandatoryColumnsArray) {
			if(!validColumnsArray.contains(column)){
				throw new RuntimeException(
						"Campo especificado no metodo getMandatoryFields() ("+column+") especificado na classe "
								+ this.getClass().getName()
								+ ". N�o n�o existe foi definido no metodo getColumnNamess()");
			}
		}
		
		
		return mandatoryColumns;
	}
	
	
	/**
	 * Retorna o nome das colunas obrigatorias separada por virgula sem espa�o
	 * @return
	 */
	public abstract String getMandatoryFields();

	// metodo adicionado para fazer o selecte(n�o tinha outro mecanismo para
	// obter de um atributo(o campo fk)

	/**
	 * metodo retorna o valor de um campo passado o nome do campo, retorna null
	 * caso o nome do campo n�o exita.
	 * 
	 * @param fieldName
	 * @return
	 */
	public String getFieldValue(String fieldName) {
		String cols = this.getTableColumnNames();
		String vals = this.getTableColumnValues();

		String[] auxCols = cols.split(",");
		String[] auxVals = vals.concat(" ").split(",");

		for (int i = 0; i < auxCols.length; i++) {
			if (auxCols[i].trim().equalsIgnoreCase(fieldName)) {
				return auxVals[i].trim();
			}
		}
		return null;
	}
}
