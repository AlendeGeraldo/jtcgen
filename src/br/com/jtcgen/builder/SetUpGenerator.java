package br.com.jtcgen.builder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import br.com.jtcgen.annotations.Param;
import br.com.jtcgen.annotations.SetUp;

public class SetUpGenerator extends TestGenerator {

	private StringBuffer buffer;
	protected List<Constructor<?>> cons;

	public SetUpGenerator(Class<?> clazz) {
		super(clazz);
		cons = new ArrayList<Constructor<?>>();
		for (Constructor<?> c : this.clazz.getConstructors())
			this.cons.add(c);
	}

	public String generate() {
		StringBuffer sb = new StringBuffer("\t" + "@Before");
		sb.append("\n");
		sb.append("\t" + "public void setUp() throws Exception {");
		sb.append("\n");
		StringBuffer assinaturaDoConstrutor = new StringBuffer();
		for (Constructor<?> co : cons) {
			if (co.isAnnotationPresent(SetUp.class)) {
				SetUp st = (SetUp) co.getAnnotation(SetUp.class);

				Param parametros = st.value();

				String[] params = parametros.value().split(";");

				Parameter[] pts = co.getParameters();

				if (params.length != co.getParameterCount())
					throw new InvalidParamDeclarationExeption("Valor total de parametros incorretos");

				int count = 0;
				assinaturaDoConstrutor.append("this.instance = new " + clazz.getSimpleName() + "(");
				for (Parameter p : pts) {
					Class<?> type = p.getType().getClass();

					String param;
					if (type == String.class) {
						param = '"' + params[count] + '"';
					} else {
						param = params[count];
					}

					if (count == params.length - 1)
						assinaturaDoConstrutor.append(param);
					else
						assinaturaDoConstrutor.append(param + ",");
					count++;
				}

				assinaturaDoConstrutor.append(");");
				break;
			}
		}

		sb.append("\t\t" + assinaturaDoConstrutor.toString());
		sb.append("\n");
		sb.append("\t" + "}");

		return sb.toString();
	}

}
