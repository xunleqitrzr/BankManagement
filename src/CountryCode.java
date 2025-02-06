public enum CountryCode {
    DE(1314),
    DK(1320),
    FI(1518),
    TR(2927),
    BE(1114),
    AL(1021),
    BA(1110),
    BR(1127),
    BG(1116),
    EE(1414),
    GE(1614),
    GI(1618),
    GR(1627);


    public final int countryCode;

    private CountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    // return the country code in a numerical format
    public int getCountryCode() {
        return countryCode;
    }

    public char[] getCountryCodeAsArray(CountryCode countryCode) {
        char[] countryCodeAsArray = new char[4];
        int ccd = countryCode.getCountryCode();
        for (int i = 3; i >= 0; i--) {
            countryCodeAsArray[i] = (char) (ccd % 10);
            ccd /= 10;
        }

        return countryCodeAsArray;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
