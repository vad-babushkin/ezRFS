import io.github.thibaultmeyer.cuid.CUID;

public class CuidTest {
	public static void main(String[] args) {
		CUID cuid = CUID.randomCUID1();
		System.out.println("CUID: " + cuid);

		cuid = CUID.randomCUID2();
		System.out.println("CUID (Version 2): " + cuid);

		final int customLength = 8;  // Length must be, at least, 1
		cuid = CUID.randomCUID2(customLength);
		System.out.println("CUID (Version 2): " + cuid);

		cuid = CUID.fromString("cl9gts1kw00393647w1z4v2tc");
		System.out.println("CUID: " + cuid);

		final boolean isValid = CUID.isValid("cl9gts1kw00393647w1z4v2tc");
		System.out.println("Is 'cl9gts1kw00393647w1z4v2tc' a valid CUID ? " + isValid);

		cuid = CUID.randomCUID2(36);
		System.out.println("CUID (Version 2 (36)): " + cuid);
	}
}
