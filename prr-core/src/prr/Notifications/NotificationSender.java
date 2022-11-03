package prr.Notifications;

public interface NotificationSender {

    void subscribe(Notifiable notifiable);

    void sendNotification(Notification notification);

    public void unsubscribeAll();

}