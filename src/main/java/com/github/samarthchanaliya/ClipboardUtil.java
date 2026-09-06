package com.github.samarthchanaliya;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class ClipboardUtil {
    public static boolean copy(String text){
        try {
            StringSelection selection = new StringSelection(text);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection,selection);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }
}
