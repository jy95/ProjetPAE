package persistance.dal;

public interface DalServices {

  public void startTransaction();

  public void commitTransaction();

  public void rollBackTransaction();

  public void openConnection();

  public void closeConnection();

}
