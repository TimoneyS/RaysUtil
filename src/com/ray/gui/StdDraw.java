package com.ray.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *  {@code StdDraw}类给程序提供一个基础的绘制能力。它使用了简单的图像模型，
 *  帮助你在屏幕上绘制图形如点、线、面、圆形和一些其他的几何形状，也可以保存这些绘制的图形到文件。
 *  标准绘制也包含文本、颜色、图形和动图的工具，而且可以和用户的键盘鼠标相互合作。
 *  <p>
 *  <b>入门</b><p>
 *  一个简单的演示程序如下：
 *  <pre>
 *   public class TestStdDraw {
 *       public static void main(String[] args) {
 *           StdDraw.setPenRadius(0.05);
 *           StdDraw.setPenColor(StdDraw.BLUE);
 *           StdDraw.point(0.5, 0.5);
 *           StdDraw.setPenColor(StdDraw.MAGENTA);
 *           StdDraw.line(0.2, 0.2, 0.8, 0.2);
 *       }
 *   }
 *  </pre>
 *  
 *  方法 {@code StdDraw.line()} 和 {@code StdDraw.point()} 绘制线条和点；方法 {@code StdDraw.setPenRadius()} 和 
 *  {@code StdDraw.setPenColor()} 控制线条的颜色和粗细。
 *  <p>
 *  <b>点和线</b>
 *  点和线相关的方法如下：
 *  <ul>
 *  <li> {@link #point(double x, double y)}
 *  <li> {@link #line(double x1, double y1, double x2, double y2)}
 *  </ul>
 *  <p>
 *  坐标 x 和 y 必须在 0 和 1 之间，否则图像将不可见。
 *  <p>
 *  <b>正方形，圆形，矩形和椭圆形。</b>
 *  方法如下：
 *  <ul>
 *  <li> {@link #circle(double x, double y, double radius)}
 *  <li> {@link #ellipse(double x, double y, double semiMajorAxis, double semiMinorAxis)}
 *  <li> {@link #square(double x, double y, double radius)}
 *  <li> {@link #rectangle(double x, double y, double halfWidth, double halfHeight)}
 *  </ul>
 *  <p>
 *  所有的这些方法都有包含图形位置和尺寸信息作为参数。位置的坐标 (x,y) 表示图形的中心的位置。
 *  圆的大小由其半径指定，椭圆的大小由半长轴和半短轴的长度指定。
 *  正方形或矩形的大小由其半宽或半高指定。
 *  <p>
 *  上述方法描绘了给定形状的轮廓。 以下方法绘制填充版本：
 *  <ul>
 *  <li> {@link #filledCircle(double x, double y, double radius)}
 *  <li> {@link #filledEllipse(double x, double y, double semiMajorAxis, double semiMinorAxis)}
 *  <li> {@link #filledSquare(double x, double y, double radius)}
 *  <li> {@link #filledRectangle(double x, double y, double halfWidth, double halfHeight)}
 *  </ul>
 *  <p>
 *  <b>圆弧</b>
 *  您可以使用以下方法绘制圆弧：
 *  <ul>
 *  <li> {@link #arc(double x, double y, double radius, double angle1, double angle2)}
 *  </ul>
 *  <p>
 *  圆弧来自以指定半径的（x,y）为中心的圆。
 *  弧从角度1延伸到角度2。 按照惯例，角度为采用逆时针的极坐标系，并以度为单位表示。
 *  例如，{@ code StdDraw.arc（0.0,0.0,1.0,0,90）} 将单位圆的圆弧从 0° 绘制到90°。
 *  <p>
 *  <b>多边形</b>
 *  您可以使用以下方法绘制多边形：
 *  <ul>
 *  <li> {@link #polygon(double[] x, double[] y)}
 *  <li> {@link #filledPolygon(double[] x, double[] y)}
 *  </ul>
 *  <p>
 *  参数是多边形的每个点的 横纵坐标。
 *  例如一个多边形包含如下点 (0.1, 0.2), (0.2, 0.3), (0.3, 0.2), (0.2, 0.1)，则绘制的代码如下：
 *  <pre>
 *   double[] x = { 0.1, 0.2, 0.3, 0.2 };
 *   double[] y = { 0.2, 0.3, 0.2, 0.1 };
 *   StdDraw.filledPolygon(x, y);
 *  </pre>
 *  <p>
 *  <b>笔粗细</b>
 *  笔是圆形的，因此当您将笔半径设置为<em> r </ em>并绘制一个点时，您将获得一个半径<em> r </ em>的圆。
 *  而且，线的厚度为2 <em> r </ em>并且具有圆形末端。默认笔半径为0.005，不受坐标缩放的影响。
 *  此默认笔半径约为默认画布宽度的1/200，因此，如果沿水平或垂直线绘制100个等间距，则可以看到各个圆，
 *  但如果绘制200个这样的点，则 结果看起来像一条线。
 *  <ul>
 *  <li> {@link #setPenRadius(double radius)}
 *  </ul>
 *  <p>
 *  例如，{@ code StdDraw.setPenRadius（0.025）}使得线条的粗细和点的大小为0.005默认值的五倍。
 *  要绘制具有最小可能半径的点（典型显示器上的一个像素），请将笔半径设置为0.0。
 *  <p>
 *  <b>笔颜色</b>
 *  默认的颜色是黑色。可以通过如下方法来改变颜色：
 *  <ul>
 *  <li> {@link #setPenColor(int red, int green, int blue)}
 *  <li> {@link #setPenColor(Color color)}
 *  </ul>
 *  <p>
 *  内置定义的颜色有如下：
 *  {@link #BLACK}, {@link #BLUE}, {@link #CYAN}, {@link #DARK_GRAY}, {@link #GRAY},
 *  {@link #GREEN}, {@link #LIGHT_GRAY}, {@link #MAGENTA}, {@link #ORANGE},
 *  {@link #PINK}, {@link #RED}, {@link #WHITE}, {@link #YELLOW},
 *  {@link #BOOK_BLUE}, {@link #BOOK_LIGHT_BLUE}, {@link #BOOK_RED}, and
 *  {@link #PRINCETON_ORANGE}.
 *  <p>
 *  <b>画布的尺寸。</b>
 *  默认情况下画布的大小是 512x512，画布没有窗口标题和窗口边界
 *  如下方法可以改变画布的尺寸：
 *  <ul>
 *  <li> {@link #setCanvasSize(int width, int height)}
 *  </ul>
 *  <p>
 *  <b>画布比例和坐标系。</b>
 *  默认情况大画布的左下角坐标为 (0, 0) ，右上角坐标为 (1, 1)
 *  如下方法可以改变默认的坐标系统：
 *  <ul>
 *  <li> {@link #setXscale(double xmin, double xmax)}
 *  <li> {@link #setYscale(double ymin, double ymax)}
 *  <li> {@link #setScale(double min, double max)}
 *  </ul>
 *  <p>
 *  参数是将出现在画布中的最小和最大 x 或 y 坐标。 
 *  例如，如果您希望使用默认坐标系但保留较小的边距，则可以调用{@code StdDraw.setScale（ - 。05,1.05）}。
 *  <p>
 *  <b>文本</b>
 *  如下方法可以绘制文本：
 *  <ul>
 *  <li> {@link #text(double x, double y, String text)}
 *  <li> {@link #text(double x, double y, String text, double degrees)}
 *  <li> {@link #textLeft(double x, double y, String text)}
 *  <li> {@link #textRight(double x, double y, String text)}
 *  </ul>
 *  <p>
 *  前两个方法是以x，y为中心绘制文本，第二个方法允许你旋转文本。
 *  最后两个方法可以是文本在指定位置，左对齐或者右对齐。
 *  <p>
 *  默认的字体是 Sans Serif 大小是16磅.
 *  可以使用如下方法改变字体：
 *  <ul>
 *  <li> {@link #setFont(Font font)}
 *  </ul>
 *  <p>
 *  可以使用{@link Font}数据类型指定字体。 这允许您选择字体的面，大小和样式。 
 *  例如，以下代码片段将字体设置为Arial Bold，60磅。
 *  <pre>
 *   Font font = new Font("Arial", Font.BOLD, 60);
 *   StdDraw.setFont(font);
 *   StdDraw.text(0.5, 0.5, "Hello, World");
 *  </pre>
 *  <p>
 *  <b>图片</b>
 *  以下方法可以绘制图片：
 *  <ul>
 *  <li> {@link #picture(double x, double y, String filename)}
 *  <li> {@link #picture(double x, double y, String filename, double degrees)}
 *  <li> {@link #picture(double x, double y, String filename, double scaledWidth, double scaledHeight)}
 *  <li> {@link #picture(double x, double y, String filename, double scaledWidth, double scaledHeight, double degrees)}
 *  </ul>
 *  <p>
 *  这些方法以（<em> x </ em>，<em> y </ em>）为中心， 绘制指定的图像。支持的图像格式为JPEG，PNG和GIF。
 *  图像将以其原始大小显示，与坐标系无关。或者，您可以逆时针旋转图像指定的度数，或者将其重新缩放到适合宽度和高度。
 *  <p>
 *  <b>保存到文件</b>
 *  可以通过 菜单上的 <em>File → Save</em> 保存图像到文件，也可以直接调用如下方法保存：
 *  <ul>
 *  <li> {@link #save(String filename)}
 *  </ul>
 *  <p>
 *  支持的图片格式为 JPEG 和 PNG，文件扩展名必须是 .jpg 或 .png。
 *  <p>
 *  <b>清空画布</b>
 *  方法如下：
 *  <ul>
 *  <li> {@link #clear()}
 *  <li> {@link #clear(Color color)}
 *  </ul>
 *  <p>
 *  第一种方法将画布清除为白色; 第二种方法允许您指定您选择的颜色。 
 *  例如，{@ code StdDraw.clear（StdDraw.LIGHT_GRAY）}将画布清除为灰色阴影色。
 *  <p>
 *  <b>计算机动画和双缓冲。</b>
 *  双缓冲是标准绘图最强大的功能之一，可实现计算机动画。
 *  以下方法控制绘制对象的方式：
 *  <ul>
 *  <li> {@link #enableDoubleBuffering()}
 *  <li> {@link #disableDoubleBuffering()}
 *  <li> {@link #show()}
 *  <li> {@link #pause(int t)}
 *  </ul>
 *  <p>
 *  默认情况下，禁用双缓冲，这意味着只要调用绘图方法（例如{@code point（）}或{@code line（）} - 结果就会显示在屏幕上。
 *  <p>
 *  通过调用{@link #enableDoubleBuffering（）}启用双缓冲时，所有绘图都在<em>屏幕外画布</ em>上进行。
 *  不显示屏幕外画布。 只有当您调用{@link #show（）}时，您的绘图才会从屏幕外画布复制到屏幕画布上，并在标准绘图窗口中显示。
 *  <p>
 *  双重缓冲最重要的用途是制作计算机动画，通过快速显示静态图纸来创造运动的幻觉。 
 *  要制作动画，请重复以下四个步骤：
 *  <ul>
 *  <li> 清空缓存画布
 *  <li> 在缓存画布绘制
 *  <li> 复制缓存画布到屏幕画布
 *  <li> 等待
 *  </ul>
 *  <p>
 * {@link #clear（）}，{@ link #show（）}和{@link #pause（int t）}方法分别支持这些步骤的第一步，第三步和第四步。
 *  <p>
 *  例如，该代码片段动画两个以圆圈运动的球。
 *  <pre>
 *   StdDraw.setScale(-2, +2);
 *   StdDraw.enableDoubleBuffering();
 *
 *   for (double t = 0.0; true; t += 0.02) {
 *       double x = Math.sin(t);
 *       double y = Math.cos(t);
 *       StdDraw.clear();
 *       StdDraw.filledCircle(x, y, 0.05);
 *       StdDraw.filledCircle(-x, -y, 0.05);
 *       StdDraw.show();
 *       StdDraw.pause(20);
 *   }
 *  </pre>
 *  <p>
 *  <b>键盘和鼠标输入</b>
 *  标准绘图对键盘和鼠标输入有非常基本的支持。它比大多数用户界面库提供的功能强大得多，但也简单得多。
 *  可以使用以下方法拦截鼠标事件：
 *  <ul>
 *  <li> {@link #isMousePressed()}
 *  <li> {@link #mouseX()}
 *  <li> {@link #mouseY()}
 *  </ul>
 *  <p>
 *  第一种方法告诉您当前是否正在按下鼠标按钮。
 *  最后两种方法告诉你鼠标当前位置的坐标，使用与画布相同的坐标系（默认情况下为单位平方）。
 *  您应该在尝试之前等待一小段时间的动画循环中使用这些方法轮询鼠标的当前状态。
 *  您可以使用以下方法拦截键盘事件：
 *  <ul>
 *  <li> {@link #hasNextKeyTyped()}
 *  <li> {@link #nextKeyTyped()}
 *  <li> {@link #isKeyPressed(int keycode)}
 *  </ul>
 *  <p>
 *  如果用户键入了许多键，它们将保存在列表中，直到您处理它们为止。
 *  第一个方法告诉您用户是否键入了一个键（您的程序尚未处理）。
 *  第二个方法返回用户键入的下一个键（您的程序尚未处理）并将其从保存的击键列表中删除。
 *  第三个方法告诉您当前是否正在按下某个键。
 *  <p>
 *  <b>获取控制参数</b>
 *  方法如下
 *  <ul>
 *  <li> {@link #getPenColor()}
 *  <li> {@link #getPenRadius()}
 *  <li> {@link #getFont()}
 *  </ul>
 *  <p>
 *  <b>其他</b>
 *  为避免混乱，API不会显式引用null，infinity或NaN的参数。
 *  <ul>
 *  <li> Any method that is passed a {@code null} argument will throw an
 *       {@link IllegalArgumentException}.
 *  <li> Except as noted in the APIs, drawing an object outside (or partly outside)
 *       the canvas is permitted—however, only the part of the object that
 *       appears inside the canvas will be visible.
 *  <li> Except as noted in the APIs, all methods accept {@link Double#NaN},
 *       {@link Double#POSITIVE_INFINITY}, and {@link Double#NEGATIVE_INFINITY}
 *       as arugments. An object drawn with an <em>x</em>- or <em>y</em>-coordinate
 *       that is NaN will behave as if it is outside the canvas, and will not be visible.
 *  <li> Due to floating-point issues, an object drawn with an <em>x</em>- or
 *       <em>y</em>-coordinate that is way outside the canvas (such as the line segment
 *       from (0.5, –&infin;) to (0.5, &infin;) may not be visible even in the
 *       part of the canvas where it should be.
 *  </ul>
 *  <p>
 *  <b>Performance tricks.</b>
 *  Standard drawing is capable of drawing large amounts of data.
 *  Here are a few tricks and tips:
 *  <ul>
 *  <li> Use <em>double buffering</em> for static drawing with a large
 *       number of objects.
 *       That is, call {@link #enableDoubleBuffering()} before
 *       the sequence of drawing commands and call {@link #show()} afterwards.
 *       Incrementally displaying a complex drawing while it is being
 *       created can be intolerably inefficient on many computer systems.
 *  <li> When drawing computer animations, call {@code show()}
 *       only once per frame, not after drawing each individual object.
 *  <li> If you call {@code picture()} multiple times with the same filename,
 *       Java will cache the image, so you do not incur the cost of reading
 *       from a file each time.
 *  </ul>
 *  <p>
 *  <b>Known bugs and issues.</b>
 *  <ul>
 *  <li> The {@code picture()} methods may not draw the portion of the image that is
 *       inside the canvas if the center point (<em>x</em>, <em>y</em>) is outside the
 *       canvas.
 *       This bug appears only on some systems.
 *  <li> Some methods may not draw the portion of the geometric object that is inside the
 *       canvas if the <em>x</em>- or <em>y</em>-coordinates are infinite.
 *       This bug appears only on some systems.
 *  </ul>
 *  <p>
 *  <b>Reference.</b>
 *  For additional documentation,
 *  see <a href="https://introcs.cs.princeton.edu/15inout">Section 1.5</a> of
 *  <em>Computer Science: An Interdisciplinary Approach</em>
 *  by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public final class StdDraw implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

    /**
     *  The color black.
     */
    public static final Color BLACK = Color.BLACK;

    /**
     *  The color blue.
     */
    public static final Color BLUE = Color.BLUE;

    /**
     *  The color cyan.
     */
    public static final Color CYAN = Color.CYAN;

    /**
     *  The color dark gray.
     */
    public static final Color DARK_GRAY = Color.DARK_GRAY;

    /**
     *  The color gray.
     */
    public static final Color GRAY = Color.GRAY;

    /**
     *  The color green.
     */
    public static final Color GREEN  = Color.GREEN;

    /**
     *  The color light gray.
     */
    public static final Color LIGHT_GRAY = Color.LIGHT_GRAY;

    /**
     *  The color magenta.
     */
    public static final Color MAGENTA = Color.MAGENTA;

    /**
     *  The color orange.
     */
    public static final Color ORANGE = Color.ORANGE;

    /**
     *  The color pink.
     */
    public static final Color PINK = Color.PINK;

    /**
     *  The color red.
     */
    public static final Color RED = Color.RED;

    /**
     *  The color white.
     */
    public static final Color WHITE = Color.WHITE;

    /**
     *  The color yellow.
     */
    public static final Color YELLOW = Color.YELLOW;

    /**
     * Shade of blue used in <em>Introduction to Programming in Java</em>.
     * It is Pantone 300U. The RGB values are approximately (9, 90, 166).
     */
    public static final Color BOOK_BLUE = new Color(9, 90, 166);

    /**
     * Shade of light blue used in <em>Introduction to Programming in Java</em>.
     * The RGB values are approximately (103, 198, 243).
     */
    public static final Color BOOK_LIGHT_BLUE = new Color(103, 198, 243);

    /**
     * Shade of red used in <em>Algorithms, 4th edition</em>.
     * It is Pantone 1805U. The RGB values are approximately (150, 35, 31).
     */
    public static final Color BOOK_RED = new Color(150, 35, 31);

    /**
     * Shade of orange used in Princeton University's identity.
     * It is PMS 158. The RGB values are approximately (245, 128, 37).
     */
    public static final Color PRINCETON_ORANGE = new Color(245, 128, 37);

    // default colors
    private static final Color DEFAULT_PEN_COLOR   = BLACK;
    private static final Color DEFAULT_CLEAR_COLOR = WHITE;

    // current pen color
    private static Color penColor;

    // default canvas size is DEFAULT_SIZE-by-DEFAULT_SIZE
    private static final int DEFAULT_SIZE = 512;
    private static int width  = DEFAULT_SIZE;
    private static int height = DEFAULT_SIZE;

    // default pen radius
    private static final double DEFAULT_PEN_RADIUS = 0.002;

    // current pen radius
    private static double penRadius;

    // show we draw immediately or wait until next show?
    private static boolean double_buffer = false; // defer

    // boundary of drawing canvas, 0% border
    // private static final double BORDER = 0.05;
    private static final double BORDER = 0.00;
    private static final double DEFAULT_XMIN = 0.0;
    private static final double DEFAULT_XMAX = 1.0;
    private static final double DEFAULT_YMIN = 0.0;
    private static final double DEFAULT_YMAX = 1.0;
    private static double xmin, ymin, xmax, ymax;

    // for synchronization
    private static Object mouseLock = new Object();
    private static Object keyLock = new Object();

    // default font
    private static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 16);

    // current font
    private static Font font;

    // double buffered graphics
    private static BufferedImage offscreenImage, onscreenImage;
    private static Graphics2D offGraph, onGraph;

    // singleton for callbacks: avoids generation of extra .class files
    private static StdDraw std = new StdDraw();

    // the frame for drawing to the screen
    private static JFrame frame;

    // mouse state
    private static boolean isMousePressed = false;
    private static double mouseX = 0;
    private static double mouseY = 0;

    // queue of typed key characters
    private static LinkedList<Character> keysTyped = new LinkedList<Character>();

    // set of key codes currently pressed down
    private static TreeSet<Integer> keysDown = new TreeSet<Integer>();

    // singleton pattern: client can't instantiate
    private StdDraw() { }


    // static initializer
    static {
        init();
    }

    /**
     * Sets the canvas (drawing area) to be 512-by-512 pixels.
     * This also erases the current drawing and resets the coordinate system,
     * pen radius, pen color, and font back to their default values.
     * Ordinarly, this method is called once, at the very beginning
     * of a program.
     */
    public static void setCanvasSize() {
        setCanvasSize(DEFAULT_SIZE, DEFAULT_SIZE);
    }

    /**
     * Sets the canvas (drawing area) to be <em>width</em>-by-<em>height</em> pixels.
     * This also erases the current drawing and resets the coordinate system,
     * pen radius, pen color, and font back to their default values.
     * Ordinarly, this method is called once, at the very beginning
     * of a program.
     *
     * @param  canvasWidth the width as a number of pixels
     * @param  canvasHeight the height as a number of pixels
     * @throws IllegalArgumentException unless both {@code canvasWidth} and
     *         {@code canvasHeight} are positive
     */
    public static void setCanvasSize(int canvasWidth, int canvasHeight) {
        if (canvasWidth <= 0 || canvasHeight <= 0)
            throw new IllegalArgumentException("width and height must be positive");
        width = canvasWidth;
        height = canvasHeight;
        init();
    }

    // init
    private static void init() {
        if (frame != null) frame.setVisible(false);
        frame = new JFrame();
        offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        onscreenImage  = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        offGraph = offscreenImage.createGraphics();
        onGraph  = onscreenImage.createGraphics();
        setXscale();
        setYscale();
        offGraph.setColor(DEFAULT_CLEAR_COLOR);
        offGraph.fillRect(0, 0, width, height);
        setPenColor();
        setPenRadius();
        setFont();
        clear();

        // add antialiasing
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                                                  RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        offGraph.addRenderingHints(hints);

        // frame stuff
        ImageIcon icon = new ImageIcon(onscreenImage);
        JLabel draw = new JLabel(icon);

        draw.addMouseListener(std);
        draw.addMouseMotionListener(std);

        frame.setContentPane(draw);
        frame.addKeyListener(std);    // JLabel cannot get keyboard focus
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            // closes all windows
        // frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);      // closes only current window
        frame.setTitle("Standard Draw");
        frame.setJMenuBar(createMenuBar());
        frame.pack();
        frame.requestFocusInWindow();
        frame.setVisible(true);
    }

    // create the menu bar (changed to private)
    private static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem menuItem1 = new JMenuItem(" Save...   ");
        menuItem1.addActionListener(std);
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menu.add(menuItem1);
        return menuBar;
    }


   /***************************************************************************
    *  User and screen coordinate systems.
    ***************************************************************************/

    /**
     * Sets the <em>x</em>-scale to be the default (between 0.0 and 1.0).
     */
    public static void setXscale() {
        setXscale(DEFAULT_XMIN, DEFAULT_XMAX);
    }

    /**
     * Sets the <em>y</em>-scale to be the default (between 0.0 and 1.0).
     */
    public static void setYscale() {
        setYscale(DEFAULT_YMIN, DEFAULT_YMAX);
    }

    /**
     * Sets the <em>x</em>-scale and <em>y</em>-scale to be the default
     * (between 0.0 and 1.0).
     */
    public static void setScale() {
        setXscale();
        setYscale();
    }

    /**
     * Sets the <em>x</em>-scale to the specified range.
     *
     * @param  min the minimum value of the <em>x</em>-scale
     * @param  max the maximum value of the <em>x</em>-scale
     * @throws IllegalArgumentException if {@code (max == min)}
     */
    public static void setXscale(double min, double max) {
        double size = max - min;
        if (size == 0.0) throw new IllegalArgumentException("the min and max are the same");
        synchronized (mouseLock) {
            xmin = min - BORDER * size;
            xmax = max + BORDER * size;
        }
    }

    /**
     * Sets the <em>y</em>-scale to the specified range.
     *
     * @param  min the minimum value of the <em>y</em>-scale
     * @param  max the maximum value of the <em>y</em>-scale
     * @throws IllegalArgumentException if {@code (max == min)}
     */
    public static void setYscale(double min, double max) {
        double size = max - min;
        if (size == 0.0) throw new IllegalArgumentException("the min and max are the same");
        synchronized (mouseLock) {
            ymin = min - BORDER * size;
            ymax = max + BORDER * size;
        }
    }

    /**
     * Sets both the <em>x</em>-scale and <em>y</em>-scale to the (same) specified range.
     *
     * @param  min the minimum value of the <em>x</em>- and <em>y</em>-scales
     * @param  max the maximum value of the <em>x</em>- and <em>y</em>-scales
     * @throws IllegalArgumentException if {@code (max == min)}
     */
    public static void setScale(double min, double max) {
        double size = max - min;
        if (size == 0.0) throw new IllegalArgumentException("the min and max are the same");
        synchronized (mouseLock) {
            xmin = min - BORDER * size;
            xmax = max + BORDER * size;
            ymin = min - BORDER * size;
            ymax = max + BORDER * size;
        }
    }

    // helper functions that scale from user coordinates to screen coordinates and back
    private static double  scaleX(double x) { return width  * (x - xmin) / (xmax - xmin); }
    private static double  scaleY(double y) { return height * (ymax - y) / (ymax - ymin); }
    private static double factorX(double w) { return w * width  / Math.abs(xmax - xmin);  }
    private static double factorY(double h) { return h * height / Math.abs(ymax - ymin);  }
    private static double   userX(double x) { return xmin + x * (xmax - xmin) / width;    }
    private static double   userY(double y) { return ymax - y * (ymax - ymin) / height;   }


    /**
     * Clears the screen to the default color (white).
     */
    public static void clear() {
        clear(DEFAULT_CLEAR_COLOR);
    }

    /**
     * Clears the screen to the specified color.
     *
     * @param color the color to make the background
     */
    public static void clear(Color color) {
        offGraph.setColor(color);
        offGraph.fillRect(0, 0, width, height);
        offGraph.setColor(penColor);
        draw();
    }

    /**
     * Returns the current pen radius.
     *
     * @return the current value of the pen radius
     */
    public static double getPenRadius() {
        return penRadius;
    }

    /**
     * Sets the pen size to the default size (0.002).
     * The pen is circular, so that lines have rounded ends, and when you set the
     * pen radius and draw a point, you get a circle of the specified radius.
     * The pen radius is not affected by coordinate scaling.
     */
    public static void setPenRadius() {
        setPenRadius(DEFAULT_PEN_RADIUS);
    }

    /**
     * Sets the radius of the pen to the specified size.
     * The pen is circular, so that lines have rounded ends, and when you set the
     * pen radius and draw a point, you get a circle of the specified radius.
     * The pen radius is not affected by coordinate scaling.
     *
     * @param  radius the radius of the pen
     * @throws IllegalArgumentException if {@code radius} is negative
     */
    public static void setPenRadius(double radius) {
        if (!(radius >= 0)) throw new IllegalArgumentException("pen radius must be nonnegative");
        penRadius = radius;
        float scaledPenRadius = (float) (radius * DEFAULT_SIZE);
        BasicStroke stroke = new BasicStroke(scaledPenRadius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        // BasicStroke stroke = new BasicStroke(scaledPenRadius);
        offGraph.setStroke(stroke);
    }

    /**
     * Returns the current pen color.
     *
     * @return the current pen color
     */
    public static Color getPenColor() {
        return penColor;
    }

    /**
     * Set the pen color to the default color (black).
     */
    public static void setPenColor() {
        setPenColor(DEFAULT_PEN_COLOR);
    }

    /**
     * Sets the pen color to the specified color.
     * <p>
     * The predefined pen colors are
     * {@code StdDraw.BLACK}, {@code StdDraw.BLUE}, {@code StdDraw.CYAN},
     * {@code StdDraw.DARK_GRAY}, {@code StdDraw.GRAY}, {@code StdDraw.GREEN},
     * {@code StdDraw.LIGHT_GRAY}, {@code StdDraw.MAGENTA}, {@code StdDraw.ORANGE},
     * {@code StdDraw.PINK}, {@code StdDraw.RED}, {@code StdDraw.WHITE}, and
     * {@code StdDraw.YELLOW}.
     *
     * @param color the color to make the pen
     */
    public static void setPenColor(Color color) {
        if (color == null) throw new IllegalArgumentException();
        penColor = color;
        offGraph.setColor(penColor);
    }

    /**
     * Sets the pen color to the specified RGB color.
     *
     * @param  red the amount of red (between 0 and 255)
     * @param  green the amount of green (between 0 and 255)
     * @param  blue the amount of blue (between 0 and 255)
     * @throws IllegalArgumentException if {@code red}, {@code green},
     *         or {@code blue} is outside its prescribed range
     */
    public static void setPenColor(int red, int green, int blue) {
        if (red   < 0 || red   >= 256) throw new IllegalArgumentException("amount of red must be between 0 and 255");
        if (green < 0 || green >= 256) throw new IllegalArgumentException("amount of green must be between 0 and 255");
        if (blue  < 0 || blue  >= 256) throw new IllegalArgumentException("amount of blue must be between 0 and 255");
        setPenColor(new Color(red, green, blue));
    }

    /**
     * Returns the current font.
     *
     * @return the current font
     */
    public static Font getFont() {
        return font;
    }

    /**
     * Sets the font to the default font (sans serif, 16 point).
     */
    public static void setFont() {
        setFont(DEFAULT_FONT);
    }

    /**
     * Sets the font to the specified value.
     *
     * @param font the font
     */
    public static void setFont(Font font) {
        if (font == null) throw new IllegalArgumentException();
        StdDraw.font = font;
    }


   /***************************************************************************
    *  Drawing geometric shapes.
    ***************************************************************************/

    /**
     * Draws a line segment between (<em>x</em><sub>0</sub>, <em>y</em><sub>0</sub>) and
     * (<em>x</em><sub>1</sub>, <em>y</em><sub>1</sub>).
     *
     * @param  x0 the <em>x</em>-coordinate of one endpoint
     * @param  y0 the <em>y</em>-coordinate of one endpoint
     * @param  x1 the <em>x</em>-coordinate of the other endpoint
     * @param  y1 the <em>y</em>-coordinate of the other endpoint
     */
    public static void line(double x0, double y0, double x1, double y1) {
        offGraph.draw(new Line2D.Double(scaleX(x0), scaleY(y0), scaleX(x1), scaleY(y1)));
        draw();
    }

    /**
     * Draws one pixel at (<em>x</em>, <em>y</em>).
     * This method is private because pixels depend on the display.
     * To achieve the same effect, set the pen radius to 0 and call {@code point()}.
     *
     * @param  x the <em>x</em>-coordinate of the pixel
     * @param  y the <em>y</em>-coordinate of the pixel
     */
    private static void pixel(double x, double y) {
        offGraph.fillRect((int) Math.round(scaleX(x)), (int) Math.round(scaleY(y)), 1, 1);
    }

    /**
     * Draws a point centered at (<em>x</em>, <em>y</em>).
     * The point is a filled circle whose radius is equal to the pen radius.
     * To draw a single-pixel point, first set the pen radius to 0.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public static void point(double x, double y) {
        double xs = scaleX(x);
        double ys = scaleY(y);
        double r = penRadius;
        float scaledPenRadius = (float) (r * DEFAULT_SIZE);

        // double ws = factorX(2*r);
        // double hs = factorY(2*r);
        // if (ws <= 1 && hs <= 1) pixel(x, y);
        if (scaledPenRadius <= 1) pixel(x, y);
        else offGraph.fill(new Ellipse2D.Double(xs - scaledPenRadius/2, ys - scaledPenRadius/2,
                                                 scaledPenRadius, scaledPenRadius));
        draw();
    }

    /**
     * Draws a circle of the specified radius, centered at (<em>x</em>, <em>y</em>).
     *
     * @param  x the <em>x</em>-coordinate of the center of the circle
     * @param  y the <em>y</em>-coordinate of the center of the circle
     * @param  radius the radius of the circle
     * @throws IllegalArgumentException if {@code radius} is negative
     */
    public static void circle(double x, double y, double radius) {
        if (!(radius >= 0)) throw new IllegalArgumentException("radius must be nonnegative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*radius);
        double hs = factorY(2*radius);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offGraph.draw(new Ellipse2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }

    /**
     * Draws a filled circle of the specified radius, centered at (<em>x</em>, <em>y</em>).
     *
     * @param  x the <em>x</em>-coordinate of the center of the circle
     * @param  y the <em>y</em>-coordinate of the center of the circle
     * @param  radius the radius of the circle
     * @throws IllegalArgumentException if {@code radius} is negative
     */
    public static void filledCircle(double x, double y, double radius) {
        if (!(radius >= 0)) throw new IllegalArgumentException("radius must be nonnegative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*radius);
        double hs = factorY(2*radius);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offGraph.fill(new Ellipse2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }


    /**
     * Draws an ellipse with the specified semimajor and semiminor axes,
     * centered at (<em>x</em>, <em>y</em>).
     *
     * @param  x the <em>x</em>-coordinate of the center of the ellipse
     * @param  y the <em>y</em>-coordinate of the center of the ellipse
     * @param  semiMajorAxis is the semimajor axis of the ellipse
     * @param  semiMinorAxis is the semiminor axis of the ellipse
     * @throws IllegalArgumentException if either {@code semiMajorAxis}
     *         or {@code semiMinorAxis} is negative
     */
    public static void ellipse(double x, double y, double semiMajorAxis, double semiMinorAxis) {
        if (!(semiMajorAxis >= 0)) throw new IllegalArgumentException("ellipse semimajor axis must be nonnegative");
        if (!(semiMinorAxis >= 0)) throw new IllegalArgumentException("ellipse semiminor axis must be nonnegative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*semiMajorAxis);
        double hs = factorY(2*semiMinorAxis);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offGraph.draw(new Ellipse2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }

    /**
     * Draws an ellipse with the specified semimajor and semiminor axes,
     * centered at (<em>x</em>, <em>y</em>).
     *
     * @param  x the <em>x</em>-coordinate of the center of the ellipse
     * @param  y the <em>y</em>-coordinate of the center of the ellipse
     * @param  semiMajorAxis is the semimajor axis of the ellipse
     * @param  semiMinorAxis is the semiminor axis of the ellipse
     * @throws IllegalArgumentException if either {@code semiMajorAxis}
     *         or {@code semiMinorAxis} is negative
     */
    public static void filledEllipse(double x, double y, double semiMajorAxis, double semiMinorAxis) {
        if (!(semiMajorAxis >= 0)) throw new IllegalArgumentException("ellipse semimajor axis must be nonnegative");
        if (!(semiMinorAxis >= 0)) throw new IllegalArgumentException("ellipse semiminor axis must be nonnegative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*semiMajorAxis);
        double hs = factorY(2*semiMinorAxis);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offGraph.fill(new Ellipse2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }


    /**
     * Draws a circular arc of the specified radius,
     * centered at (<em>x</em>, <em>y</em>), from angle1 to angle2 (in degrees).
     *
     * @param  x the <em>x</em>-coordinate of the center of the circle
     * @param  y the <em>y</em>-coordinate of the center of the circle
     * @param  radius the radius of the circle
     * @param  angle1 the starting angle. 0 would mean an arc beginning at 3 o'clock.
     * @param  angle2 the angle at the end of the arc. For example, if
     *         you want a 90 degree arc, then angle2 should be angle1 + 90.
     * @throws IllegalArgumentException if {@code radius} is negative
     */
    public static void arc(double x, double y, double radius, double angle1, double angle2) {
        if (radius < 0) throw new IllegalArgumentException("arc radius must be nonnegative");
        while (angle2 < angle1) angle2 += 360;
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*radius);
        double hs = factorY(2*radius);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offGraph.draw(new Arc2D.Double(xs - ws/2, ys - hs/2, ws, hs, angle1, angle2 - angle1, Arc2D.OPEN));
        draw();
    }

    /**
     * Draws a square of side length 2r, centered at (<em>x</em>, <em>y</em>).
     *
     * @param  x the <em>x</em>-coordinate of the center of the square
     * @param  y the <em>y</em>-coordinate of the center of the square
     * @param  halfLength one half the length of any side of the square
     * @throws IllegalArgumentException if {@code halfLength} is negative
     */
    public static void square(double x, double y, double halfLength) {
        if (!(halfLength >= 0)) throw new IllegalArgumentException("half length must be nonnegative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*halfLength);
        double hs = factorY(2*halfLength);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offGraph.draw(new Rectangle2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }

    /**
     * Draws a filled square of the specified size, centered at (<em>x</em>, <em>y</em>).
     *
     * @param  x the <em>x</em>-coordinate of the center of the square
     * @param  y the <em>y</em>-coordinate of the center of the square
     * @param  halfLength one half the length of any side of the square
     * @throws IllegalArgumentException if {@code halfLength} is negative
     */
    public static void filledSquare(double x, double y, double halfLength) {
        if (!(halfLength >= 0)) throw new IllegalArgumentException("half length must be nonnegative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*halfLength);
        double hs = factorY(2*halfLength);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offGraph.fill(new Rectangle2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }


    /**
     * Draws a rectangle of the specified size, centered at (<em>x</em>, <em>y</em>).
     *
     * @param  x the <em>x</em>-coordinate of the center of the rectangle
     * @param  y the <em>y</em>-coordinate of the center of the rectangle
     * @param  halfWidth one half the width of the rectangle
     * @param  halfHeight one half the height of the rectangle
     * @throws IllegalArgumentException if either {@code halfWidth} or {@code halfHeight} is negative
     */
    public static void rectangle(double x, double y, double halfWidth, double halfHeight) {
        if (!(halfWidth  >= 0)) throw new IllegalArgumentException("half width must be nonnegative");
        if (!(halfHeight >= 0)) throw new IllegalArgumentException("half height must be nonnegative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*halfWidth);
        double hs = factorY(2*halfHeight);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offGraph.draw(new Rectangle2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }

    /**
     * Draws a filled rectangle of the specified size, centered at (<em>x</em>, <em>y</em>).
     *
     * @param  x the <em>x</em>-coordinate of the center of the rectangle
     * @param  y the <em>y</em>-coordinate of the center of the rectangle
     * @param  halfWidth one half the width of the rectangle
     * @param  halfHeight one half the height of the rectangle
     * @throws IllegalArgumentException if either {@code halfWidth} or {@code halfHeight} is negative
     */
    public static void filledRectangle(double x, double y, double halfWidth, double halfHeight) {
        if (!(halfWidth  >= 0)) throw new IllegalArgumentException("half width must be nonnegative");
        if (!(halfHeight >= 0)) throw new IllegalArgumentException("half height must be nonnegative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*halfWidth);
        double hs = factorY(2*halfHeight);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offGraph.fill(new Rectangle2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }


    /**
     * Draws a polygon with the vertices 
     * (<em>x</em><sub>0</sub>, <em>y</em><sub>0</sub>),
     * (<em>x</em><sub>1</sub>, <em>y</em><sub>1</sub>), ...,
     * (<em>x</em><sub><em>n</em>–1</sub>, <em>y</em><sub><em>n</em>–1</sub>).
     *
     * @param  x an array of all the <em>x</em>-coordinates of the polygon
     * @param  y an array of all the <em>y</em>-coordinates of the polygon
     * @throws IllegalArgumentException unless {@code x[]} and {@code y[]}
     *         are of the same length
     */
    public static void polygon(double[] x, double[] y) {
        if (x == null) throw new IllegalArgumentException("x-coordinate array is null");
        if (y == null) throw new IllegalArgumentException("y-coordinate array is null");
        int n1 = x.length;
        int n2 = y.length;
        if (n1 != n2) throw new IllegalArgumentException("arrays must be of the same length");
        int n = n1;
        if (n == 0) return;

        GeneralPath path = new GeneralPath();
        path.moveTo((float) scaleX(x[0]), (float) scaleY(y[0]));
        for (int i = 0; i < n; i++)
            path.lineTo((float) scaleX(x[i]), (float) scaleY(y[i]));
        path.closePath();
        offGraph.draw(path);
        draw();
    }

    /**
     * Draws a polygon with the vertices 
     * (<em>x</em><sub>0</sub>, <em>y</em><sub>0</sub>),
     * (<em>x</em><sub>1</sub>, <em>y</em><sub>1</sub>), ...,
     * (<em>x</em><sub><em>n</em>–1</sub>, <em>y</em><sub><em>n</em>–1</sub>).
     *
     * @param  x an array of all the <em>x</em>-coordinates of the polygon
     * @param  y an array of all the <em>y</em>-coordinates of the polygon
     * @throws IllegalArgumentException unless {@code x[]} and {@code y[]}
     *         are of the same length
     */
    public static void filledPolygon(double[] x, double[] y) {
        if (x == null) throw new IllegalArgumentException("x-coordinate array is null");
        if (y == null) throw new IllegalArgumentException("y-coordinate array is null");
        int n1 = x.length;
        int n2 = y.length;
        if (n1 != n2) throw new IllegalArgumentException("arrays must be of the same length");
        int n = n1;
        if (n == 0) return;

        GeneralPath path = new GeneralPath();
        path.moveTo((float) scaleX(x[0]), (float) scaleY(y[0]));
        for (int i = 0; i < n; i++)
            path.lineTo((float) scaleX(x[i]), (float) scaleY(y[i]));
        path.closePath();
        offGraph.fill(path);
        draw();
    }


   /***************************************************************************
    *  Drawing images.
    ***************************************************************************/
    // get an image from the given filename
    private static Image getImage(String filename) {
        if (filename == null) throw new IllegalArgumentException();

        // to read from file
        ImageIcon icon = new ImageIcon(filename);

        // try to read from URL
        if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
            try {
                URL url = new URL(filename);
                icon = new ImageIcon(url);
            }
            catch (MalformedURLException e) {
                /* not a url */
            }
        }

        // in case file is inside a .jar (classpath relative to StdDraw)
        if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
            URL url = StdDraw.class.getResource(filename);
            if (url != null)
                icon = new ImageIcon(url);
        }

        // in case file is inside a .jar (classpath relative to root of jar)
        if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
            URL url = StdDraw.class.getResource("/" + filename);
            if (url == null) throw new IllegalArgumentException("image " + filename + " not found");
            icon = new ImageIcon(url);
        }

        return icon.getImage();
    }

   /***************************************************************************
    * [Summer 2016] Should we update to use ImageIO instead of ImageIcon()?
    *               Seems to have some issues loading images on some systems
    *               and slows things down on other systems.
    *               especially if you don't call ImageIO.setUseCache(false)
    *               One advantage is that it returns a BufferedImage.
    ***************************************************************************/
/*
    private static BufferedImage getImage(String filename) {
        if (filename == null) throw new IllegalArgumentException();

        // from a file or URL
        try {
            URL url = new URL(filename);
            BufferedImage image = ImageIO.read(url);
            return image;
        } 
        catch (IOException e) {
            // ignore
        }

        // in case file is inside a .jar (classpath relative to StdDraw)
        try {
            URL url = StdDraw.class.getResource(filename);
            BufferedImage image = ImageIO.read(url);
            return image;
        } 
        catch (IOException e) {
            // ignore
        }

        // in case file is inside a .jar (classpath relative to root of jar)
        try {
            URL url = StdDraw.class.getResource("/" + filename);
            BufferedImage image = ImageIO.read(url);
            return image;
        } 
        catch (IOException e) {
            // ignore
        }
        throw new IllegalArgumentException("image " + filename + " not found");
    }
*/
    /**
     * Draws the specified image centered at (<em>x</em>, <em>y</em>).
     * The supported image formats are JPEG, PNG, and GIF.
     * As an optimization, the picture is cached, so there is no performance
     * penalty for redrawing the same image multiple times (e.g., in an animation).
     * However, if you change the picture file after drawing it, subsequent
     * calls will draw the original picture.
     *
     * @param  x the center <em>x</em>-coordinate of the image
     * @param  y the center <em>y</em>-coordinate of the image
     * @param  filename the name of the image/picture, e.g., "ball.gif"
     * @throws IllegalArgumentException if the image filename is invalid
     */
    public static void picture(double x, double y, String filename) {
        // BufferedImage image = getImage(filename);
        Image image = getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        // int ws = image.getWidth();    // can call only if image is a BufferedImage
        // int hs = image.getHeight();
        int ws = image.getWidth(null);
        int hs = image.getHeight(null);
        if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");

        offGraph.drawImage(image, (int) Math.round(xs - ws/2.0), (int) Math.round(ys - hs/2.0), null);
        draw();
    }

    /**
     * Draws the specified image centered at (<em>x</em>, <em>y</em>),
     * rotated given number of degrees.
     * The supported image formats are JPEG, PNG, and GIF.
     *
     * @param  x the center <em>x</em>-coordinate of the image
     * @param  y the center <em>y</em>-coordinate of the image
     * @param  filename the name of the image/picture, e.g., "ball.gif"
     * @param  degrees is the number of degrees to rotate counterclockwise
     * @throws IllegalArgumentException if the image filename is invalid
     */
    public static void picture(double x, double y, String filename, double degrees) {
        // BufferedImage image = getImage(filename);
        Image image = getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        // int ws = image.getWidth();    // can call only if image is a BufferedImage
        // int hs = image.getHeight();
        int ws = image.getWidth(null);
        int hs = image.getHeight(null);
        if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");

        offGraph.rotate(Math.toRadians(-degrees), xs, ys);
        offGraph.drawImage(image, (int) Math.round(xs - ws/2.0), (int) Math.round(ys - hs/2.0), null);
        offGraph.rotate(Math.toRadians(+degrees), xs, ys);

        draw();
    }

    /**
     * Draws the specified image centered at (<em>x</em>, <em>y</em>),
     * rescaled to the specified bounding box.
     * The supported image formats are JPEG, PNG, and GIF.
     *
     * @param  x the center <em>x</em>-coordinate of the image
     * @param  y the center <em>y</em>-coordinate of the image
     * @param  filename the name of the image/picture, e.g., "ball.gif"
     * @param  scaledWidth the width of the scaled image (in screen coordinates)
     * @param  scaledHeight the height of the scaled image (in screen coordinates)
     * @throws IllegalArgumentException if either {@code scaledWidth}
     *         or {@code scaledHeight} is negative
     * @throws IllegalArgumentException if the image filename is invalid
     */
    public static void picture(double x, double y, String filename, double scaledWidth, double scaledHeight) {
        Image image = getImage(filename);
        if (scaledWidth  < 0) throw new IllegalArgumentException("width  is negative: " + scaledWidth);
        if (scaledHeight < 0) throw new IllegalArgumentException("height is negative: " + scaledHeight);
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(scaledWidth);
        double hs = factorY(scaledHeight);
        if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else {
            offGraph.drawImage(image, (int) Math.round(xs - ws/2.0),
                                       (int) Math.round(ys - hs/2.0),
                                       (int) Math.round(ws),
                                       (int) Math.round(hs), null);
        }
        draw();
    }


    /**
     * Draws the specified image centered at (<em>x</em>, <em>y</em>), rotated
     * given number of degrees, and rescaled to the specified bounding box.
     * The supported image formats are JPEG, PNG, and GIF.
     *
     * @param  x the center <em>x</em>-coordinate of the image
     * @param  y the center <em>y</em>-coordinate of the image
     * @param  filename the name of the image/picture, e.g., "ball.gif"
     * @param  scaledWidth the width of the scaled image (in screen coordinates)
     * @param  scaledHeight the height of the scaled image (in screen coordinates)
     * @param  degrees is the number of degrees to rotate counterclockwise
     * @throws IllegalArgumentException if either {@code scaledWidth}
     *         or {@code scaledHeight} is negative
     * @throws IllegalArgumentException if the image filename is invalid
     */
    public static void picture(double x, double y, String filename, double scaledWidth, double scaledHeight, double degrees) {
        if (scaledWidth < 0) throw new IllegalArgumentException("width is negative: " + scaledWidth);
        if (scaledHeight < 0) throw new IllegalArgumentException("height is negative: " + scaledHeight);
        Image image = getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(scaledWidth);
        double hs = factorY(scaledHeight);
        if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");
        if (ws <= 1 && hs <= 1) pixel(x, y);

        offGraph.rotate(Math.toRadians(-degrees), xs, ys);
        offGraph.drawImage(image, (int) Math.round(xs - ws/2.0),
                                   (int) Math.round(ys - hs/2.0),
                                   (int) Math.round(ws),
                                   (int) Math.round(hs), null);
        offGraph.rotate(Math.toRadians(+degrees), xs, ys);

        draw();
    }

   /***************************************************************************
    *  Drawing text.
    ***************************************************************************/

    /**
     * Write the given text string in the current font, centered at (<em>x</em>, <em>y</em>).
     *
     * @param  x the center <em>x</em>-coordinate of the text
     * @param  y the center <em>y</em>-coordinate of the text
     * @param  text the text to write
     */
    public static void text(double x, double y, String text) {
        if (text == null) throw new IllegalArgumentException();
        offGraph.setFont(font);
        FontMetrics metrics = offGraph.getFontMetrics();
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = metrics.stringWidth(text);
        int hs = metrics.getDescent();
        offGraph.drawString(text, (float) (xs - ws/2.0), (float) (ys + hs));
        draw();
    }

    /**
     * Write the given text string in the current font, centered at (<em>x</em>, <em>y</em>) and
     * rotated by the specified number of degrees.
     * @param  x the center <em>x</em>-coordinate of the text
     * @param  y the center <em>y</em>-coordinate of the text
     * @param  text the text to write
     * @param  degrees is the number of degrees to rotate counterclockwise
     */
    public static void text(double x, double y, String text, double degrees) {
        if (text == null) throw new IllegalArgumentException();
        double xs = scaleX(x);
        double ys = scaleY(y);
        offGraph.rotate(Math.toRadians(-degrees), xs, ys);
        text(x, y, text);
        offGraph.rotate(Math.toRadians(+degrees), xs, ys);
    }


    /**
     * Write the given text string in the current font, left-aligned at (<em>x</em>, <em>y</em>).
     * @param  x the <em>x</em>-coordinate of the text
     * @param  y the <em>y</em>-coordinate of the text
     * @param  text the text
     */
    public static void textLeft(double x, double y, String text) {
        if (text == null) throw new IllegalArgumentException();
        offGraph.setFont(font);
        FontMetrics metrics = offGraph.getFontMetrics();
        double xs = scaleX(x);
        double ys = scaleY(y);
        int hs = metrics.getDescent();
        offGraph.drawString(text, (float) xs, (float) (ys + hs));
        draw();
    }

    /**
     * Write the given text string in the current font, right-aligned at (<em>x</em>, <em>y</em>).
     *
     * @param  x the <em>x</em>-coordinate of the text
     * @param  y the <em>y</em>-coordinate of the text
     * @param  text the text to write
     */
    public static void textRight(double x, double y, String text) {
        if (text == null) throw new IllegalArgumentException();
        offGraph.setFont(font);
        FontMetrics metrics = offGraph.getFontMetrics();
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = metrics.stringWidth(text);
        int hs = metrics.getDescent();
        offGraph.drawString(text, (float) (xs - ws), (float) (ys + hs));
        draw();
    }



    /**
     * Copies the offscreen buffer to the onscreen buffer, pauses for t milliseconds
     * and enables double buffering.
     * @param t number of milliseconds
     * @deprecated replaced by {@link #enableDoubleBuffering()}, {@link #show()}, and {@link #pause(int t)}
     */
    @Deprecated
    public static void show(int t) {
        show();
        pause(t);
        enableDoubleBuffering();
    }

    /**
     * Pause for t milliseconds. This method is intended to support computer animations.
     * @param t number of milliseconds
     */
    public static void pause(int t) {
        try {
            Thread.sleep(t);
        }
        catch (InterruptedException e) {
            System.out.println("Error sleeping");
        }
    }

    /**
     * Copies offscreen buffer to onscreen buffer. There is no reason to call
     * this method unless double buffering is enabled.
     */
    public static void show() {
        onGraph.drawImage(offscreenImage, 0, 0, null);
        frame.repaint();
    }

    // draw onscreen if defer is false
    private static void draw() {
        if (!double_buffer) show();
    }

    /**
     * Enable double buffering. All subsequent calls to 
     * drawing methods such as {@code line()}, {@code circle()},
     * and {@code square()} will be deffered until the next call
     * to show(). Useful for animations.
     */
    public static void enableDoubleBuffering() {
        double_buffer = true;
    }

    /**
     * Disable double buffering. All subsequent calls to 
     * drawing methods such as {@code line()}, {@code circle()},
     * and {@code square()} will be displayed on screen when called.
     * This is the default.
     */
    public static void disableDoubleBuffering() {
        double_buffer = false;
    }


   /***************************************************************************
    *  Save drawing to a file.
    ***************************************************************************/

    /**
     * Saves the drawing to using the specified filename.
     * The supported image formats are JPEG and PNG;
     * the filename suffix must be {@code .jpg} or {@code .png}.
     *
     * @param  filename the name of the file with one of the required suffixes
     */
    public static void save(String filename) {
        if (filename == null) throw new IllegalArgumentException();
        File file = new File(filename);
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);

        // png files
        if ("png".equalsIgnoreCase(suffix)) {
            try {
                ImageIO.write(onscreenImage, suffix, file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        // need to change from ARGB to RGB for JPEG
        // reference: http://archives.java.sun.com/cgi-bin/wa?A2=ind0404&L=java2d-interest&D=0&P=2727
        else if ("jpg".equalsIgnoreCase(suffix)) {
            WritableRaster raster = onscreenImage.getRaster();
            WritableRaster newRaster;
            newRaster = raster.createWritableChild(0, 0, width, height, 0, 0, new int[] {0, 1, 2});
            DirectColorModel cm = (DirectColorModel) onscreenImage.getColorModel();
            DirectColorModel newCM = new DirectColorModel(cm.getPixelSize(),
                                                          cm.getRedMask(),
                                                          cm.getGreenMask(),
                                                          cm.getBlueMask());
            BufferedImage rgbBuffer = new BufferedImage(newCM, newRaster, false,  null);
            try {
                ImageIO.write(rgbBuffer, suffix, file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        else {
            System.out.println("Invalid image file type: " + suffix);
        }
    }


    /**
     * This method cannot be called directly.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        FileDialog chooser = new FileDialog(StdDraw.frame, "Use a .png or .jpg extension", FileDialog.SAVE);
        chooser.setVisible(true);
        String filename = chooser.getFile();
        if (filename != null) {
            StdDraw.save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }


   /***************************************************************************
    *  Mouse interactions.
    ***************************************************************************/

    /**
     * Returns true if the mouse is being pressed.
     *
     * @return {@code true} if the mouse is being pressed; {@code false} otherwise
     */
    public static boolean isMousePressed() {
        synchronized (mouseLock) {
            return isMousePressed;
        }
    }

    /**
     * Returns true if the mouse is being pressed.
     *
     * @return {@code true} if the mouse is being pressed; {@code false} otherwise
     * @deprecated replaced by {@link #isMousePressed()}
     */
    @Deprecated
    public static boolean mousePressed() {
        synchronized (mouseLock) {
            return isMousePressed;
        }
    }

    /**
     * Returns the <em>x</em>-coordinate of the mouse.
     *
     * @return the <em>x</em>-coordinate of the mouse
     */
    public static double mouseX() {
        synchronized (mouseLock) {
            return mouseX;
        }
    }

    /**
     * Returns the <em>y</em>-coordinate of the mouse.
     *
     * @return <em>y</em>-coordinate of the mouse
     */
    public static double mouseY() {
        synchronized (mouseLock) {
            return mouseY;
        }
    }


    /**
     * This method cannot be called directly.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // this body is intentionally left empty
    }

    /**
     * This method cannot be called directly.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // this body is intentionally left empty
    }

    /**
     * This method cannot be called directly.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // this body is intentionally left empty
    }

    /**
     * This method cannot be called directly.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        synchronized (mouseLock) {
            mouseX = StdDraw.userX(e.getX());
            mouseY = StdDraw.userY(e.getY());
            isMousePressed = true;
        }
    }

    /**
     * This method cannot be called directly.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        synchronized (mouseLock) {
            isMousePressed = false;
        }
    }

    /**
     * This method cannot be called directly.
     */
    @Override
    public void mouseDragged(MouseEvent e)  {
        synchronized (mouseLock) {
            mouseX = StdDraw.userX(e.getX());
            mouseY = StdDraw.userY(e.getY());
        }
    }

    /**
     * This method cannot be called directly.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        synchronized (mouseLock) {
            mouseX = StdDraw.userX(e.getX());
            mouseY = StdDraw.userY(e.getY());
        }
    }


   /***************************************************************************
    *  Keyboard interactions.
    ***************************************************************************/

    /**
     * Returns true if the user has typed a key (that has not yet been processed).
     *
     * @return {@code true} if the user has typed a key (that has not yet been processed
     *         by {@link #nextKeyTyped()}; {@code false} otherwise
     */
    public static boolean hasNextKeyTyped() {
        synchronized (keyLock) {
            return !keysTyped.isEmpty();
        }
    }

    /**
     * Returns the next key that was typed by the user (that your program has not already processed).
     * This method should be preceded by a call to {@link #hasNextKeyTyped()} to ensure
     * that there is a next key to process.
     * This method returns a Unicode character corresponding to the key
     * typed (such as {@code 'a'} or {@code 'A'}).
     * It cannot identify action keys (such as F1 and arrow keys)
     * or modifier keys (such as control).
     *
     * @return the next key typed by the user (that your program has not already processed).
     * @throws NoSuchElementException if there is no remaining key
     */
    public static char nextKeyTyped() {
        synchronized (keyLock) {
            if (keysTyped.isEmpty()) {
                throw new NoSuchElementException("your program has already processed all keystrokes");
            }
            return keysTyped.remove(keysTyped.size() - 1);
            // return keysTyped.removeLast();
        }
    }

    /**
     * Returns true if the given key is being pressed.
     * <p>
     * This method takes the keycode (corresponding to a physical key)
    *  as an argument. It can handle action keys
     * (such as F1 and arrow keys) and modifier keys (such as shift and control).
     * See {@link KeyEvent} for a description of key codes.
     *
     * @param  keycode the key to check if it is being pressed
     * @return {@code true} if {@code keycode} is currently being pressed;
     *         {@code false} otherwise
     */
    public static boolean isKeyPressed(int keycode) {
        synchronized (keyLock) {
            return keysDown.contains(keycode);
        }
    }


    /**
     * This method cannot be called directly.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        synchronized (keyLock) {
            keysTyped.addFirst(e.getKeyChar());
        }
    }

    /**
     * This method cannot be called directly.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        synchronized (keyLock) {
            keysDown.add(e.getKeyCode());
        }
    }

    /**
     * This method cannot be called directly.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        synchronized (keyLock) {
            keysDown.remove(e.getKeyCode());
        }
    }




    /**
     * Test client.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        StdDraw.square(0.2, 0.8, 0.1);
        StdDraw.filledSquare(0.8, 0.8, 0.2);
        StdDraw.circle(0.8, 0.2, 0.2);

        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.setPenRadius(0.02);
        StdDraw.arc(0.8, 0.2, 0.1, 200, 45);

        // draw a blue diamond
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        double[] x = { 0.1, 0.2, 0.3, 0.2 };
        double[] y = { 0.2, 0.3, 0.2, 0.1 };
        StdDraw.filledPolygon(x, y);

        // text
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(0.2, 0.5, "black text");
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.8, 0.8, "white text");
    }

}
