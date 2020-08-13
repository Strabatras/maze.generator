package org.strabatras.maze.generator;

/**
 * Матрица лабиринта
 */
public class Matrix {

    /**
     * Параметры лабиринта
     */
    private final MazeParameters mazeParameters;

    /**
     * Ячейки матрицы
     */
    private CellMatrix[][] cellMatrix = null;

    public Matrix( MazeParameters mazeParameters ) {
        this.mazeParameters = mazeParameters;
    }

    /**
     * @return Параметры лабиринта
     */
    private MazeParameters mazeParameters(){
        return this.mazeParameters;
    }

    /**
     * Расстановка границ и заливка ячеек матрицы
     * @param isBorderedCell Признак типа границы лабиринта
     * @return Тип ячейки
     */
    private TypeCellMatrix typeCell( boolean isBorderedCell ){
        return ( isBorderedCell ) ? ( TypeCellMatrix.BORDER ) : ( mazeParameters.getDefaultPlateTypeCellMatrix() );
    }

    /**
     * Создание ячеек строки
     * @param cells Кол-во ячеек в строке
     * @param isBorderedRow Признак строки границы лабиринта
     * @return ячейки строки
     */
    private CellMatrix[] createCells( int cells, boolean isBorderedRow ){
        CellMatrix[] cellMatrix = new CellMatrix[ cells ];
        for ( int i = 0; i < cells; i++ ){
            cellMatrix[ i ] = new CellMatrix().setTypeCellMatrix( this.typeCell( ( isBorderedRow || i == 0 || i > mazeParameters().getPlateWidth() ) ) );
        }
        return cellMatrix;
    }

    /**
     * Создание строк
     * @return Строки
     */
    private CellMatrix[][] createRows(){

        int lines = mazeParameters().getPlateHeight() + 2;
        int cells = mazeParameters().getPlateWidth() + 2;

        CellMatrix[][] rows = new CellMatrix[ lines ][];
        for ( int i = 0; i < lines; i++ ){
            rows[ i ] = this.createCells( cells, ( i == 0 || i > mazeParameters().getPlateHeight() ) );
        }
        return rows;
    }

    /**
     * Рандомное целое число из указанного диапазона
     * @param min Минимальное значение диапазона
     * @param max Максимальное значение диапазона
     * @return число из диапазона
     */
    public int randomInteger( int min, int max ) {
        return ( int )( Math.random() * ( max - min + 1 ) + min );
    }

    /**
     * @return Ячейки матрицы
     */
    private CellMatrix[][] cellMatrix(){
        if ( null == this.cellMatrix ) {
            this.create();
        }
        return this.cellMatrix;
    }

    /**
     * @return Максимальное значение для получения строк по оси X
     */
    public int maxX() {
        return this.mazeParameters().getPlateWidth();
    }

    /**
     * @return Максимальное значение для получения строк по оси Y
     */
    public int maxY() {
        return this.mazeParameters().getPlateHeight();
    }

    /**
     * @return Минимальное значение для получения строк по оси X
     */
    public int minX(){
        return 1;
    }

    /**
     * @return Минимальное значение для получения строк по оси Y
     */
    public int minY(){
        return 1;
    }

    /**
     * Генерация сетки матрицы
     */
    public void create(){
        this.cellMatrix = this.createRows();
    }

    /**
     * @return Возвращает сгенерированную сетку матрицы
     */
    public CellMatrix[][] get() {
        return this.cellMatrix();
    }

    /**
     * Возвращает ячейку сетки матрицы
     * @param x Номер ячейки по оси X
     * @param y Номер ячейки по оси Y
     * @return Матрица лабиринта
     */
    public CellMatrix getCellMatrix( int x, int y ){
        CellMatrix cellMatrix = this.cellMatrix()[ y ][ x ];
        cellMatrix.setY( y ).setX( x );
        return cellMatrix;
    }

    /**
     * @return Возвращает рандомную ячейку сетки матрицы
     */
    public CellMatrix getRandomCellMatrix() {
        return getCellMatrix(
                randomInteger( 1, maxX() ),
                randomInteger( 1, maxY() )
        );
    }
}
