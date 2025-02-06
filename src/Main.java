public class Main {
    public static void main(String[] args) {

        final char[] bank_code  = {1, 2, 0, 3, 0, 0, 0, 0};
        final char[] account_no = {9, 4, 9, 2, 2, 9, 0, 0, 0, 3};

        IBAN iban = new IBAN(CountryCode.DE, bank_code, account_no);

    }
}