package org.strabatras.maze.generator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Направления
 */
public enum Direction {
    TOP,
    RIGHT,
    BOTTOM,
    LEFT;

    /**
     * Возвращает рандомное направление
     * @param exclude Список исключений направлений из результата
     * @return Направление
     */
    public static Direction random( List< Direction > exclude ) {
        if ( null == exclude ) {
            return values()[ ( int ) ( Math.random() * directions().length ) ];
        }
        List< Direction > values = Arrays.stream( directions() )
                .filter( d -> !exclude.contains( d ) )
                .collect( Collectors.toList() );
        return values.get( ( int ) ( Math.random() * values.size() ) );
    }

    /**
     * Инвертирует направление
     * @param direction Направление
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
     * @return Список направлений
     */
    public static Direction[] directions() {
        return values();
    }
}