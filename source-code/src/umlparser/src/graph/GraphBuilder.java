package umlparser.src.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.plantuml.SourceStringReader;
import umlparser.src.constants.UConstants;
import umlparser.src.handler.AssociationHandler;
import umlparser.src.model.InstanceVariable;
import umlparser.src.model.MethodParameter;
/**
* @author Manthan H.
* @Description Class Diagram Generator
*/
public class GraphBuilder{
	UConstants uconstants = new UConstants();

	public String getVarStr(Map<String,List<InstanceVariable>> instaVarMap){
		String instVarStr = "";
		Set<String> classNamesSet = instaVarMap.keySet();
		List<InstanceVariable> instVarList;

		for(String className : instaVarMap.keySet()){
			instVarList = instaVarMap.get(className);
			for(InstanceVariable instVar : instVarList){				
				if(uconstants.isCollection(instVar.getDataType())){
					
					List<String> baseTypeList = uconstants.getBaseType(instVar.getDataType().toString());
					boolean stopSecondEntryFlag = false;
					for(String baseType : baseTypeList){						
						if(classNamesSet.contains(baseType)){	//Var is to be shown as association attribute
							instVarStr = AssociationHandler.handleDuplicateAssociation(instVarStr,className,instVar,baseType,true);																				
						}
						else{	//Var is to be shown as inline attribute
							if(!stopSecondEntryFlag){	//to avoid multiple entries for collections of type : key-value pair
	
								instVarStr += className + " : " + instVar.toString() + "\n";
								stopSecondEntryFlag = true;
							}					

						}
					}					
				}
				else{
					if(classNamesSet.contains(instVar.getDataType())){	//Var is to be shown as association attribute
						instVarStr = AssociationHandler.handleDuplicateAssociation(instVarStr,className,instVar,instVar.getDataType(),false);
					}
					else{	//Var is to be shown as inline attribute
						instVarStr += className + " : " + instVar.toString() + "\n";
					}
				}				
			}
			instVarStr += "hide "+ className + " circle"+"\n";
		}

		return instVarStr;
	}
	
		public String getUseDependencyStr(Map<String,List<MethodParameter>> methodParameterMap){
		String useDependencyStr = "";		
		List<MethodParameter> methodParamList;
		Set<String> classNamesSet = methodParameterMap.keySet();
		for(String className : classNamesSet){
			methodParamList = methodParameterMap.get(className);
			for(MethodParameter methodParam : methodParamList){
				if(uconstants.isCollection(methodParam.getDataType())){	// if parameter is a collection object
					List<String> baseTypeList = uconstants.getBaseType(methodParam.getDataType().toString());					
					for(String baseType : baseTypeList){
						if(classNamesSet.contains(baseType)){
							if(!useDependencyStr.contains(className + " ..> " + baseType+":uses"+"\n")){
								useDependencyStr += className + " ..> " + baseType+":uses"+"\n";
							}
						}					
					}
				}
				else{	//if parameter is a non-collection object
					if(classNamesSet.contains(methodParam.getDataType())){						
						if(!useDependencyStr.contains(className + " ..> " + methodParam.getDataType()+":uses"+"\n")){
							useDependencyStr += className + " ..> " + methodParam.getDataType()+":uses"+"\n";
						}
					}
				}

			}			
		}

		return useDependencyStr;
	}	
	
	public void buildClassDiagram(String source, File filePath, String fileName){
		
		String appender="@startuml\n";
		appender += "skinparam classAttributeIconSize 0 \n";
		source = appender + source;
		source += "@enduml\n";
		String imagePath;
		if("Windows".equalsIgnoreCase(System.getProperty("os.name"))){
			imagePath=filePath+"\\"+fileName+".png";
		}
		else{
			imagePath=filePath+"/"+fileName+".png";
		}
		try{
			OutputStream png = new FileOutputStream(imagePath);
			SourceStringReader reader = new SourceStringReader(source);
			reader.generateImage(png);
		}
		catch(FileNotFoundException fnfe){
			
		}
		catch(IOException ioe){
			
		}
		catch(Exception e){
			
		}
		
	}
}