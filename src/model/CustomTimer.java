package model;

import view.Controller;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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
                timerOb.schedule(new CustomTimerTask(_c), Calendar.getInstance().getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES));
            }else if(d == 2.0){
                timerOb.schedule(new CustomTimerTask(_c), Calendar.getInstance().getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS));
            }else if(d == 3.0){
                timerOb.schedule(new CustomTimerTask(_c), Calendar.getInstance().getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
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

    public void exit() {
        timerOb.cancel();
        timerOb.purge();
    }
}
