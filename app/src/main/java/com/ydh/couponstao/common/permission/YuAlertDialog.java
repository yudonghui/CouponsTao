package com.ydh.couponstao.common.permission;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.ArrayRes;
import androidx.annotation.AttrRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;

/**
 * Created by Android on 2018/4/24.
 */

public abstract class YuAlertDialog {
    /**
     * Create new Builder.
     *
     * @param context context.
     * @return {@link Builder}.
     */
    public static Builder newBuilder(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            return new APi21Builder(context);
        }
        return new Api20Builder(context);
    }

    /**
     * Create new Builder.
     *
     * @param context    context.
     * @param themeResId theme res id.
     * @return {@link Builder}.
     */
    public static Builder newBuilder(Context context, int themeResId) {
        if (Build.VERSION.SDK_INT >= 21) {
            return new APi21Builder(context, themeResId);
        }
        return new Api20Builder(context, themeResId);
    }

    /**
     * Create new Builder.
     *
     * @param context context.
     * @return {@link Builder}.
     * @deprecated use {@link #newBuilder(Context)} instead.
     */
    @Deprecated
    public static Builder build(Context context) {
        return newBuilder(context);
    }

    /**
     * Create new Builder.
     *
     * @param context    context.
     * @param themeResId theme res id.
     * @return {@link Builder}.
     * @deprecated use {@link #newBuilder(Context, int)} instead.
     */
    public static Builder build(Context context, int themeResId) {
        return newBuilder(context, themeResId);
    }

    public abstract void show();

    public abstract void dismiss();

    public abstract boolean isShowing();

    public abstract void cancel();

    public abstract Button getButton(int whichButton);

    public abstract
    @Nullable
    ListView getListView();

    public abstract
    @NonNull
    Context getContext();

    public abstract
    @Nullable
    View getCurrentFocus();

    public abstract
    @NonNull
    LayoutInflater getLayoutInflater();

    public abstract
    @Nullable
    Activity getOwnerActivity();

    public abstract int getVolumeControlStream();

    public abstract
    @Nullable
    Window getWindow();


    private static class Api21Dialog extends YuAlertDialog {

        private android.app.AlertDialog alertDialogApp;

        private Api21Dialog(android.app.AlertDialog alertDialog) {
            this.alertDialogApp = alertDialog;
        }

        @Override
        public void show() {
            alertDialogApp.show();
        }

        @Override
        public void dismiss() {
            if (alertDialogApp.isShowing())
                alertDialogApp.dismiss();
        }

        @Override
        public boolean isShowing() {
            return alertDialogApp.isShowing();
        }

        @Override
        public void cancel() {
            if (alertDialogApp.isShowing())
                alertDialogApp.cancel();
        }

        @Override
        public Button getButton(int whichButton) {
            return alertDialogApp.getButton(whichButton);
        }

        @Nullable
        @Override
        public ListView getListView() {
            return alertDialogApp.getListView();
        }

        @NonNull
        @Override
        public Context getContext() {
            return alertDialogApp.getContext();
        }

        @Nullable
        @Override
        public View getCurrentFocus() {
            return alertDialogApp.getCurrentFocus();
        }

        @NonNull
        @Override
        public LayoutInflater getLayoutInflater() {
            return alertDialogApp.getLayoutInflater();
        }

        @Nullable
        @Override
        public Activity getOwnerActivity() {
            return alertDialogApp.getOwnerActivity();
        }

        @Override
        public int getVolumeControlStream() {
            return alertDialogApp.getVolumeControlStream();
        }

        @Nullable
        @Override
        public Window getWindow() {
            return alertDialogApp.getWindow();
        }
    }

    private static class Api20Dialog extends YuAlertDialog {

        private AlertDialog alertDialog;

        private Api20Dialog(AlertDialog alertDialog) {
            this.alertDialog = alertDialog;
        }

        @Override
        public void show() {
            alertDialog.show();
        }

        @Override
        public void dismiss() {
            if (alertDialog.isShowing())
                alertDialog.dismiss();
        }

        @Override
        public boolean isShowing() {
            return alertDialog.isShowing();
        }

        @Override
        public void cancel() {
            if (alertDialog.isShowing())
                alertDialog.cancel();
        }

        @Override
        public Button getButton(int whichButton) {
            return alertDialog.getButton(whichButton);
        }

        @Nullable
        @Override
        public ListView getListView() {
            return alertDialog.getListView();
        }

        @NonNull
        @Override
        public Context getContext() {
            return alertDialog.getContext();
        }

        @Nullable
        @Override
        public View getCurrentFocus() {
            return alertDialog.getCurrentFocus();
        }

        @NonNull
        @Override
        public LayoutInflater getLayoutInflater() {
            return alertDialog.getLayoutInflater();
        }

        @Nullable
        @Override
        public Activity getOwnerActivity() {
            return alertDialog.getOwnerActivity();
        }

        @Override
        public int getVolumeControlStream() {
            return alertDialog.getVolumeControlStream();
        }

        @Nullable
        @Override
        public Window getWindow() {
            return alertDialog.getWindow();
        }
    }

    public interface Builder {

        @NonNull
        Context getContext();

        Builder setTitle(@StringRes int titleId);

        Builder setTitle(CharSequence title);

        Builder setCustomTitle(View customTitleView);

        Builder setMessage(@StringRes int messageId);

        Builder setMessage(CharSequence message);

        Builder setIcon(@DrawableRes int iconId);

        Builder setIcon(Drawable icon);

        Builder setIconAttribute(@AttrRes int attrId);

        Builder setPositiveButton(@StringRes int textId, final DialogInterface.OnClickListener listener);

        Builder setPositiveButton(CharSequence text, final DialogInterface.OnClickListener listener);

        Builder setNegativeButton(@StringRes int textId, final DialogInterface.OnClickListener listener);

        Builder setNegativeButton(CharSequence text, final DialogInterface.OnClickListener listener);

        Builder setNeutralButton(@StringRes int textId, final DialogInterface.OnClickListener listener);

        Builder setNeutralButton(CharSequence text, final DialogInterface.OnClickListener listener);

        Builder setCancelable(boolean cancelable);

        Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener);

        Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener);

        Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener);

        Builder setItems(@ArrayRes int itemsId, final DialogInterface.OnClickListener listener);

        Builder setItems(CharSequence[] items, final DialogInterface.OnClickListener listener);

        Builder setAdapter(final ListAdapter adapter, final DialogInterface.OnClickListener listener);

        Builder setCursor(final Cursor cursor, final DialogInterface.OnClickListener listener, String
                labelColumn);

        Builder setMultiChoiceItems(@ArrayRes int itemsId, boolean[] checkedItems, final DialogInterface
                .OnMultiChoiceClickListener listener);

        Builder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems, final DialogInterface
                .OnMultiChoiceClickListener listener);

        Builder setMultiChoiceItems(Cursor cursor, String isCheckedColumn, String labelColumn, final
        DialogInterface.OnMultiChoiceClickListener listener);

        Builder setSingleChoiceItems(@ArrayRes int itemsId, int checkedItem, final DialogInterface
                .OnClickListener listener);

        Builder setSingleChoiceItems(Cursor cursor, int checkedItem, String labelColumn, final
        DialogInterface.OnClickListener listener);

        Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, final DialogInterface
                .OnClickListener listener);

        Builder setSingleChoiceItems(ListAdapter adapter, int checkedItem, final DialogInterface
                .OnClickListener listener);


        Builder setOnItemSelectedListener(final AdapterView.OnItemSelectedListener listener);

        Builder setView(int layoutResId);

        Builder setView(View view);

        YuAlertDialog create();

        YuAlertDialog show();
    }

    private static class APi21Builder implements Builder {

        private android.app.AlertDialog.Builder builder;

        private APi21Builder(@NonNull Context context) {
            this(context, 0);
        }

        private APi21Builder(@NonNull Context context, @StyleRes int themeResId) {
            builder = new android.app.AlertDialog.Builder(context, themeResId);
        }

        @NonNull
        @Override
        public Context getContext() {
            return builder.getContext();
        }

        @Override
        public Builder setTitle(@StringRes int titleId) {
            builder.setTitle(titleId);
            return this;
        }

        @Override
        public Builder setTitle(CharSequence title) {
            builder.setTitle(title);
            return this;
        }

        @Override
        public Builder setCustomTitle(View customTitleView) {
            builder.setCustomTitle(customTitleView);
            return this;
        }

        @Override
        public Builder setMessage(@StringRes int messageId) {
            builder.setMessage(messageId);
            return this;
        }

        @Override
        public Builder setMessage(CharSequence message) {
            builder.setMessage(message);
            return this;
        }

        @Override
        public Builder setIcon(@DrawableRes int iconId) {
            builder.setIcon(iconId);
            return this;
        }

        @Override
        public Builder setIcon(Drawable icon) {
            builder.setIcon(icon);
            return this;
        }

        @Override
        public Builder setIconAttribute(@AttrRes int attrId) {
            builder.setIconAttribute(attrId);
            return this;
        }

        @Override
        public Builder setPositiveButton(@StringRes int textId, final DialogInterface.OnClickListener
                listener) {
            builder.setPositiveButton(textId, listener);
            return this;
        }

        @Override
        public Builder setPositiveButton(CharSequence text, final DialogInterface.OnClickListener listener) {
            builder.setPositiveButton(text, listener);
            return this;
        }

        @Override
        public Builder setNegativeButton(@StringRes int textId, final DialogInterface.OnClickListener
                listener) {
            builder.setNegativeButton(textId, listener);
            return this;
        }

        @Override
        public Builder setNegativeButton(CharSequence text, final DialogInterface.OnClickListener listener) {
            builder.setNegativeButton(text, listener);
            return this;
        }

        @Override
        public Builder setNeutralButton(@StringRes int textId, final DialogInterface.OnClickListener
                listener) {
            builder.setNeutralButton(textId, listener);
            return this;
        }

        @Override
        public Builder setNeutralButton(CharSequence text, final DialogInterface.OnClickListener listener) {
            builder.setNeutralButton(text, listener);
            return this;
        }

        @Override
        public Builder setCancelable(boolean cancelable) {
            builder.setCancelable(cancelable);
            return this;
        }

        @Override
        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            builder.setOnCancelListener(onCancelListener);
            return this;
        }

        @Override
        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                builder.setOnDismissListener(onDismissListener);
            }
            return this;
        }

        @Override
        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            builder.setOnKeyListener(onKeyListener);
            return this;
        }

        @Override
        public Builder setItems(@ArrayRes int itemsId, final DialogInterface.OnClickListener listener) {
            builder.setItems(itemsId, listener);
            return this;
        }

        @Override
        public Builder setItems(CharSequence[] items, final DialogInterface.OnClickListener listener) {
            builder.setItems(items, listener);
            return this;
        }

        @Override
        public Builder setAdapter(final ListAdapter adapter, final DialogInterface.OnClickListener listener) {
            builder.setAdapter(adapter, listener);
            return this;
        }

        @Override
        public Builder setCursor(final Cursor cursor, final DialogInterface.OnClickListener listener,
                                 String labelColumn) {
            builder.setCursor(cursor, listener, labelColumn);
            return this;
        }

        @Override
        public Builder setMultiChoiceItems(@ArrayRes int itemsId, boolean[] checkedItems, final
        DialogInterface.OnMultiChoiceClickListener listener) {
            builder.setMultiChoiceItems(itemsId, checkedItems, listener);
            return this;
        }

        @Override
        public Builder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems, final
        DialogInterface.OnMultiChoiceClickListener listener) {
            builder.setMultiChoiceItems(items, checkedItems, listener);
            return this;
        }

        @Override
        public Builder setMultiChoiceItems(Cursor cursor, String isCheckedColumn, String labelColumn, final
        DialogInterface.OnMultiChoiceClickListener listener) {
            builder.setMultiChoiceItems(cursor, isCheckedColumn, labelColumn, listener);
            return this;
        }

        @Override
        public Builder setSingleChoiceItems(@ArrayRes int itemsId, int checkedItem, final DialogInterface
                .OnClickListener listener) {
            builder.setSingleChoiceItems(itemsId, checkedItem, listener);
            return this;
        }

        @Override
        public Builder setSingleChoiceItems(Cursor cursor, int checkedItem, String labelColumn, final
        DialogInterface.OnClickListener listener) {
            builder.setSingleChoiceItems(cursor, checkedItem, labelColumn, listener);
            return this;
        }

        @Override
        public Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, final DialogInterface
                .OnClickListener listener) {
            builder.setSingleChoiceItems(items, checkedItem, listener);
            return this;
        }

        @Override
        public Builder setSingleChoiceItems(ListAdapter adapter, int checkedItem, final DialogInterface
                .OnClickListener listener) {
            builder.setSingleChoiceItems(adapter, checkedItem, listener);
            return this;
        }

        @Override
        public Builder setOnItemSelectedListener(final AdapterView.OnItemSelectedListener listener) {
            builder.setOnItemSelectedListener(listener);
            return this;
        }

        @Override
        public Builder setView(int layoutResId) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setView(layoutResId);
            }
            return this;
        }

        @Override
        public Builder setView(View view) {
            builder.setView(view);
            return this;
        }

        @Override
        public YuAlertDialog create() {
            return new Api21Dialog(builder.create());
        }

        @Override
        public YuAlertDialog show() {
            final YuAlertDialog dialog = create();
            dialog.show();
            return dialog;
        }

    }

    private static class Api20Builder implements Builder {

        private AlertDialog.Builder builder;

        private Api20Builder(@NonNull Context context) {
            this(context, 0);
        }

        private Api20Builder(@NonNull Context context, @StyleRes int themeResId) {
            builder = new AlertDialog.Builder(context, themeResId);
        }

        @NonNull
        public Context getContext() {
            return builder.getContext();
        }

        public Builder setTitle(@StringRes int titleId) {
            builder.setTitle(titleId);
            return this;
        }

        public Builder setTitle(CharSequence title) {
            builder.setTitle(title);
            return this;
        }

        public Builder setCustomTitle(View customTitleView) {
            builder.setCustomTitle(customTitleView);
            return this;
        }

        public Builder setMessage(@StringRes int messageId) {
            builder.setMessage(messageId);
            return this;
        }

        public Builder setMessage(CharSequence message) {
            builder.setMessage(message);
            return this;
        }

        public Builder setIcon(@DrawableRes int iconId) {
            builder.setIcon(iconId);
            return this;
        }

        public Builder setIcon(Drawable icon) {
            builder.setIcon(icon);
            return this;
        }

        public Builder setIconAttribute(@AttrRes int attrId) {
            builder.setIconAttribute(attrId);
            return this;
        }

        public Builder setPositiveButton(@StringRes int textId, final DialogInterface.OnClickListener
                listener) {
            builder.setPositiveButton(textId, listener);
            return this;
        }

        public Builder setPositiveButton(CharSequence text, final DialogInterface.OnClickListener listener) {
            builder.setPositiveButton(text, listener);
            return this;
        }

        public Builder setNegativeButton(@StringRes int textId, final DialogInterface.OnClickListener
                listener) {
            builder.setNegativeButton(textId, listener);
            return this;
        }

        public Builder setNegativeButton(CharSequence text, final DialogInterface.OnClickListener listener) {
            builder.setNegativeButton(text, listener);
            return this;
        }

        public Builder setNeutralButton(@StringRes int textId, final DialogInterface.OnClickListener
                listener) {
            builder.setNeutralButton(textId, listener);
            return this;
        }

        public Builder setNeutralButton(CharSequence text, final DialogInterface.OnClickListener listener) {
            builder.setNeutralButton(text, listener);
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            builder.setCancelable(cancelable);
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            builder.setOnCancelListener(onCancelListener);
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            builder.setOnDismissListener(onDismissListener);
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            builder.setOnKeyListener(onKeyListener);
            return this;
        }

        public Builder setItems(@ArrayRes int itemsId, final DialogInterface.OnClickListener listener) {
            builder.setItems(itemsId, listener);
            return this;
        }

        public Builder setItems(CharSequence[] items, final DialogInterface.OnClickListener listener) {
            builder.setItems(items, listener);
            return this;
        }

        public Builder setAdapter(final ListAdapter adapter, final DialogInterface.OnClickListener listener) {
            builder.setAdapter(adapter, listener);
            return this;
        }

        public Builder setCursor(final Cursor cursor, final DialogInterface.OnClickListener listener,
                                 String labelColumn) {
            builder.setCursor(cursor, listener, labelColumn);
            return this;
        }

        public Builder setMultiChoiceItems(@ArrayRes int itemsId, boolean[] checkedItems, final
        DialogInterface.OnMultiChoiceClickListener listener) {
            builder.setMultiChoiceItems(itemsId, checkedItems, listener);
            return this;
        }

        public Builder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems, final
        DialogInterface.OnMultiChoiceClickListener listener) {
            builder.setMultiChoiceItems(items, checkedItems, listener);
            return this;
        }

        public Builder setMultiChoiceItems(Cursor cursor, String isCheckedColumn, String labelColumn, final
        DialogInterface.OnMultiChoiceClickListener listener) {
            builder.setMultiChoiceItems(cursor, isCheckedColumn, labelColumn, listener);
            return this;
        }

        public Builder setSingleChoiceItems(@ArrayRes int itemsId, int checkedItem, final DialogInterface
                .OnClickListener listener) {
            builder.setSingleChoiceItems(itemsId, checkedItem, listener);
            return this;
        }

        public Builder setSingleChoiceItems(Cursor cursor, int checkedItem, String labelColumn, final
        DialogInterface.OnClickListener listener) {
            builder.setSingleChoiceItems(cursor, checkedItem, labelColumn, listener);
            return this;
        }

        public Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, final DialogInterface
                .OnClickListener listener) {
            builder.setSingleChoiceItems(items, checkedItem, listener);
            return this;
        }

        public Builder setSingleChoiceItems(ListAdapter adapter, int checkedItem, final DialogInterface
                .OnClickListener listener) {
            builder.setSingleChoiceItems(adapter, checkedItem, listener);
            return this;
        }

        public Builder setOnItemSelectedListener(final AdapterView.OnItemSelectedListener listener) {
            builder.setOnItemSelectedListener(listener);
            return this;
        }

        public Builder setView(int layoutResId) {
            builder.setView(layoutResId);
            return this;
        }

        public Builder setView(View view) {
            builder.setView(view);
            return this;
        }

        public YuAlertDialog create() {
            return new Api20Dialog(builder.create());
        }

        public YuAlertDialog show() {
            final YuAlertDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
