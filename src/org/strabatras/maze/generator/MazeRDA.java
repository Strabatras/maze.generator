package org.strabatras.maze.generator;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * RDA ( recursive-division-algorithm ) - метод рекурсивного деления
 */
public class MazeRDA implements MazeInterface {
    /** Параметры лабиринта */
    private final MazeParameters mazeParameters;

    /** Матрица лабиринта */
    private Matrix matrix;

    private final Queue< Polygon > polygons = new ConcurrentLinkedQueue<>();

    public MazeRDA( MazeParameters mazeParameters ) {
        this.mazeParameters = mazeParameters;
    }

    /**
     * @return Параметры лабиринта
     */
    private MazeParameters mazeParameters(){
        return this.mazeParameters;
    }

    private int vertical( Polygon polygon ) {
        int random = Math.round( polygon.x2 * ( ( float )  matrix.randomInteger( 25, 85 ) / 100 ) );
        if ( random == 0 ) {
            random = vertical( polygon );
        }
        if ( ( random % 2 ) == 1 ) {
            random = vertical( polygon );
        }
        return random;
    }

    private int horizontal( Polygon polygon ) {
        int random = Math.round( polygon.y2 * ( ( float )  matrix.randomInteger( 25, 85 ) / 100 ) );
        if ( random == 0 ) {
            random = horizontal( polygon );
        }
        if ( ( random % 2 ) == 1 ) {
            random = horizontal( polygon );
        }
        return random;
    }

    private Polygon polygonTopLeft( Polygon polygon ) {
        Polygon out = new Polygon();
        out.x1 = polygon.x1;
        out.x2 = polygon.vertical - 1;
        out.y1 = polygon.y1;
        out.y2 = polygon.horizontal -1;
        return out;
    }

    private Polygon polygonTopRight( Polygon polygon ) {
        Polygon out = new Polygon();
        out.x1 = polygon.vertical + 1;
        out.x2 = polygon.x2;
        out.y1 = polygon.y1;
        out.y2 = polygon.horizontal - 1;
        return out;
    }

    private Polygon polygonBottomLeft( Polygon polygon ) {
        Polygon out = new Polygon();
        out.x1 = polygon.x1;
        out.x2 = polygon.vertical-1;
        out.y1 = polygon.horizontal + 1;
        out.y2 = polygon.y2;
        return out;
    }

    private Polygon polygonBottomRight( Polygon polygon ) {
        Polygon out = new Polygon();
        out.x1 = polygon.vertical + 1;
        out.x2 = polygon.x2;
        out.y1 = polygon.horizontal + 1;
        out.y2 = polygon.y2;
        return out;
    }

    private void division( Polygon polygon ){

        //if ( polygon.isDivisible() == false ) {
        //    return;
        //}
        polygon.vertical = vertical( polygon );
        polygon.horizontal = horizontal( polygon );
        System.out.println( "V => " + polygon.vertical + " H => " + polygon.horizontal + " x1 => " + polygon.x1 + " x2 => " + polygon.x2 + " y1 => " + polygon.y1 + " y2 => " + polygon.y2 );

        int y = 0;
        for ( CellMatrix[] cells : matrix.get() ) {
            int x = 0;
            for ( CellMatrix cell : cells ){
                /*
                if ( ( x == polygon.vertical && y >= polygon.y1 )
                  || ( y == polygon.horizontal && x >= polygon.y1 ) ) {
                    matrix.getCellMatrix( x, y ).setVisited( true ).setTypeCellMatrix( TypeCellMatrix.WALL );
                }
                 */
                if ( x == polygon.x2 ){
                    break;
                }
                x++;
            }
            if ( y == polygon.y2 ) {
                break;
            }
            y++;
        }

        Polygon topRight = polygonTopRight( polygon );
        if ( topRight.isDivisible() ) {
            //polygons.add( topRight );
        }
        Polygon bottomLeft = polygonBottomLeft( polygon );
        if ( bottomLeft.isDivisible() ) {
            //polygons.add( bottomLeft );
        }
        Polygon bottomRight = polygonBottomRight( polygon );
        if ( bottomRight.isDivisible() ) {
            //polygons.add( bottomRight );
        }
        //Polygon topLeft = polygonTopLeft( polygon );
        //if ( topLeft.isDivisible() ) {
        //    division( polygonTopLeft( polygon ) );
        //}
        if ( bottomLeft.isDivisible() ) {
            division( bottomLeft );
        }
    }

    /**
     * @return Матрица лабиринта
     */
    @Override
    public Matrix matrix() {
        matrix = new Matrix( mazeParameters().setDefaultPlateTypeCellMatrix( TypeCellMatrix.PASSAGE ) );

        System.out.println( "matrix.minX() => " + matrix.minX() + " | matrix.maxX() => " + matrix.maxX() );
        System.out.println( "matrix.minY() => " + matrix.minY() + " | matrix.maxY() => " + matrix.maxY() );

        Polygon polygon = new Polygon();
        polygon.x1 = matrix.minX();
        polygon.x2 = matrix.maxX();
        polygon.y1 = matrix.minY();
        polygon.y2 = matrix.maxY();

        division( polygon );

        System.out.println( "polygons.size() => " + polygons.size() );

        int k = 0;
        while ( !polygons.isEmpty() ) {
            Polygon pl = polygons.poll();
            if( null != pl ) {
                division( pl );
            }
            k++;
        }
        System.out.println( "K => " + k );
        return matrix;
    }

    private class Polygon {
        /** Наименьшее значение по оси Y */
        public int y1;
        /** Наибольшее значение по оси Y */
        public int y2;
        /** Наименьшее значение по оси X */
        public int x1;
        /** Наибольшее значение по оси X */
        public int x2;
        /** Координата по оси X для вертикального разбиения полигона */
        public int vertical;
        /** Координата по оси Y для горизонтального разбиения полигона */
        public int horizontal;

        /**
         * @return Возвращает признак возможности разделения на полигоны
         */
        public boolean isDivisible(){
            if ( ( this.x2 - this.x1 ) < 2 ) {
                return false;
            }
            if ( ( this.y2 - this.y1 ) < 2 ) {
                return false;
            }
            return true;
        }
    }
}
