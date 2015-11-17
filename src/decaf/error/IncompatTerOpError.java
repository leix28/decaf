package decaf.error;

import decaf.Location;

public class IncompatTerOpError extends DecafError {
	
	private String a, b;
	
	public IncompatTerOpError(Location loc, String a, String b) {
		super(loc);
		this.a = a;
		this.b = b;
	}
	

	@Override
	protected String getErrMsg() {
		return "incompatible condition operates: " + a + " and " + b;
	}

}
