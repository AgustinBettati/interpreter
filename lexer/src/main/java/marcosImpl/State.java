package marcosImpl;


public interface State {

     State next(Character o);

    void addTokenToResult();

}
