import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PathFollow {

    public static void main(String[] args) {
        new PathFollow();
    }

    public PathFollow() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        private Shape pathShape;
        private List<Point2D> points;
        private Shape car;

        private double angle;
        private Point2D pos;
        private int index;

        protected static final double PLAY_TIME = 5000; // 5 seconds...

        private Long startTime;

        public TestPane() {

            Path2D path = new Path2D.Double();
            path.moveTo(100, 200);
            path.lineTo(300, 300);
//            path.curveTo(100, 200, 0, 100, 100, 100);
//            path.curveTo(200, 100, 0, 0, 200, 0);
            path.closePath();
            pathShape = path;
            //This is how we will generate Path...

            car = new Rectangle(0, 0, 10, 10);

            points = new ArrayList<>(25);
            PathIterator pi = pathShape.getPathIterator(null, 0.0001);
            while (!pi.isDone()) {
                double[] coords = new double[6];
                switch (pi.currentSegment(coords)) {
                    case PathIterator.SEG_MOVETO:
                    case PathIterator.SEG_LINETO:
                        points.add(new Point2D.Double(coords[0], coords[1]));
                        break;
                }
                pi.next();
            }
            
            for(Point2D i:points){
                System.out.println("X: "+i.getX()+" Y: "+i.getY());
            }
            
//          System.out.println(points.size());
//          pos = points.get(0);
//          index = 1;
            Timer timer = new Timer(40, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (startTime == null) {
                        startTime = System.currentTimeMillis();
                    }
                    long playTime = System.currentTimeMillis() - startTime;
                    double progress = playTime / PLAY_TIME;
                    if (progress >= 1.0) {
                        progress = 1d;
                        ((Timer) e.getSource()).stop();
                    }

                    int index = Math.min(Math.max(0, (int) (points.size() * progress)), points.size() - 1);

                    pos = points.get(index);
                    if (index < points.size() - 1) {
                        angle = angleTo(pos, points.get(index + 1));
                    }
                    repaint();
                }
            });
            timer.start();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1000, 1000);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.draw(pathShape);

            AffineTransform at = new AffineTransform();

            if (pos != null) {

                Rectangle bounds = car.getBounds();
                at.rotate(angle, (bounds.width / 2), (bounds.width / 2));

                Path2D player = new Path2D.Double(car, at);

                g2d.translate(pos.getX() - (bounds.width / 2), pos.getY() - (bounds.height / 2));
                g2d.draw(player);

            }

            g2d.dispose();
        }

        // In radians...
        protected double angleTo(Point2D from, Point2D to) {
            double angle = Math.atan2(to.getY() - from.getY(), to.getX() - from.getX());
            return angle;
        }

    }

}