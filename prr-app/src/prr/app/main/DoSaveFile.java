package prr.app.main;

import prr.NetworkManager;
import prr.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import java.io.IOException;

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

	DoSaveFile(NetworkManager receiver) {
		super(Label.SAVE_FILE, receiver);
	}

	@Override
	protected final void execute() {
		try {
			try {
				_receiver.save();
			} catch (MissingFileAssociationException ex) {
				saveAs();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveAs() throws IOException {
		try {
			_receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
		} catch (MissingFileAssociationException e) {
			e.printStackTrace();
		}
	}
}
