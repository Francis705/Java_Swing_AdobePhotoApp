import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

class CheckboxAction extends AbstractAction {
        public CheckboxAction(String text) {
            super(text);
        }


        public void actionPerformed(ActionEvent event) {
            JCheckBox cbLog = (JCheckBox) event.getSource();
            if (cbLog.isSelected()) {
                System.out.println("Logging is enabled");
            } else {
                System.out.println("Logging is disabled");
            }
        }
    }

