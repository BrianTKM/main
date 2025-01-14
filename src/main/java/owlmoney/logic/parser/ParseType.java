package owlmoney.logic.parser;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.bank.ListInvestmentCommand;
import owlmoney.logic.command.bank.ListSavingsCommand;
import owlmoney.logic.command.card.ListCardCommand;
import owlmoney.logic.command.goals.ListAchievementCommand;
import owlmoney.logic.command.goals.ListGoalsCommand;
import owlmoney.logic.parser.bond.ParseAddBond;
import owlmoney.logic.parser.bond.ParseBond;
import owlmoney.logic.parser.bond.ParseDeleteBond;
import owlmoney.logic.parser.bond.ParseEditBond;
import owlmoney.logic.parser.bond.ParseListBond;
import owlmoney.logic.parser.card.ParseAddCard;
import owlmoney.logic.parser.card.ParseCard;
import owlmoney.logic.parser.card.ParseDeleteCard;
import owlmoney.logic.parser.card.ParseEditCard;
import owlmoney.logic.parser.cardbill.ParseAddCardBill;
import owlmoney.logic.parser.cardbill.ParseCardBill;
import owlmoney.logic.parser.cardbill.ParseDeleteCardBill;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.parser.find.ParseFindBankOrCard;
import owlmoney.logic.parser.find.ParseFindBond;
import owlmoney.logic.parser.find.ParseFindRecurring;
import owlmoney.logic.parser.find.ParseFindTransaction;
import owlmoney.logic.parser.goals.ParseAddGoals;
import owlmoney.logic.parser.goals.ParseDeleteGoals;
import owlmoney.logic.parser.goals.ParseEditGoals;
import owlmoney.logic.parser.goals.ParseGoals;
import owlmoney.logic.parser.investment.ParseAddInvestment;
import owlmoney.logic.parser.investment.ParseDeleteInvestment;
import owlmoney.logic.parser.investment.ParseEditInvestment;
import owlmoney.logic.parser.investment.ParseInvestment;
import owlmoney.logic.parser.profile.ParseEditProfile;
import owlmoney.logic.parser.saving.ParseAddSaving;
import owlmoney.logic.parser.saving.ParseDeleteSaving;
import owlmoney.logic.parser.saving.ParseEditSaving;
import owlmoney.logic.parser.saving.ParseSaving;
import owlmoney.logic.parser.transaction.deposit.ParseAddDeposit;
import owlmoney.logic.parser.transaction.deposit.ParseDeleteDeposit;
import owlmoney.logic.parser.transaction.deposit.ParseEditDeposit;
import owlmoney.logic.parser.transaction.deposit.ParseDeposit;
import owlmoney.logic.parser.transaction.deposit.ParseListDeposit;
import owlmoney.logic.parser.transaction.expenditure.ParseRecurringExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseAddRecurringExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseDeleteRecurringExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseEditRecurringExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseListRecurringExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseAddExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseEditExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseDeleteExpenditure;
import owlmoney.logic.parser.transaction.expenditure.ParseListExpenditure;
import owlmoney.logic.parser.transfer.ParseTransfer;

import java.util.Arrays;
import java.util.List;

/**
 * Represents the second layer of parsing for secondary category of command.
 * This determines what type of command the user desires after specifying the command.
 */
class ParseType extends Parser {

    /**
     * List of whitelisted keywords that the user can use.
     */
    private static final String[] TYPE_KEYWORDS = new String[] {
        "/savings", "/investment", "/cardexpenditure", "/bankexpenditure", "/goals", "/card",
        "/recurbankexp", "/bonds", "/profile", "/deposit", "/fund", "/banktransaction", "/cardtransaction", "/cardbill",
        "/achievement"
    };
    private static final List<String> TYPE_KEYWORD_LISTS = Arrays.asList(TYPE_KEYWORDS);
    private static final String BANK = "bank";
    private static final String CARD = "card";
    private static final String SAVING = "saving";
    private static final String INVESTMENT = "investment";
    private static final String BOND = "bonds";
    private static final String RECURRING = "recurring";

    /**
     * Determines the type of command and checks if it is of valid type.
     * After determining that it is of a legal type.
     * The type is extracted just like how the first field was extracted when extracting command.
     *
     * @param command The command previously extracted from the first field of user input.
     * @param data    The remaining user input string with command removed.
     * @return The raw data left with command and type removed.
     * @throws ParserException if the user specified an invalid type.
     */
    Command parseData(String command, String data) throws ParserException {
        String type = parseFirstField(data);
        if (!TYPE_KEYWORD_LISTS.contains(type)) {
            throw new ParserException(type + " is an invalid type");
        }
        String rawData;
        if ("/list".equals(command)) {
            rawData = removeListFirstField(data, type);
        } else {
            rawData = removeFirstField(data, type);
        }
        return parseTypeMenu(command, type, rawData);
    }

    /**
     * Checks if the user wants to delete profile.
     *
     * @param command The extracted first field from the initial user input that determines the command.
     * @throws ParserException if the user wants to delete his profile.
     */
    private void isDeleteProfile(String command) throws ParserException {
        if ("/delete".equals(command)) {
            throw new ParserException("Profile cannot be deleted");
        }
    }

    /**
     * The parseTypeMenu determines what type of command object to generate based on the command and type.
     *
     * @param command The command extracted from the initial first field.
     * @param type    The type of command extracted from the subsequent first field after first layer of parsing.
     * @param rawData The remaining data after removing command and type.
     * @return The command object that is required to be executed.
     * @throws ParserException when an invalid type if specified.
     */
    private Command parseTypeMenu(String command, String type, String rawData) throws ParserException {
        switch (type) {
        case "/profile":
            if ("/edit".equals(command)) {
                ParseEditProfile parseEditProfile = new ParseEditProfile(rawData);
                parseEditProfile.fillHashTable();
                parseEditProfile.checkParameter();
                return parseEditProfile.getCommand();
            } else if ("/delete".equals(command)) {
                isDeleteProfile(command);
            }
            throw new ParserException("You entered an invalid type for profile");
        case "/savings":
            if ("/add".equals(command)) {
                ParseSaving parseAddSaving = new ParseAddSaving(rawData);
                parseAddSaving.fillHashTable();
                parseAddSaving.checkParameter();
                return parseAddSaving.getCommand();
            } else if ("/edit".equals(command)) {
                ParseSaving parseEditSaving = new ParseEditSaving(rawData);
                parseEditSaving.fillHashTable();
                parseEditSaving.checkParameter();
                return parseEditSaving.getCommand();
            } else if ("/delete".equals(command)) {
                ParseSaving parseDeleteSaving = new ParseDeleteSaving(rawData);
                parseDeleteSaving.fillHashTable();
                parseDeleteSaving.checkParameter();
                return parseDeleteSaving.getCommand();
            } else if ("/list".equals(command)) {
                return new ListSavingsCommand();
            } else if ("/find".equals(command)) {
                ParseFindBankOrCard parseFindSaving = new ParseFindBankOrCard(rawData, SAVING);
                parseFindSaving.fillHashTable();
                parseFindSaving.checkParameter();
                return parseFindSaving.getCommand();
            }
            throw new ParserException("You entered an invalid type for savings");
        case "/investment":
            if ("/add".equals(command)) {
                ParseInvestment parseAddInvestment = new ParseAddInvestment(rawData);
                parseAddInvestment.fillHashTable();
                parseAddInvestment.checkParameter();
                return parseAddInvestment.getCommand();
            } else if ("/edit".equals(command)) {
                ParseInvestment parseEditInvestment = new ParseEditInvestment(rawData);
                parseEditInvestment.fillHashTable();
                parseEditInvestment.checkParameter();
                return parseEditInvestment.getCommand();
            } else if ("/delete".equals(command)) {
                ParseInvestment parseDeleteInvestment = new ParseDeleteInvestment(rawData);
                parseDeleteInvestment.fillHashTable();
                parseDeleteInvestment.checkParameter();
                return parseDeleteInvestment.getCommand();
            } else if ("/list".equals(command)) {
                return new ListInvestmentCommand();
            } else if ("/find".equals(command)) {
                ParseFindBankOrCard parseFindInvestment = new ParseFindBankOrCard(rawData, INVESTMENT);
                parseFindInvestment.fillHashTable();
                parseFindInvestment.checkParameter();
                return parseFindInvestment.getCommand();
            }
            throw new ParserException("You entered an invalid type for investment");
        case "/bonds":
            if ("/add".equals(command)) {
                ParseBond parseAddBond = new ParseAddBond(rawData, BOND);
                parseAddBond.fillHashTable();
                parseAddBond.checkParameter();
                return parseAddBond.getCommand();
            } else if ("/edit".equals(command)) {
                ParseBond parseEditBond = new ParseEditBond(rawData, BOND);
                parseEditBond.fillHashTable();
                parseEditBond.checkParameter();
                return parseEditBond.getCommand();
            } else if ("/delete".equals(command)) {
                ParseBond parseDeleteBond = new ParseDeleteBond(rawData, BOND);
                parseDeleteBond.fillHashTable();
                parseDeleteBond.checkParameter();
                return parseDeleteBond.getCommand();
            } else if ("/list".equals(command)) {
                ParseBond parseListBond = new ParseListBond(rawData, BOND);
                parseListBond.fillHashTable();
                parseListBond.checkParameter();
                return parseListBond.getCommand();
            } else if ("/find".equals(command)) {
                ParseFindBond parseFindBond = new ParseFindBond(rawData, BOND);
                parseFindBond.fillHashTable();
                parseFindBond.checkParameter();
                return parseFindBond.getCommand();
            }
            throw new ParserException("You entered an invalid type for bond");
        case "/bankexpenditure":
            if ("/add".equals(command)) {
                ParseExpenditure parseAddExpenditure = new ParseAddExpenditure(rawData, BANK);
                parseAddExpenditure.fillHashTable();
                parseAddExpenditure.checkParameter();
                return parseAddExpenditure.getCommand();
            } else if ("/list".equals(command)) {
                ParseExpenditure parseListExpenditure = new ParseListExpenditure(rawData, BANK);
                parseListExpenditure.fillHashTable();
                parseListExpenditure.checkParameter();
                return parseListExpenditure.getCommand();
            } else if ("/delete".equals(command)) {
                ParseExpenditure parseDeleteExpenditure = new ParseDeleteExpenditure(rawData, BANK);
                parseDeleteExpenditure.fillHashTable();
                parseDeleteExpenditure.checkParameter();
                return parseDeleteExpenditure.getCommand();
            } else if ("/edit".equals(command)) {
                ParseExpenditure parseEditExpenditure = new ParseEditExpenditure(rawData, BANK);
                parseEditExpenditure.fillHashTable();
                parseEditExpenditure.checkParameter();
                return parseEditExpenditure.getCommand();
            }
            throw new ParserException("You entered an invalid type for bank expenditure");
        case "/cardexpenditure":
            if ("/add".equals(command)) {
                ParseExpenditure parseAddExpenditure = new ParseAddExpenditure(rawData, CARD);
                parseAddExpenditure.fillHashTable();
                parseAddExpenditure.checkParameter();
                return parseAddExpenditure.getCommand();
            } else if ("/list".equals(command)) {
                ParseExpenditure parseListExpenditure = new ParseListExpenditure(rawData, CARD);
                parseListExpenditure.fillHashTable();
                parseListExpenditure.checkParameter();
                return parseListExpenditure.getCommand();
            } else if ("/delete".equals(command)) {
                ParseExpenditure parseDeleteExpenditure = new ParseDeleteExpenditure(rawData, CARD);
                parseDeleteExpenditure.fillHashTable();
                parseDeleteExpenditure.checkParameter();
                return parseDeleteExpenditure.getCommand();
            } else if ("/edit".equals(command)) {
                ParseExpenditure parseEditExpenditure = new ParseEditExpenditure(rawData, CARD);
                parseEditExpenditure.fillHashTable();
                parseEditExpenditure.checkParameter();
                return parseEditExpenditure.getCommand();
            }
            throw new ParserException("You entered an invalid type for card expenditure");
        case "/deposit":
            if ("/add".equals(command)) {
                ParseDeposit parseAddDeposit = new ParseAddDeposit(rawData);
                parseAddDeposit.fillHashTable();
                parseAddDeposit.checkParameter();
                return parseAddDeposit.getCommand();
            } else if ("/list".equals(command)) {
                ParseDeposit parseListDeposit = new ParseListDeposit(rawData);
                parseListDeposit.fillHashTable();
                parseListDeposit.checkParameter();
                return parseListDeposit.getCommand();
            } else if ("/delete".equals(command)) {
                ParseDeposit parseDeleteDeposit = new ParseDeleteDeposit(rawData);
                parseDeleteDeposit.fillHashTable();
                parseDeleteDeposit.checkParameter();
                return parseDeleteDeposit.getCommand();
            } else if ("/edit".equals(command)) {
                ParseDeposit parseEditDeposit = new ParseEditDeposit(rawData);
                parseEditDeposit.fillHashTable();
                parseEditDeposit.checkParameter();
                return parseEditDeposit.getCommand();
            }
            throw new ParserException("You entered an invalid type for deposit");
        case "/card":
            if ("/add".equals(command)) {
                ParseCard addCard = new ParseAddCard(rawData);
                addCard.fillHashTable();
                addCard.checkParameter();
                return addCard.getCommand();
            } else if ("/delete".equals(command)) {
                ParseCard deleteCard = new ParseDeleteCard(rawData);
                deleteCard.fillHashTable();
                deleteCard.checkParameter();
                return deleteCard.getCommand();
            } else if ("/list".equals(command)) {
                return new ListCardCommand();
            } else if ("/edit".equals(command)) {
                ParseCard editCard = new ParseEditCard(rawData);
                editCard.fillHashTable();
                editCard.checkParameter();
                return editCard.getCommand();
            } else if ("/find".equals(command)) {
                ParseFindBankOrCard parseFindCard = new ParseFindBankOrCard(rawData, CARD);
                parseFindCard.fillHashTable();
                parseFindCard.checkParameter();
                return parseFindCard.getCommand();
            }
            throw new ParserException("You entered an invalid type for card");
        case "/goals":
            if ("/add".equals(command)) {
                ParseGoals addGoals = new ParseAddGoals(rawData);
                addGoals.fillHashTable();
                addGoals.checkParameter();
                return addGoals.getCommand();
            } else if ("/delete".equals(command)) {
                ParseGoals deleteGoals = new ParseDeleteGoals(rawData);
                deleteGoals.fillHashTable();
                deleteGoals.checkParameter();
                return deleteGoals.getCommand();
            } else if ("/edit".equals(command)) {
                ParseGoals editGoals = new ParseEditGoals(rawData);
                editGoals.fillHashTable();
                editGoals.checkParameter();
                return editGoals.getCommand();
            } else if ("/list".equals(command)) {
                return new ListGoalsCommand();
            }
            throw new ParserException("You entered an invalid type for goals");
        case "/recurbankexp":
            if ("/add".equals(command)) {
                ParseRecurringExpenditure addRecurringExpenditure = new ParseAddRecurringExpenditure(rawData, BANK);
                addRecurringExpenditure.fillHashTable();
                addRecurringExpenditure.checkParameter();
                return addRecurringExpenditure.getCommand();
            } else if ("/delete".equals(command)) {
                ParseDeleteRecurringExpenditure
                        deleteRecurringExpenditure = new ParseDeleteRecurringExpenditure(rawData, BANK);
                deleteRecurringExpenditure.fillHashTable();
                deleteRecurringExpenditure.checkParameter();
                return deleteRecurringExpenditure.getCommand();
            } else if ("/edit".equals(command)) {
                ParseRecurringExpenditure editRecurringExpenditure = new ParseEditRecurringExpenditure(rawData, BANK);
                editRecurringExpenditure.fillHashTable();
                editRecurringExpenditure.checkParameter();
                return editRecurringExpenditure.getCommand();
            } else if ("/list".equals(command)) {
                ParseRecurringExpenditure listRecurringExpenditure = new ParseListRecurringExpenditure(rawData, BANK);
                listRecurringExpenditure.fillHashTable();
                listRecurringExpenditure.checkParameter();
                return listRecurringExpenditure.getCommand();
            } else if ("/find".equals(command)) {
                ParseFindRecurring parseFindRecurringExpenditure = new ParseFindRecurring(rawData, RECURRING);
                parseFindRecurringExpenditure.fillHashTable();
                parseFindRecurringExpenditure.checkParameter();
                return parseFindRecurringExpenditure.getCommand();
            }
            throw new ParserException("You entered an invalid type for recurbankexp");
        case "/fund":
            if ("/transfer".equals(command)) {
                ParseTransfer parseTransfer = new ParseTransfer(rawData);
                parseTransfer.fillHashTable();
                parseTransfer.checkParameter();
                return parseTransfer.getCommand();
            }
            throw new ParserException("You entered an invalid type for fund");
        case "/banktransaction":
            if ("/find".equals(command)) {
                ParseFindTransaction parseFindBankTransaction = new ParseFindTransaction(rawData, BANK);
                parseFindBankTransaction.fillHashTable();
                parseFindBankTransaction.checkParameter();
                return parseFindBankTransaction.getCommand();
            }
            throw new ParserException("You entered an invalid type for banktransaction");
        case "/cardtransaction":
            if ("/find".equals(command)) {
                ParseFindTransaction parseFindCardTransaction = new ParseFindTransaction(rawData, CARD);
                parseFindCardTransaction.fillHashTable();
                parseFindCardTransaction.checkParameter();
                return parseFindCardTransaction.getCommand();
            }
            throw new ParserException("You entered an invalid type for cardtransaction");
        case "/cardbill":
            if ("/add".equals(command)) {
                ParseCardBill parseAddCardBill = new ParseAddCardBill(rawData);
                parseAddCardBill.fillHashTable();
                parseAddCardBill.checkParameter();
                return parseAddCardBill.getCommand();
            } else if ("/delete".equals(command)) {
                ParseCardBill parseDeleteCardBill = new ParseDeleteCardBill(rawData);
                parseDeleteCardBill.fillHashTable();
                parseDeleteCardBill.checkParameter();
                return parseDeleteCardBill.getCommand();
            }
            throw new ParserException("You entered an invalid type for cardbill");
        case "/achievement":
            if ("/list".equals(command)) {
                return new ListAchievementCommand();
            }
            throw new ParserException("You entered an invalid type for achievements");
        default:
            throw new ParserException("You entered an invalid type");
        }
    }
}
