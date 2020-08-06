package org.strabatras.maze.generator;

/**
 * Фабрика
 */
public class MazeFactory {

    /**
     * Параметры лабиринта
     */
    private MazeParameters mazeParameters = null;

    private MazeParameters mazeParameters(){
        return this.mazeParameters;
    }

    /**
     * Конструктор.
     * Инициализирует поле {@link MazeFactory#mazeParameters}
     * @param mazeParameters параметры лабиринта
     */
    public MazeFactory( MazeParameters mazeParameters ) {
        this.mazeParameters = mazeParameters;
    }

    /**
     * @return Лабиринт
     */
    public MazeInterface maze(){
        return new MazeDFS( this.mazeParameters() );
    }
}
