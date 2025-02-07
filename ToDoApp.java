import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToDoApp {
    private JFrame frame;
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private TaskManager taskManager;

    public ToDoApp() {
        taskManager = new TaskManager();
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("To-Do List");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        refreshTaskList();

        JScrollPane scrollPane = new JScrollPane(taskList);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JTextField taskField = new JTextField(20);
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton completeButton = new JButton("Complete");

        panel.add(taskField);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(completeButton);
        frame.add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String taskText = taskField.getText();
            if (!taskText.isEmpty()) {
                taskManager.addTask(taskText);
                taskField.setText("");
                refreshTaskList();
            }
        });

        removeButton.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                taskManager.removeTask(selectedIndex);
                refreshTaskList();
            }
        });

        completeButton.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                taskManager.markTaskCompleted(selectedIndex);
                refreshTaskList();
            }
        });

        frame.setVisible(true);
    }

    private void refreshTaskList() {
        listModel.clear();
        for (Task task : taskManager.getTasks()) {
            listModel.addElement(task.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoApp::new);
    }
}
