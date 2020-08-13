package org.strabatras.maze.generator;

import java.util.ArrayList;
import java.util.List;
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
        int random = matrix.randomInteger( polygon.x1, polygon.x2 );
/*
        int random = Math.round( polygon.x2 * ( ( float )  matrix.randomInteger( 25, 85 ) / 100 ) );
        if ( ( random == 0 ) || ( random < polygon.x1 ) || ( random > polygon.x2 ) ) {
            random = vertical( polygon );
        }
*/
        if ( ( random % 2 ) == 1 ) {
            random = vertical( polygon );
        }
        return random;
    }

    private int horizontal( Polygon polygon ) {
        int random = matrix.randomInteger( polygon.y1, polygon.y2 );
/*
        int random = Math.round( polygon.y2 * ( ( float )  matrix.randomInteger( 25, 85 ) / 100 ) );
        if ( ( random == 0 ) || ( random < polygon.y1 ) || ( random > polygon.y2 ) ) {
            random = horizontal( polygon );
        }
*/
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

    private int removeWallTop( Polygon polygon ){
        System.out.println( "TOP matrix.randomInteger( " + polygon.y1 + ", " + ( polygon.horizontal - 1 ) + "  )");
        int max = polygon.horizontal - 1;
        if ( max < polygon.y1 ) {
            return 0;
        }
        int y = matrix.randomInteger( polygon.y1, max );
        if ( ( y % 2 ) == 0 ){
            y++;
            if ( y >= polygon.horizontal ) {
                y = removeWallTop( polygon );
            }
        }
        return y;
    }

    private int removeWallRight( Polygon polygon ){
        System.out.println( "RIGHT matrix.randomInteger( " + ( polygon.vertical + 1 ) + ", " + polygon.x2 + " )" );
        int min = polygon.vertical + 1;
        if ( min > polygon.x2 ) {
            return 0;
        }
        int x = matrix.randomInteger( min, polygon.x2 );
        if ( ( x % 2 ) == 0 ) {
            x++;
            if ( x >= polygon.x2 ) {
                x  = removeWallRight( polygon );
            }
        }
        return x;
    }

    private int removeWallBottom( Polygon polygon ){
        System.out.println( "BOTTOM matrix.randomInteger( " + ( polygon.horizontal + 1 ) + ", " + polygon.y2 + " )" );
        int min = polygon.horizontal + 1;
        if ( min > polygon.y2 ) {
            return 0;
        }
        int y = matrix.randomInteger( min, polygon.y2 );
        if ( ( y % 2 ) == 0 ) {
            y++;
            if ( y >= polygon.y2 ) {
                y = removeWallBottom( polygon );
            }
        }
        return y;
    }

    private int removeWallLeft( Polygon polygon ){
        System.out.println( "LEFT matrix.randomInteger( " + polygon.x1 + ", " + ( polygon.vertical - 1 ) + " )" );
        int max = polygon.vertical - 1;
        if ( max < polygon.x1 ) {
            return 0;
        }
        int x = matrix.randomInteger( polygon.x1, max );
        if ( ( x % 2 ) == 0 ) {
            x++;
            if ( x >= polygon.vertical ){
                x = removeWallLeft( polygon );
            }
        }
        return x;
    }

    private void removeWall( Polygon polygon ){
        int iterations = Direction.directions().length - 1;
        List< Direction > directions = new ArrayList<>( iterations );

        while ( iterations > 0) {
            Direction direction = Direction.random( directions );
            switch ( direction ){
                case TOP -> {
                    int y = removeWallTop( polygon );
                    if ( y > 0 ) {
                        matrix.getCellMatrix( polygon.vertical, y ).setVisited( true ).setTypeCellMatrix( TypeCellMatrix.PASSAGE );
                    }
                    System.out.println( " => TOP X = " + polygon.vertical + ", Y = " + y + " |  x1 => " + polygon.x1 + " x2 => " + polygon.x2 + " y1 => " + polygon.y1 + " y2 => " + polygon.y2 );
                }
                case RIGHT -> {
                    int x = removeWallRight( polygon );
                    if ( x > 0 ) {
                        matrix.getCellMatrix( x, polygon.horizontal ).setVisited( true ).setTypeCellMatrix( TypeCellMatrix.PASSAGE );
                    }
                    System.out.println( " => RIGHT X = " + x + " Y = " + polygon.horizontal + " |  x1 => " + polygon.x1 + " x2 => " + polygon.x2 + " y1 => " + polygon.y1 + " y2 => " + polygon.y2 );
                }
                case BOTTOM -> {
                    int y = removeWallBottom( polygon );
                    if ( y > 0 ) {
                        matrix.getCellMatrix( polygon.vertical, y ).setVisited( true ).setTypeCellMatrix( TypeCellMatrix.PASSAGE );
                    }
                    System.out.println( " => BOTTOM X = " + polygon.vertical + ", Y = " + y + " |  x1 => " + polygon.x1 + " x2 => " + polygon.x2 + " y1 => " + polygon.y1 + " y2 => " + polygon.y2 );
                }
                default -> {
                    int x = removeWallLeft( polygon );
                    if ( x > 0 ) {
                        matrix.getCellMatrix( x, polygon.horizontal ).setVisited( true ).setTypeCellMatrix( TypeCellMatrix.PASSAGE );
                    }
                    System.out.println( " => LEFT X = " + x + " Y = " + polygon.horizontal + " |  x1 => " + polygon.x1 + " x2 => " + polygon.x2 + " y1 => " + polygon.y1 + " y2 => " + polygon.y2 );
                }
            }
            directions.add( direction );
            iterations--;
        }
    }

    private void division( Polygon polygon ){

        polygon.vertical = vertical( polygon );
        polygon.horizontal = horizontal( polygon );
        System.out.println( "V => " + polygon.vertical + " H => " + polygon.horizontal + " x1 => " + polygon.x1 + " x2 => " + polygon.x2 + " y1 => " + polygon.y1 + " y2 => " + polygon.y2 );

        int y = 0;
        for ( CellMatrix[] cells : matrix.get() ) {
            int x = 0;
            for ( CellMatrix cell : cells ){

                if( x == polygon.vertical && y >= polygon.y1 && y <= polygon.y2 ){
                    matrix.getCellMatrix( x, y ).setVisited( true ).setTypeCellMatrix( TypeCellMatrix.WALL );
                }
                if ( y == polygon.horizontal && x >= polygon.x1 && x <= polygon.x2 ){
                    matrix.getCellMatrix( x, y ).setVisited( true ).setTypeCellMatrix( TypeCellMatrix.WALL );
                }
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

        removeWall( polygon );

        Polygon topRight = polygonTopRight( polygon );
        if ( topRight.isDivisible() ) {
            polygons.add( topRight );
        }
        Polygon bottomLeft = polygonBottomLeft( polygon );
        if ( bottomLeft.isDivisible() ) {
            polygons.add( bottomLeft );
        }
        Polygon bottomRight = polygonBottomRight( polygon );
        if ( bottomRight.isDivisible() ) {
            polygons.add( bottomRight );
        }
        Polygon topLeft = polygonTopLeft( polygon );
        if ( topLeft.isDivisible() ) {
            division( polygonTopLeft( polygon ) );
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

        final ThreadGroup group = new ThreadGroup("StackGroup" );
        while( !polygons.isEmpty() ){
            if ( group.activeCount() < 5 ) {
                Thread thread = new Thread( group, ()-> {
                    Polygon poll = polygons.poll();
                    if( null != poll ) {
                        division( poll );
                    }
                });
                thread.start();
            }
        }
        while (group.activeCount() > 0 ){}
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
