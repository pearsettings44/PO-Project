package prr.clients;

public class NormalLevelClient extends Client.Level {
    private static final long serialVersionUID = 202210121202L;

    public NormalLevelClient(Client client) {
        client.super();
    }

    public void tryForPromotion() {
        float balance = this.getBalance();
        if (balance > 500)
            this.setLevel(new GoldLevelClient(this.getClient()));
    }

    public void tryForDemotion() {
        return;
    }

    @Override
    public String getLevelName() {
        return "NORMAL";
    }
}