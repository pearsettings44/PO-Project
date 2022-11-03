package prr.clients;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import prr.communications.Communication;

public class PlatinumLevelClient extends Client.Level {
    private static final long serialVersionUID = 202210121211L;

    public PlatinumLevelClient(Client client) {
        client.super();
    }

    @Override
    public String getLevelName() {
        return "PLATINUM";
    }

    public void tryForPromotion() {
        return;
    }

    public void tryForDemotion() {
        float balance = this.getBalance();
        if (balance < 0)
            this.setLevel(new NormalLevelClient(this.getClient()));
        else {
            Collection<Communication> communications = this.getClient().getSentCommunications();
            int size = communications.size();
            if (size < 2)
                return;
            List<Communication> lastTwoCommunications = communications.stream().skip(communications.size() - 2)
                    .filter(com -> com.getType().equals("TEXT")).collect(Collectors.toList());
            if (lastTwoCommunications.size() == 2)
                this.setLevel(new GoldLevelClient(this.getClient()));
        }
    }
}