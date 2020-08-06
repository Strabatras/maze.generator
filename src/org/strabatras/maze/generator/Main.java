package org.strabatras.maze.generator;

public class Main {

    public static void main( String[] args ) {
        System.out.println( "Start" );

        MazeFactory factory = new MazeFactory(
                new MazeParameters()
                        .setPlateWidth( 101 )
                        .setPlateHeight( 101 )
        );
        Matrix matrix = factory.maze().matrix();

        MemoryImage memoryImage = new MemoryImage( matrix );
        memoryImage.paint();

        System.out.println( "Stop" );
    }
}