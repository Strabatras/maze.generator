package org.strabatras.maze.generator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * DFS ( deep-first-search ) - генерация лабиринта
 */
public class MazeDFS implements MazeInterface {

    /** Параметры лабиринта */
    private final MazeParameters mazeParameters;

    /** Ячейки имеющие не посещенные направления */
    private Queue<Way> nodes = new LinkedList<>();

    /** Матрица лабиринта */
    private Matrix matrix;

    /**
     * Конструктор
     * @param mazeParameters Параметры лабиринта
     */
    public MazeDFS( MazeParameters mazeParameters ) {
        this.mazeParameters = mazeParameters;
    }

    private void safePrintln(String s) {
        synchronized (System.out) {
            System.out.println(s);
        }
    }

    /**
     * @return Параметры лабиринта
     */
    private MazeParameters mazeParameters(){
        return this.mazeParameters;
    }

    /**
     * Возвращает true при успешном выборе направления перемещения 'TOP'
     * @param from Путь. Откуда пришли
     * @param to Путь. Куда идем
     * @return Результат выбора
     */
    private boolean directionChosenTop( Way from, Way to ){
        int y = from.y - 2;
        if ( ( y < matrix.minY() ) || matrix.getCellMatrix( from.x, y ).isVisited() ) {
            return false;
        }
        to.y = y;
        to.x = from.x;
        return true;
    }

    /**
     * Возвращает true при успешном выборе направления перемещения 'RIGHT'
     * @param from Путь. Откуда пришли
     * @param to Путь. Куда идем
     * @return Результат выбора
     */
    private boolean directionChosenRight( Way from, Way to ){
        int x  = from.x + 2;
        if ( ( x > matrix.maxX() ) || matrix.getCellMatrix( x, from.y ).isVisited() ) {
            return false;
        }
        to.y = from.y;
        to.x = x;
        return true;
    }

    /**
     * Возвращает true при успешном выборе направления перемещения 'BOTTOM'
     * @param from Путь. Откуда пришли
     * @param to Путь. Куда идем
     * @return Результат выбора
     */
    private boolean directionChosenBottom( Way from, Way to ){
        int y = from.y + 2;
        if ( ( y > matrix.maxY() ) || matrix.getCellMatrix( from.x, y ).isVisited() ) {
            return false;
        }
        to.y = y;
        to.x = from.x;
        return true;
    }

    /**
     * Возвращает true при успешном выборе направления перемещения 'LEFT'
     * @param from Путь. Откуда пришли
     * @param to Путь. Куда идем
     * @return Результат выбора
     */
    private boolean directionChosenLeft( Way from, Way to ){
        int x  = from.x - 2;
        if ( ( x < matrix.minX() ) || matrix.getCellMatrix( x, from.y ).isVisited() ) {
            return false;
        }
        to.y = from.y;
        to.x = x;
        return true;
    }

    /**
     * Выбор направления перемещения
     * @param from Путь. Откуда пришли
     * @param to Путь. Куда идем
     * @return Результат выбора
     */
    private boolean directionChosen( Way from, Way to ){
        Direction direction = Direction.randomDirection( from.directions );
        from.directions.add( direction );

        if ( Direction.TOP == direction ) {
            return directionChosenTop( from, to );
        }
        if ( Direction.RIGHT == direction ) {
            return directionChosenRight ( from, to );
        }
        if ( Direction.BOTTOM == direction ) {
            return directionChosenBottom( from, to );
        }
        if ( Direction.LEFT == direction ) {
            return directionChosenLeft( from, to );
        }
        return false;
    }

    /**
     * Убирает стену на пути возврата
     * @param way Конечная точка пути
     */
    private void removeWall( Way way ){
        Direction direction = way.directions.get( way.directions.size() - 1 );
        if ( Direction.TOP == direction ) {
            matrix.getCellMatrix( way.x, way.y - 1 ).setVisited( true ).setTypeCellMatrix( TypeCellMatrix.PASSAGE );
        }
        if ( Direction.RIGHT == direction ) {
            matrix.getCellMatrix( way.x + 1, way.y ).setVisited( true ).setTypeCellMatrix( TypeCellMatrix.PASSAGE );
        }
        if ( Direction.BOTTOM == direction ) {
            matrix.getCellMatrix( way.x, way.y + 1 ).setVisited( true ).setTypeCellMatrix( TypeCellMatrix.PASSAGE );
        }
        if ( Direction.LEFT == direction ) {
            matrix.getCellMatrix( way.x - 1, way.y ).setVisited( true ).setTypeCellMatrix( TypeCellMatrix.PASSAGE );
        }
    }

    /**
     * Шаг в выбранном направлении
     * @param from Начальная точка пути
     */
    private void step( Way from ) {
        Way to = new Way();
        int i = Direction.values().length - from.directions.size();
        while ( i > 0 ){
            i--;
            if ( directionChosen( from, to ) ) {
                to.directions.add( Direction.reverse( from.directions.get( from.directions.size() - 1 ) ) );
                break;
            }
        }
        if ( Direction.values().length > from.directions.size() ) {
            nodes.add( from );
        }
        if ( ( to.x > 0 ) && ( to.y > 0 ) ) {
            matrix.getCellMatrix( to.x, to.y ).setVisited( true ).setTypeCellMatrix( TypeCellMatrix.PASSAGE );
            removeWall( to );
            step( to );
        }
    }

    /**
     * Матрица лабиринта
     * @return
     */
    @Override
    public Matrix matrix() {
        matrix = new Matrix( mazeParameters() );
        CellMatrix cellMatrix = matrix.getRandomCellMatrix();

        Way way = new Way();
        way.x = ( ( cellMatrix.getX() % 2 ) == 1 ) ? ( cellMatrix.getX() ) : ( cellMatrix.getX() -1 );
        way.y = ( ( cellMatrix.getY() % 2 ) == 1 ) ? ( cellMatrix.getY() ) : ( cellMatrix.getY() -1 );

        if ( way.x < matrix.minX() ) {
            way.x = matrix.minX();
        }
        if ( way.y < matrix.minY() ) {
            way.y = matrix.minY();
        }
        //System.out.println( "WAY => " );
        matrix.getCellMatrix( way.x, way.y ).setVisited( true ).setTypeCellMatrix( TypeCellMatrix.PASSAGE );

        step( way );

        return matrix;
    }

    /**
     * Направления для перемещения
     */
    private enum Direction {
        TOP,
        RIGHT,
        BOTTOM,
        LEFT;

        /**
         * Возвращает рандомное направление для перемещения
         * @param exclude Список исключений из результата
         * @return Направление
         */
        public static Direction randomDirection( List< Direction > exclude ) {
            if ( null == exclude ) {
                return values()[ ( int ) ( Math.random() * directions().length ) ];
            }
            List< Direction > values = Arrays.stream( directions() )
                                             .filter( d -> !exclude.contains( d ) )
                                             .collect( Collectors.toList() );
            return values.get( ( int ) ( Math.random() * values.size() ) );
        }

        /**
         * Инвертирует направление для перемещения
         * @param direction Направление для перемещения
         * @return Направление
         */
        public static Direction reverse( Direction direction ) {
            if ( TOP == direction ){
                return BOTTOM;
            }
            if ( RIGHT == direction ){
                return LEFT;
            }
            if ( BOTTOM == direction ){
                return  TOP;
            }
            return RIGHT;
        }

        /**
         * @return Список направлений для перемещения
         */
        public static Direction[] directions() {
            return values();
        }
    }

    /**
     * Путь
     */
    private class Way{
        /** Координаты по оси X */
        public int x;
        /** Координаты по оси Y */
        public int y;
        /** Список задействованных направлений перемещения */
        public List< Direction > directions;

        public Way() {
            this.directions = new ArrayList<>( Direction.values().length );
        }
    }
}
