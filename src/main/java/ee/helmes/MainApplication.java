package ee.helmes;

import ee.helmes.messanger.ConsoleMessenger;

public class MainApplication {

    public static void main(String[] args){

        ConsoleMessenger messenger = new ConsoleMessenger();
        messenger.print();
    }
}
