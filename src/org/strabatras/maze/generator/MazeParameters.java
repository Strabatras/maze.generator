package org.strabatras.maze.generator;

/**
 * Параметры лабиринта
 */
public class MazeParameters {

    /**
     * Высота матрицы лабиринта
     */
    private int plateHeight;

    /**
     * Ширина матрицы лабиринта
     */
    private int plateWidth;

    /**
     * @return - Возвращает высоту матрицы лабиринта
     */
    public int getPlateHeight() {
        return plateHeight;
    }

    /**
     * Устанавливает высоту матрицы лабиринта
     * @param plateHeight Высота матрицы
     * @return Параметры лабиринта
     */
    public MazeParameters setPlateHeight( int plateHeight ) {
        this.plateHeight = plateHeight;
        return this;
    }
    /**
     * @return - Возвращает ширину матрицы лабиринта
     */
    public int getPlateWidth() {
        return plateWidth;
    }

    /**
     * Устанавливает ширину матрицы лабиринта
     * @param plateWidth Ширина матрицы
     * @return Параметры лабиринта
     */
    public MazeParameters setPlateWidth( int plateWidth ) {
        this.plateWidth = plateWidth;
        return this;
    }
}
