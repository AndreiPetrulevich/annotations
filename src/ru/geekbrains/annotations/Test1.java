package ru.geekbrains.annotations;

public class Test1 {
    @BeforeSuite
    public void setUp() {
        System.out.println("PRESET PIROG");
    }

    @Test()
    public void test1() {
        System.out.println("CHECK PIROG IS GOOD");
    }

    @Test(priority = 2)
    public void test2() {
        System.out.println("CHECK PIROG IS TASTY");
    }

    @Test(priority = 3)
    public void test3() {
        System.out.println("CHECK PIROG NOT CONTAIN KOLBASA");
    }

    @AfterSuite
    public void tearDown() {
        System.out.println("MOVE PIROG TO THE FRIDGE");
    }
}
