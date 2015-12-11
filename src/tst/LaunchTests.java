package tst;
class LancerTests {
    public static void main(String... args) throws Exception {
        boolean estMisAssertion = false;
        assert estMisAssertion = true;	

        if (!estMisAssertion) {
            System.out.println("You need option -ea to launch the test.");
            return;
        }

        lancer(TestHeading.class);
    }

    public static void lancer(Class c) throws Exception {
        java.lang.reflect.Method[] mesTest = c.getMethods();
        int nbTest = 0;
        for (int i = 0; i<mesTest.length; i++){
            java.lang.reflect.Method t = mesTest[i];
            if (t.getName().startsWith("test")){
                System.out.print(".");
                t.invoke(c.newInstance());
                nbTest++;
            }
        }
        System.out.println( "(" + nbTest + ") tests passed for " +
                c.getName());
    }

}
