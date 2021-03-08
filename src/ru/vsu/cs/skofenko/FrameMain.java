package ru.vsu.cs.skofenko;

import ru.vsu.cs.skofenko.logic.Logic;
import ru.vsu.cs.util.JTableUtils;
import ru.vsu.cs.util.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;

public class FrameMain extends JFrame {

    private JPanel panelMain;
    private JTable tableIn;
    private JButton loadInputButton;
    private JButton writeInputButton;
    private JButton solveButton;
    private JButton saveButton;
    private JButton mySolveButton;
    private JTextField leftCornerI;
    private JTextField leftCornerJ;
    private JTextField widthField;
    private JTextField heightField;

    private final JFileChooser fileChooserOpen;
    private final JFileChooser fileChooserSave;

    public FrameMain() {
        this.setTitle("Task 3");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        JTableUtils.initJTableForArray(tableIn, 40, true, true, true, true);
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserOpen.addChoosableFileFilter(filter);

        fileChooserSave = new JFileChooser();
        fileChooserSave.setCurrentDirectory(new File("."));
        fileChooserSave.addChoosableFileFilter(filter);

        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        loadInputButton.addActionListener(e -> {
            try {
                if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    int[][] data = IOClass.readMatrixFromFile(fileChooserOpen.getSelectedFile().getPath());
                    JTableUtils.writeArrayToJTable(tableIn, data);
                }
            } catch (Exception exception) {
                SwingUtils.showErrorMessageBox(exception);
            }
        });

        solveButton.addActionListener(e -> solve(true));

        saveButton.addActionListener(e -> {
            String file = "";
            try {
                if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    file = fileChooserSave.getSelectedFile().getPath();
                    if (!file.toLowerCase().endsWith(".txt")) {
                        file += ".txt";
                    }
                    IOClass.writeMatrixToFile(file,
                            new int[][]{{Integer.parseInt(leftCornerI.getText()), Integer.parseInt(leftCornerJ.getText())},
                                    {Integer.parseInt(widthField.getText()), Integer.parseInt(heightField.getText())}});
                }
            } catch (NumberFormatException exception) {
                try {
                    IOClass.writeMatrixToFile(file, new int[0][0]);
                } catch (FileNotFoundException ignored) {
                }
            } catch (Exception exception) {
                SwingUtils.showErrorMessageBox(exception);
            }
        });

        writeInputButton.addActionListener(e -> {
            try {
                if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    String file = fileChooserSave.getSelectedFile().getPath();
                    if (!file.toLowerCase().endsWith(".txt")) {
                        file += ".txt";
                    }
                    int[][] outArray = JTableUtils.readIntMatrixFromJTable(tableIn);
                    IOClass.writeMatrixToFile(file, outArray);
                }
            } catch (Exception exception) {
                SwingUtils.showErrorMessageBox(exception);
            }
        });
        mySolveButton.addActionListener(e -> solve(false));
    }

    private void solve(boolean useStandardStack) {
        try {
            int[][] mat = JTableUtils.readIntMatrixFromJTable(tableIn);
            Answer answer = Logic.solution(mat, useStandardStack);
            leftCornerI.setText(String.valueOf(answer.getLeftI()));
            leftCornerJ.setText(String.valueOf(answer.getLeftJ()));
            widthField.setText(String.valueOf(answer.getWidth()));
            heightField.setText(String.valueOf(answer.getHeight()));

        } catch (Exception exception) {
            SwingUtils.showErrorMessageBox(exception);
        }
    }
}
