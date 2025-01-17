package owlmoney.logic.parser.transaction.deposit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import owlmoney.logic.command.Command;
import owlmoney.logic.parser.ParseRawData;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.regex.RegexUtil;

/**
 * Abstracts common Deposit methods and functions where the child parsers will inherit from.
 */
public abstract class ParseDeposit {
    HashMap<String, String> depositParameters = new HashMap<String, String>();
    private ParseRawData parseRawData = new ParseRawData();
    private String rawData;

    static final String AMOUNT_PARAMETER = "/amount";
    static final String DATE_PARAMETER = "/date";
    static final String DESCRIPTION_PARAMETER = "/desc";
    static final String TO_PARAMETER = "/to";
    static final String TRANSACTION_NUMBER_PARAMETER = "/transno";
    static final String FROM_PARAMETER = "/from";
    static final String NUM_PARAMETER = "/num";
    private static final String[] EXPENDITURE_KEYWORD = new String[] {
        AMOUNT_PARAMETER, DATE_PARAMETER,
        DESCRIPTION_PARAMETER, TO_PARAMETER,
        TRANSACTION_NUMBER_PARAMETER, FROM_PARAMETER, NUM_PARAMETER};
    private static final List<String> EXPENDITURE_KEYWORD_LISTS = Arrays.asList(EXPENDITURE_KEYWORD);

    /**
     * Creates an instance of any ParseSaving type object.
     *
     * @param data Raw user input data.
     */
    ParseDeposit(String data) {
        this.rawData = data;
    }

    /**
     * Checks the user input for any redundant parameters.
     *
     * @param parameter Redundant parameter to check for,
     * @param command   Command the user performed.
     * @throws ParserException If a redundant parameter is detected.
     */
    void checkRedundantParameter(String parameter, String command) throws ParserException {
        if (rawData.contains(parameter)) {
            throw new ParserException(command + " /deposit should not contain " + parameter);
        }
    }

    /**
     * Checks if the first parameter is a valid parameter.
     *
     * @throws ParserException If the first parameter is invalid.
     */
    void checkFirstParameter() throws ParserException {
        String[] rawDateSplit = rawData.split(" ", 2);
        if (!EXPENDITURE_KEYWORD_LISTS.contains(rawDateSplit[0])) {
            throw new ParserException("Incorrect parameter " + rawDateSplit[0]);
        }
    }

    /**
     * Fills a hash table mapping each user input to each parameter.
     *
     * @throws ParserException If duplicate parameters are detected.
     */
    public void fillHashTable() throws ParserException {
        depositParameters.put(AMOUNT_PARAMETER,
                parseRawData.extractParameter(rawData, AMOUNT_PARAMETER, EXPENDITURE_KEYWORD).trim());
        depositParameters.put(DATE_PARAMETER,
                parseRawData.extractParameter(rawData, DATE_PARAMETER, EXPENDITURE_KEYWORD).trim());
        depositParameters.put(DESCRIPTION_PARAMETER,
                parseRawData.extractParameter(rawData, DESCRIPTION_PARAMETER, EXPENDITURE_KEYWORD).trim());
        depositParameters.put(TO_PARAMETER,
                parseRawData.extractParameter(rawData, TO_PARAMETER, EXPENDITURE_KEYWORD).trim());
        depositParameters.put(TRANSACTION_NUMBER_PARAMETER,
                parseRawData.extractParameter(rawData, TRANSACTION_NUMBER_PARAMETER, EXPENDITURE_KEYWORD).trim());
        depositParameters.put(FROM_PARAMETER,
                parseRawData.extractParameter(rawData, FROM_PARAMETER, EXPENDITURE_KEYWORD).trim());
        depositParameters.put(NUM_PARAMETER,
                parseRawData.extractParameter(rawData, NUM_PARAMETER, EXPENDITURE_KEYWORD).trim());
    }

    /**
     * Checks if the amount entered by the user is a double and only contains numbers.
     *
     * @param valueString String to be converted to double as the user's amount.
     * @throws ParserException If the string is not a double value.
     */
    void checkAmount(String valueString) throws ParserException {
        if (!RegexUtil.regexCheckMoney(valueString)) {
            throw new ParserException("/amount can only be positive numbers"
                    + " with at most 9 digits and 2 decimal places");
        }
    }

    /**
     * Checks if the transaction number or display number entered by the user is an integer.
     *
     * @param valueString String to be converted to integer.
     * @throws ParserException If the string is not an integer.
     */
    void checkInt(String variable, String valueString) throws ParserException {
        if (!RegexUtil.regexCheckListNumber(valueString)) {
            throw new ParserException(variable + " can only be a positive integer with at most 9 digits");
        }
    }

    /**
     * Checks if the description entered by the user does not have special characters and is not too long.
     *
     * @param descString Deposit description.
     * @throws ParserException If the string has special characters or is too long.
     */
    void checkDescription(String descString) throws ParserException {
        if (!RegexUtil.regexCheckDescription(descString)) {
            throw new ParserException("/desc can only contain numbers and letters and at most 50 characters");
        }
    }

    /**
     * Checks if the bank name entered by the user does not contain special character and not too long.
     *
     * @param nameString Name of bank
     * @param variable   /to or /from
     * @throws ParserException If the name is too long or contain special characters.
     */
    void checkName(String nameString, String variable) throws ParserException {
        if (!RegexUtil.regexCheckName(nameString)) {
            throw new ParserException(variable + " can only be alphanumeric and at most 30 characters");
        }
    }

    /**
     * Checks if the deposit date is of valid format and not after now.
     *
     * @param dateString Date to be checked.
     * @return Date if checks pass.
     * @throws ParserException If date format is invalid.
     */
    Date checkDate(String dateString) throws ParserException {
        if (RegexUtil.regexCheckDateFormat(dateString)) {
            DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
            temp.setLenient(false);
            Date date;
            try {
                date = temp.parse(dateString);
                if (date.compareTo(new Date()) > 0) {
                    throw new ParserException("/date cannot be after today");
                }
                return date;
            } catch (ParseException e) {
                throw new ParserException("Incorrect date format."
                        + " Date format is dd/mm/yyyy in year range of 1900-2099");
            }
        }
        throw new ParserException("Incorrect date format." + " Date format is dd/mm/yyyy in year range of 1900-2099");
    }

    /**
     * Checks the parameters given by the user.
     *
     * @throws ParserException If any parameters fail the check.
     */
    public abstract void checkParameter() throws ParserException;

    /**
     * Gets the expected command to be executed.
     *
     * @return Command to be executed.
     */
    public abstract Command getCommand();
}
