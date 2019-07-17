package io.swagger.api.service.repository;

import io.swagger.api.model.Account;

import java.util.TreeMap;

/**
 * The class that is representative of accounts database.
 *
 * @author Atila Ekimci
 */
public class AccountDb {
    private TreeMap<Integer, Account> accounts;

    public AccountDb() {
        accounts = new TreeMap<>();
        accounts.put(101, new Account(101, "Atila Ekimci", 0L));
        accounts.put(102, new Account(102, "Agata Borek", 100L));
        accounts.put(103, new Account(103, "Revolut Perks", 500L));
        accounts.put(104, new Account(104, "Bernie Sanders", 1000L));
        accounts.put(105, new Account(105, "Karl Marx", 5000L));
    }

    public TreeMap<Integer, Account> getAccounts() {
        return accounts;
    }
}
