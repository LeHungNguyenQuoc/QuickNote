package kn.multinote.log;

public interface IBaseLog {
	String command(String command);

	void export(String log);

	void clearLog();
}
