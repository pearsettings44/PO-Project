package prr.Notifications;

public interface Notifiable {

    void notify(Notification notification);

    void setNotificationDeliveryMethod(NotificationDeliveryMethod deliveryMethod);

}