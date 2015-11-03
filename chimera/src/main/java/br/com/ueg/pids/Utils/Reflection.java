package br.com.ueg.pids.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

import br.com.ueg.pids.Annotations.Campo;
import br.com.ueg.pids.Annotations.Table;
import br.com.ueg.pids.Converter.Converter;
import br.com.ueg.pids.Model.IModel;


public class Reflection {
	@SuppressWarnings("rawtypes")
	public static void printClassInfo(Class cls){
		try {
			
			Field[] fld = cls.getDeclaredFields();
			
			for(int i = 0; i < fld.length; i++){
				System.out.println("Field: "+fld[i].getName());
				Campo cmp = fld[i].getAnnotation(Campo.class);
				
				if(cmp!=null){
					System.out.println("Anotation #Nome:" + cmp.nome());
					System.out.println("Anotation #pk:" + cmp.pk());
				}
				System.out.println("teste");
			}
			System.out.println("");
			//Class cls = Class.forName("aula01.Ex3Classe");
			Method methlist[] = cls.getDeclaredMethods();
			
			for (int i = 0; i < methlist.length; i++) {
				Method m = methlist[i];
				System.out.println("nome = " + m.getName());
				System.out.println("membro da classe = " + m.getDeclaringClass());
				System.out.println("modificador = " + Modifier.toString( m.getModifiers() ));
				Class pvec[] = m.getParameterTypes();
				
				Campo cmp = m.getAnnotation(Campo.class);
				if(cmp!=null){
					System.out.println("Anotation #Nome:" + cmp.nome());
					System.out.println("Anotation #pk:" + cmp.pk());
				}
	
				for (int j = 0; j < pvec.length; j++){				
					System.out.println("par�metro #" + j + " " + pvec[j]);
				}
	
				Class evec[] = m.getExceptionTypes();
				for (int j = 0; j < evec.length; j++)
					System.out.println("exce��o #" + j + " " + evec[j]);
	
				System.out.println("tipo de retorno = " + m.getReturnType());
				System.out.println("-----");
			}
		}
		catch (Throwable e) {
			System.err.println(e);
		}	
	}
	
	public static Object getPkValue(IModel<?> iModel){
		String pkName = Reflection.getPkName(iModel);
		Object retorno = Reflection.getFieldValue(iModel, pkName);
		if(retorno ==null){
			throw new RuntimeException("Classe n�o foi anotada com informa��es para obter pk(campo)! ");
		}
		return retorno;
	}
	public static Object getFieldValue(IModel<?> imodel, String fieldName){
		Object pkValue = null;
		try {
			Class<?> cls = imodel.getClass();	
			Field[] fld = cls.getDeclaredFields();
			
			for(int i = 0; i < fld.length; i++){
				//System.out.println("Field: "+fld[i].getName());
				Campo cmp = fld[i].getAnnotation(Campo.class);
				
				if(cmp!=null && fld[i].getName().equalsIgnoreCase(fieldName)){
					String auxName = "get"+Utils.firstToUpperCase(fld[i].getName());
					
					Method metodo = Reflection.findMethod(cls, IModel.class, auxName, null);
					
					pkValue = metodo.invoke(imodel, null);
					break;
				}				
			}
		}catch (Throwable e) {
			System.err.println(e);
			pkValue = null;
		}
			
		if(pkValue==null){
			new RuntimeException("Classe n�o foi anotada com informa��es para obter pk(campo)! ");
		}
		return pkValue;
	}
	
	public static String getPkName(IModel<?> imodel){
		return Reflection.getPkNameOrFieldName(imodel, false);
	}
	public static String getPkFieldName(IModel<?> imodel){
		return Reflection.getPkNameOrFieldName(imodel, true);
	}
	/**
	 * @param imodel
	 * @param fieldName se true retorna o nome do field, se false retorno o nome da pk
	 * @return
	 */
	private static String getPkNameOrFieldName(IModel<?> imodel,boolean fieldName){
		String nomeRetorno = null;
		try {
			Class<?> cls = imodel.getClass();	
			Field[] fld = cls.getDeclaredFields();
			
			for(int i = 0; i < fld.length; i++){
				//System.out.println("Field: "+fld[i].getName());
				Campo cmp = fld[i].getAnnotation(Campo.class);
				
				if(cmp!=null && cmp.pk()){
					if(fieldName){
						nomeRetorno = fld[i].getName();
					}else{
						nomeRetorno = cmp.nome();
					}
					break;
				}				
			}
		}catch (Throwable e) {
			System.err.println(e);
			nomeRetorno = null;
		}
			
		if(nomeRetorno==null){
			new RuntimeException("Classe n�o foi anotada com informa��es para obter pk(campo)! ");
		}
		return nomeRetorno;
	}
	
	public static String getFiendNameByColumName(IModel<?> imodel,String fieldName){
		String nomeRetorno = null;
		try {
			Class<?> cls = imodel.getClass();	
			Field[] fld = cls.getDeclaredFields();
			
			for(int i = 0; i < fld.length; i++){
				//System.out.println("Field: "+fld[i].getName());
				Campo cmp = fld[i].getAnnotation(Campo.class);
				
				if(cmp!=null && cmp.nome().equalsIgnoreCase(fieldName)){
						nomeRetorno = fld[i].getName();
					break;
				}				
			}
		}catch (Throwable e) {
			System.err.println(e);
			nomeRetorno = null;
		}
			
		if(nomeRetorno==null){
			new RuntimeException("Classe n�o foi anotada com informa��es para obter pk(campo)! ");
		}
		return nomeRetorno;
	}
	
	
	public static void setPkValue(IModel<?> table, Object value){
		String pkName = Reflection.getPkName(table);
		Reflection.setFieldValue(table, pkName, value);		
	}
	public static void setFieldValue(IModel<?> table, String fieldName, Object value){
		Reflection.setFieldValue(table, fieldName, value, false);
	}
	public static void setFieldValueByDatabaseName(IModel<?> table, String fieldName, Object value){
		Reflection.setFieldValue(table, fieldName, value, true);
	}
	private static void setFieldValue(IModel<?> table, String fieldName, Object value, boolean fieldNameIsDatabase){
		
		try {
			Class<?> cls = table.getClass();	
			Field[] fld = cls.getDeclaredFields();
			
			for(int i = 0; i < fld.length; i++){
				//System.out.println("Field: "+fld[i].getName());
				Campo cmp = fld[i].getAnnotation(Campo.class);
				
				if(cmp!=null && ( 
							(fld[i].getName().equalsIgnoreCase(fieldName) && !fieldNameIsDatabase) ||// para atributo entidade
							(cmp.nome().equalsIgnoreCase(fieldName) && fieldNameIsDatabase) ) ){//para atributo nome campo
					String auxName = "set"+Utils.firstToUpperCase(fld[i].getName());
					
					Method metodo = Reflection.findMethod(cls, Table.class, auxName, fld[i].getType());
					if(		!fld[i].getType().getSimpleName().equalsIgnoreCase("string")
							//&&	!fld[i].getType().getSimpleName().equalsIgnoreCase("date")
							){
						String converter = "converter."+fld[i].getType().getSimpleName();
						Class<?> clsConveter = Class.forName(converter); 
						Converter conv = (Converter) clsConveter.newInstance();
						//if(!fieldNameIsDatabase){
							value = conv.parserValue(String.valueOf(value));
						//}else{
							//value = conv.parserValueScreenValue((String) value);
						//}
					}
					if(!fld[i].getType().isAssignableFrom(value.getClass()) && !fieldNameIsDatabase){
						String converter = "converter."+fld[i].getType().getSimpleName();
						Class<?> clsConveter = Class.forName(converter); 
						Converter conv = (Converter) clsConveter.newInstance();
						value = conv.parserValueScreenValue((String) value);
					}
					metodo.invoke(table, value);
					break;
				}				
			}
		}catch (Throwable e) {
			System.err.println(e);
			
		}
			
		
	}
	
	public static String getTableName(IModel<?> table){
		String tableName = null;
		Class<?> cls = table.getClass();
		tableName = cls.getSimpleName().toLowerCase();
		try {
			
			
			Table tableAnotation = cls.getAnnotation(Table.class);				
				if(tableAnotation!=null){
					tableName = tableAnotation.nome();
				}				
			
		}catch (Throwable e) {
			System.err.println(e);
			tableName = null;
		}
		
		//caso n�o queira assumir o nome da classe
		//como padr�o descomentar o c�digo abaixo
		/*if(tableName==null){
			throw new RuntimeException("Classe n�o foi anotada com informa��es para Nome da tabela(campo)! ");		
		}*/
		return tableName;
	}
	
	/**
	 * M�todo respons�vel por procurar a declara��o do m�todo na sua
	 * classe ou superclasse.
	 * Ir� puxar as superclasse da classe principal enquanto ela herdar
	 * a superclasse passada como par�metro
	 * 
	 * @param clazz classe do m�todo
	 * @param superclass interface ou superclasse limite que deve ser procurado o m�todo
	 * @param methodName nome do m�todo
	 * @return Method encontrado
	 */
	private static Method findMethod(Class<?> clazz, Class<?> superclass, String methodName, Class<?>... parameterTypes) {
		try {
			return clazz.getDeclaredMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e) {
			if (superclass.isAssignableFrom(clazz.getSuperclass())) {
				return findMethod(clazz.getSuperclass(), superclass, methodName, parameterTypes);
			} else {
				throw new RuntimeException("Unable to find method ".concat(methodName), e);
			}
		}
	}
	
	
	/**
	 * M�todo respons�vel por procurar a declara��o do m�todo na sua
	 * classe ou superclasse.
	 * 
	 * @param clazz classe do m�todo
	 * @param methodName nome do m�todo
	 * @return Method encontrado
	 */
	public static Method findMethod(Class<?> clasz, String methodName){
		return Reflection.findMethod(clasz, clasz.getSuperclass(),methodName);
	}
	public static Class<?> getGenericParameterType(Class<?> classe, int indice){
		 return (Class<?>)
	        ((ParameterizedType)
	            classe
	                .getGenericSuperclass())
	                    .getActualTypeArguments()[indice];
	}
	
	/**
	 * Metodo utilizado para invocar o metodod e a��o, ele recebe o nome da a��o ex: edit
	 * e infoca o metodo actionEdit
	 * @param object - Objecto que se deseja invocar o metodo
	 * @param action - nome da acao que sera invocada seguindo o padrao actionNOMEACAO
	 * @return
	 */
	
	/**
	 * metodo retorno o Metaobjeto Field do atributo se ele for do tipo Table ou null caso contr�rio
	 * @param table
	 * @param fieldName
	 * @return
	 */
	public static Field getTableFieldFromFieldName(IModel<?> table, String fieldName){
		Field foreingField = null;
		try {
			Class<?> cls = table.getClass();	
			Field[] fld = cls.getDeclaredFields();
			
			for(int i = 0; i < fld.length; i++){
				//System.out.println("Field: "+fld[i].getName());
				Campo cmp = fld[i].getAnnotation(Campo.class);
				
				if(cmp!=null && fld[i].getName().equalsIgnoreCase(fieldName) && Table.class.isAssignableFrom(fld[i].getType())){
						foreingField = fld[i];
					break;
				}				
			}
		}catch (Throwable e) {
			System.err.println(e);
		}
		return foreingField;
	}
	
	/**
	 * @param table Objeto para buscar os dados.
	 * @param fieldName nome do campo do objeto
	 * @param hist utilizado para historico da hieraquia subida.
	 * @return
	 */
	public static HashMap<String, Object> getRelationTableFields(IModel<?> table, String fieldName, Object value, String hist ){
		HashMap<String,Object> record = new HashMap<String, Object>();
		IModel<?> foreingTable = null;
		try {
			Class<?> cls = table.getClass();	
			Field[] fld = cls.getDeclaredFields();
			
			for(int i = 0; i < fld.length; i++){
				//System.out.println("Field: "+fld[i].getName());
				Campo cmp = fld[i].getAnnotation(Campo.class);
				
				if(cmp!=null && cmp.nome().equalsIgnoreCase(fieldName) && IModel.class.isAssignableFrom(fld[i].getType())){
						foreingTable = (IModel<?>) fld[i].getType().newInstance();
					break;
				}				
			}
		}catch (Throwable e) {
			System.err.println(e);
		}
		
		if(foreingTable!=null){
			
			Class<?> cls = foreingTable.getClass();
			Field[] fld = cls.getDeclaredFields();
			
			for(int i=0;i< fld.length; i++){
				Campo cmp = fld[i].getAnnotation(Campo.class);
				if(cmp!=null){
					String auxValue = String.valueOf(Reflection.getFieldValue(table, fld[i].getName()));
					record.put(foreingTable.getClass().getSimpleName().toLowerCase()+"."+cmp.nome(),auxValue);
				}
					
			}
		}
		
		return record;
	}
}