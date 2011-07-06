package ui;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * 
 * 
 * FilterComboBox - A JComboBox that allows the user to enter text filtering the combobox's list based on text typed in.
 *
 * To ensure that text the user type in is not recognized as a valid entry when focus is lost extend this class and override
 * getSelectedItem()
 * 
 * 
 * @author mdeblase
 */
@SuppressWarnings("serial")
public class FilterComboBox extends JComboBox implements KeyListener {

   private List<? extends Object> masterList;

   public FilterComboBox() {
      super();
      this.setEditable(true);
      this.getEditor().getEditorComponent().addKeyListener(this);
   }

   public FilterComboBox(List<? extends Object> list) {
      super();
      this.setEditable(true);
      masterList = list;
      for (Object obj : list) {
         this.addItem(obj);
      }
      this.getEditor().getEditorComponent().addKeyListener(this);
   }

   public void keyPressed(KeyEvent e) {}

   public void keyReleased(KeyEvent e) {
      int code = e.getKeyCode();
      if (!(code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT)) {
         if (masterList != null) {
            String txt = this.getEditor().getItem().toString();
            this.removeAllItems();
            boolean show = false;
            for (int i = 0; i <= masterList.size() - 1; i++) {
               Object item = masterList.get(i);
               if (item != null) {
                  String str = item.toString();
                  if (!("".equals(txt)) && (str.toUpperCase().startsWith(txt.toUpperCase()))) {
                     // text is entered...add items starting with txt
                     this.addItem(item);
                     show = true;
                  } else if ("".equals(txt.trim())) {
                     // text was deleted ...this will add back all items
                     this.addItem(item);
                     show = true;
                  } else if ("".equals(str.trim()) && i == 0) {
                     // first item in the list is an empty string
                     this.addItem(item);
                     show = true;
                  }
               } else if (item == null && i == 0) {
                  // first item in the masterList is a null object or empty selection
                  // add it so that is always available as a valid choice
                  this.addItem(item);
                  show = true;
               }
            }
            this.setSelectedIndex(-1);
            this.getEditor().setItem(new String(txt));
            JTextField textField = (JTextField) e.getSource();
            textField.setCaretPosition(textField.getDocument().getLength());
            this.hidePopup();
            if (show) {
               this.showPopup();
            }
         }
      }
   }

   public void keyTyped(KeyEvent e) {}

   public List<? extends Object> getMasterList() {
      return masterList;
   }

   public void setMasterList(List<?> masterList) {
      this.masterList = masterList;
   }

}
