class HelloWorld {
    public native void displayHelloWorld();
    static {
        System.loadLibrary("Dll2");
    }

    public static void main(String[] args) {
//        System.out.println(System.getProperty("java.library.path"));
        new HelloWorld().displayHelloWorld();
    }
}
