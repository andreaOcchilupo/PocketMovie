/**
 * Created by jedepaepe on 28/05/2018.
 */

class SaveFilmManager {
    private static final SaveFilmManager ourInstance = new SaveFilmManager();

    static SaveFilmManager getInstance() {
        return ourInstance;
    }

    private SaveFilmManager() {
    }
}
