package br.com.ueg.pids.Annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * Anota��o responsavel por identificar um campo em uma entidade do tipo model.
 * Seu nome deve ser iqual ao nome do campo da tabela no banco de dados.
 * � utilizado nas itera��es do sistemana com o banco de daddos....
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Campo {
	String nome();
	boolean pk() default false;
	boolean obrigatorio() default false;
}