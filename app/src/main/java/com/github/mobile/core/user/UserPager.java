
package com.github.mobile.core.user;

import com.github.mobile.core.ResourcePager;

import org.eclipse.egit.github.core.User;

/**
 * Pager over users
 */
public abstract class UserPager extends ResourcePager<User> {

    @Override
    protected Object getId(User resource) {
        return resource.getId();
    }
}
