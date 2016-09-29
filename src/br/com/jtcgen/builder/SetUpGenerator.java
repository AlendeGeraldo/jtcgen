package br.com.jtcgen.builder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import br.com.jtcgen.annotations.SetUp;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.helpers.TextEditor;

class SetUpGenerator extends TestGenerator {

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
		if (cons.size() == 1) {
			if (!cons.get(0).isAnnotationPresent(SetUp.class))
				if(cons.get(0).getParameterCount() == 0)
			assinaturaDoConstrutor.append(
				TextEditor.newLine("this.instance = new " + clazz.getSimpleName() + "();", 3)
			);
			
		}
		for (Constructor<?> co : cons) {
			if (co.isAnnotationPresent(SetUp.class)) {
				SetUp st = (SetUp) co.getAnnotation(SetUp.class);

				String[] params = st.value();

				Parameter[] pts = co.getParameters();

				if (params.length != co.getParameterCount())
					throw new InvalidParamDeclarationException("Valor total de parametros incorretos");

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
		sb.append("\n");

		return sb.toString();
	}

}
