package prr.clients;

public class GoldLevelClient extends Client.Level {
    private static final long serialVersionUID = 202210121210L;

    public GoldLevelClient(Client client) {
        client.super();
    }

    @Override
    public String getLevelName() {
        return "GOLD";
    }
}