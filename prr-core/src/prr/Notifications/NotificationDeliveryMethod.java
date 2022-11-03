package prr.Notifications;

import java.io.Serializable;

@FunctionalInterface
public interface NotificationDeliveryMethod extends Serializable {

    void deliver(Notification notification);

}