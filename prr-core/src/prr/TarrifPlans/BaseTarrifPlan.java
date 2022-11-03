package prr.TarrifPlans;

import prr.communications.Communication;

public class BaseTarrifPlan extends TarrifPlan {
    private static final long serialVersionUID = 202211021620L;

    private float _normalSmallTextPrice = 10.0f;
    private float _normalMediumTextPrice = 16.0f;
    private float _goldSmallTextPrice = 10.0f;
    private float _goldMediumTextPrice = 10.0f;
    private float _platinumSmallTextPrice = 0.0f;
    private float _platinumMediumTextPrice = 4.0f;
    private float _platinumLargeTextPrice = 4.0f;

    private float _normalVoicePrice = 20.0f;
    private float _normalVideoPrice = 30.0f;
    private float _goldVoicePrice = 10.0f;
    private float _goldVideoPrice = 20.0f;
    private float _platinumVoicePrice = 10.0f;
    private float _platinumVideoPrice = 10.0f;

    public BaseTarrifPlan() {
    }

    public double getCommunicationPrice(Communication communication) {
        String senderType = communication.getSender().getClient().getLevel().getLevelName();
        String communicationType = communication.getType();
        double units = communication.getUnits();
        float friendsDiscount = communication.getSender().getFriends().containsKey(communication.getReceiver().getKey()) ? 0.5f : 1.0f;
        switch (senderType) {
            case "NORMAL":
                switch (communicationType) {
                    case "VOICE":
                        return _normalVoicePrice * units * friendsDiscount;
                    case "VIDEO":
                        return _normalVideoPrice * units * friendsDiscount;
                    case "TEXT":
                        if (units < 50)
                            return _normalSmallTextPrice;
                        else if (units < 100)
                            return _normalMediumTextPrice;
                        else
                            return (2 * units);
                    default:
                        return 0.0f;
                }
            case "GOLD":
                switch (communicationType) {
                    case "VOICE":
                        return _goldVoicePrice * units * friendsDiscount;
                    case "VIDEO":
                        return _goldVideoPrice * units * friendsDiscount;
                    case "TEXT":
                        if (units < 50)
                            return _goldSmallTextPrice;
                        else if (units < 100)
                            return _goldMediumTextPrice;
                        else
                            return (2 * units);
                    default:
                        return 0.0f;
                }
            case "PLATINUM":
                switch (communicationType) {
                    case "VOICE":
                        return _platinumVoicePrice * units * friendsDiscount;
                    case "VIDEO":
                        return _platinumVideoPrice * units * friendsDiscount;
                    case "TEXT":
                        if (units < 50)
                            return _platinumSmallTextPrice;
                        else if (units < 100)
                            return _platinumMediumTextPrice;
                        else
                            return _platinumLargeTextPrice;
                    default:
                        return 0.0f;
                }
            default:
                return 0.0f;

        }
    }
}
