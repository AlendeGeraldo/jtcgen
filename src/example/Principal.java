package example;

import java.util.HashMap;
import java.util.Map;

import javax.el.ValueExpression;

import org.apache.el.ExpressionFactoryImpl;
import org.apache.el.lang.EvaluationContext;

import static br.com.jtcgen.el.JTCGenEL.*;

public class Principal {
	public static void main(String[] args) {
		Map<String, Object> mapa = new HashMap<>();
		mapa.put("a", 23);
		mapa.put("b",10);
		String expr = "${a+duplicar(b)}";
		EvaluationContext ec = criarContexto(Principal.class, mapa);
		ValueExpression result = new ExpressionFactoryImpl()
		.createValueExpression(ec, expr, int.class);
		System.out.println(result.getValue(ec));
	}

	public static int duplicar(int i){
		return 2*i;
	}
}