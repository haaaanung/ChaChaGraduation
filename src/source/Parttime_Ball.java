package source;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Parttime_Ball extends JFrame implements ActionListener{
	Player player;
	static final int WIDTH = 600;
	static final int HEIGHT = 200;
	private static final int PERIOD = 10;
	
	JPanel readyPanel, ballPanel;
	int score;
	
	Parttime_Ball(Player player){
		this.player = player;
		score = 0;
		ballPanel = new BallPanel();
		ballPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(ballPanel);
        pack();
        ballPanel.setVisible(false);
        
		readyPanel = new ReadyPanel();
		readyPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		add(readyPanel);
		pack();
		
		setTitle("Bounce Ball");
		Timer timer = new Timer(PERIOD, this);
		timer.start();
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public int getScore() {
		return score; //score별로 받을 money를 계산해서 return해도 될듯
	}
	
	class Ball{
		private int x, y, xInc, yInc, diameter;
		private Color color;
		
		public Ball(int diameter, Color color){
			this.xInc = (int)(Math.random() * (5 - 1 + 1) + 1);
			this.yInc = (int)(Math.random() * (5 - 1 + 1) + 1);
			this.diameter = diameter;
			this.color = color;
			this.x = (int)(Math.random() * (WIDTH - diameter - diameter + 1) + diameter); 
			this.y = (int)(Math.random() * (HEIGHT - diameter - diameter + 1) + diameter); 
		}
		
		public void paint(Graphics g) {
			if(x<0 || x>(Parttime_Ball.WIDTH - diameter)) {
				xInc = -xInc;
			}
			if(y<0 || y>(Parttime_Ball.HEIGHT-diameter)) {
				yInc = -yInc;
			}
			x += xInc;
			y += yInc;
			g.setColor(color);
			g.fillOval(x, y, diameter, diameter);
		}
		
		public boolean isClicked(int mouseX, int mouseY) {
	        // 클릭이 공 내부에 있는지 확인
			return mouseX >= x && mouseX <= x + diameter && mouseY >= y && mouseY <= y + diameter;
	    }
		
		public int calculatePoints() {
	        // 색깔에 따라 점수 계산
	        if (color.equals(Color.RED)) {
	            return 1;
	        } else if (color.equals(Color.GREEN)) {
	            return 2;
	        } else if (color.equals(Color.BLUE)) {
	            return -1;
	        } else {
	            return 0; // 다른 색깔인 경우 0점으로 처리
	        }
	    }
	}
	
	class ReadyPanel extends JPanel{ //준비화면
		JButton startBtn;
		
		public ReadyPanel() {
			startBtn = new JButton("시작");
			add(startBtn);
			
			startBtn.addActionListener(e -> {
	            setVisible(false);
	            ballPanel.setVisible(true);
			});
		}
	}
	
	class BallPanel extends JPanel{
		Ball[] ballArr = new Ball[10];
		JLabel scoreLabel;
		
		public BallPanel(){
//			for (int i = 0; i < ballArr.length; i++) {
//				ballArr[i] = new Ball();
//			}
			ballArr[0] = new Ball(60, Color.RED);
			ballArr[1] = new Ball(40, Color.GREEN);
			ballArr[2] = new Ball(50, Color.BLUE);
			ballArr[3] = new Ball(50, Color.BLUE);
			ballArr[4] = new Ball(50, Color.BLUE);
			ballArr[5] = new Ball(50, Color.BLUE);
			ballArr[6] = new Ball(50, Color.BLUE);
			ballArr[7] = new Ball(50, Color.BLUE);
			ballArr[8] = new Ball(50, Color.BLUE);
			ballArr[9] = new Ball(50, Color.BLUE);
			
			scoreLabel = new JLabel(Integer.toString(score));
			scoreLabel.setFont(new Font("Serif", Font.BOLD, 25));
			scoreLabel.setHorizontalAlignment(JLabel.NORTH_EAST);
			add(scoreLabel);
			
			addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                // 클릭이 어떤 공 내부에 있는지 확인합니다.
	                for (Ball ball : ballArr) {
	                    if (ball.isClicked(e.getX(), e.getY())) {
	                        // 공이 클릭되면 공의 색깔에 따라 점수를 증가
	                        int points = ball.calculatePoints();
	                        score += points;
	                        scoreLabel.setText(Integer.toString(score));
	                    }
	                }
	            }
	        });
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for (int i = 0; i < ballArr.length; i++) {
				ballArr[i].paint(g);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}
}