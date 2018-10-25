package com.ray.gui;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * 
 * �����ṩ���� RGB ��ɫģʽ�����ع��ɵ�ͼ�εķ�����Alpha Ŀǰ��֧�֡� ԭʼ��ͼƬ������ {@code PNG}, {@code GIF},
 * {@code JPEG} �ļ���ʹ����Ҳ���Դ���һ�����ļ��� ���������ͼƬչʾ�ڴ��ڵķ������ǽ�ͼƬ�������ļ���
 * <p>
 * ���أ�<em>col</em>, <em>row</em>��col ��ʾ�����У�row ��ʾ�����С� Ĭ������£���ʼ���� ��0,0����ʾ���Ͻǣ� ����
 * {@link #setOriginLowerLeft()} ���Ըı���ʼ����Ϊ���½ǡ�
 * <p>
 * {@code get()} �� {@code set()} ������ {@link Color} ��������ȡ�������ض����ص���ɫ��
 * {@code getRGB()} �� {@code setRGB()} ������32λ��������������ɫ�Ӷ�����ֱ�Ӵ���{@code Color} ����.
 * ��(R)����(G)����(B)����ʹ����������Ҫ24λ��
 * <p>
 * ����һ��32λ�������ɫ������ʹ�����·�������ȡ RGB �ɷ� <blockquote>
 * 
 * <pre>
 * int r = (rgb >> 16) & 0xFF;
 * int g = (rgb >> 8) & 0xFF;
 * int b = (rgb >> 0) & 0xFF;
 * </pre>
 * 
 * </blockquote> ���� RGB �Ĺ��ɿ���ʹ�����·���������Ϊ 32 λ��ɫ <blockquote>
 * 
 * <pre>
 * int rgb = (r << 16) + (g << 8) + (b << 0);
 * </pre>
 * 
 * </blockquote>
 * <p>
 * һ��<em>W</em>x<en>H</em> ��ͼ��ռ�� ~ 4 <em>W H</em> �ֽ��ڴ�,
 * 
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public final class Picture implements ActionListener {

    private BufferedImage image;                    // the rasterized image
    private JFrame        frame;                    // on-screen view
    private String        filename;                 // name of file
    private boolean       isOriginUpperLeft = true; // location of origin
    private final int     width, height;            // width and height

    /**
     * ����һ�� {@code width}x{@code height} ��ͼ��{@code width} ��ʾ������ {@code height}
     * ��ʾ������ÿ�����ض��Ǻ�ɫ��
     * 
     * @param ͼ����
     * @param ͼ��߶�
     * @throws IllegalArgumentException
     *             {@code width} �� {@code height} �Ǹ�ֵ
     */
    public Picture(int width, int height) {
        if (width < 0)
            throw new IllegalArgumentException("width must be non-negative");
        if (height < 0)
            throw new IllegalArgumentException("height must be non-negative");
        this.width = width;
        this.height = height;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // set to TYPE_INT_ARGB here and in next constructor to support
        // transparency
    }

    /**
     * �������ڲ��������ͼ������
     *
     * @param picture
     *            the picture to copy
     * @throws IllegalArgumentException
     *             if {@code picture} is {@code null}
     */
    public Picture(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("constructor argument is null");

        width = picture.width();
        height = picture.height();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        filename = picture.filename;
        isOriginUpperLeft = picture.isOriginUpperLeft;
        for (int col = 0; col < width(); col++)
            for (int row = 0; row < height(); row++)
                image.setRGB(col, row, picture.image.getRGB(col, row));
    }

    /**
     * ��ĳ���ļ���Url��ȡ������ͼƬ
     *
     * @param filename
     *            the name of the file (.png, .gif, or .jpg) or URL.
     * @throws IllegalArgumentException
     *             if cannot read image
     * @throws IllegalArgumentException
     *             if {@code filename} is {@code null}
     */
    public Picture(String filename) {
        if (filename == null)
            throw new IllegalArgumentException("constructor argument is null");

        this.filename = filename;
        try {

            File file = new File(filename);
            if (file.isFile()) {
                // try to read from file in working directory
                image = ImageIO.read(file);
            } else {
                // now try to read from file in same directory as this .class
                // file
                URL url = getClass().getResource(filename);
                if (url == null) {
                    url = new URL(filename);
                }
                image = ImageIO.read(url);
            }

            if (image == null) {
                throw new IllegalArgumentException("could not read image file: " + filename);
            }

            width = image.getWidth(null);
            height = image.getHeight(null);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("could not open image file: " + filename, ioe);
        }
    }

    /**
     * ͨ���ļ����󴴽�ͼ��
     *
     * @param file
     *            the file
     * @throws IllegalArgumentException
     *             if cannot read image
     * @throws IllegalArgumentException
     *             if {@code file} is {@code null}
     */
    public Picture(File file) {
        if (file == null)
            throw new IllegalArgumentException("constructor argument is null");

        try {
            image = ImageIO.read(file);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("could not open file: " + file, ioe);
        }
        if (image == null) {
            throw new IllegalArgumentException("could not read file: " + file);
        }
        width = image.getWidth(null);
        height = image.getHeight(null);
        filename = file.getName();
    }

    /**
     * ���ذ����˸�ͼ��� {@link JLabel}
     *
     * @return the {@code JLabel}
     */
    public JLabel getJLabel() {
        if (image == null)
            return null; // no image available
        ImageIcon icon = new ImageIcon(image);
        return new JLabel(icon);
    }

    /**
     * ��ͼ����ʼ������Ϊ���Ϸ�
     */
    public void setOriginUpperLeft() {
        isOriginUpperLeft = true;
    }

    /**
     * ��ͼ����ʼ������Ϊ���·�
     */
    public void setOriginLowerLeft() {
        isOriginUpperLeft = false;
    }

    /**
     * �ڴ���չʾͼƬ
     */
    public void show() {

        // create the GUI for viewing the image if needed
        if (frame == null) {
            frame = new JFrame();

            JMenuBar menuBar = new JMenuBar();
            JMenu menu = new JMenu("File");
            menuBar.add(menu);
            JMenuItem menuItem1 = new JMenuItem(" Save...   ");
            menuItem1.addActionListener(this);
            menuItem1.setAccelerator(
                    KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            menu.add(menuItem1);
            frame.setJMenuBar(menuBar);

            frame.setContentPane(getJLabel());
            // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            if (filename == null)
                frame.setTitle(width + "-by-" + height);
            else
                frame.setTitle(filename);
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
        }

        // draw
        frame.repaint();
    }

    /**
     * ����ͼ��ĸ߶�
     *
     * @return the height of the picture (in pixels)
     */
    public int height() {
        return height;
    }

    /**
     * ����ͼ����
     *
     * @return the width of the picture (in pixels)
     */
    public int width() {
        return width;
    }

    private void validateRowIndex(int row) {
        if (row < 0 || row >= height())
            throw new IllegalArgumentException("row index must be between 0 and " + (height() - 1) + ": " + row);
    }

    private void validateColumnIndex(int col) {
        if (col < 0 || col >= width())
            throw new IllegalArgumentException("column index must be between 0 and " + (width() - 1) + ": " + col);
    }

    /**
     * ����ָ�����ص���ɫ ({@code col}, {@code row}) ��װΪ {@link java.awt.Color}����
     *
     * @param col
     *            the column index
     * @param row
     *            the row index
     * @return the color of pixel ({@code col}, {@code row})
     * @throws IllegalArgumentException
     *             unless both {@code 0 <= col < width} and
     *             {@code 0 <= row < height}
     */
    public Color get(int col, int row) {
        validateColumnIndex(col);
        validateRowIndex(row);
        int rgb = getRGB(col, row);
        return new Color(rgb);
    }

    /**
     * �������صı���Ϊ���͵���ɫ
     */
    public int getRGB(int col, int row) {
        validateColumnIndex(col);
        validateRowIndex(row);
        if (isOriginUpperLeft)
            return image.getRGB(col, row);
        else
            return image.getRGB(col, height - row - 1);
    }

    /**
     * ��������ɫ����Ϊָ������ɫ
     *
     * @param col
     *            the column index
     * @param row
     *            the row index
     * @param color
     *            the color
     * @throws IllegalArgumentException
     *             unless both {@code 0 <= col < width} and
     *             {@code 0 <= row < height}
     * @throws IllegalArgumentException
     *             if {@code color} is {@code null}
     */
    public void set(int col, int row, Color color) {
        validateColumnIndex(col);
        validateRowIndex(row);
        if (color == null)
            throw new IllegalArgumentException("color argument is null");
        int rgb = color.getRGB();
        setRGB(col, row, rgb);
    }

    /**
     * ��������ɫ����Ϊָ�������ͱ���
     *
     * @param col
     *            the column index
     * @param row
     *            the row index
     * @param rgb
     *            the integer representation of the color
     * @throws IllegalArgumentException
     *             unless both {@code 0 <= col < width} and
     *             {@code 0 <= row < height}
     */
    public void setRGB(int col, int row, int rgb) {
        validateColumnIndex(col);
        validateRowIndex(row);
        if (isOriginUpperLeft)
            image.setRGB(col, row, rgb);
        else
            image.setRGB(col, height - row - 1, rgb);
    }

    /**
     * �ȶ�ͼƬ�Ƿ���ͬ
     * @param other
     *            the other picture
     * @return {@code true} if this picture is the same dimension as
     *         {@code other} and if all pixels have the same color;
     *         {@code false} otherwise
     */
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (other == null)
            return false;
        if (other.getClass() != this.getClass())
            return false;
        Picture that = (Picture) other;
        if (this.width() != that.width())
            return false;
        if (this.height() != that.height())
            return false;
        for (int col = 0; col < width(); col++)
            for (int row = 0; row < height(); row++)
                if (this.getRGB(col, row) != that.getRGB(col, row))
                    return false;
        return true;
    }

    /**
     * Returns a string representation of this picture. The result is a
     * <code>width</code>-by-<code>height</code> matrix of pixels, where the
     * color of a pixel is represented using 6 hex digits to encode the red,
     * green, and blue components.
     *
     * @return a string representation of this picture
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(width + "-by-" + height + " picture (RGB values given in hex)\n");
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int rgb = 0;
                if (isOriginUpperLeft)
                    rgb = image.getRGB(col, row);
                else
                    rgb = image.getRGB(col, height - row - 1);
                sb.append(String.format("#%06X ", rgb & 0xFFFFFF));
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * This operation is not supported because pictures are mutable.
     *
     * @return does not return a value
     * @throws UnsupportedOperationException
     *             if called
     */
    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported because pictures are mutable");
    }

    /**
     * ����ͼƬΪ��׼��ͼƬ��ʽ���ļ�
     *
     * @param filename
     *            the name of the file
     * @throws IllegalArgumentException
     *             if {@code name} is {@code null}
     */
    public void save(String filename) {
        if (filename == null)
            throw new IllegalArgumentException("argument to save() is null");
        save(new File(filename));
    }

    /**
     * ����ͼƬΪ��׼��ͼƬ��ʽ���ļ�
     *
     * @param file
     *            the file
     * @throws IllegalArgumentException
     *             if {@code file} is {@code null}
     */
    public void save(File file) {
        if (file == null)
            throw new IllegalArgumentException("argument to save() is null");
        filename = file.getName();
        if (frame != null)
            frame.setTitle(filename);
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        if ("jpg".equalsIgnoreCase(suffix) || "png".equalsIgnoreCase(suffix)) {
            try {
                ImageIO.write(image, suffix, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: filename must end in .jpg or .png");
        }
    }

    /**
     * Opens a save dialog box when the user selects "Save As" from the menu.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        FileDialog chooser = new FileDialog(frame, "Use a .png or .jpg extension", FileDialog.SAVE);
        chooser.setVisible(true);
        if (chooser.getFile() != null) {
            save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }

    /**
     * Unit tests this {@code Picture} data type. Reads a picture specified by
     * the command-line argument, and shows it in a window on the screen.
     *
     * @param args
     *            the command-line arguments
     */
    public static void main(String[] args) {
        
        int width = 640;
        int height = 320;
        
        Picture pic = new Picture(width, height);
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pic.set(i, j, Color.GRAY);
            }
        }
        
        for (int i = 100; i < 200; i++) {
            for (int j = 100; j < 200; j++) {
                pic.set(i, j, Color.RED);
            }
        }
        
        
        
        pic.show();
        
        
    }

}
