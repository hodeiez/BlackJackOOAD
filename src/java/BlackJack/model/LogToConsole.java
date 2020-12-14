package BlackJack.model;

/**
 * Created by Hodei Eceiza
 * Date: 12/12/2020
 * Time: 16:58
 * Project: BlackJackOOAD
 * Copyright: MIT
 */
public class LogToConsole implements IlogObserver{
    @Override
    public void update(String text) {
        System.out.println(text);
    }
}
