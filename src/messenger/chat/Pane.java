package messenger.chat;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Pane extends JTextPane{

	
	
	
	public void appendLine(JPanel panel) {
        try {
            Document doc = this.getDocument();
            doc.insertString(doc.getLength(), System.lineSeparator(), null);
            this.insertComponent(panel);
        } catch(BadLocationException e) {
            System.err.println(e);
        }
    }
	
	
}
