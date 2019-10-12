package owlmoney.model.bank;

import java.util.ArrayList;

import owlmoney.model.transaction.Transaction;
import owlmoney.ui.Ui;

/**
 * The BankList class that provides a layer of abstraction for the ArrayList that stores bank accounts.
 */

public class BankList {
    private ArrayList<Bank> bankLists;

    /**
     * Creates a instance of BankList that contains an arrayList of Banks.
     */
    public BankList() {
        bankLists = new ArrayList<Bank>();
    }

    /**
     * Gets the name of the bank account.
     *
     * @param bankListIndex The index of the bank account in the arrayList.
     * @return The name of the bank account.
     */
    //for testing in case
    public String getBankName(int bankListIndex) {
        return bankLists.get(bankListIndex).getAccountName();
    }
    /*
    /**
     * Returns the list of all bank accounts in the BankList.
     *//*
    public void listBanks() {
        if (bankLists.size() <= 0) {
            System.out.println("No bank account");
        } else {
            for (int i = 0; i < bankLists.size(); i++) {
                bankLists.get(i).getDescription();
            }
        }
    }*/

    /**
     * Adds an instance of a bank account into the BankList.
     *
     * @param newBank a new bank object.
     * @param ui      required for printing.
     */
    public void addBank(Bank newBank, Ui ui) {
        bankLists.add(newBank);
        ui.printMessage("Added new bank: ");
        ui.printMessage(newBank.getDescription());
    }

    /**
     * Deletes an instance of a bank account from the BankList.
     *
     * @param bankName name of the bank account.
     * @param ui       required for printing.
     */
    //need to SLAP
    public void deleteBank(String bankName, Ui ui) {
        if (bankLists.size() <= 0) {
            ui.printError("There are 0 bank accounts in your profile");
        } else if (bankLists.size() == 1) {
            ui.printError("There must be at least 1 bank account");
        } else {
            for (int i = 0; i < bankLists.size(); i++) {
                if (bankLists.get(i).getAccountName().equals(bankName)) {
                    ui.printMessage("Removing " + bankLists.get(i).getAccountName());
                    bankLists.remove(i);
                    break;
                }
            }
        }
    }

    /**
     * Edits the saving details.
     *
     * @param bankName Bank account to be edited.
     * @param newName  New name of bank account.
     * @param amount   New amount of bank account.
     * @param income   New income of bank account.
     * @param ui       required for printing.
     */
    public void editSavings(String bankName, String newName, String amount, String income, Ui ui) {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankLists.get(i).getAccountName().equals(bankName)
                    && "saving".equals(bankLists.get(i).getType())) {
                ui.printMessage("Editing " + bankLists.get(i).getAccountName() + "\n");
                if (!(newName.isEmpty() || newName.isBlank())) {
                    bankLists.get(i).setAccountName(newName);
                }
                if (!(amount.isBlank() || amount.isEmpty())) {
                    bankLists.get(i).setCurrentAmount(Double.parseDouble(amount));
                }
                if (!(income.isEmpty() || income.isBlank())) {
                    bankLists.get(i).setIncome(Double.parseDouble(income));
                }
                ui.printMessage("New details of the account:\n");
                ui.printMessage(bankLists.get(i).getDescription() + "\n");
                break;
            }
        }
    }

    /**
     * Edits the investment account details.
     *
     * @param bankName Bank account to be edited.
     * @param newName  New name of bank account.
     * @param amount   New amount of bank account.
     * @param ui       required for printing.
     */
    public void editInvestment(String bankName, String newName, String amount, Ui ui) {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankLists.get(i).getAccountName().equals(bankName)
                    && "investment".equals(bankLists.get(i).getType())) {
                ui.printMessage("Editing " + bankLists.get(i).getAccountName() + "\n");
                if (!(newName.isEmpty() || newName.isBlank())) {
                    bankLists.get(i).setAccountName(newName);
                }
                if (!(amount.isBlank() || amount.isEmpty())) {
                    bankLists.get(i).setCurrentAmount(Double.parseDouble(amount));
                }
                ui.printMessage("New details of the account:\n");
                ui.printMessage(bankLists.get(i).getDescription() + "\n");
                break;
            }
        }
    }

    /**
     * Adds an expenditure tied to a bank account.
     * This will store the expenditure in the ExpenditureList in the bank account.
     *
     * @param accName The Bank account name.
     * @param exp     The instance of the expenditure.
     * @param ui      Required for printing.
     */
    //need change exception class in the future for this
    public void addExpenditure(String accName, Transaction exp, Ui ui) {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankLists.get(i).getAccountName().equals(accName)) {
                bankLists.get(i).addInExpenditure(exp, ui);
                return;
            }
        }
        ui.printError("There are no account named :" + accName);
    }

    /**
     * Lists all bank accounts in the BankList.
     *
     * @param ui required for printing.
     */
    public void listBankAccount(Ui ui) {
        if (bankLists.size() <= 0) {
            ui.printError("There are 0 saving accounts.");
        }
        for (int i = 0; i < bankLists.size(); i++) {
            ui.printMessage((i + 1) + ".\n" + bankLists.get(i).getDescription());
        }
    }
    /*
    /**
     * Lists all expenditure tied to a bank account.
     *
     * @param bankToList The bank account name.
     * @param ui         required for printing.
     *//*
    public void listBankTransaction(String bankToList, Ui ui) {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankToList.equals(bankLists.get(i).getAccountName())) {
                bankLists.get(i).listAllTransaction(ui);
                return;
            }
        }
        ui.printError("Cannot find bank with name: " + bankToList);
    }*/

    /**
     * Lists expenditures in the bank account.
     *
     * @param bankToList The name of the bank account.
     * @param ui         required for printing.
     * @param displayNum Number of expenditures to list.
     */
    public void listBankExpenditure(String bankToList, Ui ui, int displayNum) {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankToList.equals(bankLists.get(i).getAccountName())) {
                bankLists.get(i).listAllExpenditure(ui, displayNum);
                return;
            }
        }
        ui.printError("Cannot find bank with name: " + bankToList);
    }

    /**
     * Lists deposits in the bank account.
     *
     * @param bankToList The name of the bank account.
     * @param ui         required for printing.
     * @param displayNum Number of deposits to list.
     */
    public void listBankDeposit(String bankToList, Ui ui, int displayNum) {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankToList.equals(bankLists.get(i).getAccountName())) {
                bankLists.get(i).listAllDeposit(ui, displayNum);
                return;
            }
        }
        ui.printError("Cannot find bank with name: " + bankToList);
    }

    /**
     * Deletes an expenditure from the transactionList in the bank account.
     *
     * @param expNum         The transaction number.
     * @param deleteFromBank The name of the bank account.
     * @param ui             required for printing.
     */
    public void deleteExp(int expNum, String deleteFromBank, Ui ui) {
        for (int i = 0; i < bankLists.size(); i++) {
            if (deleteFromBank.equals(bankLists.get(i).getAccountName())) {
                bankLists.get(i).deleteExpenditure(expNum, ui);
                return;
            }
        }
        ui.printError("Cannot find bank with name: " + deleteFromBank);
    }

    /**
     * Edits an expenditure from the transactionList in the bank account.
     *
     * @param expNum       The transaction number.
     * @param editFromBank The name of the bank account.
     * @param desc         The description of the expenditure.
     * @param amount       The amount of the expenditure.
     * @param date         The date of the expenditure.
     * @param category     The category of the expenditure.
     * @param ui           required for printing.
     */
    public void editExp(int expNum, String editFromBank, String desc, String amount, String date, String category,
            Ui ui) {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankLists.get(i).getAccountName().equals(editFromBank)) {
                bankLists.get(i).editExpenditureDetails(expNum, desc, amount, date, category, ui);
                return;
            }
        }
        ui.printError("Cannot find bank with name: " + editFromBank);
    }

    /**
     * Edits a deposit from the transactionList in the bank account.
     *
     * @param expNum       The transaction number.
     * @param editFromBank The name of the bank account.
     * @param desc         The description of the deposit.
     * @param amount       The amount of the deposit.
     * @param date         The date of the deposit.
     * @param ui           required for printing.
     */
    public void editDep(int expNum, String editFromBank, String desc, String amount, String date, Ui ui) {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankLists.get(i).getAccountName().equals(editFromBank)) {
                bankLists.get(i).editDepositDetails(expNum, desc, amount, date, ui);
                return;
            }
        }
        ui.printError("Cannot find bank with name: " + editFromBank);
    }

    /**
     * Adds a deposit tied to a bank account.
     * This will store the expenditure in the transactionList in the bank account.
     *
     * @param accName The Bank account name.
     * @param dep     The instance of the deposit.
     * @param ui      Required for printing.
     */
    public void addDeposit(String accName, Transaction dep, Ui ui) {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankLists.get(i).getAccountName().equals(accName)) {
                bankLists.get(i).addDepositTransaction(dep, ui);
                return;
            }
        }
        ui.printError("Cannot find bank with name: " + accName);
    }

    /**
     * Deletes a deposit from the transactionList in the bank account.
     *
     * @param accName The name of the bank account.
     * @param index   The transaction number.
     * @param ui      required for printing.
     */
    public void deleteDeposit(String accName, int index, Ui ui) {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankLists.get(i).getAccountName().equals(accName)) {
                bankLists.get(i).deleteDepositTransaction(index, ui);
                return;
            }
        }
        ui.printError("Cannot find bank with name: " + accName);
    }
}
