package umlparser.src.model;

import umlparser.src.constants.UConstants;
/**
* @author Manthan H.
* @Description Model for parameters and local variables. To handle 'use' dependency.
*/
public class MethodParameter{
	String name;
	String dataType;	

	public MethodParameter(){
		this.name = "";
		this.dataType= "";
	}

	public MethodParameter(String name, String dataType){
		this.name = name;
		this.dataType = dataType;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setDataType(String dataType){
		this.dataType = dataType;
	}

	public String getName(){
		return name;		
	}

	public String getDataType(){
		return dataType;		
	}

	@Override
	public String toString(){
		return name + ":" + dataType;
	}

	public static String formatDataType(String dataType){
		UConstants uConstants = new UConstants();
		if(uConstants.isCollection(dataType)){
			String typeStr = "(";
			if(uConstants.getBaseType(dataType).size() > 1){
				for(String baseType : uConstants.getBaseType(dataType)){
					typeStr += baseType + ", ";
				}
				return typeStr.substring(0,typeStr.lastIndexOf(",")) + ")(*)";
			}
			else{
				return uConstants.getBaseType(dataType).get(0) + "(*)";
			}						
		}
		else{
			return dataType;
		}
	}

}