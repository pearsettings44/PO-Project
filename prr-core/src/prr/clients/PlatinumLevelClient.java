package prr.clients;

public class PlatinumLevelClient extends Client.Level {
    private static final long serialVersionUID = 202210121211L;

    public PlatinumLevelClient(Client client) {
        client.super();
    }

    @Override
    public String getLevelName() {
        return "PLATINUM";
    }
}