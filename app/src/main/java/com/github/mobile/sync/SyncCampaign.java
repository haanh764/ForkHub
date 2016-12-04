
package com.github.mobile.sync;

import android.content.SyncResult;
import android.database.SQLException;
import android.util.Log;

import com.github.mobile.persistence.DatabaseCache;
import com.github.mobile.persistence.OrganizationRepositories;
import com.github.mobile.persistence.Organizations;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import org.eclipse.egit.github.core.User;

import java.io.IOException;
import java.util.List;

/**
 * A cancelable sync operation to synchronize data for a given account
 */
public class SyncCampaign implements Runnable {

    private static final String TAG = "SyncCampaign";

    /**
     * Factory to create campaign
     */
    public interface Factory {

        /**
         * Create campaign for result
         *
         * @param syncResult
         * @return campaign
         */
        SyncCampaign create(SyncResult syncResult);
    }

    @Inject
    private DatabaseCache cache;

    @Inject
    private OrganizationRepositories.Factory repos;

    @Inject
    private Organizations persistedOrgs;

    private final SyncResult syncResult;

    private boolean cancelled = false;

    /**
     * Create campaign for result
     *
     * @param syncResult
     */
    @Inject
    public SyncCampaign(@Assisted SyncResult syncResult) {
        this.syncResult = syncResult;
    }

    @Override
    public void run() {
        List<User> orgs;
        try {
            orgs = cache.requestAndStore(persistedOrgs);
            syncResult.stats.numUpdates++;
        } catch (IOException e) {
            syncResult.stats.numIoExceptions++;
            Log.d(TAG, "Exception requesting users and orgs", e);
            return;
        } catch (SQLException e) {
            syncResult.stats.numIoExceptions++;
            Log.d(TAG, "Exception requesting users and orgs", e);
            return;
        }

        Log.d(TAG, "Syncing " + orgs.size() + " users and orgs");
        for (User org : orgs) {
            if (cancelled)
                return;

            Log.d(TAG, "Syncing repos for " + org.getLogin());
            try {
                cache.requestAndStore(repos.under(org));
                syncResult.stats.numUpdates++;
            } catch (IOException e) {
                syncResult.stats.numIoExceptions++;
                Log.d(TAG, "Exception requesting repositories", e);
            } catch (SQLException e) {
                syncResult.stats.numIoExceptions++;
                Log.d(TAG, "Exception requesting repositories", e);
            }
        }

        Log.d(TAG, "Sync campaign finished");
    }

    /**
     * Cancel campaign
     */
    public void cancel() {
        cancelled = true;
        Log.d(TAG, "Cancelled");
    }
}
