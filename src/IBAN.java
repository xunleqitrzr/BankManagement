import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Arrays;

import static helper.Helper.convertToPrintableChars;

public class IBAN {
    private final CountryCode cc;
    private char[] checksum = new char[2];
    private char[] bank_code = new char[8];
    private char[] account_no = new char[10];
    // final length: 24


    /**
     * @param countryCode
     * @param bank_code
     * @param account_no
     */
    public IBAN(@NotNull CountryCode countryCode, char[] bank_code, char[] account_no) {
        this.cc = countryCode;
        this.bank_code = bank_code;
        this.account_no = account_no;
        this.checksum = calculate_iban_checksum(this.cc, this.bank_code, this.account_no);
    }

    /**
     * @param cc
     * @param bankCode
     * @param accountNo
     * @return Returns the checksum for an IBAN calculated by cc, bankCode, accountNo <p> Return is a length of two character array which is inserted into the IBAN
     */
    private char @NotNull [] calculate_iban_checksum(@NotNull CountryCode cc, char[] bankCode, char[] accountNo) {
        char[] pre = new char[24];

        char[] countryCodeArr = cc.getCountryCodeAsArray(cc);
        char[] zero_pad = {0, 0};

        System.arraycopy(bankCode, 0, pre, 0, bankCode.length);
        System.arraycopy(accountNo, 0, pre, bankCode.length, accountNo.length);
        System.arraycopy(countryCodeArr, 0, pre, bankCode.length + accountNo.length, countryCodeArr.length);
        //System.arraycopy(zero_pad, 0, pre, bankCode.length + accountNo.length, zero_pad.length);
        pre[22] = 0;
        pre[23] = 0;
        // array is filled -> pre[] = {bank_code_length = 8, account_no_length  = 10, countryCodeArr_length = 4, zero_pad_length = 2 => 8 + 10 + 4 + 2 = 24}

        char[] pre_printable = convertToPrintableChars(pre);            // convert from raw to printable characters
        String preStr = new String(pre_printable);                      // store character array as String
        BigInteger check_pre = new BigInteger(preStr);                  // store String as BigInteger
        BigInteger mod97 = new BigInteger("97");                    // modulo 24 digit BigInteger with 97
        int check_pre2 = check_pre.mod(mod97).intValue();               // get int value of operation
        int checksum_int = 98 - check_pre2;

        return ("" + checksum_int).toCharArray();
    }

    /**
     * @param iban
     * @return Checks wether or not the given IBAN is valid. <p> Performs this by calculating the checksum based of the parameter iban and the checksum given
     *         in the parameter iban. <p> If they match iban is valid if not, it is not.
     */
    public boolean validateIBAN(IBAN iban) {
        char[] checksum = iban.getChecksum();
        char[] calc_checksum = calculate_iban_checksum(iban.cc, iban.bank_code, iban.account_no);

        return Arrays.equals(checksum, calc_checksum);
    }

    public CountryCode getCC() {
        return this.cc;
    }

    public char[] getChecksum() {
        return this.checksum;
    }

    public char[] getBank_code() {
        return this.bank_code;
    }

    public char[] getAccount_no() {
        return this.account_no;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.cc);
        sb.append(this.getChecksum());
        sb.append(convertToPrintableChars(this.getBank_code()));
        sb.append(convertToPrintableChars(this.getAccount_no()));
        return sb.toString();
    }

    public String toString(char delimiter) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.cc);
        sb.append(delimiter);
        sb.append(this.getChecksum());
        sb.append(delimiter);
        sb.append(convertToPrintableChars(this.getBank_code()));
        sb.append(delimiter);
        sb.append(convertToPrintableChars(this.getAccount_no()));
        return sb.toString();
    }
}
