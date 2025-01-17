package owlmoney.logic.command;

import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.goals.exception.GoalsException;
import owlmoney.model.card.exception.CardException;
import owlmoney.model.profile.Profile;
import owlmoney.model.profile.exception.ProfileException;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * Executes the expected command and prints the results.
 */
public abstract class Command {
    protected boolean isExit = false;

    /**
     * Executes the expected commands given by the user.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return True if OwlMoney should terminate after execution.
     */
    public abstract boolean execute(Profile profile, Ui ui) throws BankException,
            TransactionException, BondException, CardException, GoalsException, ProfileException;
}
