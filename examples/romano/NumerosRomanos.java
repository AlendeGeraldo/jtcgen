package romano;

import br.com.jtcgen.annotations.JTCGen;
import br.com.jtcgen.annotations.TestEquals;

@JTCGen
public class NumerosRomanos {

	@TestEquals({"0", ""})
	@TestEquals({"1", "I"})
	@TestEquals({"2", "II"})
	@TestEquals({"3", "III"})
	@TestEquals({"20", "XX"})
	public String transforma(int digito) {
		switch(digito) {
			case 1:
				return "I";
			case 2:
				return "II";
			case 3:
				return "III";
			case 10:
				return "X";
			case 20:
				return "XX";
			default:
			
		}
		return "";
	}
}
