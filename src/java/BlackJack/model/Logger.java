package BlackJack.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hodei Eceiza
 * Date: 12/12/2020
 * Time: 16:57
 * Project: BlackJackOOAD
 * Copyright: MIT
 */
public class Logger {
    private List<IlogObserver> observers=new ArrayList();
    private String text;
    public String getState(){
        return this.text;
    }
    public void setState(String text){
        this.text=text;
    }
    public void attach(IlogObserver o){
        observers.add(o);
    }
    public void notifyObservers(){
        for(IlogObserver o:observers)
            o.update(this.text);
    }
}
