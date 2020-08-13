package org.strabatras.maze.generator;

/**
 * Eller’s algorithm - алгоритм Эйлера
 */
public class MazeEller implements MazeInterface {
    /** Параметры лабиринта */
    private final MazeParameters mazeParameters;

    /** Матрица лабиринта */
    private Matrix matrix;

    /**
     * Конструктор
     * @param mazeParameters Параметры лабиринта
     */
    public MazeEller( MazeParameters mazeParameters ) {
        this.mazeParameters = mazeParameters;
    }

    /**
     * @return Параметры лабиринта
     */
    private MazeParameters mazeParameters(){
        return this.mazeParameters;
    }

    /**
     * @return Матрица лабиринта
     */
    @Override
    public Matrix matrix() {
        matrix = new Matrix( mazeParameters().setDefaultPlateTypeCellMatrix(TypeCellMatrix.WALL ) );
        return matrix;
    }
}
