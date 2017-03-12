package domain;

public enum Status {

	PENDING("PENDING", "PENDIENTE"), ACCEPTED("ACCEPTED", "ACEPTADA"), DENIED("DENIED", "DENEGADA");

	private final String	name;
	private final String	spanishName;


	private Status(String name, String spanishName) {
		this.name = name;
		this.spanishName = spanishName;
	}

	public String getName() {
		return name;
	}

	public String getSpanishName() {
		return spanishName;
	}
}
