package org.strabatras.maze.generator;

/**
 * RDA ( recursive-division-algorithm ) - метод рекурсивного деления
 */
public class MazeRDA implements MazeInterface {
    /** Параметры лабиринта */
    private final MazeParameters mazeParameters;

    /** Матрица лабиринта */
    private Matrix matrix;

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

    private void division( Polygon polygon ){

        if ( polygon.isDivisible() == false ) {
            return;
        }
        polygon.vertical = vertical( polygon );
        polygon.horizontal = horizontal( polygon );
        System.out.println( "vertical => " + polygon.vertical + " horizontal => " + polygon.horizontal );

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
