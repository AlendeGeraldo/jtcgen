package folhapgto.funcionarios;

import br.com.jtcgen.annotations.JTCGen;
import br.com.jtcgen.annotations.SetUp;
import br.com.jtcgen.annotations.TestEquals;

@JTCGen
public class Descontos {
	
	private double valeTransporte;
	private double assistMedica;
	private double assistDental;
	private double adiantamento;

	public Descontos() {
		this.valeTransporte = 0.0;
		this.assistMedica = 0.0;
		this.assistDental = 0.0;
		this.adiantamento = 0.0;
	}
	
	@SetUp({"100.0", "100.0", "100.0","100.0"})
	public Descontos(double vt, double am, double ad, double adiant) {
		this.valeTransporte = vt;
		this.assistMedica = am;
		this.assistDental = ad;
		this.adiantamento = adiant;
	}
	
	public Descontos(double vt, double am, double ad) {
		this.valeTransporte = vt;
		this.assistMedica = am;
		this.assistDental = ad;
		this.adiantamento = 0;
	}
	
	public double getValeTransporte() {
		return valeTransporte;
	}
	public void setValeTransporte(double valeTransporte) {
		this.valeTransporte = valeTransporte;
	}
	public double getAssistMedica() {
		return assistMedica;
	}
	public void setAssistMedica(double assistMedica) {
		this.assistMedica = assistMedica;
	}
	public double getAssistDental() {
		return assistDental;
	}
	public void setAssistDental(double assistDental) {
		this.assistDental = assistDental;
	}
	public double getAdiantamento() {
		return adiantamento;
	}
	public void setAdiantamento(double adiantamento) {
		this.adiantamento = adiantamento;
	}
	
	@TestEquals({"", "400.0"})
	public double obtemDescontosSomados() {
		return this.adiantamento + this.assistDental + this.assistMedica + this.valeTransporte;
	}
	
	
	
}
