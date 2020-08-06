package org.strabatras.maze.generator;

import javax.swing.*;
import java.awt.*;
import java.awt.image.MemoryImageSource;

/**
 * Визуализация лабиринта
 */
public class MemoryImage {

    private final int w = Color.gray.getRGB();
    private final int p = Color.white.getRGB();
    private final int b = Color.black.getRGB();

    private int[] imageData;

    private final Matrix matrix;

    /**
     * @return Список данных для визуализации матрицы лабиринта
     */
    private int[] imageData(){
        if ( null == imageData ) {
            CellMatrix[][] rows = matrix.get();
            imageData = new int[ ( matrix.maxX() + 2 ) * ( matrix.maxY() + 2 ) ];
            int i = 0;

            for ( CellMatrix[] cells : rows ){
                for ( CellMatrix cell : cells ) {
                    int color;
                    if ( cell.getTypeCellMatrix() == TypeCellMatrix.WALL ) {
                        color = w;
                    } else if ( cell.getTypeCellMatrix() == TypeCellMatrix.PASSAGE ) {
                        color = p;
                    } else {
                        color = b;
                    }
                    imageData[ i ] = color;
                    i++;
                }
            }
        }
        return imageData;
    }

    /**
     * Конструктор
     * @param matrix Матрица лабиринта
     */
    public MemoryImage( Matrix matrix ) {
        this.matrix = matrix;
    }

    /**
     * Вывод лабиринта
     */
    public void paint() {
        new JFrame() {
            {
                setBounds(0, 0,1400,1400);
                setVisible(true);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
            public void paint( Graphics g ){
                MemoryImageSource imageSource = new MemoryImageSource(  matrix.maxX() + 2, matrix.maxY() + 2, imageData(), 0, matrix.maxX() + 2 );
                imageSource.setAnimated( true );
                Image image = Toolkit.getDefaultToolkit().createImage( imageSource );
                g.drawImage( image, 10, 30, matrix.maxX() * 3, matrix.maxY() * 3, null );
            }
        };
    }
}