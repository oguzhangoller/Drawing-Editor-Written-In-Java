import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DrawingEditor {
	
	
	
	JButton[] north,right,left,south;  //these are the buttons to move created shapes 
	JButton [] delete;				   //this button will be used to delete shapes
	
	//below are the arrays which contains created shapes
	RectangleComponent[] rt;		    
	CircleComponent[] cl;
	SquareComponent[] sq;
	LineComponent[] ld ;
	
	JFrame f;  		//this is our main frame
	JLabel[] lab;			//this label is used to itemize shapes
	Container con;
	JMenuItem rect,circle,square,line;				//this is used for buttons on menu
	JPanel p,p2,p3,p4,p5;							//these are the panels to control shapes
	static int shapeNo = 0, rectNo=0, circleNo =0 , squareNo=0 , lineNo = 0 ;		//these hold the specific number of each shape
	Point po1,po2 ;				
	Boolean clicked;		//this value is for enabling user to draw only one line after clicking line
	
	
	

	
public static void main(String[] args){
	new DrawingEditor().gui();			//start the application
	
}

/*
 * this method adds necessary delete and move buttons for each shape
 * it takes an actionlistener and a number as parameter
 * the number is used for indicating which shape's buttons will be created
 * 1 stands for rectangle, 2 for circle, 3 for square and 4 for line.
 * it also contains an actionlistener for delete button
 */
public void addButtons(ShapeActionListener sp, int number){
	delete[shapeNo] = new JButton("Clear");
	 
	//add necessary move buttons and their actionlisteners
	//n stands for north, l for left, r for right, s for south
	north[shapeNo] = new JButton("N");
	north[shapeNo].addActionListener(sp);
	left[shapeNo] = new JButton("L");
	left[shapeNo].addActionListener(sp);
	right[shapeNo] = new JButton("R");
	right[shapeNo].addActionListener(sp);
	south[shapeNo] = new JButton("S");
	south[shapeNo].addActionListener(sp);
	
	
	//below are the if statements
	//they are used to place buttons of each shape in their specific location
	//buttons of rectangles are placed at right side of page, squares at left side, lines at top and circles at bottom
	if(number == 1){
	rectNo++;
	p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
	con.add(p2, BorderLayout.EAST);
	lab[shapeNo] = new JLabel("Rectangle "+ rectNo);
	p2.add(lab[shapeNo]);
	p2.add(delete[shapeNo]);
	p2.add(north[shapeNo]);
	p2.add(left[shapeNo]);
	p2.add(right[shapeNo]);
	p2.add(south[shapeNo]);
	}
	
	else if(number == 2){
		circleNo++ ;
		p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
		con.add(p3, BorderLayout.SOUTH);
		lab[shapeNo] = new JLabel("Circle "+ circleNo);
		p3.add(lab[shapeNo]);
		p3.add(delete[shapeNo]);
		p3.add(north[shapeNo]);
		p3.add(left[shapeNo]);
		p3.add(right[shapeNo]);
		p3.add(south[shapeNo]);
	}
	
	else if(number == 3){
		squareNo++ ;
		p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
		con.add(p4, BorderLayout.WEST);
		lab[shapeNo] = new JLabel("Square "+ circleNo);
		p4.add(lab[shapeNo]);
		p4.add(delete[shapeNo]);
		p4.add(north[shapeNo]);
		p4.add(left[shapeNo]);
		p4.add(right[shapeNo]);
		p4.add(south[shapeNo]);
	}
	
	else if(number == 4){
		lineNo++ ;
		p5.setLayout(new BoxLayout(p5, BoxLayout.X_AXIS));
		con.add(p5, BorderLayout.NORTH);
		lab[shapeNo] = new JLabel("Line "+ lineNo);
		p5.add(lab[shapeNo]);
		p5.add(delete[shapeNo]);
		p5.add(north[shapeNo]);
		p5.add(left[shapeNo]);
		p5.add(right[shapeNo]);
		p5.add(south[shapeNo]);
	}
		//here is the action listener for deleting shapes if clicked on clear button
	delete[shapeNo].addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			//program enables maximum 40 shapes to be created 
			// and below is the code to discriminate each clear button from each other
			for(int i=1 ; i<40 ; i++){
			if(e.getSource()==delete[i]){
				if(rt[i]!=null)
					rt[i].setVisible(false);
				if(cl[i]!=null)
					cl[i].setVisible(false);
				if(sq[i]!=null)
					sq[i].setVisible(false);
				if(ld[i]!=null)
					ld[i].setVisible(false);
				
				//and also their related move and clear buttons are also deleted
				delete[i].setVisible(false);
				north[i].setVisible(false);
				left[i].setVisible(false);
				right[i].setVisible(false);
				south[i].setVisible(false);
				lab[i].setVisible(false);
			}
			
		}
		}
		 ;
	 }); 
	
}
/*
 * the two methods below are for enabling user to draw a line only once after choosing line at menu bar
 */
public void stopLineDrawing(){
	clicked = false;
}

public void enterLineDrawing(){
	clicked = true;
}
public void gui(){
	//below are the buttons groups initialized 
	//each button and label created will be added to their own button group
	clicked = true ;
	rt = new RectangleComponent[40];
	cl = new CircleComponent[40];
	sq = new SquareComponent[40];
	ld = new LineComponent[40];
	north = new JButton[40];
	left = new JButton[40];
	right = new JButton[40];
	south = new JButton[40];
	delete = new JButton[40];
	lab = new JLabel[40];
	
	//size and specifications of main frame is determined
	JFrame f = new JFrame("Drawing Editor");
	f.setSize(2000, 2000);
	f.setVisible(true);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	con = f.getContentPane();
	
	//each panel is initialized, they will be located at different parts of jframe
    p5 = new JPanel();
	p4 = new JPanel();
	p3 = new JPanel();
	p2 = new JPanel();
	
	//below is the actionListener used for creating shapes
	
	ActionListener act = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			//if statements are used for discriminating which button user clicks
			if(e.getSource()==rect){
				shapeNo++;
				//these text fields are used for getting size of shape on each creation
				 JTextField xField = new JTextField(5);
			     JTextField yField = new JTextField(5);
				 JPanel myPanel = new JPanel();
			      myPanel.add(new JLabel("height:"));
			      myPanel.add(xField);
			      myPanel.add(Box.createHorizontalStrut(15)); 
			      myPanel.add(new JLabel("width:"));
			      myPanel.add(yField);
			      int hei=0;
			      int wi=0;

			      int result = JOptionPane.showConfirmDialog(null, myPanel, 
			               "Height and Width", JOptionPane.OK_CANCEL_OPTION);
			      if (result == JOptionPane.OK_OPTION) {
			         wi = Integer.parseInt(xField.getText());
			         hei = Integer.parseInt(yField.getText());
			      }
			   
				f.revalidate();
				
				//after getting the dimensions, create a new shape and add it to the frame
				rt[shapeNo] = new RectangleComponent(hei,wi);
				f.add(rt[shapeNo]);
				ShapeActionListener sp = new ShapeActionListener();
				addButtons(sp,1);
				 
				 f.revalidate();
				
			} else if(e.getSource() == circle){  //if statements are used for discriminating which button user clicks
				shapeNo++;
				//these text fields are used for getting size of shape on each creation
				JTextField rField = new JTextField(5);
			     
				 JPanel myPanel = new JPanel();
			      myPanel.add(new JLabel("radius:"));
			      myPanel.add(rField);
			      myPanel.add(Box.createHorizontalStrut(15)); 			      
			      int radius = 0 ;

			      int result = JOptionPane.showConfirmDialog(null, myPanel, 
			               "Radius", JOptionPane.OK_CANCEL_OPTION);
			      if (result == JOptionPane.OK_OPTION) {
			         radius = Integer.parseInt(rField.getText());
			        
			      }
			   
				f.revalidate();
				//after getting the dimensions, create a new shape and add it to the frame
				cl[shapeNo] = new CircleComponent(radius);

				f.add(cl[shapeNo]);
				ShapeActionListener sp = new ShapeActionListener();
				addButtons(sp,2);
				 
				 f.revalidate();
				
			} else if(e.getSource() == square){		//if statements are used for discriminating which button user clicks
				shapeNo++;
				//these text fields are used for getting size of shape on each creation
				JTextField hField = new JTextField(5);
			     
				 JPanel myPanel = new JPanel();
			      myPanel.add(new JLabel("height:"));
			      myPanel.add(hField);
			      myPanel.add(Box.createHorizontalStrut(15)); 			      
			      int hei = 0 ;

			      int result = JOptionPane.showConfirmDialog(null, myPanel, 
			               "Height", JOptionPane.OK_CANCEL_OPTION);
			      if (result == JOptionPane.OK_OPTION) {
			         hei = Integer.parseInt(hField.getText());
			        
			      }
			   
				f.revalidate();
				//after getting the dimensions, create a new shape and add it to the frame
				sq[shapeNo] = new SquareComponent(hei);

				f.add(sq[shapeNo]);
				ShapeActionListener sp = new ShapeActionListener();
				addButtons(sp,3);
				 
				 f.revalidate();
				
			} 
		}
	};
	
	//below is a simple menubar creating for user to create shapes
	JMenuBar menubar = new JMenuBar();
	JMenu menu = new JMenu("Shapes");
	rect = new JMenuItem("Rectangle");
	square = new JMenuItem("Square");
	circle = new JMenuItem("Circle");
	line = new JMenuItem("Line");
	menu.add(rect);
	menu.add(square);
	menu.add(circle);
	menu.add(line);
	menubar.add(menu);
	f.setJMenuBar(menubar);
	
	//add action Listeners to each item on menu bar
	rect.addActionListener(act);
	circle.addActionListener(act);
	square.addActionListener(act);
	
	
	//line will have a specific action listener since it will be created on mouse click
	line.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == line){
				enterLineDrawing();			//this method is for enabling user to draw only one line on jpanel
				f.addMouseListener(new MouseListener() {
					int x1 = 0;
					
					int y1 = 0 ;
					
				    @Override
				    public void mouseClicked(MouseEvent e) {
				
				    }

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
				        x1=e.getX();
				        y1=e.getY();
				        
				        
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						
						int x2=e.getX();
				        int y2=e.getY();
				        //a line will be drawn between points where user clicks and releases mouse
				        if(clicked){
				        	shapeNo++;
				        	//some numbers are subtracted from x and y values because
				        	//these points are not relative to our jframe so we need to convert them
				        	ld[shapeNo] =  new LineComponent(x1-5, x2-5, y1-80, y2-80);

							f.add(ld[shapeNo]);
							ShapeActionListener sp = new ShapeActionListener();
							addButtons(sp,4);
							 
							f.revalidate();
				        	
				        	
				        	stopLineDrawing();		//this method is to stop enabling user to draw more than one line on panel
				        }
				        
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
			}
		}
		
	});
	
	
	
}

//below are the classes of shapes
//they have parameters to determine their heights and widths, which will be taken from user
class RectangleComponent extends JComponent
{
  Rectangle rectangle;
  int height,width;

  public RectangleComponent(int height,int width)
  {
	this.height=height;
	this.width=width;
	rectangle  = new Rectangle(50, 50,width, height);
  }

  public void paintComponent(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    g2.draw(rectangle);
  } 
}

class CircleComponent extends JComponent
{
	int radius;
	public CircleComponent(int radius)
	  {
		this.radius = radius;
		
		
	  }

  public void paintComponent(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    g2.drawOval(400, 10, 2*radius, 2*radius);;
  } 
}

class SquareComponent extends JComponent
{
  Rectangle sq;

  public SquareComponent(int height)
  {
    sq = new Rectangle(700,10,height,height);
  }

  public void paintComponent(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    g2.draw(sq);
  } 
}

class LineComponent extends JComponent
{
	int x1,x2,y1,y2;
	public LineComponent(int x1,int x2, int y1,int y2)
	  {
	   this.x1 = x1;
	   this.x2 = x2;
	   this.y1 = y1;
	   this.y2 = y2;
	  }
  public void paintComponent(Graphics g)
  {
	 
      Graphics2D g2 = (Graphics2D) g;
      Line2D lin = new Line2D.Float(x1, y1, x2, y2);
      g2.draw(lin);
  } 
}

//this is the class for moving each shape depending of which direction button is clicked
//it loops 40 times since maximum of 40 shapes is allowed
class ShapeActionListener implements ActionListener {
	
   
	
    public void actionPerformed(ActionEvent e) {
    	for(int i=1 ;i<40 ; i++){
        if(e.getSource() == north[i]){
        	if(rt[i]!=null)
        		rt[i].setLocation(rt[i].getLocation().x,rt[i].getLocation().y-10);
        	if(cl[i]!=null)
        		cl[i].setLocation(cl[i].getLocation().x,cl[i].getLocation().y-10);
        	if(sq[i]!=null)
        		sq[i].setLocation(sq[i].getLocation().x,sq[i].getLocation().y-10);
        	if(ld[i]!=null)
        		ld[i].setLocation(ld[i].getLocation().x,ld[i].getLocation().y-10);
        }
        else if(e.getSource() == right[i]){
        	if(rt[i]!=null)
        		rt[i].setLocation(rt[i].getLocation().x+10,rt[i].getLocation().y);
        	if(cl[i]!=null)
        		cl[i].setLocation(cl[i].getLocation().x+10,cl[i].getLocation().y);
        	if(sq[i]!=null)
        		sq[i].setLocation(sq[i].getLocation().x+10,sq[i].getLocation().y);
        	if(ld[i]!=null)
        		ld[i].setLocation(ld[i].getLocation().x+10,ld[i].getLocation().y);
        	
        }
        else if(e.getSource() == left[i]){
        	if(rt[i]!=null)
        		rt[i].setLocation(rt[i].getLocation().x-10,rt[i].getLocation().y);
        	if(cl[i]!=null)
        		cl[i].setLocation(cl[i].getLocation().x-10,cl[i].getLocation().y);
        	if(sq[i]!=null)
        		sq[i].setLocation(sq[i].getLocation().x-10,sq[i].getLocation().y);
        	if(ld[i]!=null)
        		ld[i].setLocation(ld[i].getLocation().x-10,ld[i].getLocation().y);       	
        }
        else if(e.getSource() == south[i]){
        	if(rt[i]!=null)
        		rt[i].setLocation(rt[i].getLocation().x,rt[i].getLocation().y+10);
        	if(cl[i]!=null)
        		cl[i].setLocation(cl[i].getLocation().x,cl[i].getLocation().y+10);
        	if(sq[i]!=null)
        		sq[i].setLocation(sq[i].getLocation().x,sq[i].getLocation().y+10);
        	if(ld[i]!=null)
        		ld[i].setLocation(ld[i].getLocation().x,ld[i].getLocation().y+10);
        	
        	}
    	}
    }
}


}
