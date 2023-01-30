package com.example.jetnote;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class Notification_MembersInjector implements MembersInjector<Notification> {
  private final Provider<NotificationCompat.Builder> notifyBuilderProvider;

  private final Provider<NotificationManagerCompat> notifyManagerProvider;

  public Notification_MembersInjector(Provider<NotificationCompat.Builder> notifyBuilderProvider,
      Provider<NotificationManagerCompat> notifyManagerProvider) {
    this.notifyBuilderProvider = notifyBuilderProvider;
    this.notifyManagerProvider = notifyManagerProvider;
  }

  public static MembersInjector<Notification> create(
      Provider<NotificationCompat.Builder> notifyBuilderProvider,
      Provider<NotificationManagerCompat> notifyManagerProvider) {
    return new Notification_MembersInjector(notifyBuilderProvider, notifyManagerProvider);
  }

  @Override
  public void injectMembers(Notification instance) {
    injectNotifyBuilder(instance, notifyBuilderProvider.get());
    injectNotifyManager(instance, notifyManagerProvider.get());
  }

  @InjectedFieldSignature("com.example.jetnote.Notification.notifyBuilder")
  public static void injectNotifyBuilder(Notification instance,
      NotificationCompat.Builder notifyBuilder) {
    instance.notifyBuilder = notifyBuilder;
  }

  @InjectedFieldSignature("com.example.jetnote.Notification.notifyManager")
  public static void injectNotifyManager(Notification instance,
      NotificationManagerCompat notifyManager) {
    instance.notifyManager = notifyManager;
  }
}
