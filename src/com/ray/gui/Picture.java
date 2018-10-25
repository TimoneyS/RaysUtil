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
 * 该类提供操作 RGB 颜色模式的像素构成的图形的方法。Alpha 目前不支持。 原始的图片可以是 {@code PNG}, {@code GIF},
 * {@code JPEG} 文件，使用者也可以创建一个空文件。 该类包含将图片展示在窗口的方法或是将图片保存至文件。
 * <p>
 * 像素（<em>col</em>, <em>row</em>）col 表示所在列，row 表示所在行。 默认情况下，起始像素 （0,0）表示左上角， 方法
 * {@link #setOriginLowerLeft()} 可以改变起始像素为左下角。
 * <p>
 * {@code get()} 和 {@code set()} 方法用 {@link Color} 对象来获取或设置特定像素的颜色。
 * {@code getRGB()} 和 {@code setRGB()} 方法用32位的整形来编码颜色从而避免直接创建{@code Color} 对象.
 * 红(R)，绿(G)，蓝(B)编码使用了至少需要24位。
 * <p>
 * 给定一个32位编码的颜色，可以使用以下方法来获取 RGB 成分 <blockquote>
 * 
 * <pre>
 * int r = (rgb >> 16) & 0xFF;
 * int g = (rgb >> 8) & 0xFF;
 * int b = (rgb >> 0) & 0xFF;
 * </pre>
 * 
 * </blockquote> 给定 RGB 的构成可以使用以下方法来编码为 32 位颜色 <blockquote>
 * 
 * <pre>
 * int rgb = (r << 16) + (g << 8) + (b << 0);
 * </pre>
 * 
 * </blockquote>
 * <p>
 * 一幅<em>W</em>x<en>H</em> 的图像占用 ~ 4 <em>W H</em> 字节内存,
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
     * 创建一幅 {@code width}x{@code height} 的图像，{@code width} 表示列数， {@code height}
     * 表示行数，每个像素都是黑色。
     * 
     * @param 图像宽度
     * @param 图像高度
     * @throws IllegalArgumentException
     *             {@code width} 或 {@code height} 是负值
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
     * 创建基于参数传入的图像的深拷贝
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
     * 从某个文件或Url读取并创建图片
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
     * 通过文件对象创建图像
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
     * 返回包含了该图像的 {@link JLabel}
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
     * 将图像起始点设置为右上方
     */
    public void setOriginUpperLeft() {
        isOriginUpperLeft = true;
    }

    /**
     * 将图像起始点设置为右下方
     */
    public void setOriginLowerLeft() {
        isOriginUpperLeft = false;
    }

    /**
     * 在窗口展示图片
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
     * 返回图像的高度
     *
     * @return the height of the picture (in pixels)
     */
    public int height() {
        return height;
    }

    /**
     * 返回图像宽度
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
     * 返回指定像素的颜色 ({@code col}, {@code row}) 包装为 {@link java.awt.Color}对象
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
     * 返回像素的编码为整型的颜色
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
     * 将像素颜色设置为指定的颜色
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
     * 将像素颜色设置为指定的整型编码
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
     * 比对图片是否相同
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
     * 保存图片为标准的图片格式到文件
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
     * 保存图片为标准的图片格式到文件
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
