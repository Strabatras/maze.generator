package org.strabatras.maze.generator;

/**
 * RDA ( recursive-division-algorithm ) - метод рекурсивного деления
 */
public class MazeRDA {
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
        //int random = matrix.randomInteger( polygon.x1, polygon.x2 );
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
        //int random = matrix.randomInteger( polygon.y1, polygon.y2 );
        if ( ( random % 2 ) == 1 ) {
            random = horizontal( polygon );
        }
        return random;
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

        public int vertical;

        public int horizontal;
    }
}
