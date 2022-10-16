package prr.terminals;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import prr.clients.Client;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */ {

        /** Serial number for serialization. */
        private static final long serialVersionUID = 202208091753L;

        /** The terminal's key. */
        private String _key;

        /** Total payments made by this terminal. */
        private float _payments;

        /** Total debts of this terminal. */
        private float _debts;

        /** The terminal's owner */
        private String _client;

        /** The terminal's state */
        private State _state;

        /** The terminal's friends */
        private Map<String, Terminal> _friends = new TreeMap<>();

        /**
         * Constructor.
         * 
         * @param key    the terminal's key
         * @param type   the terminal's type
         * @param client the terminal's owner
         */
        public Terminal(String key, String client) {
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
        public Terminal(String key, String client, String state) {
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
        public String getClient() {
                return _client;
        }

        /**
         * @return the terminal's state
         */
        public String getState() {
                return _state.getState();
        }

        /**
         * @return the terminal's payments
         */
        public float getPayments() {
                return _payments;
        }

        /**
         * @return the terminal's debts
         */
        public float getDebts() {
                return _debts;
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

                public abstract String getState();
        }

        /**
         * Checks if this terminal can end the current interactive communication.
         *
         * @return true if this terminal is busy (i.e., it has an active interactive
         *         communication) and
         *         it was the originator of this communication.
         **/
        public boolean canEndCurrentCommunication() {
                // FIXME add implementation code
                // so para correr
                return false;
        }

        /**
         * Checks if this terminal can start a new communication.
         *
         * @return true if this terminal is neither off neither busy, false otherwise.
         **/
        public boolean canStartCommunication() {
                // FIXME add implementation code
                // so para correr
                return true;
        }
}
