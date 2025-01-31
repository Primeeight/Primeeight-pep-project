package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;
    //No arg constructor
    public AccountService(){
        this.accountDAO = new AccountDAO();
    }
    //Constructor with a DAO.
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    //new user registration
    public Account registerUser(Account account){
        if (account.getUsername() != "" && account.password.length() >= 4 && accountDAO.getAccount(account.getUsername()) == null){
            return accountDAO.userRegistration(account);
        }
        return null;
    }
    public Account loginAccount(Account account){
        return accountDAO.getAccount(account);
        // if (reqsAccount != null && reqsAccount.getUsername() == account.getUsername() 
        // && reqsAccount.getPassword() == account.getPassword()) { 
        //     return reqsAccount;
        // } 
        // return null;
    }
}
