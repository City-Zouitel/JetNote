package com.example.jetnote.di;

import android.content.Context;
import androidx.core.app.NotificationManagerCompat;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class NotificationMod_NotificationChannelFactory implements Factory<NotificationManagerCompat> {
  private final Provider<Context> contextProvider;

  public NotificationMod_NotificationChannelFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public NotificationManagerCompat get() {
    return notificationChannel(contextProvider.get());
  }

  public static NotificationMod_NotificationChannelFactory create(
      Provider<Context> contextProvider) {
    return new NotificationMod_NotificationChannelFactory(contextProvider);
  }

  public static NotificationManagerCompat notificationChannel(Context context) {
    return Preconditions.checkNotNullFromProvides(NotificationMod.INSTANCE.notificationChannel(context));
  }
}
