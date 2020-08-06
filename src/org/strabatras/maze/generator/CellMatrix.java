package org.strabatras.maze.generator;

/**
 * Ячейка матрицы лабиринта
 */
public class CellMatrix {

    /**
     * Признак посещения ячейки
     */
    private boolean visited = false;

    /**
     * Тип ячейки матрицы лабиринта
     */
    private TypeCellMatrix typeCellMatrix;

    /**
     * Координаты ячейки по оси X
     */
    private int x;

    /**
     * Координаты ячейки по оси Y
     */
    private int y;

    /**
     * @return Признак посещения ячейки
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * Устанавливает признак посещения ячейки
     * @param visited Признак
     * @return Ячейка матрицы лабиринта
     */
    public CellMatrix setVisited( boolean visited ) {
        this.visited = visited;
        return this;
    }

    /**
     * @return Возвращает координаты ячейки по оси X
     */
    public int getX() {
        return x;
    }

    /**
     * Устанавливает координаты ячейки по оси X
     * @param x Координаты ячейки по оси X
     * @return Ячейка матрицы лабиринта
     */
    public CellMatrix setX( int x ) {
        this.x = x;
        return this;
    }

    /**
     * Возвращает координаты ячейки по оси Y
     * @return Координаты
     */
    public int getY() {
        return y;
    }

    /**
     * Устанавливает координаты ячейки по оси Y
     * @param y Координаты ячейки по оси Y
     * @return Ячейка матрицы лабиринта
     */
    public CellMatrix setY( int y ) {
        this.y = y;
        return this;
    }

    /**
     * @return Возвращает тип ячейки матрицы лабиринта
     */
    public TypeCellMatrix getTypeCellMatrix() {
        return typeCellMatrix;
    }

    /**
     * Устанавливает тип ячейки матрицы лабиринта
     * @param typeCellMatrix Тип ячейки матрицы
     * @return Ячейка матрицы лабиринта
     */
    public CellMatrix setTypeCellMatrix( TypeCellMatrix typeCellMatrix ) {
        this.typeCellMatrix = typeCellMatrix;
        return this;
    }
}
