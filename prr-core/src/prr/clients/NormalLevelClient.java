package prr.clients;

public class NormalLevelClient extends Client.Level {
    private static final long serialVersionUID = 202210121202L;

    public NormalLevelClient(Client client) {
        client.super();
    }

    @Override
    public String getLevelName() {
        return "NORMAL";
    }
}