/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */
package com.amazon.apl.viewhost;

import com.amazon.apl.viewhost.primitives.Decodable;
import com.amazon.apl.viewhost.request.ExecuteCommandsRequest;
import com.amazon.apl.viewhost.request.FinishDocumentRequest;
import com.amazon.apl.viewhost.request.UpdateDataSourceRequest;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Allows the runtime to manipulate an APL document that was previously submitted for rendering.
 * <p>
 * Implementations of this class are guaranteed to be thread-safe.
 */
@ThreadSafe
public abstract class DocumentHandle extends UserDataHolder {
    /**
     * Determines whether this handle refers to a valid document. A document is considered valid if
     * one of the following is true:
     * - the document has not been rendered on screen yet, but is enqueued to be rendered
     * - the document is currently on screen
     * - the document is no longer on screen, but hasn't been finished yet (e.g. the document is on
     *   the backstack)
     *
     * @return @c true if this handles refers to a valid document, @c false otherwise
     */
    public abstract boolean isValid();

    /**
     * Returns the token provided for this document at the time it was prepared. The token defaults
     * to an empty string if not specified by the APL runtime). This is an opaque token, the
     * viewhost only ever performs an equality check on it. It has no intrinsic meaning.
     *
     * @return the opaque token for this document
     */
    public abstract String getToken();

    /**
     * The document's globally unique identifier. This ID is internally generated by the viewhost
     * when the document is first received, and does not change for the entire lifecycle of the
     * document.
     *
     * @return the unique ID of this document
     */
    public abstract String getUniqueId();

    /**
     * Request the document's current visual context and return it via the provided callback.
     *
     * @return @c true if the visual context could be requested or false if the document handle is
     *         no longer valid and the callback will not be called.
     */
    public abstract boolean requestVisualContext(VisualContextCallback callback);

    /**
     * Request the document's current data source context and return it via the provided callback.
     *
     * @return @c true if the data source context could be requested or false if the document handle
     *         is no longer valid and the callback will not be called.
     */
    public abstract boolean requestDataSourceContext(DataSourceContextCallback callback);

    /**
     * Executes the specified commands for this document, if valid. If the document is valid and
     * not currently displayed, the commands will be stored until either the document becomes
     * displayed, or it becomes invalid. If the document is no longer valid, this call has no
     * effect. Executing commands is typically performed asynchronously, after this call returns.
     * Runtimes that wish to take actions based on a commands being successfully executed should
     * monitor document lifecycle events (see @c MessageHandler). If this call returns @c
     * true, a message will be published when commands have been executed (successfully or not).
     * If this call returns @c false, no message will be published for this call.
     *
     * @return @c true if the request has been accepted for execution, @c false otherwise
     */
    public abstract boolean executeCommands(ExecuteCommandsRequest request);

    /**
     * Method to handle any data source updates.This method will be executed if the document is valid,
     * in case it is not, this call will return false.
     *
     * @return @c true if the request was accepted for execution, @c false otherwise
     */
    public abstract boolean updateDataSource(UpdateDataSourceRequest request);

    /**
     * Finishes this document, if it is still valid. Finishing a document removes it from the
     * display, if applicable, and releases resources associated with that document. If the document
     * is no longer valid, this call has no effect.
     *
     * Finishing the document is typically performed asynchronously, after this call returns.
     * Runtimes that wish to take actions based on a document being successfully finished should
     * monitor document lifecycle events (see @c MessageHandler). If this call returns @c true, a
     * message will be published when the document is successfully finished. If this call returns @c
     * false, no message will be published for this call.
     *
     * @return @c true if the request was accepted for execution, @c false otherwise
     */
    public abstract boolean finish(FinishDocumentRequest request);

    /**
     * Interface for returning the document's serialized visual context
     */
    public interface VisualContextCallback {
        /**
         * Called when the visual context has been serialized
         */
        void onSuccess(Decodable context);

        /**
         * Called when the visual context could not be serialized. This could occur if the document
         * was finished before this request could be processed.
         *
         * @param reason a human-readable reason suitable for logging
         */
        void onFailure(String reason);
    }

    /**
     * Interface for returning the document's serialized data source context
     */
    public interface DataSourceContextCallback {
        /**
         * Called when the data source context has been serialized
         */
        void onSuccess(Decodable context);

        /**
         * Called when the data source context could not be serialized. This could occur if the
         * document was finished before this request could be processed.
         *
         * @param reason a human-readable reason suitable for logging
         */
        void onFailure(String reason);
    }
}
