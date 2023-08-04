package clock_app;

import java.lang.*;
import javax.swing.*;
import java.awt.*;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

class dclock extends JPanel implements Runnable{
    Calendar calendar;
    SimpleDateFormat timeFormat;
    SimpleDateFormat dayFormat;
    SimpleDateFormat dateFormat;
    JLabel timeLabel;
    JLabel dayLabel;
    JLabel dateLabel;
    JLabel label;
    String time;
    String day;
    String date;
    JProgressBar bar;
    int dayOfYear;
    int val;

    dclock(){
        timeFormat = new SimpleDateFormat("hh:mm:ss a");
        dayFormat = new SimpleDateFormat("EEEE");
        dateFormat = new SimpleDateFormat("MMMMM dd, yyyy");

        Calendar calOne = Calendar.getInstance(TimeZone.getDefault());
        dayOfYear = calOne.get(Calendar.DAY_OF_YEAR);

        timeLabel = new JLabel();
        dayLabel = new JLabel();
        dateLabel = new JLabel();
        label = new JLabel();
        bar = new JProgressBar();

        this.setLayout(null);
        this.setBounds(50, 75, 400, 400);
        this.setBackground(new Color(26,80,139));

        timeLabel.setLocation(80, 50);
        timeLabel.setSize(250, 100);
        timeLabel.setFont(new Font("League Gothic",Font.BOLD,66));
        timeLabel.setForeground(new Color(241,246,249));
        timeLabel.setForeground(Color.WHITE);

        dayLabel.setLocation(80, 140);
        dayLabel.setSize(250, 75);
        dayLabel.setFont(new Font("League Gothic",Font.BOLD,36));
        dayLabel.setForeground(Color.WHITE);

        dateLabel.setLocation(80, 180);
        dateLabel.setSize(250, 75);
        dateLabel.setFont(new Font("League Gothic",Font.BOLD,36));
        dateLabel.setForeground(Color.WHITE);

        label.setText("Year's Progress:");
        label.setLocation(80, 240);
        label.setSize(250, 75);
        label.setFont(new Font("League Gothic",Font.BOLD,26));
        label.setForeground(Color.WHITE);
        
        bar.setBounds(80, 300, 300, 40);
        bar.setStringPainted(true);
        bar.setForeground(new Color(22,36,71));
        bar.setBorderPainted(false);
        bar.setStringPainted(true);


        Thread m1 = new Thread(this);
        m1.start();

        this.add(timeLabel);
        this.add(dayLabel);
        this.add(dateLabel);
        this.add(label);
        this.add(bar);
    }
    @Override
    public void run() {
        while(true) {
            time = timeFormat.format(Calendar.getInstance().getTime());
            timeLabel.setText(time);

            day = dayFormat.format(Calendar.getInstance().getTime());
            dayLabel.setText(day);

            date = dateFormat.format(Calendar.getInstance().getTime());
            dateLabel.setText(date);

            double val = (dayOfYear/365.0)*100;
            int val2 = (int) val;
            bar.setValue(val2);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


class alarm extends JPanel implements ActionListener, Runnable {

    JComboBox comboBox;
    JTextField textField1;
    JTextField textField2;
    JTextField textField3;
    JButton button;
    SimpleDateFormat timeFormat1;
    SimpleDateFormat timeFormat2;
    SimpleDateFormat timeFormat3;
    SimpleDateFormat timeFormat4;

    Calendar calendar;

    int alarm_hr,alarm_min, alarm_sec;
    int curr_hr, curr_min, curr_sec;
    String in_am_pm, curr_am_pm, alarm_text;
    String[] responses = {"Dismiss", "Snooze"};
    int response;
    ImageIcon icon2 = new ImageIcon("C:\\Users\\Gaurav\\Documents\\NetBeansProjects\\app1\\src\\clock_app\\assets\\icons8-alarm-clock-50.png");
    ImageIcon icon = new ImageIcon("C:\\Users\\Gaurav\\Documents\\NetBeansProjects\\app1\\src\\clock_app\\assets\\icons8-alarm-25.png");
    File file = new File("C:\\Users\\Gaurav\\Documents\\NetBeansProjects\\app1\\src\\clock_app\\assets\\Voices.wav");
    AudioInputStream audioInputStream;
    Clip clip;
    String[] am_pm = {"AM", "PM"};
    JLabel label;
    JLabel label_hr, label_min, label_sec, label_alarm;

    alarm(){
        calendar = Calendar.getInstance(TimeZone.getDefault());

        try {
            audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        label = new JLabel();
        label_hr = new JLabel();
        label_min = new JLabel();
        label_sec = new JLabel();
        label_alarm = new JLabel();

        label.setText("Set Alarm");
        label.setIcon(icon2);
        label.setLocation(120, 30);
        label.setSize(200, 100);
        label.setFont(new Font("League Gothic",Font.PLAIN,50));
        label.setForeground(new Color(240, 240, 240));

        label_hr.setText("Hr:");
        label_hr.setLocation(110, 140);
        label_hr.setSize(40, 40);
        label_hr.setFont(new Font("League Gothic",Font.PLAIN,26));
        label_hr.setForeground(new Color(240, 240, 240));

        label_min.setText("Min:");
        label_min.setLocation(170, 140);
        label_min.setSize(40, 40);
        label_min.setFont(new Font("League Gothic",Font.PLAIN,26));
        label_min.setForeground(new Color(240, 240, 240));

        label_sec.setText("Sec:");
        label_sec.setLocation(230, 140);
        label_sec.setSize(40, 40);
        label_sec.setFont(new Font("League Gothic",Font.PLAIN,26));
        label_sec.setForeground(new Color(240, 240, 240));

        label_alarm.setText("Alarm set for --:--:--");
        label_alarm.setLocation(150, 300);
        label_alarm.setSize(200, 60);
        label_alarm.setFont(new Font("League Gothic",Font.PLAIN,24));
        label_alarm.setForeground(new Color(240, 240, 240));

        timeFormat1 = new SimpleDateFormat("h");
        timeFormat2 = new SimpleDateFormat("m");
        timeFormat3 = new SimpleDateFormat("s");
        timeFormat4 = new SimpleDateFormat("a");

        textField1 = new JTextField("--");
        textField1.setLocation(110, 180);
        textField1.setSize(50, 35);
        textField1.setFont(new Font("San-Serif",Font.BOLD,15));

        textField2 = new JTextField("--");
        textField2.setLocation(170, 180);
        textField2.setSize(50, 35);
        textField2.setFont(new Font("San-Serif",Font.BOLD,15));

        textField3 = new JTextField("--");
        textField3.setLocation(230, 180);
        textField3.setSize(50, 35);
        textField3.setFont(new Font("San-Serif",Font.BOLD,15));

        comboBox = new JComboBox(am_pm);
        comboBox.setLocation(290, 180);
        comboBox.setSize(50, 35);
        comboBox.setSelectedIndex(0);
        comboBox.setFont(new Font("San-Serif",Font.BOLD,15));
        comboBox.setBackground(new Color(26,80,139));
        comboBox.setForeground(Color.WHITE);
        comboBox.addActionListener(this);
        this.add(comboBox);

        button = new JButton("Submit");
        button.setLocation(260, 235);
        button.setSize(80, 35);
        button.addActionListener(this);
        button.setFont(new Font("San-Serif",Font.BOLD,12));
        button.setBackground(new Color(22,36,71));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        Thread m1 = new Thread(this);
        m1.start();

        this.add(label);
        this.add(label_hr);
        this.add(label_min);
        this.add(label_sec);
        this.add(label_alarm);
        this.add(textField1);
        this.add(textField2);
        this.add(textField3);
        this.add(button);
        this.setLayout(null);
        this.setBounds(0, 0, 450, 400);
        this.setBackground(new Color(26,80,139));
    }

    public void mouseClicked(MouseEvent e){
        textField1.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==comboBox) {
            in_am_pm = (String) comboBox.getSelectedItem();
        }

        if(e.getSource() == button) {
            alarm_hr = Integer.parseInt(textField1.getText());
            alarm_min = Integer.parseInt(textField2.getText());
            alarm_sec = Integer.parseInt(textField3.getText());
            alarm_text = textField1.getText()+":"+textField2.getText()+":"+textField3.getText();
            label_alarm.setText("Alarm set for "+alarm_text);
            System.out.println(alarm_hr + ":" + alarm_min + ":" + alarm_sec + " " + in_am_pm);
            response = -1;
        }
    }

    public void run() {
        while(true) {
            curr_hr = Integer.parseInt(timeFormat1.format(Calendar.getInstance().getTime()));
            curr_min = Integer.parseInt(timeFormat2.format(Calendar.getInstance().getTime()));
            curr_sec = Integer.parseInt(timeFormat3.format(Calendar.getInstance().getTime()));
            curr_am_pm = timeFormat4.format(Calendar.getInstance().getTime()).toUpperCase(Locale.ROOT);

            if(alarm_hr==curr_hr && alarm_min==curr_min && alarm_sec==curr_sec && in_am_pm.equals(curr_am_pm)){
                //JOptionPane.showMessageDialog(null, "Wake Up!", "Wake Up, it's time!", JOptionPane.WARNING_MESSAGE);
                clip.start();
                response = JOptionPane.showOptionDialog(this,
                        "It's time to wake up.",
                        "Wake Up!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        icon, responses, 0);
            }

            if(response == 0){
                clip.stop();
                label_alarm.setText("Alarm set for --:--:--");
            }
            else if (response == 1){
                clip.stop();
                if(alarm_min>=59){
                    alarm_min=0;
                }
                textField2.setText(Integer.toString(alarm_min+=1));
                alarm_text = textField1.getText()+":"+textField2.getText()+":"+textField3.getText();
                label_alarm.setText("Alarm set for "+alarm_text);
                //alarm_min+=1;
                System.out.println(alarm_hr + ":" + alarm_min + ":" + alarm_sec + " " + in_am_pm);
                response=2;
            }
            else if(response == 2){
                alarm_text = textField1.getText()+":"+textField2.getText()+":"+textField3.getText();
                label_alarm.setText("Alarm set for "+alarm_text);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class aclock extends JPanel{
    public int hour;
    public int min;
    public int sec;
    SimpleDateFormat timeFormat1;
    SimpleDateFormat timeFormat2;
    String date, day;
    ClockDial cd;

    public aclock() {
        cd=new ClockDial(this);
        Date curr=new Date();
        String time=curr.toString();
        hour=Integer.parseInt(time.substring(11,13));
        min=Integer.parseInt(time.substring(14,16));
        sec=Integer.parseInt(time.substring(17,19));
        timeFormat1 = new SimpleDateFormat("E");
        timeFormat2 = new SimpleDateFormat("d");

        ClockEngine.setPriority(ClockEngine.getPriority()+3);
        ClockEngine.start();

        this.add(cd);
        this.setLayout(null);
        this.setBounds(-28, -34, 500, 500);
        this.setBackground(new Color(26,80,139));
    }

    Thread ClockEngine=new Thread()

    {
        int newsec,newmin;

        public void run()
        {
            while(true)
            {
                newsec=(sec+1)%60;
                newmin=(min+(sec+1)/60)%60;
                hour=(hour+(min+(sec+1)/60)/60)%12;
                sec=newsec;
                min=newmin;

                day = timeFormat1.format(Calendar.getInstance().getTime());
                date = timeFormat2.format(Calendar.getInstance().getTime());
                
                try {

                    Thread.sleep(1000);

                } catch (InterruptedException ex) {}

                cd.repaint();
            }
        }
    };
}


class ClockDial extends JPanel{

    aclock parent;
    public ClockDial(aclock pt){
        this.setSize(600,600);
        parent=pt;
        Timer updateTimer;
        int DELAY = 100;

        updateTimer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date currentTime = new Date();
                String formatTimeStr = "hh:mm:ss";
                DateFormat formatTime = new SimpleDateFormat(formatTimeStr);
                String formattedTimeStr = formatTime.format(currentTime);
            }
        });
        updateTimer.start();
    }


    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(26,80,139));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.fillOval(100, 89,300,300);
         g.setColor(new Color(22,36,71));
        g.fillOval(105, 94,290,290);
        g.setColor(Color.WHITE);
        g.fillOval(237,237,15,15);
        

        g.setFont(g.getFont().deriveFont(Font.BOLD,18));
        g.drawString(parent.day.toUpperCase(Locale.ROOT), 290, 240);
        g.drawString(parent.date.toUpperCase(Locale.ROOT), 295, 264);

        g.setFont(g.getFont().deriveFont(Font.BOLD,32));
        for(int i=1;i<=12;i++)
            g.drawString(Integer.toString(i),240-(i/12)*11+(int)(120*Math.sin(i*Math.PI/6)),253-(int)(120*Math.cos(i*Math.PI/6)));

        double minsecdeg=(double)Math.PI/30;
        double hrdeg=(double)Math.PI/6;
        int tx,ty;
        int xpoints[]=new int[3];
        int ypoints[]=new int[3];

        //second hand
        tx=245+(int)(105*Math.sin(parent.sec*minsecdeg));
        ty=245-(int)(105*Math.cos(parent.sec*minsecdeg));
        g.drawLine(245,245,tx,ty);

        //minute hand
        tx=245+(int)(100*Math.sin(parent.min*minsecdeg));
        ty=245-(int)(100*Math.cos(parent.min*minsecdeg));
        xpoints[0]=245;
        xpoints[1]=tx+2;
        xpoints[2]=tx-2;
        ypoints[0]=245;
        ypoints[1]=ty+2;
        ypoints[2]=ty-2;
        g.fillPolygon(xpoints, ypoints,3);

        //hour hand
        tx=245+(int)(80*Math.sin(parent.hour*hrdeg+parent.min*Math.PI/360));
        ty=245-(int)(80*Math.cos(parent.hour*hrdeg+parent.min*Math.PI/360));
        xpoints[1]=tx+4;
        xpoints[2]=tx-4;
        ypoints[1]=ty-4;
        ypoints[2]=ty+4;
        g.fillPolygon(xpoints, ypoints, 3);

    }
}


/**
 *
 * @author Gaurav Singh
 * @author Anmol Kumar
 * @author Pratham Aggarwal
 * @author Armaan Gulia
 * @author Inderjeet Arya
 * @author Pankaj
 * @version 1.0
 * @since 2021-03-30
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    int posX=0,posY=0;
    CardLayout cardLayout;
    URI temp;
    String URL = "www.google.com";
    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    
    public Main() {
       
        super.setTitle("Clock App");
                
        try {
            temp = new URI(URL);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        initComponents();
        cardLayout = (CardLayout)(panelCards.getLayout());
        this.setResizable(false);
                
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        sidepane = new javax.swing.JPanel();
        btn_analog = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btn_digital = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btn_alarm = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        documentation = new javax.swing.JLabel();
        panelCards = new javax.swing.JPanel();
        panelCard3 = new javax.swing.JPanel();
        panelCard2 = new javax.swing.JPanel();
        panelCard1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sidepane.setBackground(new java.awt.Color(20, 39, 78));

        btn_analog.setBackground(new java.awt.Color(3, 83, 164));
        btn_analog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_analogMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_analogMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_analogMouseExited(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/clock_app/images/icons8-clock-16.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Proxima Nova Rg", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(240, 240, 240));
        jLabel2.setText("Analog Clock");

        javax.swing.GroupLayout btn_analogLayout = new javax.swing.GroupLayout(btn_analog);
        btn_analog.setLayout(btn_analogLayout);
        btn_analogLayout.setHorizontalGroup(
            btn_analogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_analogLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_analogLayout.setVerticalGroup(
            btn_analogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_analogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        btn_digital.setBackground(new java.awt.Color(3, 83, 164));
        btn_digital.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_digitalMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_digitalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_digitalMouseExited(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/clock_app/images/icons8-display-16.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Proxima Nova Rg", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(240, 240, 240));
        jLabel4.setText("Digital Clock");

        javax.swing.GroupLayout btn_digitalLayout = new javax.swing.GroupLayout(btn_digital);
        btn_digital.setLayout(btn_digitalLayout);
        btn_digitalLayout.setHorizontalGroup(
            btn_digitalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_digitalLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_digitalLayout.setVerticalGroup(
            btn_digitalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_digitalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        btn_alarm.setBackground(new java.awt.Color(3, 83, 164));
        btn_alarm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_alarmMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_alarmMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_alarmMouseExited(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/clock_app/images/icons8-alarm-16.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Proxima Nova Rg", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(240, 240, 240));
        jLabel6.setText("Alarm");

        javax.swing.GroupLayout btn_alarmLayout = new javax.swing.GroupLayout(btn_alarm);
        btn_alarm.setLayout(btn_alarmLayout);
        btn_alarmLayout.setHorizontalGroup(
            btn_alarmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_alarmLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_alarmLayout.setVerticalGroup(
            btn_alarmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_alarmLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addContainerGap())
        );

        jLabel7.setFont(new java.awt.Font("Proxima Nova Bl", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(240, 240, 240));
        jLabel7.setText("Clock App");

        jLabel8.setFont(new java.awt.Font("Proxima Nova Lt", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("Version 1.0.0");

        documentation.setFont(new java.awt.Font("Proxima Nova Rg", 0, 18)); // NOI18N
        documentation.setForeground(new java.awt.Color(204, 204, 204));
        documentation.setText("Documentation");
        documentation.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        documentation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                documentationMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout sidepaneLayout = new javax.swing.GroupLayout(sidepane);
        sidepane.setLayout(sidepaneLayout);
        sidepaneLayout.setHorizontalGroup(
            sidepaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_analog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_digital, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_alarm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(sidepaneLayout.createSequentialGroup()
                .addGroup(sidepaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sidepaneLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(sidepaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(sidepaneLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(documentation))
                    .addGroup(sidepaneLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel8)))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        sidepaneLayout.setVerticalGroup(
            sidepaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidepaneLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btn_analog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_digital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_alarm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(documentation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(16, 16, 16))
        );

        panelCards.setLayout(new java.awt.CardLayout());

        panelCard3.setBackground(new java.awt.Color(26, 80, 139));

        javax.swing.GroupLayout panelCard3Layout = new javax.swing.GroupLayout(panelCard3);
        panelCard3.setLayout(panelCard3Layout);
        panelCard3Layout.setHorizontalGroup(
            panelCard3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 431, Short.MAX_VALUE)
        );
        panelCard3Layout.setVerticalGroup(
            panelCard3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 413, Short.MAX_VALUE)
        );

        panelCards.add(panelCard3, "panelCard3");

        panelCard2.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout panelCard2Layout = new javax.swing.GroupLayout(panelCard2);
        panelCard2.setLayout(panelCard2Layout);
        panelCard2Layout.setHorizontalGroup(
            panelCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 431, Short.MAX_VALUE)
        );
        panelCard2Layout.setVerticalGroup(
            panelCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 413, Short.MAX_VALUE)
        );

        panelCards.add(panelCard2, "panelCard2");

        panelCard1.setBackground(new java.awt.Color(26, 80, 139));

        javax.swing.GroupLayout panelCard1Layout = new javax.swing.GroupLayout(panelCard1);
        panelCard1.setLayout(panelCard1Layout);
        panelCard1Layout.setHorizontalGroup(
            panelCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 431, Short.MAX_VALUE)
        );
        panelCard1Layout.setVerticalGroup(
            panelCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 413, Short.MAX_VALUE)
        );

        panelCards.add(panelCard1, "panelCard1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sidepane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sidepane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelCards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void btn_analogMouseClicked(java.awt.event.MouseEvent evt) {                                        

        cardLayout.show(panelCards, "panelCard1");

        aclock a1 = new aclock();
        panelCard1.add(a1);      
    }                                       

    private void btn_digitalMouseClicked(java.awt.event.MouseEvent evt) {                                         

        cardLayout.show(panelCards, "panelCard2");
       

        dclock d1 = new dclock();
        panelCard2.setLayout(new BorderLayout());
        panelCard2.add(d1);
    }                                        

    private void btn_alarmMouseClicked(java.awt.event.MouseEvent evt) {                                       

        cardLayout.show(panelCards, "panelCard3");
        alarm p1 = new alarm();
        panelCard3.add(p1);
    }                                      

    private void btn_analogMouseEntered(java.awt.event.MouseEvent evt) {                                        
        setColor_hover(btn_analog);
    }                                       

    private void btn_analogMouseExited(java.awt.event.MouseEvent evt) {                                       
        resetColor(btn_analog);
    }                                      

    private void btn_digitalMouseEntered(java.awt.event.MouseEvent evt) {                                         
        setColor_hover(btn_digital);
    }                                        

    private void btn_digitalMouseExited(java.awt.event.MouseEvent evt) {                                        
        resetColor(btn_digital);
    }                                       

    private void btn_alarmMouseEntered(java.awt.event.MouseEvent evt) {                                       
        setColor_hover(btn_alarm);
    }                                      

    private void btn_alarmMouseExited(java.awt.event.MouseEvent evt) {                                      
        resetColor(btn_alarm);
    }                                     

    private void documentationMouseClicked(java.awt.event.MouseEvent evt) {                                           
         try
                {
                    Desktop.getDesktop().browse(temp);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
    }                                          
    
    void setColor_hover(JPanel panel){
        panel.setBackground(new Color(4, 100, 196));
    }
    
    void setColor_clicked(JPanel panel){
        panel.setBackground(new Color(128, 109, 179));
    }
    
    void resetColor(JPanel panel){
        panel.setBackground(new Color(3,83,164));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JPanel btn_alarm;
    private javax.swing.JPanel btn_analog;
    private javax.swing.JPanel btn_digital;
    private javax.swing.JLabel documentation;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel panelCard1;
    private javax.swing.JPanel panelCard2;
    private javax.swing.JPanel panelCard3;
    private javax.swing.JPanel panelCards;
    private javax.swing.JPanel sidepane;
    // End of variables declaration                   
}
