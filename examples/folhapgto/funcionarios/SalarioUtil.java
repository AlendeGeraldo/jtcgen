package folhapgto.funcionarios;

import br.com.jtcgen.annotations.JTCGen;
import br.com.jtcgen.annotations.Test;

@JTCGen
public class SalarioUtil {
	
	@Test("setup([]).parameter([3000.0, 0.05]).eq(3150.0)")
	public double calculaDissidio(double salario, double porcentagem) {

		
		return salario + (salario * porcentagem);
	}
	
}
