package marcosImpl;

import java.util.List;

public interface Context {

    int getActualColumn();
    int getActualRow();
    int getInitialRow();
    int getInitialColumn();
    Range getColumnRange();
    Range getRowRange();

    void addToken(TokenMarcos tokenMarcos);

    List<TokenMarcos> getResult();

    void nextRow();

    void nextColumn();

    void restart();
}
