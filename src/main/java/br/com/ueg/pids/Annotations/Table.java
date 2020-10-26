package br.com.ueg.pids.Annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * Anota��o responsavel por identificar o nome da Tabela parao banco de dados de uma entidade do tipo model.
 * Seu nome deve ser iqual ao nome da tabela no banco de dados.
 * � utilizado nas itera��es do sistemana com o banco de daddos....
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
	String nome();
}