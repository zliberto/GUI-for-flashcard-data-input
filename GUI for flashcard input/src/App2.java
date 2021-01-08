//Zuzanna Liberto
//12.07.2018

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;

public class App2 extends JFrame implements ActionListener {
    JPanel mpanel, qpanel, apanel, bpanel, upanel;
    JTextField tf1, tf2, tf3;
    JButton jb;
    JLabel jl1, jl2, jl3;
    String path;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    QAData qaObject = new QAData();


    public static void main(String[] args) {
        App2 A2object = new App2();
    }

    App2() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 200);
        this.setTitle("App2");
        mpanel = new JPanel();
        this.add(mpanel);
        mpanel.setLayout(new GridLayout(4, 1));
        qpanel = new JPanel();
        mpanel.add(qpanel);
        apanel = new JPanel();
        mpanel.add(apanel);
        upanel = new JPanel();
        mpanel.add(upanel);
        bpanel = new JPanel();
        mpanel.add(bpanel);

        jl1 = new JLabel("Question: ");
        qpanel.add(jl1);
        tf1 = new JTextField(20);
        qpanel.add(tf1);
        tf1.addActionListener(this);

        jl2 = new JLabel("Answer: ");
        apanel.add(jl2);
        tf2 = new JTextField(20);
        apanel.add(tf2);
        tf2.addActionListener(this);

        jl3 = new JLabel("Path: ");
        upanel.add(jl3);
        tf3 = new JTextField(20);
        upanel.add(tf3);
        tf3.addActionListener(this);

        jb = new JButton("Update");
        bpanel.add(jb);
        jb.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == jb) {
            load();
            save();
            tf1.setText("");
            tf2.setText("");
        }
    }


    void load() {
        try {
            path = tf3.getText();
            File file = new File(path);
            boolean fileIsOk = file.exists() && !(tf3.getText().equals("")) && !(file.isDirectory());

            if (fileIsOk) {
                FileInputStream fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                qaObject.question = (String[]) ois.readObject();
                qaObject.answer = (String[]) ois.readObject();
            } else {
                qaObject.question = new String[0];
                qaObject.answer = new String[0];
                System.err.println("Input a valid file");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("No such class found");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("I/O exception during the load");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("The object you are trying to read has the null value");
            e.printStackTrace();
        } finally {
            if (ois != null)
                try {
                    ois.close();
                }
                catch (IOException e) {
                    System.err.println("IO Exception!");
                }
        }

    }

    void save() {
        try {

            File file = new File(path);
            boolean fileIsOk = file.exists() && !(tf3.getText().equals("")) && !(file.isDirectory());
            if (fileIsOk) {
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                qaObject.question = Arrays.copyOf(qaObject.question, qaObject.question.length + 1);
                qaObject.question[(qaObject.question.length - 1)] = tf1.getText();

                qaObject.answer = Arrays.copyOf(qaObject.answer, qaObject.answer.length + 1);
                qaObject.answer[(qaObject.answer.length - 1)] = tf2.getText();

                oos.writeObject(qaObject.question);
                oos.writeObject(qaObject.answer);
            }

        } catch (IOException e) {
            System.err.println("I/O exception during the save");
        } catch (NullPointerException e) {
            System.err.println("The object you are trying to save has the null value");
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    System.err.println("IO Exception!");
                }
            }
        }
    }
}

class QAData implements Serializable {
    String[] question = new String[0];
    String[] answer = new String[0];
}