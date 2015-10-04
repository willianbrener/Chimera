package br.com.ueg.pids.Utils;

import org.zkoss.zk.ui.Executions;

public class Utils {
	/**
	 * M�todo que retorna string com a primeira letra em mai�scula, o restante permanece o mesmo.
	 * 
	 * @param str valor
	 * @return valor com a primeira letra em mai�sculo
	 */
	public static String firstToUpperCase(String str) {
		return str.substring(0, 1).toUpperCase().concat(str.substring(1));
	}
	/**
	 * M�todo que retorna string com a primeira letra em min�sculo, o restante permanece o mesmo.
	 * 
	 * @param str valor
	 * @return valor com a primeira letra em min�sculo
	 */
	public static String firstToLowerCase(String str) {
		return str.substring(0, 1).toLowerCase().concat(str.substring(1));
	}
	
	public static String totalLowerCase(String str) {
		return str.toLowerCase();
	}
	
	public static String retirarExtensao(String string) {
		String resposta = null;
		if(string == null) return null;
		String extensao = null;
		int tamanho = string.length();
		extensao = string.substring((tamanho-4), tamanho);
		if(extensao.substring(0, 1).equalsIgnoreCase(".")){
			resposta= extensao;
		}else{
			resposta = string.substring((tamanho-5), tamanho);
		}
		return Utils.totalLowerCase(resposta);
	}
	
	public String verificaPaginaSolicitacoes(String string) {
		String retorno = null;
		String criar = "cadastrar";
		String listar = "pesquisar";
		boolean resultado1, resultado2;
		resultado1 = string.contains(criar);
		resultado2 = string.contains(listar);
		if (resultado1 == true && resultado2 == false) {
			retorno = "criar";
		} else if (resultado1 == false && resultado2 == true) {
			retorno = "listar";
		}
		return retorno;

	}
}