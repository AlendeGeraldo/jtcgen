package br.com.jtcgen.el;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import javax.el.ArrayELResolver;
import javax.el.BeanELResolver;
import javax.el.CompositeELResolver;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.ListELResolver;
import javax.el.MapELResolver;
import javax.el.VariableMapper;

import org.apache.el.ValueExpressionLiteral;
import org.apache.el.lang.EvaluationContext;
import org.apache.el.lang.FunctionMapperImpl;
import org.apache.el.lang.VariableMapperImpl;

public class JTCGenEL extends ELContext {

	private FunctionMapper functionMapper;
	private VariableMapper variableMapper;
	private CompositeELResolver elResolver;

	public JTCGenEL(FunctionMapper fm, VariableMapper vm, ELResolver... elResolvers) {
		this.functionMapper = fm;
		this.variableMapper = vm;
		this.elResolver = new CompositeELResolver();
		for (ELResolver e : elResolvers)
			this.elResolver.add(e);
	}

	@Override
	public ELResolver getELResolver() {
		return this.elResolver;
	}

	@Override
	public FunctionMapper getFunctionMapper() {
		return this.functionMapper;
	}

	@Override
	public VariableMapper getVariableMapper() {
		return this.variableMapper;
	}

	public static VariableMapper mapearVariaveis(Map<String, Object> vars) {
		VariableMapper mapper = new VariableMapperImpl();
		for (String attributeName : vars.keySet()) {
			if (vars.get(attributeName) != null) {
				Class<?> clazz = vars.get(attributeName).getClass();
				ValueExpressionLiteral expr = new ValueExpressionLiteral(vars.get(attributeName), clazz);
				mapper.setVariable(attributeName, expr);
			}
		}

		return mapper;
	}

	public static FunctionMapper mapearFuncoes(Class<?> clazz) {
		FunctionMapper mapper = new FunctionMapperImpl();
		for (Method m : clazz.getMethods()) {
			if (Modifier.isStatic(m.getModifiers()))
				mapper.mapFunction("", m.getName(), m);
		}

		return mapper;
	}

	public static EvaluationContext criarContexto(Class<?> functionClass, Map<String, Object> attributeMap) {
		VariableMapper vMapper = mapearVariaveis(attributeMap);
		FunctionMapper fMapper = mapearFuncoes(functionClass);
		JTCGenEL context = new JTCGenEL(fMapper, vMapper, new ArrayELResolver(), new ListELResolver(),
				new MapELResolver(), new BeanELResolver());

		return new EvaluationContext(context, fMapper, vMapper);
	}

}