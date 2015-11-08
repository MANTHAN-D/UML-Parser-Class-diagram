package umlparser.src.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import umlparser.src.constants.UConstants;
import umlparser.src.graph.GraphBuilder;
import umlparser.src.model.InstanceVariable;
import umlparser.src.model.MethodParameter;
import umlparser.src.visitors.ClassVisitor;
import umlparser.src.visitors.ConstructorVisitor;
import umlparser.src.visitors.MethodVisitor;
import umlparser.src.visitors.VariableVisitor;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;

/**
 * @author Manthan H.
 * @Description Main Parser
 */
public class Parser{

	UConstants uconstants = new UConstants();
	private static String os;
	private String extendStr;
	private String implementStr;
	private String ballNSocketStr;
	private static HashMap<String,List<InstanceVariable>> instaVarMap;
	private static HashMap<String,List<MethodParameter>> methodParameterMap;

	public Parser(){
		os=System.getProperty("os.name");;
		extendStr="";
		implementStr="";
		ballNSocketStr="";
		instaVarMap = new HashMap<String,List<InstanceVariable>>();
		methodParameterMap = new HashMap<String,List<MethodParameter>>();

	}
	public static void main(String[] args){

		Parser parser = new Parser();
		GraphBuilder graphBuilder = new GraphBuilder();
		String source="";
		
		boolean javaFilesFoundFlag = false;

		if(args.length == 2){

			File dir = new File(args[0]);
			String[] files = dir.list();
			String basePath = dir.getAbsolutePath();
			if(files.length > 0){				
				System.out.println("Java file parsing in progress...");
				for(int i=0; i< files.length;i++)
				{
					if(files[i].endsWith(".java")){
						if("Windows".equalsIgnoreCase(os)){
							source += parser.parseFile(basePath+"\\"+files[i],false);
						}
						else{
							source += parser.parseFile(basePath+"/"+files[i],false);
						}
						javaFilesFoundFlag = true;
					}			
				}
				if(javaFilesFoundFlag){					
					
					source += graphBuilder.getVarStr(instaVarMap);
					source += graphBuilder.getUseDependencyStr(methodParameterMap);			

					System.out.println("Java file parsing done");
					System.out.println("Class diagram generation in progress...");					
					graphBuilder.buildClassDiagram(source, dir, args[1]);
					System.out.println("Class diagram generated at following path :");
					System.out.println("Directory : " + dir);
					System.out.println("Image name : " + args[1]);
				}
				else{
					System.out.println("No java files found at given path !");
				}
				
			}
			else{
				System.out.println("Empty input directory !");
			}
		}
		else{
			System.out.println("Incorrect number of inputs !!!");
		}		
	}	

	@SuppressWarnings("unchecked")
	public String parseFile(String name,boolean ballNSocketFlag){

		FileInputStream in;
		CompilationUnit cu;
		String classNameStr;
		String methodString;		

		try{
			in =  new FileInputStream(name);

			try{
				cu = JavaParser.parse(in);
			}finally{
				in.close();
			}

			ClassVisitor classVisitor = new ClassVisitor();
			classVisitor.visit(cu,null);
			classNameStr = classVisitor.getClassNameStr();
			extendStr= classVisitor.getExtendStr();
			implementStr= classVisitor.getImplementStr();
			ballNSocketStr = classVisitor.getBallNSocketStr();

			VariableVisitor variableVisitor = new VariableVisitor();
			variableVisitor.visit(cu,null);
			instaVarMap.put(classNameStr.substring(0,classNameStr.indexOf(" ")),
					variableVisitor.getVarList());

			ConstructorVisitor constructorVisitor = new ConstructorVisitor();
			constructorVisitor.visit(cu,null);
			methodString =  getConstructorStr(classNameStr,constructorVisitor.getConstructorList());		

			MethodVisitor methodVisitor = new MethodVisitor();
			methodVisitor.visit(cu,null);
			methodString += getMethodStr(classNameStr,methodVisitor.getMethodList());


			constructorVisitor.getConstructorParamList().addAll(methodVisitor.getMethodParamList());
			methodParameterMap.put(classNameStr.substring(0,classNameStr.indexOf(" ")),constructorVisitor.getConstructorParamList());

			if(ballNSocketFlag)
				return extendStr + ballNSocketStr + methodString;
			else
				return extendStr + implementStr + methodString;

		}catch(FileNotFoundException fnfe){
			System.out.println("Corrupted source files !");
		}catch (ParseException pe) {
			System.out.println("Java files - compilation errors !");
		}catch (IOException ioe) {
			// do nothing
		}	
		return "";
	}

	private String getConstructorStr(String classNameStr, List<String> construcStrList){
		String construcStr = "";

		for(String constructor : construcStrList){
			construcStr += classNameStr + constructor + "\n";
		}

		return construcStr;
	}

	private String getMethodStr(String classNameStr, List<String> methodStrList){
		String methodStr = "";
		Set<String> methodNameSet = new HashSet<String>();
		for(String method : methodStrList){
			methodNameSet.add(method.substring(method.indexOf(" ")+1,method.indexOf("(")));
		}

		for(InstanceVariable instaVar : instaVarMap.get(classNameStr.substring(0,classNameStr.indexOf(" :")))){

			if(methodNameSet.contains("get"+instaVar.getNameInFirstCap()) 
					&& methodNameSet.contains("set"+instaVar.getNameInFirstCap())){
				instaVar.setAccessType('+');	// Make variable as public
				methodNameSet.remove("get"+instaVar.getNameInFirstCap()); // Remove corresponding getter method
				methodNameSet.remove("set"+instaVar.getNameInFirstCap()); // Remove corresponding setter method
			}

		}

		for(String method : methodStrList){
			if(methodNameSet.contains(method.substring(method.indexOf(" ")+1,method.indexOf("(")))){
				methodStr += classNameStr + method + "\n";
			}			
		}

		return methodStr;
	}	
}