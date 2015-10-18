package edu.milton.miltonmobileandroid.settings.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.io.IOException;

import android.preference.PreferenceManager;
import edu.milton.miltonmobileandroid.R;
import edu.milton.miltonmobileandroid.util.Callback;

public class AccountMethods {

    private static final String LOG_TAG = AccountMethods.class.getName();

    public static Account getAccount(Context context) {
        AccountManager manager = AccountManager.get(context);
        if (!isLoggedIn(context)) {
            return null;
        }
        return manager.getAccountsByType(Consts.ACCOUNT_TYPE)[0];
    }

    public static boolean isLoggedIn(final Context context) {
        AccountManager manager = AccountManager.get(context);
        int numberofaccounts = manager.getAccountsByType(Consts.ACCOUNT_TYPE).length;
        return numberofaccounts > 0;
    }

    public static void login(final Context context, AccountManagerCallback<Bundle> callback) {
        AccountManager manager = AccountManager.get(context);
        manager.addAccount(Consts.ACCOUNT_TYPE, Consts.AUTHTOKEN_TYPE, null, null, (Activity) context, callback, null);
    }

    public static void logout(final Context context, final Callback callback) {
        AccountManager manager = AccountManager.get(context);
        if (isLoggedIn(context)) {
            final String username = getUsername(context);
            manager.removeAccount(getAccount(context), new AccountManagerCallback<Boolean>() {
                @Override
                public void run(AccountManagerFuture<Boolean> future) {
                    try {
                        Bundle info = new Bundle();
                        if (future.getResult().booleanValue()) {
                            info.putBoolean(Consts.KEY_SUCCESS,true);
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                            preferences.edit().clear().apply();
                            callback.run(info);
                            return;
                        }
                        info.putBoolean(Consts.KEY_SUCCESS,false);
                        info.putString(Consts.KEY_MESSAGE,context.getResources().getString(R.string.settings_login_logout_error));
                        callback.run(info);
                    } catch (OperationCanceledException | IOException | AuthenticatorException e) {
                        Bundle info = new Bundle();
                        info.putBoolean(Consts.KEY_SUCCESS,false);
                        info.putString(Consts.KEY_MESSAGE,context.getResources().getString(R.string.settings_login_logout_error));
                        callback.run(info);

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setCancelable(false);
                        builder.setTitle(context.getResources().getString(R.string.string_Error));
                        builder.setMessage(context.getResources().getString(R.string.settings_login_logout_error));
                        builder.setPositiveButton(R.string.string_OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                callback.run(null);
                            }
                        });
                        builder.create().show();
                    }
                }
            }, null);
        }
        else {
            Bundle info = new Bundle();
            info.putBoolean(Consts.KEY_SUCCESS,false);
            info.putString(Consts.KEY_MESSAGE,context.getResources().getString(R.string.settings_login_not_logged_in));
            callback.run(info);
        }
    }

    public static String getUsername(Context context) {
        AccountManager manager = AccountManager.get(context);
        if (!isLoggedIn(context)) {
            return null;
        }
        return manager.getUserData(getAccount(context),AccountManager.KEY_ACCOUNT_NAME);
    }

    public static String getPassword(Context context) {
        AccountManager manager = AccountManager.get(context);
        if (!isLoggedIn(context)) {
            return null;
        }
        return manager.getPassword(getAccount(context));
    }

    public static String getFirstName(Context context) {
        AccountManager manager = AccountManager.get(context);
        if (!isLoggedIn(context)) {
            return null;
        }
        return manager.getUserData(getAccount(context),Consts.KEY_FIRSTNAME);
    }

    public static String getLastName(Context context) {
        AccountManager manager = AccountManager.get(context);
        if (!isLoggedIn(context)) {
            return null;
        }
        return manager.getUserData(getAccount(context),Consts.KEY_LASTNAME);
    }

    public static String getClassNumber(Context context) {
        AccountManager manager = AccountManager.get(context);
        if (!isLoggedIn(context)) {
            return null;
        }
        return manager.getUserData(getAccount(context),Consts.KEY_CLASSNUMBER);
    }
}
