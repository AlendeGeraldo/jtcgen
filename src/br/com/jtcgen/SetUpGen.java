package br.com.jtcgen;

import java.lang.reflect.Constructor;
import java.util.List;

public class SetUpGen {

	private StringBuffer buffer;
	protected List<Constructor<?>> cons;

	public SetUpGen(Constructor<?>... cons) {
		for (Constructor<?> c : cons)
			this.cons.add(c);
	}

	public String generate() {

		for (Constructor co : cons) {

		}

		return null;
	}

}
