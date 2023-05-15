package View;

import Core.VegetableFactory;
import Exceptions.VegetableNotAvailableException;
import Model.Salad;
import Model.Vegetables.Vegetable;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

public class VegetableGUI extends JFrame {

    private Salad salad;
    private final VegetableFactory vegetableFactory = new VegetableFactory();
    private JTable vegetableListTable;
    private JButton addVegetableButton;
    private JButton sortVegetablesButton;
    private JButton saveSaladButton;
    private JButton loadSaladButton;
    private JTextArea vegetableListTextArea;
    private JButton removeVegetableButton;

    public VegetableGUI() {
        super("Овочевий салат");
        salad = new Salad();

        initializeGUI();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeGUI() {
        JPanel inputPanel = new JPanel();

        addVegetableButton = new JButton("Додати овоч");
        addVegetableButton.addActionListener(e -> {
            try {
                addVegetable();
            } catch (VegetableNotAvailableException ex) {
                ex.printStackTrace();
            }
        });
        inputPanel.add(addVegetableButton);

        sortVegetablesButton = new JButton("Сортувати за калоріями");
        sortVegetablesButton.addActionListener(e -> sortVegetables());
        inputPanel.add(sortVegetablesButton);

        saveSaladButton = new JButton("Зберегти салат");
        saveSaladButton.addActionListener(e -> saveSaladToFile());
        inputPanel.add(saveSaladButton);

        loadSaladButton = new JButton("Завантажити салат");
        loadSaladButton.addActionListener(e -> loadSaladFromFile());
        inputPanel.add(loadSaladButton);

        removeVegetableButton = new JButton("Видалити овоч");
        removeVegetableButton.addActionListener(e -> removeVegetable());
        inputPanel.add(removeVegetableButton);

        add(inputPanel, BorderLayout.NORTH);

        vegetableListTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(vegetableListTable);
        add(scrollPane, BorderLayout.CENTER);

        vegetableListTextArea = new JTextArea();
        add(new JScrollPane(vegetableListTextArea), BorderLayout.CENTER);

        vegetableListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshVegetableList();
    }

    private void addVegetable() throws VegetableNotAvailableException {
        String name = JOptionPane.showInputDialog(this, "Введіть назву овоча:");
        if (name != null && !name.isEmpty()) {
            double calories = Double.parseDouble(JOptionPane.showInputDialog(this, "Введіть калорійність овоча:"));
            Vegetable vegetable = vegetableFactory.Create(name, (int) calories);
            salad.addVegetable(vegetable);
            refreshVegetableList();
        }
    }

    private void sortVegetables() {
        salad.sortVegetablesByCalories();
        refreshVegetableList();
    }

    private void saveSaladToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(salad);
                JOptionPane.showMessageDialog(this, "Салат успішно збережено до файлу");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadSaladFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                salad = (Salad) ois.readObject();
                refreshVegetableList();
                JOptionPane.showMessageDialog(this, "Салат успішно завантажено з файлу");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void removeVegetable() {
        String input = JOptionPane.showInputDialog(this, "Введіть номер овоча для видалення:");
        try {
            int vegetableNumber = Integer.parseInt(input);
            if (vegetableNumber >= 1 && vegetableNumber <= salad.getVegetables().size()) {
                int selectedIndex = vegetableNumber - 1;
                salad.removeVegetable(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Введено недійсний номер овоча");
            }
        } catch (NumberFormatException | VegetableNotAvailableException e) {
            JOptionPane.showMessageDialog(this, "Введено некоректне значення номера овоча");
        }

        refreshVegetableList();
    }

    private void refreshVegetableList() {
        vegetableListTextArea.setText("");
        for (Vegetable vegetable : salad.getVegetables()) {
            vegetableListTextArea.append(vegetable.toString() + "\n");
        }
    }
}

