/*
 * Author: Sydney Norman
 * Project: Minesweeper
 * Date: October 1st, 2017
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowEvent;

/*
 * CustomBombs allows the user to enter their custom board size and bomb count.
 */
public class CustomBombs implements ChangeListener {

    private JFrame customFrame;
    private JSlider heightSlider, widthSlider, bombSlider;
    private JButton acceptButton;
    private JLabel heightLabel, widthLabel, bombLabel;
    private JLabel heightValueLabel, widthValueLabel, bombValueLabel;

    private JPanel sliderView, labelView, valueView, acceptView;

    private int length = 5;
    private int width = 5;
    private int bombCount = 1;

    /*
     * Constructor for the Custom Bomb Console
     */
    CustomBombs(JFrame frame, Bombs.CustomBombHandler CBH) {
        customFrame = frame;

        // Get the content pane, onto which everything is added
        Container c = customFrame.getContentPane();

        // Allocate two major panels to hold interface
        valueView = new JPanel();     // Used to hole slider values
        sliderView = new JPanel();    // Used to hold main game board
        labelView = new JPanel();     // Used to hold slider labels
        acceptView = new JPanel();    // Used to hold the accept button

        // Set Borders for Views
        valueView.setBorder(new EmptyBorder(10, 10, 10, 20));
        sliderView.setBorder(new EmptyBorder(10, 20, 10, 10));
        labelView.setBorder(new EmptyBorder(10, 20, 10, 10));
        acceptView.setBorder(new EmptyBorder(10, 10, 20, 10));

        // Allocate Buttons
        acceptButton = new JButton("Accept");
        acceptButton.addActionListener(CBH);

        // Allocate Sliders
        heightSlider = new JSlider(JSlider.HORIZONTAL, 5, 10, 5);
        widthSlider = new JSlider(JSlider.HORIZONTAL, 5, 10, 5);
        bombSlider = new JSlider(JSlider.HORIZONTAL, 1, 50, 1);

        // Customize Sliders
        heightSlider.setMajorTickSpacing(5);
        heightSlider.setPaintLabels(true);
        heightSlider.addChangeListener(this);

        widthSlider.setMajorTickSpacing(5);
        widthSlider.setPaintLabels(true);
        widthSlider.addChangeListener(this);

        bombSlider.setMajorTickSpacing(49);
        bombSlider.setPaintLabels(true);
        bombSlider.addChangeListener(this);

        // Allocate Labels
        heightLabel = new JLabel("Height");
        widthLabel = new JLabel("Width");
        bombLabel = new JLabel("Bombs");
        heightValueLabel = new JLabel("5");
        widthValueLabel = new JLabel("5");
        bombValueLabel = new JLabel("1");

        // Customize Labels
        heightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        widthLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bombLabel.setHorizontalAlignment(SwingConstants.CENTER);
        heightValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        widthValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bombValueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the interface elements to the label view
        labelView.setLayout(new GridLayout(3, 1, 25, 5));
        labelView.add(heightLabel);
        labelView.add(widthLabel);
        labelView.add(bombLabel);

        // Add the interface elements to the slider view
        sliderView.setLayout(new GridLayout(3, 1, 5, 5));
        sliderView.add(heightSlider);
        sliderView.add(widthSlider);
        sliderView.add(bombSlider);

        // Add the interface elements to the value view
        valueView.setLayout(new GridLayout(3, 1, 25, 5));
        valueView.add(heightValueLabel);
        valueView.add(widthValueLabel);
        valueView.add(bombValueLabel);

        // Add the interface element to the accept view
        acceptView.setLayout(new GridLayout(1, 1, 5, 5));
        acceptView.add(acceptButton);

        // Add the views to the container
        c.add(labelView, BorderLayout.WEST);
        c.add(sliderView, BorderLayout.CENTER);
        c.add(valueView, BorderLayout.EAST);
        c.add(acceptView, BorderLayout.SOUTH);

        customFrame.setSize(400, 300);
        customFrame.setResizable(false);
        customFrame.setVisible(true);
    }

    /*
     * Responds to the state change from the slider
     *
     * @param   e       The change event occur which called the function
     */
    public void stateChanged(ChangeEvent e) {
        JSlider curSlider = (JSlider)e.getSource();

        int value = curSlider.getValue();

        if (curSlider == heightSlider) {
            heightValueLabel.setText(Integer.toString(value));
            length = value;
        }
        else if (curSlider == widthSlider) {
            widthValueLabel.setText(Integer.toString(value));
            width = value;
        }
        else if (curSlider == bombSlider) {
            bombValueLabel.setText(Integer.toString(value));
            bombCount = value;
        }

        // Update Bomb Slider Range
        int max = length * width / 2;
        bombSlider.setMaximum(max);
        customFrame.repaint();
    }

    /*
     * Retrieves the length set from the slider.
     *
     * @return      the desired board length
     */
    public int getLength() {
        return length;
    }

    /*
     * Retrieves the width set from the slider.
     *
     * @return      the desired board width
     */
    public int getWidth() {
        return width;
    }

    /*
     * Retrieves the bomb count from the slider.
     *
     * @return      the desired bomb count
     */
    public int getBombCount() {
        return bombCount;
    }

    /*
     * Close the custom bomb window.
     */
    public void close() {
        customFrame.dispatchEvent(new WindowEvent(customFrame, WindowEvent.WINDOW_CLOSING));
    }


}
