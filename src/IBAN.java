import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

import static helper.Helper.convertToPrintableChars;

public class IBAN {
    private final CountryCode cc;
    private char[] checksum = new char[2];
    private char[] bank_code = new char[8];
    private char[] account_no = new char[10];
    // final length: 24

    public IBAN(@NotNull CountryCode countryCode, char[] bank_code, char[] account_no) {
        //IBAN iban = new IBAN();
        //iban.cc = countryCode;
        //iban.bank_code = bank_code;
        //iban.account_no = account_no;
        //iban.checksum = calculate_iban_checksum(iban.cc, iban.bank_code, iban.account_no);

        this.cc = countryCode;
        this.bank_code = bank_code;
        this.account_no = account_no;
        this.checksum = calculate_iban_checksum(this.cc, this.bank_code, this.account_no);
    }

    private char @NotNull [] calculate_iban_checksum(@NotNull CountryCode cc, char[] bankCode, char[] accountNo) {
        char[] pre = new char[24];

        char[] countryCodeArr = cc.getCountryCodeAsArray(cc);
        char[] zero_pad = {0, 0};

        System.arraycopy(bankCode, 0, pre, 0, bankCode.length);
        System.arraycopy(accountNo, 0, pre, bankCode.length, accountNo.length);
        System.arraycopy(countryCodeArr, 0, pre, bankCode.length + accountNo.length, countryCodeArr.length);
        System.arraycopy(zero_pad, 0, pre, bankCode.length + accountNo.length, zero_pad.length);
        // array is filled -> pre[] = {bank_code_length = 8, account_no_length  = 10, countryCodeArr_length = 4, zero_pad_length = 2 => 8 + 10 + 4 + 2 = 24}

        char[] pre_printable = convertToPrintableChars(pre);            // convert from raw to printable characters
        String preStr = new String(pre_printable);                      // store character array as String
        BigInteger check_pre = new BigInteger(preStr);                  // store String as BigInteger
        BigInteger mod97 = new BigInteger("97");                    // modulo 24 digit BigInteger with 97
        int check_pre2 = check_pre.mod(mod97).intValue();               // get int value of operation
        int checksum_int = 98 - check_pre2;

        char[] checksum = ("" + checksum_int).toCharArray();

        // clean up
        countryCodeArr = null;
        zero_pad = null;
        pre = null;
        pre_printable = null;
        preStr = null;
        check_pre = null;
        mod97 = null;
        System.gc();

        return checksum;
    }

    public CountryCode getCc() {
        return cc;
    }

    public char[] getChecksum() {
        return checksum;
    }

    public char[] getBank_code() {
        return bank_code;
    }

    public char[] getAccount_no() {
        return account_no;
    }
}
