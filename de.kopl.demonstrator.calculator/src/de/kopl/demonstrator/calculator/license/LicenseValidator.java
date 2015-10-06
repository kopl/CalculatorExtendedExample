package de.kopl.demonstrator.calculator.license;

/**
 * A license validator mock that returns true for all questioned licenses. The
 * interface of the validator is conform to the CAS case study. It can be used
 * together with all CAS specific variability mechanisms. Nevertheless, this is
 * a mockup only.
 */
public class LicenseValidator {

	private static final LicenseValidator INSTANCE = new LicenseValidator();

	private LicenseValidator() {
	}

	/**
	 * Returns the singleton instance of the license validator.
	 * 
	 * @return An instance of the license validator.
	 */
	public static LicenseValidator getInstance() {
		return INSTANCE;
	}

	/**
	 * Tests if the given license is available in the current application
	 * context.
	 * 
	 * @param licenseConstant
	 *            The string constant identifying the license.
	 * @return Always true (for mockup purposes).
	 */
	public boolean hasUserModuleLicense(String licenseConstant) {
		return true;
	}

}
