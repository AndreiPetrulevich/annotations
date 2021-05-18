package ru.geekbrains.annotations;

public class Test2 {
    @BeforeSuite
    public void setUp() {
        System.out.println("Test2 Before");
    }

    @Test()
    public void test1() {
        System.out.println("Test2 test1 running...");
    }

    @Test(priority = 2)
    public void test2() {
        System.out.println("Test2 test2 running...");
    }

    @Test(priority = 3)
    public void test3() {
        System.out.println("Test2 test3 running...");
    }

    @AfterSuite
    public void tearDown() {
        System.out.println("Test2 After");
    }
}
