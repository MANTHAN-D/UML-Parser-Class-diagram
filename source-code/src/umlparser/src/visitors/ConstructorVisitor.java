package umlparser.src.visitors;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import umlparser.src.handler.AccessHandler;
import umlparser.src.model.MethodParameter;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
/**
* @author Manthan H.
* @Description Constructor Parser
*/
@SuppressWarnings("rawtypes")
public class ConstructorVisitor extends VoidVisitorAdapter{

	private MethodParameter constructorParam;
	private List<MethodParameter> constructorParamList;
	private List<String> construcStrList;

	public ConstructorVisitor(){
		constructorParamList = new ArrayList<MethodParameter>();
		construcStrList = new ArrayList<String>();
	}
	
	/** Generates list of all defined constructors */
	@Override	
	public void visit(ConstructorDeclaration n, Object arg){		
		String construcStr="";

		construcStr +=  AccessHandler.getAccessSpecifier(n.getModifiers(),true) +
				" " + n.getName() + "(";

		// System.out.println((ModifierSet.getAccessSpecifier(n.getModifiers())).getCodeRepresenation() +
		//  " <-> " + n.getName() + " <-> ");
		List<Parameter> params = n.getParameters();
		Iterator<Parameter> paramsIt = params.iterator();
		Parameter param;
		while(paramsIt.hasNext()){
			param = paramsIt.next();
			construcStr += param.getId() + ":" + MethodParameter.formatDataType(param.getType().toString()) + ",";

			constructorParam = new MethodParameter(param.getId().toString(),param.getType().toString());
			constructorParamList.add(constructorParam);
			// System.out.println("Constructor Param : " + param.getType() + " " + param.getId());
		}

		if(construcStr.lastIndexOf(",") > 0){			
			construcStr = construcStr.substring(0,construcStr.lastIndexOf(","));
			construcStr += ")";
		}
		else{
			construcStr+= ")";
		}

		construcStrList.add(construcStr);
	}

	/** Returns list of all constructors */
	public List<String> getConstructorList(){			
		return construcStrList;
	}
	/** Returns list of all constructors */
	public List<MethodParameter> getConstructorParamList(){			
		return constructorParamList;
	}

}