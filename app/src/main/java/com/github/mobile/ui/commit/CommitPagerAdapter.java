
package com.github.mobile.ui.commit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.github.mobile.ui.FragmentStatePagerAdapter;

import org.eclipse.egit.github.core.Repository;

import static com.github.mobile.Intents.EXTRA_BASE;
import static com.github.mobile.Intents.EXTRA_REPOSITORY;

/**
 * Pager over commits
 */
public class CommitPagerAdapter extends FragmentStatePagerAdapter {

    private final Repository repository;

    private final CharSequence[] ids;

    /**
     * @param activity
     * @param repository
     * @param ids
     */
    public CommitPagerAdapter(AppCompatActivity activity,
            Repository repository, CharSequence[] ids) {
        super(activity);

        this.repository = repository;
        this.ids = ids;
    }

    @Override
    public Fragment getItem(final int item) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_BASE, ids[item].toString());
        arguments.putSerializable(EXTRA_REPOSITORY, repository);
        CommitDiffListFragment fragment = new CommitDiffListFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public int getCount() {
        return ids.length;
    }
}
