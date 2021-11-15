package com.egg.clubit.enumeraciones;

public enum EtiquetaEnum {
	
	OTROS(1,"Otros"),HTML(2,"HTML"),CSS(3,"CSS"),PYTHON(4,"Python"),
	JAVASCRIPT(5,"JavaScript"),PHP(6,"PHP"),MYSQL(7,"MySQL"),JAVA(8,"Java"),CPlus(9,"C++");
	
	private Integer codigo;
	private String valor;

	private EtiquetaEnum(Integer codigo, String valor){
		this.codigo = codigo;
		this.valor = valor;
	}

	public Integer getCodigo(){
		return codigo;
	}

	public String getValor(){
		return valor;
	}
}
