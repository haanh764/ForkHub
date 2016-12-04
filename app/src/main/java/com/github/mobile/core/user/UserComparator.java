
package com.github.mobile.core.user;

import com.github.mobile.accounts.GitHubAccount;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.User;

import java.util.Comparator;

import static java.lang.String.CASE_INSENSITIVE_ORDER;

/**
 * Sorts users and orgs in alphabetical order with special handling to put
 * currently authenticated user first.
 */
public class UserComparator implements Comparator<User> {

    private final String login;

    /**
     * Create comparator for given account
     *
     * @param account
     */
    @Inject
    public UserComparator(final GitHubAccount account) {
        login = account.getUsername();
    }

    @Override
    public int compare(final User lhs, final User rhs) {
        final String lhsLogin = lhs.getLogin();
        final String rhsLogin = rhs.getLogin();

        if (lhsLogin.equals(login))
            return rhsLogin.equals(login) ? 0 : -1;

        if (rhsLogin.equals(login))
            return lhsLogin.equals(login) ? 0 : 1;

        return CASE_INSENSITIVE_ORDER.compare(lhsLogin, rhsLogin);
    }
}
