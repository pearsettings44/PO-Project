package prr.terminals;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import prr.Notifications.Notifiable;
import prr.Notifications.Notification;
import prr.Notifications.NotificationSender;
import prr.clients.Client;
import prr.communications.Communication;
import prr.exceptions.InvalidCommunicationException;
import prr.exceptions.NoOngoingCommunicationException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable, NotificationSender /* FIXME maybe addd more interfaces */ {

        /** Serial number for serialization. */
        private static final long serialVersionUID = 202208091753L;

        /** The terminal's key. */
        private String _key;

        /** Total payments made by this terminal. */
        private long _payments;

        /** Total debts of this terminal. */
        private long _debts;

        /** The terminal's owner */
        private Client _client;

        /** The terminal's state */
        private State _state;

        private String _prevState = "";

        /** The terminal's friends */
        private Map<String, Terminal> _friends = new TreeMap<>();

        private Map<Integer, Communication> _communications = new TreeMap<>();

        private final Set<Notifiable> subscribers = new HashSet<>();

        public Set<Notifiable> getsubscribers() {
                return subscribers;
        }

        /**
         * Constructor.
         * 
         * @param key    the terminal's key
         * @param client the terminal's owner
         */
        public Terminal(String key, Client client) {
                _key = key;
                _client = client;
                _payments = 0;
                _debts = 0;
                _state = new IdleTerminal(this);

        }

        /**
         * Constructor.
         * 
         * @param key    the terminal's key
         * @param client the terminal's owner
         * @param state  the terminal's state
         */
        public Terminal(String key, Client client, String state) {
                _key = key;
                _client = client;
                _payments = 0;
                _debts = 0;
                switch (state) {
                        case "ON":
                                _state = new IdleTerminal(this);
                                break;
                        case "BUSY":

                                _state = new BusyTerminal(this);
                                break;
                        case "SILENCE":
                                _state = new SilenceTerminal(this);
                                break;
                        case "OFF":
                                _state = new OffTerminal(this);
                                break;
                }

        }

        /**
         * @return the terminal's key
         */
        public String getKey() {
                return _key;
        }

        /**
         * @return the terminal's owner
         */
        public Client getClient() {
                return _client;
        }

        /**
         * @return the terminal's state
         */
        public String getState() {
                return _state.getStateName();
        }

        /**
         * @return the terminal's payments
         */
        public long getPayments() {
                return _payments;
        }

        public void addPayment(long payment) {
                _payments += payment;
        }

        /**
         * @return the terminal's debts
         */
        public long getDebts() {
                return _debts;
        }

        public void addDebt(long debt) {
                _debts += debt;
        }

        /**
         * @return true if the terminal has any friends, false otherwise
         */
        public boolean hasFriends() {
                return !_friends.isEmpty();
        }

        /**
         * @return the terminal's friends
         */
        public Map<String, Terminal> getFriends() {
                return _friends;
        }

        public void setPrevState(String state) {
                _prevState = state;
        }

        public String getPrevState() {
                return _prevState;
        }

        public boolean hasNoCommunications() {
                return _communications.isEmpty();
        }

        public Communication getCommunication(int id) {
                return _communications.get(id);
        }

        public void performPayment(int id) throws InvalidCommunicationException {
                Communication communication = _communications.get(id);
                if (communication == null)
                        throw new InvalidCommunicationException();
                if (!communication.getSender().getKey().equals(getKey()) || communication.isFinished() == false
                                || communication.isPaid() == true)
                        throw new InvalidCommunicationException();
                communication.setPaid();
                addPayment((long) communication.getPrice());
                addDebt(-(long) communication.getPrice());
                Client client = getClient();
                client.tryLevelUp();
                client.tryLevelDown();
        }

        public void addCommunication(Communication communication) {
                _communications.put(communication.getId(), communication);
        }

        public Communication getOngoingCommunication() throws NoOngoingCommunicationException {
                for (Communication c : _communications.values())
                        if (!c.isFinished())
                                return c;
                throw new NoOngoingCommunicationException();
        }

        public long getLastInteractiveCommunicationPrice() {
                Communication last = null;
                for (Communication c : _communications.values())
                        if ((c.getType().equals("VIDEO") || c.getType().equals("VOICE")) && c.isFinished())
                                last = c;
                return (long) last.getPrice();
        }

        /**
         * Gets the terminal's friend's keys formatted as a string.
         * 
         * @return the terminal's friend's keys formatted as a string
         */
        public String friendsToString() {
                String friends = "";
                for (Terminal terminal : _friends.values()) {
                        friends += terminal.getKey() + ",";
                }
                return friends.substring(0, friends.length() - 1);
        }

        /**
         * Adds a friend to the terminal's friend list
         * 
         * @param friend
         */
        public void insertFriend(String friendKey, Terminal friend) {
                _friends.putIfAbsent(friendKey, friend);
        }

        /**
         * Deletes a friend from the terminal's friend list
         * 
         * @param friend
         */
        public void deleteFriend(String friendKey) {
                _friends.remove(friendKey);
        }

        /**
         * Gets a friend from the terminal's friend list
         * 
         * @param friendKey
         * @return the friend
         */
        public Terminal getFriend(String friendKey) {
                return _friends.get(friendKey);
        }

        public abstract class State implements Serializable {
                private static final long serialVersionUID = 202210151200L;

                public abstract String getStateName();
        }

        /**
         * Set the terminal's state
         */
        public void setState(State state) {
                Terminal.this._state = state;
        }

        public Map<Integer, Communication> getSentCommunications() {
                Map<Integer, Communication> sentCommunications = new TreeMap<>();
                for (Communication communication : _communications.values()) {
                        if (communication.getSender().equals(this)) {
                                sentCommunications.put(communication.getId(), communication);
                        }
                }
                return sentCommunications;
        }

        public Map<Integer, Communication> getReceivedCommunications() {
                Map<Integer, Communication> receivedCommunications = new TreeMap<>();
                for (Communication communication : _communications.values()) {
                        if (communication.getReceiver().equals(this)) {
                                receivedCommunications.put(communication.getId(), communication);
                        }
                }
                return receivedCommunications;
        }

        abstract public String getType();

        /**
         * Checks if this terminal can end the current interactive communication.
         *
         * @return true if this terminal is busy (i.e., it has an active interactive
         *         communication) and
         *         it was the originator of this communication.
         **/
        public boolean canEndCurrentCommunication() {
                if (!(getState().equals("BUSY")))
                        return false;
                for (Communication communication : _communications.values())
                        if (communication.getSender().getKey().equals(getKey()) && !communication.isFinished())
                                return true;
                return false;
        }

        /**
         * Checks if this terminal can start a new communication.
         *
         * @return true if this terminal is neither off neither busy, false otherwise.
         **/
        public boolean canStartCommunication() {
                return !(getState().equals("OFF")) && !(getState().equals("BUSY"));
        }

        @Override
        public void subscribe(Notifiable notifiable) {
                this.subscribers.add(notifiable);
        }

        @Override
        public void unsubscribe(Notifiable notifiable) {
                this.subscribers.remove(notifiable);
        }

        @Override
        public boolean isSubscribed(Notifiable notifiable) {
                return this.subscribers.contains(notifiable);
        }

        @Override
        public void toggleSubscription(Notifiable notifiable) {
                if (this.isSubscribed(notifiable)) {
                        this.unsubscribe(notifiable);
                } else {
                        this.subscribe(notifiable);
                }
        }

        @Override
        public void sendNotification(Notification notification) {
                this.subscribers.forEach(subscriber -> subscriber.notify(notification));
        }
}
