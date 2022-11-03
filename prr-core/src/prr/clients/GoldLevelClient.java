package prr.clients;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import prr.communications.Communication;

public class GoldLevelClient extends Client.Level {
    private static final long serialVersionUID = 202210121210L;

    public GoldLevelClient(Client client) {
        client.super();
    }

    public void tryForPromotion() {
        float balance = this.getBalance();
        Collection<Communication> communications = this.getClient().getSentCommunications();
        int size = communications.size();
        if (size < 5)
            return;
        List<Communication> lastFiveCommunications = communications.stream().skip(communications.size() - 5)
                .filter(com -> com.getType().equals("VIDEO") && com.getSender().getType().equals("GOLD"))
                .collect(Collectors.toList());
        if (lastFiveCommunications.size() == 5 && balance >= 0)
            this.setLevel(new PlatinumLevelClient(this.getClient()));
    }

    public void tryForDemotion() {
        float balance = this.getBalance();
        if (balance < 0)
            this.setLevel(new NormalLevelClient(this.getClient()));
    }

    @Override
    public String getLevelName() {
        return "GOLD";
    }
}