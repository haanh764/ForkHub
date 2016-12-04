
package com.github.mobile.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.github.mobile.accounts.AccountScope;
import com.github.mobile.sync.SyncCampaign.Factory;
import com.google.inject.Inject;

import roboguice.inject.ContextScope;
import roboguice.inject.ContextSingleton;

/**
 * Sync adapter
 */
@ContextSingleton
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    @Inject
    private ContextScope contextScope;

    @Inject
    private AccountScope accountScope;

    @Inject
    private Factory campaignFactory;

    private SyncCampaign campaign = null;

    /**
     * Create sync adapter for context
     *
     * @param context
     */
    @Inject
    public SyncAdapter(final Context context) {
        super(context, true);
    }

    @Override
    public void onPerformSync(final Account account, final Bundle extras,
            final String authority, final ContentProviderClient provider,
            final SyncResult syncResult) {
        accountScope.enterWith(account, AccountManager.get(getContext()));
        try {
            contextScope.enter(getContext());
            try {
                cancelCampaign();
                campaign = campaignFactory.create(syncResult);
                campaign.run();
            } finally {
                contextScope.exit(getContext());
            }
        } finally {
            accountScope.exit();
        }
    }

    @Override
    public void onSyncCanceled() {
        cancelCampaign();
    }

    private void cancelCampaign() {
        if (campaign != null)
            campaign.cancel();
    }
}
