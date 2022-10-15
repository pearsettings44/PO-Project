package prr.terminals;

import java.io.Serializable;

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
        private String _state = "IDLE";

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

        }

        /**
         * Constructor.
         * 
         * @param key    the terminal's key
         * @param type   the terminal's type
         * @param client the terminal's owner
         * @param state  the terminal's state
         */
        public Terminal(String key, String client, String state) {
                _key = key;
                _client = client;
                _payments = 0;
                _debts = 0;
                _state = state;

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
                return _state;
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
                return false;
        }
}
