package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({TestCountNeighbors.class, TestIsGameOver.class, TestIsOccupiedTile.class, TestIsValidCoordinate.class})
public class TestSuite {

}
