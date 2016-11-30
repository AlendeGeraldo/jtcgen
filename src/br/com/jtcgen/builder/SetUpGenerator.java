package br.com.jtcgen.builder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import br.com.jtcgen.annotations.SetUp;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.helpers.TextEditor;

class SetUpGenerator extends TestGenerator {

	protected List<Constructor<?>> constructors;

	public SetUpGenerator(Class<?> clazz) {
		super(clazz);
		constructors = new ArrayList<Constructor<?>>();
		for (Constructor<?> c : this.clazz.getConstructors())
			this.constructors.add(c);
	}
	
	@Override
	public String generate() {
		StringBuffer sb = new StringBuffer("\t" + "@Before");
		sb.append("\n");
		sb.append("\t" + "public void setUp() throws Exception {");
		sb.append("\n");
		StringBuffer assinaturaDoConstrutor = new StringBuffer();
		if (constructors.size() == 1) {
			if (!constructors.get(0).isAnnotationPresent(SetUp.class))
				if(constructors.get(0).getParameterCount() == 0)
			assinaturaDoConstrutor.append(
				TextEditor.newLine("this.instance = new " + clazz.getSimpleName() + "();", 3)
			);
			
		}
		for (Constructor<?> co : constructors) {
			if (co.isAnnotationPresent(SetUp.class)) {
				SetUp st = (SetUp) co.getAnnotation(SetUp.class);

				String[] params = st.value();

				Parameter[] pts = co.getParameters();
				
				if (params.length != co.getParameterCount())
					throw new InvalidParamDeclarationException(
						"Total de parametros incorretos na annotation SetUp, na classe: " + clazz.getSimpleName()
					);

				int count = 0;
				assinaturaDoConstrutor.append("this.instance = new " + clazz.getSimpleName() + "(");
				for (Parameter p : pts) {
					Class<?> type = p.getType();

					String param;
					if (type== String.class) {
						param = '"' + params[count] + '"';
					} else if (type== char.class) {
						param = '\'' + params[count] + '\'';
					} else if (type == double.class || type == float.class) {
						param = params[count];
						
						if(!params[count].matches("^[0-9]+\\.[0-9]+$")) {
							if(params[count].matches("^[0-9]+$")) {
								param = params[count] + ".0";
							} else {
								throw new InvalidParamDeclarationException(
									"Tipo de parâmetro inválido na annotation SetUp da classe " + clazz.getSimpleName() + ". Valor inválido: " + params[count]
								);
							}
						}
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
