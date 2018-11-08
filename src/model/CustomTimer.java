package model;

import view.Controller;

import java.util.Timer;
import java.util.TimerTask;

class CustomTimerTask extends TimerTask {
    Controller _c;

    CustomTimerTask(Controller c){
        _c = c;
    }

    @Override
    public void run() {
        _c.updateNowButtonAction();
    }
}

public class CustomTimer {
    Model _m;
    Controller _c;
    Boolean running = false;
    Timer timerOb = new Timer();

    public CustomTimer(Model m, Controller c){
        _m = m;
        _c = c;
    }

    public void start(){
        stop();
        double d = _m.getRefreshRate();
        if(d > 0.0){
            if(d == 1.0){
                timerOb.scheduleAtFixedRate(new CustomTimerTask(_c), 0, 60000);//60000 for minutes
            }else if(d == 2.0){
                timerOb.scheduleAtFixedRate(new CustomTimerTask(_c), 0, 3600000); //Hour
            }else if(d == 3.0){
                timerOb.scheduleAtFixedRate(new CustomTimerTask(_c), 0, 86400000); //Day
            }
            this.running = true;
        }
    }

    public void stop(){
        if(running){
            timerOb.cancel();
            timerOb = new Timer();
            this.running = false;
        }
    }
}
