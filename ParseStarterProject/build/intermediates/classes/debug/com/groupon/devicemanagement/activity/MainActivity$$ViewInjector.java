// Generated code from Butter Knife. Do not modify!
package com.groupon.devicemanagement.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class MainActivity$$ViewInjector<T extends com.groupon.devicemanagement.activity.MainActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165188, "field 'btnCheckout' and method 'checkout'");
    target.btnCheckout = finder.castView(view, 2131165188, "field 'btnCheckout'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.checkout();
        }
      });
  }

  @Override public void reset(T target) {
    target.btnCheckout = null;
  }
}
