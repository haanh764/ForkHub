
package com.github.mobile.core.code;

import android.accounts.Account;
import android.content.Context;

import com.github.mobile.accounts.AuthenticatedUserTask;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Blob;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.DataService;

/**
 * Task to refresh a blob
 */
public class RefreshBlobTask extends AuthenticatedUserTask<Blob> {

    private final Repository repository;

    private final String blobSha;

    @Inject
    private DataService service;

    /**
     * @param repository
     * @param blobSha
     * @param context
     */
    public RefreshBlobTask(Repository repository, String blobSha,
            Context context) {
        super(context);

        this.repository = repository;
        this.blobSha = blobSha;
    }

    @Override
    protected Blob run(Account account) throws Exception {
        return service.getBlob(repository, blobSha);
    }
}
