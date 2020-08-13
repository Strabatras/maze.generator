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
     * Дефолтное значение для типа ячейки матрицы
     */
    private TypeCellMatrix defaultPlateTypeCellMatrix;

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

    /**
     * @return Возвращает дефолтное значение для типа ячейки матрицы
     */
    public TypeCellMatrix getDefaultPlateTypeCellMatrix() {
        return defaultPlateTypeCellMatrix;
    }

    /**
     * Устанавливает дефолтное значение для типа ячейки матрицы
     * @param defaultPlateTypeCellMatrix Дефолтное значение
     * @return Параметры лабиринта
     */
    public MazeParameters setDefaultPlateTypeCellMatrix( TypeCellMatrix defaultPlateTypeCellMatrix ) {
        this.defaultPlateTypeCellMatrix = defaultPlateTypeCellMatrix;
        return this;
    }
}