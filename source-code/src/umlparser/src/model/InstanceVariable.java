package umlparser.src.model;

import umlparser.src.constants.UConstants;
/**
* @author Manthan H.
* @Description Model for instance variables
*/
public class InstanceVariable{
	String name;
	String dataType;
	char accessType;

	public InstanceVariable(){
		this.name = "";
		this.dataType= "";
		this.accessType=' ';
	}

	public InstanceVariable(char accessType, String name, String dataType){
		this.accessType = accessType;
		this.name = name;
		this.dataType = dataType;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setDataType(String dataType){
		this.dataType = dataType;
	}

	public void setAccessType(char accessType){
		this.accessType = accessType;
	}

	public String getName(){
		return name;		
	}

	public String getDataType(){
		return dataType;		
	}

	public char getAccessType(){
		return accessType;		
	}

	@Override
	public String toString(){
		UConstants uConstants = new UConstants();
		if(uConstants.isCollection(dataType)){
			String typeStr = "(";
			if(uConstants.getBaseType(dataType).size() > 1){
				for(String baseType : uConstants.getBaseType(dataType)){
					typeStr += baseType + ", ";
				}
				return accessType + "" + name + ":" + typeStr.substring(0,typeStr.lastIndexOf(",")) + ")(*)";
			}
			else{
				return accessType + "" + name + ":" + uConstants.getBaseType(dataType).get(0) + "(*)";
			}						
		}
		else{
			return accessType + "" + name + ":" + dataType;
		}		
	}

	public String getNameInFirstCap(){
		return (""+name.charAt(0)).toUpperCase() + name.substring(1,name.length());
	}

}