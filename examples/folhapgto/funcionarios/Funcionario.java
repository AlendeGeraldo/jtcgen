package folhapgto.funcionarios;

import br.com.jtcgen.annotations.JTCGen;
import br.com.jtcgen.annotations.SetUp;
import br.com.jtcgen.annotations.Test;
import br.com.jtcgen.annotations.TestEquals;
import br.com.jtcgen.annotations.TestFalse;
import br.com.jtcgen.annotations.TestNotNull;
import br.com.jtcgen.annotations.TestTrue;
import br.com.jtcgen.annotations.TestVoidEquals;

@JTCGen
public class Funcionario {
	
	protected String nome;
	protected double salarioBase;
	protected TipoContratacao tipoContrato;
	
	public Funcionario(){
		super();
		this.nome = "";
		this.salarioBase = 0.0;
		this.tipoContrato = null;
	}
	
	public Funcionario(String nome, double salarioBase, TipoContratacao tipoContrato) {
		super();
		this.nome = nome;
		this.salarioBase = salarioBase;
		this.tipoContrato = tipoContrato;
	}
	
	public Funcionario(String nome, double salarioBase) {
		super();
		this.nome = nome;
		this.salarioBase = salarioBase;
		this.tipoContrato = new TipoContratacao("CPS", "Prestador de Servico");
	}
	
	@Test("setup(['Jose', 3000.0]).parameter([140.0]).isTrue()")
	public boolean salarioExcedenteVT(double valeTransporte) {
		return valeTransporte < (salarioBase * 0.06);		
	}
	
	@Test("setup(['Jose', 3000.0, {c:'TipoContratacao@getSigla()', v:['CLT']}]).parameter([140.0]).eq(180.0)")
	public double calculaDescontoVT(double valeTransporte) {
		double percentualDesconto = 0.0;
		if(salarioExcedenteVT(valeTransporte) && this.tipoContrato.getSigla().equals("CLT"))
			percentualDesconto = 0.06;
		
		return (this.salarioBase * percentualDesconto);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getSalarioBase() {
		return salarioBase;
	}

	public void setSalarioBase(double salarioBase) {
		this.salarioBase = salarioBase;
	}

	public TipoContratacao getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(TipoContratacao tipoContrato) {
		this.tipoContrato = tipoContrato;
	}	
	
}