package owlmoney.model.profile;

import owlmoney.logic.parser.exception.CardException;
import owlmoney.model.bank.Bank;
import owlmoney.model.bank.BankList;
import owlmoney.model.card.Card;
import owlmoney.model.card.CardList;
import owlmoney.model.transaction.Transaction;
import owlmoney.ui.Ui;

/**
 * The profile class that stores details of the user which includes bank accounts, cards, names.
 */

public class Profile {
    private String username;
    private BankList bankList;
    private CardList cardList;

    /**
     * Constructor that creates a new instance of the user profile.
     *
     * @param newUserName The username that the user desires to use.
     */
    public Profile(String newUserName) {
        this.username = newUserName;
        this.bankList = new BankList();
        this.cardList = new CardList();
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the profile.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Adds a new bank account into the BankList stored in this profile.
     *
     * @param newBank an instance of the new bank account.
     * @param ui      required for printing.
     */
    public void addNewBank(Bank newBank, Ui ui) {
        bankList.addBank(newBank, ui);
    }

    /**
     * Deletes a bank account from the BankList.
     *
     * @param bankName name of the Bank account.
     * @param ui       required for printing.
     */
    public void deleteBank(String bankName, String bankType, Ui ui) {
        bankList.deleteBank(bankName, bankType, ui);
    }

    /**
     * Lists all the bank accounts in the BankList.
     *
     * @param ui required for printing.
     */
    public void listBanks(String bankType, Ui ui) {
        bankList.listBankAccount(bankType, ui);
    }

    /**
     * Adds a new expenditure tied to a specific bank account.
     *
     * @param accName The name of the bank account.
     * @param exp     An expenditure object.
     * @param ui      required for printing.
     * @param type    Represents type of expenditure to be added.
     */
    public void addNewExpenditure(String accName, Transaction exp, Ui ui, String type) {
        if ("card".equals(type)) {
            cardList.addExpenditure(accName, exp, ui);
        } else {
            bankList.addExpenditure(accName, exp, ui);
        }
    }

    /**
     * Deletes an expenditure tied to a specific bank account.
     *
     * @param expIndex The index of the expenditure in the expenditureList tied to a specific bank account.
     * @param bankName The name of the bank account.
     * @param ui       required for printing.
     */
    public void deleteExpenditure(int expIndex, String bankName, Ui ui) {
        bankList.deleteExp(expIndex, bankName, ui);
    }
    /*
    /**
     * Lists all expenditure tied to a bank account.
     *
     * @param listedBank The bank account name.
     * @param ui         required for printing.
     *//*
    public void listTransaction(String listedBank, Ui ui) {
        bankList.listBankTransaction(listedBank, ui);
    }*/

    /**
     * Lists deposits from a specific bank account.
     *
     * @param listedBank Bank account to list from.
     * @param ui         required for printing.
     * @param displayNum Number of deposits to list.
     */
    public void listDeposit(String listedBank, Ui ui, int displayNum) {
        bankList.listBankDeposit(listedBank, ui, displayNum);
    }

    /**
     * Lists expenditure from a specific bank account.
     *
     * @param listedBank Bank account to list from.
     * @param ui         required for printing.
     * @param displayNum Number of expenditure to list.
     */
    public void listExpenditure(String listedBank, Ui ui, int displayNum, String type) {
        if ("card".equals(type)) {
            cardList.listCardExpenditure(listedBank, ui, displayNum);
        } else {
            bankList.listBankExpenditure(listedBank, ui, displayNum);
        }
    }

    /**
     * Edits a specific expenditure from a specific bank account.
     *
     * @param expNum       Transaction number of the expenditure.
     * @param editFromBank Bank account of expenditure.
     * @param desc         New description of expenditure.
     * @param amount       New amount of expenditure.
     * @param date         New date of expenditure.
     * @param category     New category of expenditure.
     * @param ui           required for printing.
     */
    public void editExpenditure(int expNum, String editFromBank, String desc, String amount, String date,
            String category, Ui ui) {
        bankList.editExp(expNum, editFromBank, desc, amount, date, category, ui);
    }

    /**
     * Edits a specific savings account.
     *
     * @param name    Name of savings account to be edited.
     * @param newName New name of the savings account.
     * @param amount  New amount of the savings account.
     * @param income  New income of the saving account.
     * @param ui      required for printing.
     */
    public void editSavingsAccount(String name, String newName, String amount, String income, Ui ui) {
        bankList.editSavings(name, newName, amount, income, ui);
    }

    /**
     * Edits a specific investment account.
     *
     * @param name    Name of investment account to be edited.
     * @param newName New name of the investment account.
     * @param amount  New amount of the investment account.
     * @param ui      required for printing.
     */
    public void editInvestmentAccount(String name, String newName, String amount, Ui ui) {
        bankList.editInvestment(name, newName, amount, ui);
    }

    /**
     * Deletes a specific deposit from a specific bank account.
     *
     * @param depIndex Transaction number of the deposit.
     * @param bankName Bank name of the deposit.
     * @param ui       required for printing.
     */
    public void deleteDeposit(int depIndex, String bankName, Ui ui) {
        bankList.deleteDeposit(bankName, depIndex, ui);
    }

    /**
     * Adds a new deposit to a specific bank account.
     *
     * @param accName Bank account name.
     * @param dep     Deposit to be added.
     * @param ui      required for printing.
     */
    public void addNewDeposit(String accName, Transaction dep, Ui ui) {
        bankList.addDeposit(accName, dep, ui);
    }

    /**
     * Edits a specific deposit from a specific bank account.
     *
     * @param expNum       Transaction number of the deposit.
     * @param editFromBank Bank account of deposit.
     * @param desc         New description of deposit.
     * @param amount       New amount of deposit.
     * @param date         New date of deposit.
     * @param ui           required for deposit.
     */
    public void editDeposit(int expNum, String editFromBank, String desc, String amount, String date, Ui ui) {
        bankList.editDep(expNum, editFromBank, desc, amount, date, ui);
    }

    /**
     * Adds a new credit card into the CardList stored in this profile.
     *
     * @param newCard an instance of the new credit card.
     * @param ui required for printing.
     */
    public void addNewCard(Card newCard, Ui ui) {
        cardList.addCard(newCard, ui);
    }

    public void editCardDetails(String name, String newName, String limit, String rebate, Ui ui) throws CardException {
        cardList.editCard(name, newName, limit, rebate, ui);
    }

    /**
     * Deletes a card from the CardList.
     *
     * @param name name of the credit card.
     * @param ui required for printing.
     */
    public void deleteCard(String name, Ui ui) throws CardException {
        cardList.deleteCard(name, ui);
    }

    /**
     * Deletes a card from the CardList.
     *
     * @param name name of the credit card.
     * @param ui required for printing.
     */
    /*
    public void editCard(String name, String newName, String limit, String rebate, String dueDate, Ui ui) {
        cardList.editCard(name, newName, limit, rebate, dueDate, ui);
    }
    */

    /**
     * Lists all the cards in the CardList.
     *
     * @param ui required for printing.
     */
    public void listCards(Ui ui) throws CardException {
        cardList.listCards(ui);
    }
}
