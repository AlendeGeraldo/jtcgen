package br.com.jtcgen;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.List;

import org.junit.Before;

import br.com.jtcgen.generator.annotations.Param;
import br.com.jtcgen.generator.annotations.SetUp;

public class SetUpGen {

	private StringBuffer buffer;
	protected List<Constructor<?>> cons;

	public SetUpGen(Constructor<?>... cons) {
		for (Constructor<?> c : cons)
			this.cons.add(c);
	}

	private String generate() {
		StringBuffer assinaturaDoConstrutor = new StringBuffer();
		for (Constructor co : cons) {
			if (co.isAnnotationPresent(SetUp.class)) {
				SetUp st = (SetUp) co.getAnnotation(SetUp.class);

				Param parametros = st.value();

				String[] params = parametros.value().split(";");

				Parameter[] pts = co.getParameters();

				if (params.length != co.getParameterCount())
					throw new InvalidParamDeclarationExeption("Valor total de parametros incorretos");

				int count = 0;
				assinaturaDoConstrutor.append("this.instance = new " + co.getName() + "(");
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
			}
		}

		return assinaturaDoConstrutor.toString();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("\t" + "@Before");
		sb.append("\n");
		sb.append("\t" + "public void setUp() throws Exception {");
		sb.append("\n");
		sb.append("\t\t" + this.generate());
		sb.append("\n");
		sb.append("\t" + "}");

		return sb.toString();
	}

}
