package visualization;

public class SimulationState {
    private static SimulationState instance = null;

    public int step=1;
    public boolean isRunning=true;

    public static SimulationState getInstance(){
        if(instance == null){
            return new SimulationState();
        }
        return instance;

    }
    private SimulationState(){

    }
}
