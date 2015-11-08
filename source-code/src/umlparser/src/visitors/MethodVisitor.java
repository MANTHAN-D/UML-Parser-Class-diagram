package umlparser.src.visitors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import umlparser.src.handler.AccessHandler;
import umlparser.src.model.MethodParameter;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
/**
* @author Manthan H.
* @Description Method Parser
*/
@SuppressWarnings("rawtypes")
public class MethodVisitor extends VoidVisitorAdapter{

	private MethodParameter methodParam;
	private List<String> methodStrList;
	private List<MethodParameter> methodParamList;

	public MethodVisitor(){
		methodStrList = new ArrayList<String>();
		methodParamList = new ArrayList<MethodParameter>();
	}
	
	/** Generates list of all methods */
	@Override
	public void visit(MethodDeclaration n, Object arg){

		String methodStr = "";
		boolean publicMethodFlag = false;

		if(AccessHandler.getAccessSpecifier(n.getModifiers(),true) == '+'){	// Condition to include only public methods in diagram
			if(ModifierSet.isStatic(n.getModifiers()))
				methodStr += "{static}";
			if(ModifierSet.isAbstract(n.getModifiers()))
				methodStr += "{abstract}";

			methodStr += AccessHandler.getAccessSpecifier(n.getModifiers(),true) +
					" " + n.getName()+ "(";

			publicMethodFlag = true;			 	
			if(n.getBody() != null)
				methodParamList.addAll(getClassUsesFromBody(n.getBody().toString()));
		}

		// System.out.println((ModifierSet.getAccessSpecifier(n.getModifiers())).getCodeRepresenation() +
		//  " <->" +n.getName()+ ":" + n.getType());

		List<Parameter> params = n.getParameters();
		Iterator<Parameter> paramsIt = params.iterator();
		Parameter param;
		while(paramsIt.hasNext()){
			param = paramsIt.next();
			if(publicMethodFlag){
				methodStr += param.getId() + ":" + MethodParameter.formatDataType(param.getType().toString()) + ",";
			}				

			methodParam = new MethodParameter(param.getId().toString(),param.getType().toString());
			methodParamList.add(methodParam);
			// System.out.println("Method Param : " + param.getType() + " " + param.getId());
		}

		if(publicMethodFlag){
			if(methodStr.lastIndexOf(",") > 0){			
				methodStr = methodStr.substring(0,methodStr.lastIndexOf(","));
				methodStr += "):" + MethodParameter.formatDataType(n.getType().toString());
			}
			else{				
				methodStr+="):" + MethodParameter.formatDataType(n.getType().toString());
			}

			methodStrList.add(methodStr);
		}			
	} 

	/** Returns list of all methods */
	public List<String> getMethodList(){
		return methodStrList;
	}

	/** Returns list of all method parameters */
	public List<MethodParameter> getMethodParamList(){
		return methodParamList;
	}

	/** Returns list of all classes used inside method body */
	public List<MethodParameter> getClassUsesFromBody(String methodBody){
		String pattern = "[a-zA-Z_$][a-zA-Z0-9_$]*(\\[\\])?\\s(([a-zA-Z_$][a-zA-Z0-9_$]*)(\\,)?)+(=|\\s=|;)";
		String statement;
		boolean assignmentFlag;
		MethodParameter methodLocalParam;
		List<MethodParameter> methodLocalParamList = new ArrayList<>();			
		int startPosition = 1,endPosition;						
		do{
			endPosition = methodBody.indexOf(";",startPosition+1)+1;
			if(endPosition > startPosition){
				assignmentFlag = false;					
				statement = methodBody.substring(startPosition,endPosition);
				if(statement!= null && statement.length() > 2){
					if(statement.contains("=")){
						statement = methodBody.substring(startPosition,methodBody.indexOf("=",startPosition+1)+1);
						assignmentFlag = true;
					}
					statement = statement.trim();
					if(Pattern.matches(pattern,statement)){							
						if(assignmentFlag){
							methodLocalParam = new MethodParameter(statement.substring(statement.indexOf(" ")+1,statement.indexOf("=")).trim(),
									statement.substring(0,statement.indexOf(" ")));
						}
						else{
							methodLocalParam = new MethodParameter(statement.substring(statement.indexOf(" ")+1,statement.indexOf(";")).trim(),
									statement.substring(0,statement.indexOf(" ")));
						}							
						methodLocalParamList.add(methodLocalParam);
					}					
				}
				startPosition = endPosition + 1;					
			}

			else{
				break;
			}								
		}while(startPosition >= 0 && startPosition < methodBody.length() - 1);

		return methodLocalParamList;
	}

}